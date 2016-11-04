package fr.istic.taa.service.impl;

import fr.istic.taa.domain.DonneesEntreprise;
import fr.istic.taa.domain.Entreprise;
import fr.istic.taa.repository.DonneesEntrepriseRepository;
import fr.istic.taa.repository.EntrepriseRepository;
import fr.istic.taa.repository.search.EntrepriseSearchRepository;
import fr.istic.taa.service.EntrepriseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing Entreprise.
 */
@Service
@Transactional
public class EntrepriseServiceImpl implements EntrepriseService {

    private final Logger log = LoggerFactory.getLogger(EntrepriseServiceImpl.class);

    @Inject
    private EntrepriseRepository entrepriseRepository;

    @Inject
    private EntrepriseSearchRepository entrepriseSearchRepository;

    @Inject
    private DonneesEntrepriseRepository donneesEntrepriseRepository;

    /**
     * Save a entreprise.
     *
     * @param entreprise the entity to save
     * @return the persisted entity
     */
    public Entreprise save(Entreprise entreprise) {
        log.debug("Request to save Entreprise : {}", entreprise);
        Entreprise entRes = entrepriseRepository.save(entreprise);
        entrepriseSearchRepository.save(entRes);
        return entRes;
    }


    /**
     * Get all the entreprises.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Entreprise> findAll() {
        log.debug("Request to get all Entreprises");
        return entrepriseRepository.findAll();
    }

    /**
     * Get one entreprise by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Entreprise findOne(Long id) {
        log.debug("Request to get Entreprise : {}", id);
        Entreprise entreprise = entrepriseRepository.findOne(id);
        return Optional.ofNullable(entreprise)
            .orElse(null);
    }

    /**
     * Delete the  entreprise by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Entreprise : {}", id);
        for (DonneesEntreprise donnees : donneesEntrepriseRepository.findAllByIdEntreprise(id)) {
            donneesEntrepriseRepository.delete(donnees);
        }
        entrepriseRepository.delete(id);
        entrepriseSearchRepository.delete(id);
    }

    /**
     * Search for the entreprise corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Entreprise> search(String query) {
        log.debug("Request to search Entreprises for query {}", query);
        return StreamSupport
            .stream(entrepriseSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());

    }
}
