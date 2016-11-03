package fr.istic.taa.service.impl;

import fr.istic.taa.domain.DonneesEtudiant;
import fr.istic.taa.repository.DonneesEtudiantRepository;
import fr.istic.taa.repository.search.DonneesEtudiantSearchRepository;
import fr.istic.taa.service.DonneesEtudiantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing DonneesEtudiant.
 */
@Service
@Transactional
public class DonneesEtudiantServiceImpl implements DonneesEtudiantService {

    private final Logger log = LoggerFactory.getLogger(DonneesEtudiantServiceImpl.class);

    @Inject
    private DonneesEtudiantRepository donneesEtudiantRepository;

    @Inject
    private DonneesEtudiantSearchRepository donneesEtudiantSearchRepository;

    /**
     * Save a donneesEtudiant.
     *
     * @param donneesEtudiant the entity to save
     * @return the persisted entity
     */
    public DonneesEtudiant save(DonneesEtudiant donneesEtudiant) {
        log.debug("Request to save DonneesEtudiant : {}", donneesEtudiant);
        if (donneesEtudiant.getDatemodif() == null) {
            donneesEtudiant.setDatemodif(ZonedDateTime.now());
        }
        DonneesEtudiant result = donneesEtudiantRepository.save(donneesEtudiant);
        donneesEtudiantSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the donneesEtudiants.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DonneesEtudiant> findAll() {
        log.debug("Request to get all DonneesEtudiants");
        List<DonneesEtudiant> result = donneesEtudiantRepository.findAll();

        return result;
    }

    /**
     * Get one donneesEtudiant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DonneesEtudiant findOne(Long id) {
        log.debug("Request to get DonneesEtudiant : {}", id);
        DonneesEtudiant donneesEtudiant = donneesEtudiantRepository.findOne(id);
        return donneesEtudiant;
    }

    /**
     * Delete the  donneesEtudiant by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DonneesEtudiant : {}", id);
        donneesEtudiantRepository.delete(id);
        donneesEtudiantSearchRepository.delete(id);
    }

    /**
     * Search for the donneesEtudiant corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DonneesEtudiant> search(String query) {
        log.debug("Request to search DonneesEtudiants for query {}", query);
        return StreamSupport
            .stream(donneesEtudiantSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public DonneesEtudiant findLastByIdEtudiant(Long id) {
        log.debug("Request to get DonneesEtudiant for Etudiant : {}", id);
        DonneesEtudiant donneesEtudiant = donneesEtudiantRepository.findLastByIdEtudiant(id);
        return donneesEtudiant;
    }

    @Override
    public DonneesEtudiant findLastByIdEtudiantAndDate(Long id, ZonedDateTime date) {
        log.debug("Request to get DonneesEtudiant for Etudiant : {}, {}", id, date);
        DonneesEtudiant donneesEtudiant = donneesEtudiantRepository.findLastByIdEtudiantAndDate(id, date);
        return donneesEtudiant;
    }
}
