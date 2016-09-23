package fr.istic.taa.repository;

import fr.istic.taa.domain.Alternance;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Alternance entity.
 */
@SuppressWarnings("unused")
public interface AlternanceRepository extends JpaRepository<Alternance, Long> {

}
