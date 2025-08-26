package com.child.configuration;

 import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

  import com.child.model.Utilisateur;
import com.child.repo.UtilisateurRepo;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    
    @Autowired
    private UtilisateurRepo utilisateurRepo;
        
    @Override

        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Utilisateur utilisateur = utilisateurRepo.findByEmail(username);

            if (utilisateur == null) {
                throw new UsernameNotFoundException("Utilisateur not found: " + username);
            }

            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + utilisateur.getRole().getNomRoles());
            return new User(utilisateur.getEmail(), utilisateur.getPassword(), Collections.singletonList(authority));
        }

    }
