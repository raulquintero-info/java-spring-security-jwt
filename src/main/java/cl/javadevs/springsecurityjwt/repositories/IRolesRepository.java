package cl.javadevs.springsecurityjwt.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.javadevs.springsecurityjwt.models.Roles;

@Repository
public interface IRolesRepository extends JpaRepository<Roles, Long> {

	Optional<Roles> findByName(String name);
}
