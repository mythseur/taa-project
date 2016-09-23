package fr.istic.taa.service.impl;

import fr.istic.taa.domain.Diplome;
import fr.istic.taa.repository.DiplomeRepository;
import fr.istic.taa.repository.search.DiplomeSearchRepository;
import fr.istic.taa.service.DiplomeService;
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
 * Service Implementation for managing Diplome.
 */
@Service
@Transactional
public class DiplomeServiceImpl implements DiplomeService {

    private final Logger log = LoggerFactory.getLogger(DiplomeServiceImpl.class);

    @Inject
    private DiplomeRepository diplomeRepository;

    @Inject
    private DiplomeSearchRepository diplomeSearchRepository;

    /**
     * Save a diplome.
     *
     * @param diplome the entity to save
     * @return the persisted entity
     */
    public Diplome save(Diplome diplome) {
        log.debug("Request to save Diplome : {}", diplome);
        Diplome result = diplomeRepository.save(diplome);
        diplomeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the diplomes.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Diplome> findAll() {
        log.debug("Request to get all Diplomes");
        List<Diplome> result = diplomeRepository.findAll();

        return result;
    }

    /**
     * Get one diplome by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Diplome findOne(Long id) {
        log.debug("Request to get Diplome : {}", id);
        Diplome diplome = diplomeRepository.findOne(id);
        return diplome;
    }

    /**
     * Delete the  diplome by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Diplome : {}", id);
        diplomeRepository.delete(id);
        diplomeSearchRepository.delete(id);
    }

    /**
     * Search for the diplome corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Diplome> search(String query) {
        log.debug("Request to search Diplomes for query {}", query);
        return StreamSupport
            .stream(diplomeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
