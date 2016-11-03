package fr.istic.taa.service.impl;

import fr.istic.taa.domain.DonneesEntreprise;
import fr.istic.taa.domain.Entreprise;
import fr.istic.taa.dto.EntrepriseIHM;
import fr.istic.taa.repository.DonneesEntrepriseRepository;
import fr.istic.taa.repository.EntrepriseRepository;
import fr.istic.taa.repository.search.DonneesEntrepriseSearchRepository;
import fr.istic.taa.repository.search.EntrepriseSearchRepository;
import fr.istic.taa.service.EntrepriseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Inject
    private DonneesEntrepriseSearchRepository donneesEntrepriseSearchRepository;

    /**
     * Save a entreprise.
     *
     * @param entreprise the entity to save
     * @return the persisted entity
     */
    public Entreprise save(Entreprise entreprise) {
        //TODO Virer l'héritage pour mettre le bordel dans le code
        log.debug("Request to save Entreprise : {}", entreprise);

//        Entreprise ent = entreprise.createEntreprise();
//        DonneesEntreprise don = entreprise.createDonnees();

//        Entreprise entRes = entrepriseRepository.save(ent);
//        entrepriseSearchRepository.save(entRes);
//
//        don.setEntreprise(entRes);
//
//        DonneesEntreprise donRes = donneesEntrepriseRepository.save(don);
//        donneesEntrepriseSearchRepository.save(donRes);
//
//        return EntrepriseIHM.create(entRes,donRes);
        return null;
    }


    /**
     * Get all the entreprises.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Entreprise> findAll() {
        log.debug("Request to get all Entreprises");
        List<Entreprise> entreprises = entrepriseRepository.findAll();

        List<Entreprise> result = new ArrayList<>();

        for (Entreprise ent : entreprises) {
            DonneesEntreprise don = donneesEntrepriseRepository.findLastByIdEntreprise(ent.getId());
            Entreprise entI = EntrepriseIHM.create(ent, don);
            result.add(entI);
        }

        return result;
    }

    /**
     * Get one entreprise by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public EntrepriseIHM findOne(Long id) {
        log.debug("Request to get Entreprise : {}", id);
        Entreprise entreprise = entrepriseRepository.findOne(id);
        DonneesEntreprise donneesEntreprise = donneesEntrepriseRepository.findLastByIdEntreprise(id);
        return Optional.ofNullable(entreprise)
            .map(result -> EntrepriseIHM.create(entreprise,donneesEntreprise))
            .orElse(null);
    }

    /**
     * Delete the  entreprise by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Entreprise : {}", id);
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
//        log.debug("Request to search Entreprises for query {}", query);
//        List<Entreprise> entreprises = StreamSupport
//            .stream(entrepriseSearchRepository.search(queryStringQuery(query)).spliterator(), false)
//            .collect(Collectors.toList());
//        List<EntrepriseIHM> res = new ArrayList<>();
//        for (Entreprise ent : entreprises) {
//            DonneesEntreprise don = donneesEntrepriseRepository.findLastByIdEntreprise(ent.getId());
//            EntrepriseIHM entI = EntrepriseIHM.create(ent,don);
//            res.add(entI);
//        }
//        return res;
        return null;
    }

    @Override
    public EntrepriseIHM findOneByDate(Long id, ZonedDateTime date) {
        log.debug("Request to get Entreprise : {}", id);
        Entreprise entreprise = entrepriseRepository.findOne(id);
        DonneesEntreprise donneesEntreprise = donneesEntrepriseRepository.findLastByIdEntrepriseAndDate(id, date);
        return Optional.ofNullable(entreprise)
            .map(result -> EntrepriseIHM.create(entreprise,donneesEntreprise))
            .orElse(null);
    }
}
