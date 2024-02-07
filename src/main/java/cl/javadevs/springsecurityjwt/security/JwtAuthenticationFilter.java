package cl.javadevs.springsecurityjwt.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


// la funcion de esta clase sera validar la informacion de este token y si esto es exitoso , establecera la autenticacion de un usuario en la solicitud
// o en el contexto de seguridad de nuestgra aplicacions
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private CustomUsersDetailsService customUsersDetailsService;
	
	@Autowired
	private JwtGenerador jwtGenerador;
	
	private String obtenerTokenDeSolicitud(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
			// si encuentra el token JWT, se devuelve una subcadena de "bearerRoken" que comienza despues de los primeros 7 caracteres hasta el final de la cadena
			return bearerToken.substring(7, bearerToken.length());
		}
		
		return null;
	}
	
	@Override						//Solicitud entrante
	protected void doFilterInternal(HttpServletRequest request,
									//Respuesta Saliente
									HttpServletResponse response,
									// mecanismo para invocar el siguiente filtro en la siguiente cadena de filtros
									FilterChain filterChain)
			throws ServletException, IOException {
		
		// obtenemos los ddatos del token
		String token = obtenerTokenDeSolicitud(request);
		if(StringUtils.hasText(token) && jwtGenerador.validarToken(token)) {
			//asignamos el nombre de usuario contenido en el objeto "token" y lo pasamos a la variable "usernmae"
			String username = jwtGenerador.obtenerUsernameDeJwt(token);
			//luego creamos el objeto userDetails el cual contendra todos los detalles de nuestro username, osea nombre,passw, y roles segun el metodo loadUserByUsername
			UserDetails userDetails = customUsersDetailsService.loadUserByUsername(username);
			//cargamos una lista de string con los roles alojados en BD
			List<String> userRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
			// comprobamos que el usuario autenticado posse alguno de los siguientes roles alojados en BD
			if (userRoles.contains("USER") || userRoles.contains("ADMIN")) {
				// creamos el objeto UsernamePasswordAuthenticationToken el cual contendralos detalles de autenticacion del usuario
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				////aca establecimos informacion adicional de la autenticacion, como por ejemplo, la direccion ip del usuario o el agente de usuario para hacer la solicitud, etc.
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				//establecemos el objeto anterior(autenticacion del usuario) en el contexto de seguridad
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		filterChain.doFilter(request,  response);
	}

}
