package cl.javadevs.springsecurityjwt.dtos;

//import java.util.ArrayList;
import java.util.List;

import cl.javadevs.springsecurityjwt.models.Roles;



public class DtoUsuario {

	private Long idUsuario;
	private String username;
	private String password;
	private List<Roles> roles;

	
	
	
	
	
	public DtoUsuario() {
		super();
	}

	public DtoUsuario(Long idUsuario, String username, String password, List<Roles> roles) {
		super();
		this.idUsuario = idUsuario;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}
	
	
	
	
	
	

}
