package fr.istic.taa.service;

import java.util.List;

import fr.istic.taa.domain.Stage;

/**
 * Service Interface for managing Stage.
 */
public interface StageService {

    /**
     * Save a stage.
     *
     * @param stage the entity to save
     * @return the persisted entity
     */
    Stage save(Stage stage);

    /**
     * Get all the stages.
     *
     * @return the list of entities
     */
    List<Stage> findAll();

    /**
     * Get the "id" stage.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Stage findOne(Long id);

    /**
     * Delete the "id" stage.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the stage corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    List<Stage> search(String query);

    /**
     * Get the stages for etudiant "id"
     *
     * @param id the id of the etudiant
     * @return the list of entities
     */
    List<Stage> findAllByEtudiant(Long id);

    /**
     * Get the stages for entreprise "id"
     *
     * @param id the id of the entreprise
     * @return the list of entities
     */
    List<Stage> findAllByEntreprise(Long id);
}
