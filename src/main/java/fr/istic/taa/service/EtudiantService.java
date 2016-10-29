package fr.istic.taa.service;

import java.time.ZonedDateTime;
import java.util.List;

import fr.istic.taa.dto.EtudiantIHM;

/**
 * Service Interface for managing Etudiant.
 */
public interface EtudiantService {

    /**
     * Save a etudiant.
     *
     * @param etudiant the entity to save
     * @return the persisted entity
     */
    EtudiantIHM save(EtudiantIHM etudiant);

    /**
     * Get all the etudiants.
     *
     * @return the list of entities
     */
    List<EtudiantIHM> findAll();

    /**
     * Get the "id" etudiant.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EtudiantIHM findOne(Long id);

    /**
     * Delete the "id" etudiant.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the etudiant corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    List<EtudiantIHM> search(String query);

    /**
     * Get the etudiant with ine "ine".
     *
     * @param ine the ine of the entity
     * @return the entity
     */
    EtudiantIHM getByIne(String ine);

    /**
     * Get the "id" etudiant with its data from the given "date"
     *
     * @param id the id of the entity
     * @param date the date of the data
     * @return the entity
     */
    EtudiantIHM findOneByDate(Long id, ZonedDateTime date);
}
