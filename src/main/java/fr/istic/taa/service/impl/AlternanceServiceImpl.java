package fr.istic.taa.service.impl;

import fr.istic.taa.domain.Alternance;
import fr.istic.taa.repository.AlternanceRepository;
import fr.istic.taa.repository.search.AlternanceSearchRepository;
import fr.istic.taa.service.AlternanceService;
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
 * Service Implementation for managing Alternance.
 */
@Service
@Transactional
public class AlternanceServiceImpl implements AlternanceService {

    private final Logger log = LoggerFactory.getLogger(AlternanceServiceImpl.class);

    @Inject
    private AlternanceRepository alternanceRepository;

    @Inject
    private AlternanceSearchRepository alternanceSearchRepository;

    /**
     * Save a alternance.
     *
     * @param alternance the entity to save
     * @return the persisted entity
     */
    public Alternance save(Alternance alternance) {
        log.debug("Request to save Alternance : {}", alternance);
        Alternance result = alternanceRepository.save(alternance);
        alternanceSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the alternances.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Alternance> findAll() {
        log.debug("Request to get all Alternances");
        List<Alternance> result = alternanceRepository.findAll();

        return result;
    }

    /**
     * Get one alternance by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Alternance findOne(Long id) {
        log.debug("Request to get Alternance : {}", id);
        Alternance alternance = alternanceRepository.findOne(id);
        return alternance;
    }

    /**
     * Delete the  alternance by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Alternance : {}", id);
        alternanceRepository.delete(id);
        alternanceSearchRepository.delete(id);
    }

    /**
     * Search for the alternance corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Alternance> search(String query) {
        log.debug("Request to search Alternances for query {}", query);
        return StreamSupport
            .stream(alternanceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
