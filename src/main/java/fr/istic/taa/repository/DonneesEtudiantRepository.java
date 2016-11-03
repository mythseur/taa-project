package fr.istic.taa.repository;

import fr.istic.taa.domain.DonneesEtudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the DonneesEtudiant entity.
 */
@SuppressWarnings("unused")
public interface DonneesEtudiantRepository extends JpaRepository<DonneesEtudiant, Long> {

    @Query(value = "select donnee from DonneesEtudiant as donnee where donnee.etudiant.id = :id and donnee.datemodif = " +
        "(select max(d2.datemodif) from DonneesEtudiant as d2 where d2.etudiant.id = :id)")
    DonneesEtudiant findLastByIdEtudiant(@Param("id") Long id);

    @Query(value = "select donnee from DonneesEtudiant as donnee where donnee.etudiant.id = :id and donnee.datemodif = " +
        "(select max(d2.datemodif) from DonneesEtudiant as d2 where d2.datemodif < :date and d2.etudiant.id = :id)")
    DonneesEtudiant findLastByIdEtudiantAndDate(@Param("id") Long id, @Param("date") ZonedDateTime date);

    @Query(value = "select donnee from DonneesEtudiant as donnee where donnee.etudiant.id = :id")
    List<DonneesEtudiant> findAllByIdEtudiant(@Param("id") Long id);
}
