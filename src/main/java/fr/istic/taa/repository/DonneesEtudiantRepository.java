package fr.istic.taa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.istic.taa.domain.DonneesEtudiant;

/**
 * Spring Data JPA repository for the DonneesEtudiant entity.
 */
@SuppressWarnings("unused")
public interface DonneesEtudiantRepository extends JpaRepository<DonneesEtudiant, Long> {

    @Query(value = "select donnee from DonneesEtudiant as donnee where donnee.etudiant.id = :id and donnee.datemodif = (select max(d2.datemodif) from DonneesEtudiant as d2 where d2.etudiant.id = :id)")
    DonneesEtudiant findLastByIdEtudiant(@Param("id") Long id);
}
