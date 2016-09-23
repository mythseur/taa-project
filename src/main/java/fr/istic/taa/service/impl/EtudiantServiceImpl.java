package fr.istic.taa.service.impl;

import fr.istic.taa.domain.Etudiant;
import fr.istic.taa.repository.EtudiantRepository;
import fr.istic.taa.repository.search.EtudiantSearchRepository;
import fr.istic.taa.service.EtudiantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing Etudiant.
 */
@Service
@Transactional
public class EtudiantServiceImpl implements EtudiantService {

    private final Logger log = LoggerFactory.getLogger(EtudiantServiceImpl.class);

    @Inject
    private EtudiantRepository etudiantRepository;

    @Inject
    private EtudiantSearchRepository etudiantSearchRepository;

    /**
     * Save a etudiant.
     *
     * @param etudiant the entity to save
     * @return the persisted entity
     */
    public Etudiant save(Etudiant etudiant) {
        log.debug("Request to save Etudiant : {}", etudiant);
        Etudiant result = etudiantRepository.save(etudiant);
        etudiantSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the etudiants.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Etudiant> findAll() {
        log.debug("Request to get all Etudiants");
        List<Etudiant> result = etudiantRepository.findAll();

        return result;
    }

    /**
     * Get one etudiant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Etudiant findOne(Long id) {
        log.debug("Request to get Etudiant : {}", id);
        Etudiant etudiant = etudiantRepository.findOne(id);
        return etudiant;
    }

    /**
     * Delete the  etudiant by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Etudiant : {}", id);
        etudiantRepository.delete(id);
        etudiantSearchRepository.delete(id);
    }

    /**
     * Search for the etudiant corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Etudiant> search(String query) {
        log.debug("Request to search Etudiants for query {}", query);
        return StreamSupport
            .stream(etudiantSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
