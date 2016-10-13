package fr.istic.taa.repository;

import fr.istic.taa.domain.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Etudiant entity.
 */
@SuppressWarnings("unused")
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

    @Query(value = "select etudiant from Etudiant as etudiant where etudiant.iNe = :ine")
    Etudiant getByIne(@Param("ine") String ine);
}
