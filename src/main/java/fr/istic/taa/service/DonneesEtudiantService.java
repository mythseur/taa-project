package fr.istic.taa.service;

import java.util.List;

import fr.istic.taa.domain.DonneesEtudiant;

/**
 * Service Interface for managing DonneesEtudiant.
 */
public interface DonneesEtudiantService {

    /**
     * Save a donneesEtudiant.
     *
     * @param donneesEtudiant the entity to save
     * @return the persisted entity
     */
    DonneesEtudiant save(DonneesEtudiant donneesEtudiant);

    /**
     * Get all the donneesEtudiants.
     *
     * @return the list of entities
     */
    List<DonneesEtudiant> findAll();

    /**
     * Get the "id" donneesEtudiant.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DonneesEtudiant findOne(Long id);

    /**
     * Delete the "id" donneesEtudiant.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the donneesEtudiant corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    List<DonneesEtudiant> search(String query);

    DonneesEtudiant findLastByIdEtudiant(Long id);
}
