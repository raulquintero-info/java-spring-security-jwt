package cl.javadevs.springsecurityjwt.repositories;

import org.springframework.stereotype.Repository;

import cl.javadevs.springsecurityjwt.models.Usuarios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



@Repository
public interface IUsuariosRepository  extends JpaRepository<Usuarios, Long>{

	Optional<Usuarios> findByUsername(String username);
	
	Boolean existsByUsername(String username);
}
