package fr.istic.taa.service.impl;

import fr.istic.taa.domain.EtudiantDiplome;
import fr.istic.taa.repository.EtudiantDiplomeRepository;
import fr.istic.taa.repository.search.EtudiantDiplomeSearchRepository;
import fr.istic.taa.service.EtudiantDiplomeService;
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
 * Service Implementation for managing EtudiantDiplome.
 */
@Service
@Transactional
public class EtudiantDiplomeServiceImpl implements EtudiantDiplomeService {

    private final Logger log = LoggerFactory.getLogger(EtudiantDiplomeServiceImpl.class);

    @Inject
    private EtudiantDiplomeRepository etudiantDiplomeRepository;

    @Inject
    private EtudiantDiplomeSearchRepository etudiantDiplomeSearchRepository;

    /**
     * Save a etudiantDiplome.
     *
     * @param etudiantDiplome the entity to save
     * @return the persisted entity
     */
    public EtudiantDiplome save(EtudiantDiplome etudiantDiplome) {
        log.debug("Request to save EtudiantDiplome : {}", etudiantDiplome);
        EtudiantDiplome result = etudiantDiplomeRepository.save(etudiantDiplome);
        etudiantDiplomeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the etudiantDiplomes.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<EtudiantDiplome> findAll() {
        log.debug("Request to get all EtudiantDiplomes");
        List<EtudiantDiplome> result = etudiantDiplomeRepository.findAll();

        return result;
    }

    /**
     * Get one etudiantDiplome by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public EtudiantDiplome findOne(Long id) {
        log.debug("Request to get EtudiantDiplome : {}", id);
        EtudiantDiplome etudiantDiplome = etudiantDiplomeRepository.findOne(id);
        return etudiantDiplome;
    }

    /**
     * Delete the  etudiantDiplome by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EtudiantDiplome : {}", id);
        etudiantDiplomeRepository.delete(id);
        etudiantDiplomeSearchRepository.delete(id);
    }

    /**
     * Search for the etudiantDiplome corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<EtudiantDiplome> search(String query) {
        log.debug("Request to search EtudiantDiplomes for query {}", query);
        return StreamSupport
            .stream(etudiantDiplomeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
