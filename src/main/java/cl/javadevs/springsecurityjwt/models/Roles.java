package cl.javadevs.springsecurityjwt.models;

import jakarta.persistence.*;


@Entity
@Table(name="role")
public class Roles {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_role")
	private Long idRole;
	private String name;
	
	
	
	
	public Roles() {
		super();
	}
	
	public Roles(Long idRole, String name) {
		super();
		this.idRole = idRole;
		this.name = name;
	}
	
	public Long getIdRole() {
		return idRole;
	}
	public void setIdRole(Long idRole) {
		this.idRole = idRole;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
