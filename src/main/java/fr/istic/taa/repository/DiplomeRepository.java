package fr.istic.taa.repository;

import fr.istic.taa.domain.Diplome;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Diplome entity.
 */
@SuppressWarnings("unused")
public interface DiplomeRepository extends JpaRepository<Diplome, Long> {

}
