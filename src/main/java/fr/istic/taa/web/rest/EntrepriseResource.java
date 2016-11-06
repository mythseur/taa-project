package fr.istic.taa.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.taa.domain.DonneesEntreprise;
import fr.istic.taa.domain.Entreprise;
import fr.istic.taa.domain.User;
import fr.istic.taa.dto.EntrepriseIHM;
import fr.istic.taa.security.AuthoritiesConstants;
import fr.istic.taa.service.DonneesEntrepriseService;
import fr.istic.taa.service.EntrepriseService;
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
 * REST controller for managing Entreprise.
 */
@RestController
@RequestMapping("/api")
public class EntrepriseResource {

    private final Logger log = LoggerFactory.getLogger(EntrepriseResource.class);

    @Inject
    private EntrepriseService entrepriseService;

    @Inject
    private DonneesEntrepriseService donneesEntrepriseService;

    @Inject
    private UserService userService;

    @Inject
    private MailService mailService;

    private static Set<String> authorities = new HashSet<>(Collections.singletonList(AuthoritiesConstants.ENTREPRISE));

    /**
     * POST  /entreprises : Create a new entreprise.
     *
     * @param entreprise the entreprise to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entreprise, or with status 400 (Bad Request) if the entreprise has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/entreprises",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EntrepriseIHM> createEntreprise(@RequestBody EntrepriseIHM entreprise, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save Entreprise : {}", entreprise);

        ManagedUserVM userVm = new ManagedUserVM(
            null,
            String.valueOf(entreprise.getId()),
            null,
            entreprise.getNom(),
            "",
            entreprise.getMail(),
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

        if (entreprise.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("entreprise", "idexists", "A new entreprise cannot already have an ID")).body(null);
        }

        Entreprise entr = entrepriseService.save(entreprise.createEntreprise());
        entreprise.setEntreprise(entr);
        DonneesEntreprise donneesEntreprise = donneesEntrepriseService.save(entreprise.createDonnees());
        entreprise.setDonnees(donneesEntreprise);

        return ResponseEntity.created(new URI("/api/entreprises/" + entreprise.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("entreprise", entreprise.getId().toString()))
            .body(entreprise);
    }

    /**
     * PUT  /entreprises : Updates an existing entreprise.
     *
     * @param entreprise the entreprise to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entreprise,
     * or with status 400 (Bad Request) if the entreprise is not valid,
     * or with status 500 (Internal Server Error) if the entreprise couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/entreprises",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EntrepriseIHM> updateEntreprise(@RequestBody EntrepriseIHM entreprise, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to update Entreprise : {}", entreprise);
        if (entreprise.getId() == null) {
            return createEntreprise(entreprise, request);
        }
        Entreprise entr = entrepriseService.save(entreprise.createEntreprise());
        DonneesEntreprise don = donneesEntrepriseService.save(entreprise.createDonnees());
        EntrepriseIHM result = EntrepriseIHM.create(entr, don);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("entreprise", entreprise.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entreprises : get all the entreprises.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of entreprises in body
     */
    @RequestMapping(value = "/entreprises",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EntrepriseIHM> getAllEntreprises() {
        log.debug("REST request to get all Entreprises");
        List<Entreprise> list = entrepriseService.findAll();

        return list.stream()
            .map(entreprise -> EntrepriseIHM.create(entreprise, donneesEntrepriseService.findLastByIdEntreprise(entreprise.getId())))
            .collect(Collectors.toList());
    }

    /**
     * GET  /entreprises/:id : get the "id" entreprise.
     *
     * @param id the id of the entreprise to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entreprise, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/entreprises/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EntrepriseIHM> getEntreprise(@PathVariable Long id) {
        log.debug("REST request to get Entreprise : {}", id);
        Entreprise entreprise = entrepriseService.findOne(id);
        DonneesEntreprise donnees = null;
        if (entreprise != null) {
            donnees = donneesEntrepriseService.findLastByIdEntreprise(entreprise.getId());
        }
        return Optional.ofNullable(EntrepriseIHM.create(entreprise, donnees))
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /entreprises/:id : delete the "id" entreprise.
     *
     * @param id the id of the entreprise to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/entreprises/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEntreprise(@PathVariable Long id) {
        log.debug("REST request to delete Entreprise : {}", id);
        entrepriseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("entreprise", id.toString())).build();
    }

    /**
     * SEARCH  /_search/entreprises?query=:query : search for the entreprise corresponding
     * to the query.
     *
     * @param query the query of the entreprise search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/entreprises",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EntrepriseIHM> searchEntreprises(@RequestParam String query) {
        log.debug("REST request to search Entreprises for query {}", query);
        return entrepriseService.search(query).stream()
            .map(entreprise -> EntrepriseIHM.create(entreprise, donneesEntrepriseService.findLastByIdEntreprise(entreprise.getId())))
            .collect(Collectors.toList());
    }


}
