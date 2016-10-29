package fr.istic.taa.service;

import java.util.List;

import fr.istic.taa.dto.StageIHM;

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
    StageIHM save(StageIHM stage);

    /**
     * Get all the stages.
     *
     * @return the list of entities
     */
    List<StageIHM> findAll();

    /**
     * Get the "id" stage.
     *
     * @param id the id of the entity
     * @return the entity
     */
    StageIHM findOne(Long id);

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
    List<StageIHM> search(String query);

    /**
     * Get the stages for etudiant "id"
     *
     * @param id the id of the etudiant
     * @return the list of entities
     */
    List<StageIHM> findAllByEtudiant(Long id);

    /**
     * Get the stages for entreprise "id"
     *
     * @param id the id of the entreprise
     * @return the list of entities
     */
    List<StageIHM> findAllByEntreprise(Long id);

    /**
     * Get the stages for enseignant "id"
     *
     * @param id the id of the enseignant
     * @return the list of entities
     */
    List<StageIHM> findAllByEnseignant(Long id);

    /**
     * Get the stages for contact "id"
     *
     * @param id the id of the contact
     * @return the list of entities
     */
    List<StageIHM> findAllByContact(Long id);
}
