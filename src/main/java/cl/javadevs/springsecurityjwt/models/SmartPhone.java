package cl.javadevs.springsecurityjwt.models;

import jakarta.persistence.*;

@Entity
public class SmartPhone {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "id_smartphone")
	private Long idSmartPhone;
	private String marca;
	private Long precio;
	
	
	
	
	public SmartPhone() {
		super();
	}




	public SmartPhone(Long idSmartPhone, String marca, Long precio) {
		super();
		this.idSmartPhone = idSmartPhone;
		this.marca = marca;
		this.precio = precio;
	}




	public Long getIdSmartPhone() {
		return idSmartPhone;
	}




	public void setIdSmartPhone(Long idSmartPhone) {
		this.idSmartPhone = idSmartPhone;
	}




	public String getMarca() {
		return marca;
	}




	public void setMarca(String marca) {
		this.marca = marca;
	}




	public Long getPrecio() {
		return precio;
	}




	public void setPrecio(Long precio) {
		this.precio = precio;
	}
	
	
	
	
}
