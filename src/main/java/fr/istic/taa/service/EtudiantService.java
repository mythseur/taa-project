package fr.istic.taa.service;

import fr.istic.taa.domain.Etudiant;

import java.util.List;

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
    Etudiant save(Etudiant etudiant);

    /**
     * Get all the etudiants.
     *
     * @return the list of entities
     */
    List<Etudiant> findAll();

    /**
     * Get the "id" etudiant.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Etudiant findOne(Long id);

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
    List<Etudiant> search(String query);
}
