package fr.istic.taa.service.impl;

import fr.istic.taa.domain.Filiere;
import fr.istic.taa.repository.FiliereRepository;
import fr.istic.taa.repository.search.FiliereSearchRepository;
import fr.istic.taa.service.FiliereService;
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
 * Service Implementation for managing Filiere.
 */
@Service
@Transactional
public class FiliereServiceImpl implements FiliereService {

    private final Logger log = LoggerFactory.getLogger(FiliereServiceImpl.class);

    @Inject
    private FiliereRepository filiereRepository;

    @Inject
    private FiliereSearchRepository filiereSearchRepository;

    /**
     * Save a filiere.
     *
     * @param filiere the entity to save
     * @return the persisted entity
     */
    public Filiere save(Filiere filiere) {
        log.debug("Request to save Filiere : {}", filiere);
        Filiere result = filiereRepository.save(filiere);
        filiereSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the filieres.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Filiere> findAll() {
        log.debug("Request to get all Filieres");
        List<Filiere> result = filiereRepository.findAll();

        return result;
    }

    /**
     * Get one filiere by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Filiere findOne(Long id) {
        log.debug("Request to get Filiere : {}", id);
        Filiere filiere = filiereRepository.findOne(id);
        return filiere;
    }

    /**
     * Delete the  filiere by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Filiere : {}", id);
        filiereRepository.delete(id);
        filiereSearchRepository.delete(id);
    }

    /**
     * Search for the filiere corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Filiere> search(String query) {
        log.debug("Request to search Filieres for query {}", query);
        return StreamSupport
            .stream(filiereSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
