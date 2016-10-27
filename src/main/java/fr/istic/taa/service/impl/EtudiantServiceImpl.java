package fr.istic.taa.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;

import fr.istic.taa.domain.DonneesEtudiant;
import fr.istic.taa.domain.Etudiant;
import fr.istic.taa.dto.EtudiantIHM;
import fr.istic.taa.repository.DonneesEtudiantRepository;
import fr.istic.taa.repository.EtudiantRepository;
import fr.istic.taa.repository.search.DonneesEtudiantSearchRepository;
import fr.istic.taa.repository.search.EtudiantSearchRepository;
import fr.istic.taa.service.EtudiantService;

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

    @Inject
    private DonneesEtudiantRepository donneesEtudiantRepository;

    @Inject
    private DonneesEtudiantSearchRepository donneesEtudiantSearchRepository;

    /**
     * Save a etudiant.
     *
     * @param etudiant the entity to save
     * @return the persisted entity
     */
    public EtudiantIHM save(EtudiantIHM etudiant) {
        log.debug("Request to save Etudiant : {}", etudiant);

        Etudiant etu = etudiant.createEtudiant();
        DonneesEtudiant don = etudiant.createDonnees();

        Etudiant etuRes = etudiantRepository.save(etu);
        etudiantSearchRepository.save(etuRes);

        don.setEtudiant(etuRes);

        DonneesEtudiant donRes = donneesEtudiantRepository.save(don);
        donneesEtudiantSearchRepository.save(donRes);

        return EtudiantIHM.create(etuRes, donRes);
    }

    /**
     * Get all the etudiants.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<EtudiantIHM> findAll() {
        log.debug("Request to get all Etudiants");
        List<Etudiant> etudiants = etudiantRepository.findAll();

        List<EtudiantIHM> result = new ArrayList<>();

        for (Etudiant etu : etudiants) {
            DonneesEtudiant don = donneesEtudiantRepository.findLastByIdEtudiant(etu.getId());
            EtudiantIHM etuI = EtudiantIHM.create(etu,don);
            result.add(etuI);
        }

        return result;
    }

    /**
     * Get one etudiant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public EtudiantIHM findOne(Long id) {
        log.debug("Request to get Etudiant : {}", id);
        Etudiant etudiant = etudiantRepository.findOne(id);
        DonneesEtudiant donneesEtudiant = donneesEtudiantRepository.findLastByIdEtudiant(id);
        return Optional.ofNullable(etudiant)
            .map(result -> EtudiantIHM.create(etudiant,donneesEtudiant))
            .orElse(null);
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
    public List<EtudiantIHM> search(String query) {
        log.debug("Request to search Etudiants for query {}", query);
        List<Etudiant> etudiants = StreamSupport
            .stream(etudiantSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
        List<EtudiantIHM> res = new ArrayList<>();
        for (Etudiant etu : etudiants) {
            DonneesEtudiant don = donneesEtudiantRepository.findLastByIdEtudiant(etu.getId());
            EtudiantIHM etuI = EtudiantIHM.create(etu,don);
            res.add(etuI);
        }
        return res;
    }

    @Override
    public EtudiantIHM getByIne(String ine) {
        log.debug("Request to get Etudiant by ine : {}", ine);
        Etudiant etu = etudiantRepository.getByIne(ine);
        DonneesEtudiant donnee = donneesEtudiantRepository.findLastByIdEtudiant(etu.getId());
        return EtudiantIHM.create(etu,donnee);
    }
}
