package fr.istic.taa.service.impl;

import fr.istic.taa.domain.Enseignant;
import fr.istic.taa.repository.EnseignantRepository;
import fr.istic.taa.repository.search.EnseignantSearchRepository;
import fr.istic.taa.service.EnseignantService;
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
 * Service Implementation for managing Enseignant.
 */
@Service
@Transactional
public class EnseignantServiceImpl implements EnseignantService {

    private final Logger log = LoggerFactory.getLogger(EnseignantServiceImpl.class);

    @Inject
    private EnseignantRepository enseignantRepository;

    @Inject
    private EnseignantSearchRepository enseignantSearchRepository;

    /**
     * Save a enseignant.
     *
     * @param enseignant the entity to save
     * @return the persisted entity
     */
    public Enseignant save(Enseignant enseignant) {
        log.debug("Request to save Enseignant : {}", enseignant);
        Enseignant result = enseignantRepository.save(enseignant);
        enseignantSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the enseignants.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Enseignant> findAll() {
        log.debug("Request to get all Enseignants");
        List<Enseignant> result = enseignantRepository.findAll();

        return result;
    }

    /**
     * Get one enseignant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Enseignant findOne(Long id) {
        log.debug("Request to get Enseignant : {}", id);
        Enseignant enseignant = enseignantRepository.findOne(id);
        return enseignant;
    }

    /**
     * Delete the  enseignant by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Enseignant : {}", id);
        enseignantRepository.delete(id);
        enseignantSearchRepository.delete(id);
    }

    /**
     * Search for the enseignant corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Enseignant> search(String query) {
        log.debug("Request to search Enseignants for query {}", query);
        return StreamSupport
            .stream(enseignantSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
