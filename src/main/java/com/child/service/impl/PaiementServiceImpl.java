package com.child.service.impl;

import com.child.model.Enfant;
import com.child.model.Parent;
import com.child.model.Paiement;
import com.child.repo.EnfantRepo;
import com.child.repo.ParentRepo;
import com.child.repo.PaiementRepository;
import com.child.service.PaiementService;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements PaiementService {

    private final ParentRepo parentRepo;
    private final EnfantRepo enfantRepo;
    private final PaiementRepository paiementRepository;
    private final JavaMailSender mailSender;

    @Override
    public Paiement createPaiement(Long parentId, Long enfantId, Double montant, String mois, String modePaiement) {
        Parent parent = parentRepo.findById(parentId)
                .orElseThrow(() -> new EntityNotFoundException("Parent not found with id: " + parentId));
        Enfant enfant = enfantRepo.findById(enfantId)
                .orElseThrow(() -> new EntityNotFoundException("Enfant not found with id: " + enfantId));

        Paiement paiement = Paiement.builder()
                .parent(parent)
                .enfant(enfant)
                .montant(montant)
                .mois(mois)
                .modePaiement(modePaiement)
                .datePaiement(LocalDate.now())
                .build();

        // Générer PDF
        byte[] pdf = generateReceiptPDF(parent, enfant, paiement);
        paiement.setRecuPdf(pdf);

        // Marquer l'enfant comme payé
        enfant.setPayee(true);
        enfantRepo.save(enfant);

        Paiement savedPaiement = paiementRepository.save(paiement);

        // Envoyer email avec PDF
        sendPaiementEmail(parent, savedPaiement);

        // Envoyer SMS via Email-to-SMS
        String smsText = "Bonjour " + parent.getNom() +
                ", le paiement de " + paiement.getMontant() + " TND pour " +
                enfant.getNom() + " " + enfant.getPrenom() + " a été effectué avec succès.";
        //sendSmsViaEmail(parent, smsText);

        return savedPaiement;
    }

    private void sendPaiementEmail(Parent parent, Paiement paiement) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(parent.getEmail());
            helper.setSubject("Confirmation de paiement pour " + paiement.getEnfant().getNom());
            helper.setText(
                    "Bonjour " + parent.getNom() + ",\n\n" +
                            "Le paiement pour " + paiement.getEnfant().getNom() + " " + paiement.getEnfant().getPrenom() +
                            " a été effectué avec succès.\n" +
                            "Montant: " + paiement.getMontant() + " TND\n" +
                            "Mois: " + paiement.getMois() + "\n\n" +
                            "Vous trouverez ci-joint le reçu PDF.\n\n" +
                            "Merci pour votre confiance."
            );

            helper.addAttachment("recu-" + paiement.getId() + ".pdf", new ByteArrayResource(paiement.getRecuPdf()));

            mailSender.send(message);
            System.out.println("✅ Email envoyé à " + parent.getEmail());
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'envoi de l'email");
        }
    }

    private void sendSmsViaEmail(Parent parent, String messageBody) {
        try {
            MimeMessage smsMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(smsMessage, false);

            // A adapter selon l'opérateur du parent
            String smsEmail = parent.getTelPrincipal() + "@sms.ooredoo.tn"; // Ex: Orange TN
            // Pour Ooredoo : "@sms.ooredoo.tn", Tunisie Telecom : "@sms.tn"

            helper.setTo(smsEmail);
            helper.setSubject(""); // pas de sujet pour SMS
            helper.setText(messageBody);

            mailSender.send(smsMessage);
            System.out.println("✅ SMS envoyé via Email-to-SMS à " + parent.getTelPrincipal());
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'envoi du SMS via Email-to-SMS");
        }
    }

    // ================== PDF ===================
    private byte[] generateReceiptPDF(Parent parent, Enfant enfant, Paiement paiement) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Logo
            String logoPath = "C:/recu_paiement/logo.png";
            if (new File(logoPath).exists()) {
                ImageData logoData = ImageDataFactory.create(logoPath);
                com.itextpdf.layout.element.Image logo = new com.itextpdf.layout.element.Image(logoData)
                        .scaleAbsolute(80, 80)
                        .setFixedPosition(50, 750);
                document.add(logo);
            }

            Paragraph title = new Paragraph("REÇU DE PAIEMENT")
                    .setFontSize(20)
                    .setBold()
                    .setFontColor(new DeviceRgb(33, 66, 99))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(30);
            document.add(title);

            float[] columnWidths = {220f, 300f};
            Table table = new Table(columnWidths).setWidth(UnitValue.createPercentValue(100));

            table.addCell(makeCell("Nom et prénom de l’enfant :", true));
            table.addCell(makeCell(enfant.getNom() + " " + enfant.getPrenom(), false));

            table.addCell(makeCell("Classe :", true));
            table.addCell(makeCell(enfant.getClasse().getNomClass(), false));

            table.addCell(makeCell("Numéro de téléphone du parent :", true));
            table.addCell(makeCell(parent.getTelPrincipal(), false));

            table.addCell(makeCell("Mode de paiement :", true));
            table.addCell(makeCell(paiement.getModePaiement(), false));

            table.addCell(makeCell("Date du paiement :", true));
            table.addCell(makeCell(paiement.getDatePaiement().toString(), false));

            table.addCell(makeCell("Montant :", true));
            table.addCell(makeCell(paiement.getMontant() + " TND", false));

            document.add(table.setMarginBottom(30));

            Paragraph confirmation = new Paragraph(
                    "Nous confirmons avoir reçu la somme de " + paiement.getMontant() +
                            " dinars pour le mois de " + paiement.getMois() + "."
            ).setFontSize(12).setMarginBottom(20);
            document.add(confirmation);

            document.add(new Paragraph("Merci pour votre confiance.")
                    .setBold()
                    .setFontColor(new DeviceRgb(80, 80, 80))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(12)
                    .setMarginBottom(40));

            Table signatureTable = new Table(UnitValue.createPercentArray(new float[]{1, 1})).useAllAvailableWidth();
            signatureTable.addCell(new Cell().add(new Paragraph("Signature :"))
                    .setBorder(com.itextpdf.layout.borders.Border.NO_BORDER));
            signatureTable.addCell(new Cell().add(new Paragraph("La Direction"))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(com.itextpdf.layout.borders.Border.NO_BORDER));

            document.add(signatureTable);
            document.close();

            System.out.println("✅ PDF reçu généré en mémoire");
            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur génération reçu PDF");
        }
    }

    private Cell makeCell(String text, boolean bold) {
        Paragraph p = new Paragraph(text);
        if (bold) p.setBold();
        return new Cell().add(p)
                .setBorder(com.itextpdf.layout.borders.Border.NO_BORDER)
                .setPadding(5);
    }

    // ================== Autres méthodes ===================
    @Override
    public Paiement getById(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paiement not found with id: " + id));
    }

    @Override
    public List<Paiement> getByParent(Long parentId) {
        Parent parent = parentRepo.findById(parentId)
                .orElseThrow(() -> new EntityNotFoundException("Parent not found with id: " + parentId));
        return paiementRepository.findByParent(parent);
    }

    @Override
    public Paiement getByEnfant(Long enfantId) {
        Enfant enfant = enfantRepo.findById(enfantId)
                .orElseThrow(() -> new EntityNotFoundException("Enfant not found with id: " + enfantId));
        return paiementRepository.findByEnfant(enfant);
    }

    @Override
    public List<Paiement> getAll() {
        return paiementRepository.findAll();
    }

    @Override
    public byte[] getRecuPdf(Long paiementId) {
        Paiement paiement = getById(paiementId);
        System.out.print(paiementId);
        if (paiement.getRecuPdf() == null) {
            throw new EntityNotFoundException("Reçu PDF non disponible pour le paiement " + paiementId);
        }
        return paiement.getRecuPdf();
    }

}
