package cl.javadevs.springsecurityjwt.security;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTTokenProvider {

	
	public String generarToken(Authentication authentication) {
		String username = authentication.getName();
		Date tiempoActual = new Date();
		Date expirationToken = new Date(tiempoActual.getTime() + ConstantesSeguridad.JWT_EXPIRATION_TOKEN);
		
		String token = Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(expirationToken)
				.signWith(SignatureAlgorithm.HS512,ConstantesSeguridad.JWT_FIRMA)
				.compact();
		
		return token;
	}
	
	
	public String obtenerUsernameDeJwt(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(ConstantesSeguridad.JWT_FIRMA)
				.parseClaimsJws(token)
				.getBody();
		 
		return claims.getSubject();
	}
	
	
	public Boolean validarToken(String token) {
		try {
			Jwts.parser().setSigningKey(ConstantesSeguridad.JWT_FIRMA).parseClaimsJws(token);
			return true;
		} catch(Exception e) {
			throw new AuthenticationCredentialsNotFoundException("Jwt ha expirado o esta incorrecto");
		}
	}
	
	
	
	
}
