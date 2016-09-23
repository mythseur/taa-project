package fr.istic.taa.service;

import fr.istic.taa.domain.Enquete;

import java.util.List;

/**
 * Service Interface for managing Enquete.
 */
public interface EnqueteService {

    /**
     * Save a enquete.
     *
     * @param enquete the entity to save
     * @return the persisted entity
     */
    Enquete save(Enquete enquete);

    /**
     * Get all the enquetes.
     *
     * @return the list of entities
     */
    List<Enquete> findAll();

    /**
     * Get the "id" enquete.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Enquete findOne(Long id);

    /**
     * Delete the "id" enquete.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the enquete corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    List<Enquete> search(String query);
}
