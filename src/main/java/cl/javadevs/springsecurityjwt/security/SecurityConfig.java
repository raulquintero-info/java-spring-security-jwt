package cl.javadevs.springsecurityjwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//le indica al contenedor de spring que esta es una clase de seguridad al momento de arrancar la aplicacion
@Configuration
//indicamos que se activa la seguridad web en nuestra aplicacion y ademas esta sera una clase,  la cual contendra toda la configuracion referente a la seguridad
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	
	// este bean va a encargarse de verificar la informacion de los usuarios que se loguearan en nuestar api
	@Bean
	AuthenticationManager authenticationManager( AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	//Con este dean nos encargaremos de encriptar todas nuestras contraseñas
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	//Este bean incorpora el filtro de seguridad de json web token que creamos en nuestra clase anterior
	@Bean
	JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	
	// Vamos a crear un bean el cual va a establecer una cadena de filtros de seguridad en nuestra aplicacion, 
	// y es aqui donde aplicaremos los permisos segun los reles de ussuarios para acceder a nuestra aplicacion
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
			.csrf().disable()
			.exceptionHandling() //permitimos el manejo de excepciones
			.authenticationEntryPoint(jwtAuthenticationEntryPoint) //nos establece un punto de entrada personalizado de autenticacion para el manejo de autenticacion no autorizadas
			.and()// concatenar
			.sessionManagement()  //permitela gestion de sesiones
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeHttpRequests() //toda peticion http debe ser autorizada
			.requestMatchers("/api/auth/**").permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
		
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
}
