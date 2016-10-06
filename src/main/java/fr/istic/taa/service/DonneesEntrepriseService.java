package fr.istic.taa.service;

import java.util.List;

import fr.istic.taa.domain.DonneesEntreprise;

/**
 * Service Interface for managing DonneesEntreprise.
 */
public interface DonneesEntrepriseService {

    /**
     * Save a donneesEntreprise.
     *
     * @param donneesEntreprise the entity to save
     * @return the persisted entity
     */
    DonneesEntreprise save(DonneesEntreprise donneesEntreprise);

    /**
     * Get all the donneesEntreprises.
     *
     * @return the list of entities
     */
    List<DonneesEntreprise> findAll();

    /**
     * Get the "id" donneesEntreprise.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DonneesEntreprise findOne(Long id);

    /**
     * Delete the "id" donneesEntreprise.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the donneesEntreprise corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    List<DonneesEntreprise> search(String query);

    DonneesEntreprise findLastByIdEntreprise(Long id);
}
