package fr.istic.taa.service;

import java.time.ZonedDateTime;
import java.util.List;

import fr.istic.taa.dto.EntrepriseIHM;

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
    EntrepriseIHM save(EntrepriseIHM entreprise);

    /**
     * Get all the entreprises.
     *
     * @return the list of entities
     */
    List<EntrepriseIHM> findAll();

    /**
     * Get the "id" entreprise.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EntrepriseIHM findOne(Long id);

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
    List<EntrepriseIHM> search(String query);

    /**
     * Get the "id" entreprise with its data from the given "date"
     *
     * @param id the id of the entity
     * @param date the date of the data
     * @return the entity
     */
    EntrepriseIHM findOneByDate(Long id, ZonedDateTime date);
}
