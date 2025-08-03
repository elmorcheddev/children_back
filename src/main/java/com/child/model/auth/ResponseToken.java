package com.child.model.auth;

import com.child.model.Utilisateur;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class ResponseToken {

	private String token;
	private Utilisateur utilisateur;
}
