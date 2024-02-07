package cl.javadevs.springsecurityjwt.controllers;

import java.security.Principal;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.javadevs.springsecurityjwt.dtos.DtoAuthRespuesta;
import cl.javadevs.springsecurityjwt.dtos.DtoLogin;
import cl.javadevs.springsecurityjwt.dtos.DtoRegistro;
import cl.javadevs.springsecurityjwt.dtos.DtoUsuario;
import cl.javadevs.springsecurityjwt.models.Roles;
import cl.javadevs.springsecurityjwt.models.Usuarios;
import cl.javadevs.springsecurityjwt.repositories.IRolesRepository;
import cl.javadevs.springsecurityjwt.repositories.IUsuariosRepository;
import cl.javadevs.springsecurityjwt.security.CustomUsersDetailsService;
import cl.javadevs.springsecurityjwt.security.JwtGenerador;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class RestControllerAuth {

	
	private AuthenticationManager authenticationManager;
	private PasswordEncoder passwordENcoder;
	private IRolesRepository rolesRepository;
	private IUsuariosRepository usuariosRepository;
	private JwtGenerador jwtGenerador;
	
	
	@Autowired CustomUsersDetailsService usersDetailsService;
	
	public RestControllerAuth(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, IRolesRepository rolesRepository, IUsuariosRepository usuariosRepository, JwtGenerador jwtGenerador) {
		this.authenticationManager = authenticationManager;
		this.passwordENcoder = passwordEncoder;
		this.rolesRepository = rolesRepository;
		this.usuariosRepository = usuariosRepository;
		this.jwtGenerador = jwtGenerador;
	}
	
	//Metodo para registrar usuarios con role user
	@PostMapping("register")
	public ResponseEntity<String> registrar (@RequestBody DtoRegistro dtoRegistro){
		
		
		if(usuariosRepository.existsByUsername(dtoRegistro.getUsername())) {
			return new ResponseEntity<>("el usuario ya existe, intenta con otro", HttpStatus.BAD_REQUEST);
		}
		Usuarios usuarios = new Usuarios();
		usuarios.setUsername(dtoRegistro.getUsername());
		usuarios.setPassword(passwordENcoder.encode(dtoRegistro.getPassword()));
		Roles roles = rolesRepository.findByName("USER").get();
		usuarios.setRoles(Collections.singletonList(roles));
		try {
			usuariosRepository.save(usuarios);
		}catch (Exception e) {
			System.out.println("Error al grabar el usuario nuevo");
		}
		
		
		return new ResponseEntity<>("Registro de usuario exitoso", HttpStatus.OK);
	}
	
	//Metodo para registrar usuarios con role ADMIN
		@PostMapping("register-admin")
		public ResponseEntity<String> registrarAdmin (@RequestBody DtoRegistro dtoRegistro){
			
			if(usuariosRepository.existsByUsername(dtoRegistro.getUsername())) {
				return new ResponseEntity<>("el usuario ya existe, intenta con otro", HttpStatus.BAD_REQUEST);
			}
			Usuarios usuarios = new Usuarios();
			usuarios.setUsername(dtoRegistro.getUsername());
			usuarios.setPassword(passwordENcoder.encode(dtoRegistro.getPassword()));
			Roles roles = rolesRepository.findByName("ADMIN").get();
			usuarios.setRoles(Collections.singletonList(roles));
			usuariosRepository.save(usuarios);
			
			return new ResponseEntity<>("Registro de usuario exitoso", HttpStatus.OK);
		}
	
	//Metodo para poder loggear un usuario y obtener un token
	@PostMapping("login")
	public ResponseEntity<DtoAuthRespuesta> login (@RequestBody DtoLogin dtoLogin){
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				dtoLogin.getUsername(), dtoLogin.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtGenerador.generarToken(authentication);
		
		return new ResponseEntity<>( new DtoAuthRespuesta(token), HttpStatus.OK);
	}
	
	@GetMapping("current-user")
	public UserDetails obtenerUsuario(Principal principal) {
//		System.out.println("usuario actual >>>>>>> " +principal.getName());
		if (principal == null) return  null;
		return  (UserDetails) this.usersDetailsService.loadUserByUsername(principal.getName());
	}
	
}
