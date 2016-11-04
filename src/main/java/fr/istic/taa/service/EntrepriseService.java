package fr.istic.taa.service;

import fr.istic.taa.domain.Entreprise;

import java.util.List;


/**
 * Service Interface for managing Entreprise.
 */
public interface EntrepriseService {

    /**
     * Save a entreprise.
     *
     * @param entreprise the entity to save
     * @return the persisted entity
     */
    Entreprise save(Entreprise entreprise);

    /**
     * Get all the entreprises.
     *
     * @return the list of entities
     */
    List<Entreprise> findAll();

    /**
     * Get the "id" entreprise.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Entreprise findOne(Long id);

    /**
     * Delete the "id" entreprise.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the entreprise corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    List<Entreprise> search(String query);

}
