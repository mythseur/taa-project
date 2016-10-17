package fr.istic.taa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import fr.istic.taa.domain.Stage;

/**
 * Spring Data JPA repository for the Stage entity.
 */
@SuppressWarnings("unused")
public interface StageRepository extends JpaRepository<Stage, Long> {

    @Query(value = "select stage from Stage as stage where stage.etudiant.id = :id")
    List<Stage> findAllByEtudiant(@Param("id") Long id);

    @Query(value = "select stage from Stage as stage where stage.entreprise.id = :id")
    List<Stage> findAllByEntreprise(@Param("id") Long id);

    @Query(value = "select stage from Stage as stage where stage.referent.id = :id")
    List<Stage> findAllByEnseignant(@Param("id") Long id);

    @Query(value = "select stage from Stage as stage where stage.encadrant.id = :id or stage.responsable.id = :id")
    List<Stage> findAllByContact(@Param("id") Long id);
}
