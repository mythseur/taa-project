package fr.istic.taa.service;

import fr.istic.taa.domain.Alternance;

import java.util.List;

/**
 * Service Interface for managing Alternance.
 */
public interface AlternanceService {

    /**
     * Save a alternance.
     *
     * @param alternance the entity to save
     * @return the persisted entity
     */
    Alternance save(Alternance alternance);

    /**
     * Get all the alternances.
     *
     * @return the list of entities
     */
    List<Alternance> findAll();

    /**
     * Get the "id" alternance.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Alternance findOne(Long id);

    /**
     * Delete the "id" alternance.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the alternance corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    List<Alternance> search(String query);
}
