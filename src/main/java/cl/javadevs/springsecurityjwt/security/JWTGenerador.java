package cl.javadevs.springsecurityjwt.security;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTGenerador {

	
	public String generarToken(Authentication authentication) {
		String username = authentication.getName();
		Date tiempoActual = new Date();
		Date expirationToken = new Date(tiempoActual.getTime() + ConstantesSeguridad.JWT_EXPIRATION_TOKEN);
		
		String token = Jwts.builder()			// construimos un token JWT llamado token
				.setSubject(username)			//Aca establecemos el nombre de usuario que estamos iniciando sesion
				.setIssuedAt(new Date())		// Establecemos ka fecha de emision del token en el momento actual
				.setExpiration(expirationToken)	// establecemos la fecha de caducidad del token
				.signWith(SignatureAlgorithm.HS512,ConstantesSeguridad.JWT_FIRMA) /* utilizamos este metodo para
				nuestro token  y de esta manera evitar la manipulacion o midificacion de este */
				.compact();			// este metodo finaliza la construccion del token y lo convierte en una cadena compacta
		
		return token;
	}
	
	
	public String obtenerUsernameDeJwt(String token) {
		Claims claims = Jwts.parser() 			// el metodo parser se utliza con el fin de analizar el token
				.setSigningKey(ConstantesSeguridad.JWT_FIRMA) // Establece la clave de firma, que se utiliza para verificar la firma del token
				.parseClaimsJws(token)			//se utiliza para verificar la firma  token, apartir del string "token"
				.getBody();  	/* obtenemos el claims(cuerpo) ya verificado del token el cual contendra la informacion de
				nombre de usuario, fecha de expiracion y firma del token*/
		 
		return claims.getSubject(); // devolvemos el nombre de usuario
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
