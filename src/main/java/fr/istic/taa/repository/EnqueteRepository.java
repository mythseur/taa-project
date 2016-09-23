package fr.istic.taa.repository;

import fr.istic.taa.domain.Enquete;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Enquete entity.
 */
@SuppressWarnings("unused")
public interface EnqueteRepository extends JpaRepository<Enquete, Long> {

}
