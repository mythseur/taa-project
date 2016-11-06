package fr.istic.taa.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.taa.domain.DonneesEtudiant;
import fr.istic.taa.domain.Etudiant;
import fr.istic.taa.domain.User;
import fr.istic.taa.dto.EtudiantIHM;
import fr.istic.taa.security.AuthoritiesConstants;
import fr.istic.taa.service.DonneesEtudiantService;
import fr.istic.taa.service.EtudiantService;
import fr.istic.taa.service.MailService;
import fr.istic.taa.service.UserService;
import fr.istic.taa.web.rest.util.HeaderUtil;
import fr.istic.taa.web.rest.vm.ManagedUserVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * REST controller for managing Etudiant.
 */
@RestController
@RequestMapping("/api")
public class EtudiantResource {

    private final Logger log = LoggerFactory.getLogger(EtudiantResource.class);

    @Inject
    private EtudiantService etudiantService;

    @Inject
    private DonneesEtudiantService donneesEtudiantService;

    @Inject
    private UserService userService;

    @Inject
    private MailService mailService;

    private static Set<String> authorities = new HashSet<>(Collections.singletonList(AuthoritiesConstants.ETUDIANT));

    /**
     * POST  /etudiants : Create a new etudiant.
     *
     * @param etudiant the etudiant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new etudiant, or with status 400 (Bad Request) if the etudiant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/etudiants",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EtudiantIHM> createEtudiant(@RequestBody EtudiantIHM etudiant, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save Etudiant : {}", etudiant);

        ManagedUserVM userVm = new ManagedUserVM(
            null,
            etudiant.getiNe(),
            null,
            etudiant.getPrenom(),
            etudiant.getNom(),
            etudiant.getMail(),
            true,
            "fr",
            authorities,
            null,
            null,
            null,
            null
        );

        User user = userService.createUser(userVm);

        String baseUrl = request.getScheme() + // "http"
            "://" +                                // "://"
            request.getServerName() +              // "myhost"
            ":" +                                  // ":"
            request.getServerPort() +              // "80"
            request.getContextPath();              // "/myContextPath" or "" if deployed in root context

        mailService.sendCreationEmail(user, baseUrl);

        if (etudiant.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("etudiant", "idexists", "A new etudiant cannot already have an ID")).body(null);
        }
        Etudiant etu = etudiantService.save(etudiant.createEtudiant());
        etudiant.setEtudiant(etu);
        DonneesEtudiant don = donneesEtudiantService.save(etudiant.createDonnees());
        etudiant.setDonnees(don);



        return ResponseEntity.created(new URI("/api/etudiants/" + etudiant.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("etudiant", etudiant.getId().toString()))
            .body(etudiant);
    }

    /**
     * PUT  /etudiants : Updates an existing etudiant.
     *
     * @param etudiant the etudiant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated etudiant,
     * or with status 400 (Bad Request) if the etudiant is not valid,
     * or with status 500 (Internal Server Error) if the etudiant couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/etudiants",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EtudiantIHM> updateEtudiant(@RequestBody EtudiantIHM etudiant, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to update Etudiant : {}", etudiant);
        if (etudiant.getId() == null) {
            return createEtudiant(etudiant, request);
        }
        Etudiant etu = etudiantService.save(etudiant.createEtudiant());
        DonneesEtudiant donnees = donneesEtudiantService.save(etudiant.createDonnees());
        EtudiantIHM result = EtudiantIHM.create(etu, donnees);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("etudiant", etudiant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /etudiants : get all the etudiants.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of etudiants in body
     */
    @RequestMapping(value = "/etudiants",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EtudiantIHM> getAllEtudiants() {
        log.debug("REST request to get all Etudiants");
        List<Etudiant> etudiants = etudiantService.findAll();

        return etudiants.stream()
            .map(etudiant -> EtudiantIHM.create(etudiant, donneesEtudiantService.findLastByIdEtudiant(etudiant.getId())))
            .collect(Collectors.toList());
    }

    /**
     * GET  /etudiants/:id : get the "id" etudiant.
     *
     * @param id the id of the etudiant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the etudiant, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/etudiants/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EtudiantIHM> getEtudiant(@PathVariable Long id) {
        log.debug("REST request to get Etudiant : {}", id);
        Etudiant etu = etudiantService.findOne(id);
        DonneesEtudiant donnees = null;
        if (etu != null) {
            donnees = donneesEtudiantService.findLastByIdEtudiant(etu.getId());
        }
        return Optional.ofNullable(EtudiantIHM.create(etu, donnees))
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /etudiants/:id : delete the "id" etudiant.
     *
     * @param id the id of the etudiant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/etudiants/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEtudiant(@PathVariable Long id) {
        log.debug("REST request to delete Etudiant : {}", id);
        etudiantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("etudiant", id.toString())).build();
    }

    /**
     * SEARCH  /_search/etudiants?query=:query : search for the etudiant corresponding
     * to the query.
     *
     * @param query the query of the etudiant search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/etudiants",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EtudiantIHM> searchEtudiants(@RequestParam String query) {
        log.debug("REST request to search Etudiants for query {}", query);
        return etudiantService.search(query).stream()
            .map(etudiant -> EtudiantIHM.create(etudiant, donneesEtudiantService.findLastByIdEtudiant(etudiant.getId())))
            .collect(Collectors.toList());
    }

    /**
     * GET  /etudiants/ine/:ine : get the etudiant with ine "ine".
     *
     * @param ine the ine of the etudiant to retrieve
     * @return the Etudiant with ine "ine"
     */
    @RequestMapping(value = "/etudiants/ine/{ine}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public EtudiantIHM getEtudiantByIne(@PathVariable String ine) {
        log.debug("REST request to get Etudiant by INE : {}", ine);
        Etudiant etu = etudiantService.getByIne(ine);
        DonneesEtudiant donnees = donneesEtudiantService.findLastByIdEtudiant(etu.getId());
        return EtudiantIHM.create(etu, donnees);
    }


}
