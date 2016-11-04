package fr.istic.taa.repository;

import fr.istic.taa.domain.DonneesEntreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the DonneesEntreprise entity.
 */
@SuppressWarnings("unused")
public interface DonneesEntrepriseRepository extends JpaRepository<DonneesEntreprise, Long> {

    @Query(value = "select donnee from DonneesEntreprise as donnee where donnee.entreprise.id = :id and donnee.datemodif = " +
        "(select max(d2.datemodif) from DonneesEntreprise as d2 where d2.entreprise.id = :id)")
    DonneesEntreprise findLastByIdEntreprise(@Param("id") Long id);

    @Query(value = "select donnee from DonneesEntreprise as donnee where donnee.entreprise.id = :id and donnee.datemodif = " +
        "(select max(d2.datemodif) from DonneesEntreprise as d2 where d2.datemodif < :date and d2.entreprise.id = :id)")
    DonneesEntreprise findLastByIdEntrepriseAndDate(@Param("id") Long id, @Param("date") ZonedDateTime date);

    @Query(value = "select donnee from DonneesEntreprise as donnee where donnee.entreprise.id = :id")
    List<DonneesEntreprise> findAllByIdEntreprise(@Param("id") Long id);
}
