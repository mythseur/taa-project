package fr.istic.taa.service;

import fr.istic.taa.domain.Enseignant;

import java.util.List;

/**
 * Service Interface for managing Enseignant.
 */
public interface EnseignantService {

    /**
     * Save a enseignant.
     *
     * @param enseignant the entity to save
     * @return the persisted entity
     */
    Enseignant save(Enseignant enseignant);

    /**
     * Get all the enseignants.
     *
     * @return the list of entities
     */
    List<Enseignant> findAll();

    /**
     * Get the "id" enseignant.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Enseignant findOne(Long id);

    /**
     * Delete the "id" enseignant.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the enseignant corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    List<Enseignant> search(String query);
}
