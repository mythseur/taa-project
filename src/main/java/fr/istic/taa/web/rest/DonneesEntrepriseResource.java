package fr.istic.taa.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import fr.istic.taa.domain.DonneesEntreprise;
import fr.istic.taa.service.DonneesEntrepriseService;
import fr.istic.taa.web.rest.util.HeaderUtil;

/**
 * REST controller for managing DonneesEntreprise.
 */
@RestController
@RequestMapping("/api")
public class DonneesEntrepriseResource {

    private final Logger log = LoggerFactory.getLogger(DonneesEntrepriseResource.class);

    @Inject
    private DonneesEntrepriseService donneesEntrepriseService;

    /**
     * POST  /donnees-entreprises : Create a new donneesEntreprise.
     *
     * @param donneesEntreprise the donneesEntreprise to create
     * @return the ResponseEntity with status 201 (Created) and with body the new donneesEntreprise, or with status 400 (Bad Request) if the donneesEntreprise has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/donnees-entreprises",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DonneesEntreprise> createDonneesEntreprise(@RequestBody DonneesEntreprise donneesEntreprise) throws URISyntaxException {
        log.debug("REST request to save DonneesEntreprise : {}", donneesEntreprise);
        if (donneesEntreprise.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("donneesEntreprise", "idexists", "A new donneesEntreprise cannot already have an ID")).body(null);
        }
        DonneesEntreprise result = donneesEntrepriseService.save(donneesEntreprise);
        return ResponseEntity.created(new URI("/api/donnees-entreprises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("donneesEntreprise", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /donnees-entreprises : Updates an existing donneesEntreprise.
     *
     * @param donneesEntreprise the donneesEntreprise to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated donneesEntreprise,
     * or with status 400 (Bad Request) if the donneesEntreprise is not valid,
     * or with status 500 (Internal Server Error) if the donneesEntreprise couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/donnees-entreprises",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DonneesEntreprise> updateDonneesEntreprise(@RequestBody DonneesEntreprise donneesEntreprise) throws URISyntaxException {
        log.debug("REST request to update DonneesEntreprise : {}", donneesEntreprise);
        if (donneesEntreprise.getId() == null) {
            return createDonneesEntreprise(donneesEntreprise);
        }
        DonneesEntreprise result = donneesEntrepriseService.save(donneesEntreprise);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("donneesEntreprise", donneesEntreprise.getId().toString()))
            .body(result);
    }

    /**
     * GET  /donnees-entreprises : get all the donneesEntreprises.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of donneesEntreprises in body
     */
    @RequestMapping(value = "/donnees-entreprises",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DonneesEntreprise> getAllDonneesEntreprises() {
        log.debug("REST request to get all DonneesEntreprises");
        return donneesEntrepriseService.findAll();
    }

    /**
     * GET  /donnees-entreprises/:id : get the "id" donneesEntreprise.
     *
     * @param id the id of the donneesEntreprise to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the donneesEntreprise, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/donnees-entreprises/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DonneesEntreprise> getDonneesEntreprise(@PathVariable Long id) {
        log.debug("REST request to get DonneesEntreprise : {}", id);
        DonneesEntreprise donneesEntreprise = donneesEntrepriseService.findOne(id);
        return Optional.ofNullable(donneesEntreprise)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /donnees-entreprises/entreprise/:id : get the donneesEntreprise for Entreprise "id".
     *
     * @param id the id of the donneesEntreprise.entreprise to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the donneesEntreprise, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/donnees-entreprises/entreprise/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DonneesEntreprise> getDonneesEntrepriseByIdEntreprise(@PathVariable Long id) {
        log.debug("REST request to get DonneesEntreprise for Entreprise : {}", id);
        DonneesEntreprise donneesEntreprise = donneesEntrepriseService.findLastByIdEntreprise(id);
        return Optional.ofNullable(donneesEntreprise)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /donnees-entreprises/entreprisedate/:id/:date : get the donneesEntreprise for Entreprise "id".
     *
     * @param id the id of the donneesEntreprise.entreprise to retrieve
     * @param date the date with format yyyy-MM-dd
     * @return the ResponseEntity with status 200 (OK) and with body the donneesEntreprise, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/donnees-entreprises/entreprisedate/{id}/{date}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DonneesEntreprise> getDonneesEntrepriseByIdEntrepriseAndDate(@PathVariable Long id, @PathVariable("date") String date) {
        log.debug("REST request to get DonneesEntreprise of Entreprise by date : {}", date);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ldate = LocalDate.parse(date, formatter);
        ZonedDateTime dateTime = ldate.atStartOfDay(ZoneId.systemDefault());

        DonneesEntreprise donneesEntreprise = donneesEntrepriseService.findLastByIdEntrepriseAndDate(id,dateTime);
        return Optional.ofNullable(donneesEntreprise)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /donnees-entreprises/:id : delete the "id" donneesEntreprise.
     *
     * @param id the id of the donneesEntreprise to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/donnees-entreprises/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDonneesEntreprise(@PathVariable Long id) {
        log.debug("REST request to delete DonneesEntreprise : {}", id);
        donneesEntrepriseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("donneesEntreprise", id.toString())).build();
    }

    /**
     * SEARCH  /_search/donnees-entreprises?query=:query : search for the donneesEntreprise corresponding
     * to the query.
     *
     * @param query the query of the donneesEntreprise search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/donnees-entreprises",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DonneesEntreprise> searchDonneesEntreprises(@RequestParam String query) {
        log.debug("REST request to search DonneesEntreprises for query {}", query);
        return donneesEntrepriseService.search(query);
    }


}
