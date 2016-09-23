package fr.istic.taa.service.impl;

import fr.istic.taa.domain.Enquete;
import fr.istic.taa.repository.EnqueteRepository;
import fr.istic.taa.repository.search.EnqueteSearchRepository;
import fr.istic.taa.service.EnqueteService;
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
 * Service Implementation for managing Enquete.
 */
@Service
@Transactional
public class EnqueteServiceImpl implements EnqueteService {

    private final Logger log = LoggerFactory.getLogger(EnqueteServiceImpl.class);

    @Inject
    private EnqueteRepository enqueteRepository;

    @Inject
    private EnqueteSearchRepository enqueteSearchRepository;

    /**
     * Save a enquete.
     *
     * @param enquete the entity to save
     * @return the persisted entity
     */
    public Enquete save(Enquete enquete) {
        log.debug("Request to save Enquete : {}", enquete);
        Enquete result = enqueteRepository.save(enquete);
        enqueteSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the enquetes.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Enquete> findAll() {
        log.debug("Request to get all Enquetes");
        List<Enquete> result = enqueteRepository.findAll();

        return result;
    }

    /**
     * Get one enquete by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Enquete findOne(Long id) {
        log.debug("Request to get Enquete : {}", id);
        Enquete enquete = enqueteRepository.findOne(id);
        return enquete;
    }

    /**
     * Delete the  enquete by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Enquete : {}", id);
        enqueteRepository.delete(id);
        enqueteSearchRepository.delete(id);
    }

    /**
     * Search for the enquete corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Enquete> search(String query) {
        log.debug("Request to search Enquetes for query {}", query);
        return StreamSupport
            .stream(enqueteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
