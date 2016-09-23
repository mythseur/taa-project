package fr.istic.taa.repository;

import fr.istic.taa.domain.EtudiantDiplome;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the EtudiantDiplome entity.
 */
@SuppressWarnings("unused")
public interface EtudiantDiplomeRepository extends JpaRepository<EtudiantDiplome, Long> {

}
