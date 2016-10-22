package fr.istic.taa.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;

import fr.istic.taa.domain.DonneesEntreprise;
import fr.istic.taa.repository.DonneesEntrepriseRepository;
import fr.istic.taa.repository.search.DonneesEntrepriseSearchRepository;
import fr.istic.taa.service.DonneesEntrepriseService;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing DonneesEntreprise.
 */
@Service
@Transactional
public class DonneesEntrepriseServiceImpl implements DonneesEntrepriseService {

    private final Logger log = LoggerFactory.getLogger(DonneesEntrepriseServiceImpl.class);

    @Inject
    private DonneesEntrepriseRepository donneesEntrepriseRepository;

    @Inject
    private DonneesEntrepriseSearchRepository donneesEntrepriseSearchRepository;

    /**
     * Save a donneesEntreprise.
     *
     * @param donneesEntreprise the entity to save
     * @return the persisted entity
     */
    public DonneesEntreprise save(DonneesEntreprise donneesEntreprise) {
        log.debug("Request to save DonneesEntreprise : {}", donneesEntreprise);
        DonneesEntreprise result = donneesEntrepriseRepository.save(donneesEntreprise);
        donneesEntrepriseSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the donneesEntreprises.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DonneesEntreprise> findAll() {
        log.debug("Request to get all DonneesEntreprises");
        List<DonneesEntreprise> result = donneesEntrepriseRepository.findAll();

        return result;
    }

    /**
     * Get one donneesEntreprise by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DonneesEntreprise findOne(Long id) {
        log.debug("Request to get DonneesEntreprise : {}", id);
        DonneesEntreprise donneesEntreprise = donneesEntrepriseRepository.findOne(id);
        return donneesEntreprise;
    }

    /**
     * Delete the  donneesEntreprise by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DonneesEntreprise : {}", id);
        donneesEntrepriseRepository.delete(id);
        donneesEntrepriseSearchRepository.delete(id);
    }

    /**
     * Search for the donneesEntreprise corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DonneesEntreprise> search(String query) {
        log.debug("Request to search DonneesEntreprises for query {}", query);
        return StreamSupport
            .stream(donneesEntrepriseSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public DonneesEntreprise findLastByIdEntreprise(Long id) {
        log.debug("Request to get DonneesEntreprise for Entreprise : {}", id);
        DonneesEntreprise donneesEntreprise = donneesEntrepriseRepository.findLastByIdEntreprise(id);
        return donneesEntreprise;
    }

    @Override
    public DonneesEntreprise findLastByIdEntrepriseAndDate(Long id, ZonedDateTime date) {
        log.debug("Request to get DonneesEntreprise for Entreprise : {}, {}", id, date);
        DonneesEntreprise donneesEntreprise = donneesEntrepriseRepository.findLastByIdEntrepriseAndDate(id, date);
        return donneesEntreprise;
    }
}
