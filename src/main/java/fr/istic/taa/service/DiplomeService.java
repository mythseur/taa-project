package fr.istic.taa.service;

import fr.istic.taa.domain.Diplome;

import java.util.List;

/**
 * Service Interface for managing Diplome.
 */
public interface DiplomeService {

    /**
     * Save a diplome.
     *
     * @param diplome the entity to save
     * @return the persisted entity
     */
    Diplome save(Diplome diplome);

    /**
     * Get all the diplomes.
     *
     * @return the list of entities
     */
    List<Diplome> findAll();

    /**
     * Get the "id" diplome.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Diplome findOne(Long id);

    /**
     * Delete the "id" diplome.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the diplome corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    List<Diplome> search(String query);
}
