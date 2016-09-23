package fr.istic.taa.service;

import fr.istic.taa.domain.Stage;

import java.util.List;

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
}
