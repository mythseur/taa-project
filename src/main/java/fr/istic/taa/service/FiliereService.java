package fr.istic.taa.service;

import fr.istic.taa.domain.Filiere;

import java.util.List;

/**
 * Service Interface for managing Filiere.
 */
public interface FiliereService {

    /**
     * Save a filiere.
     *
     * @param filiere the entity to save
     * @return the persisted entity
     */
    Filiere save(Filiere filiere);

    /**
     * Get all the filieres.
     *
     * @return the list of entities
     */
    List<Filiere> findAll();

    /**
     * Get the "id" filiere.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Filiere findOne(Long id);

    /**
     * Delete the "id" filiere.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the filiere corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    List<Filiere> search(String query);
}
