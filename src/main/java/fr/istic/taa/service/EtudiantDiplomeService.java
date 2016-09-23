package fr.istic.taa.service;

import fr.istic.taa.domain.EtudiantDiplome;

import java.util.List;

/**
 * Service Interface for managing EtudiantDiplome.
 */
public interface EtudiantDiplomeService {

    /**
     * Save a etudiantDiplome.
     *
     * @param etudiantDiplome the entity to save
     * @return the persisted entity
     */
    EtudiantDiplome save(EtudiantDiplome etudiantDiplome);

    /**
     * Get all the etudiantDiplomes.
     *
     * @return the list of entities
     */
    List<EtudiantDiplome> findAll();

    /**
     * Get the "id" etudiantDiplome.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EtudiantDiplome findOne(Long id);

    /**
     * Delete the "id" etudiantDiplome.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the etudiantDiplome corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    List<EtudiantDiplome> search(String query);
}
