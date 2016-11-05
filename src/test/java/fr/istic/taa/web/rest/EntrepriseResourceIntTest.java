package fr.istic.taa.web.rest;

import fr.istic.taa.TaaProjectApp;
import fr.istic.taa.domain.Entreprise;
import fr.istic.taa.dto.EntrepriseIHM;
import fr.istic.taa.repository.DonneesEntrepriseRepository;
import fr.istic.taa.repository.EntrepriseRepository;
import fr.istic.taa.repository.search.DonneesEntrepriseSearchRepository;
import fr.istic.taa.repository.search.EntrepriseSearchRepository;
import fr.istic.taa.service.DonneesEntrepriseService;
import fr.istic.taa.service.EntrepriseService;
import fr.istic.taa.service.MailService;
import fr.istic.taa.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EntrepriseResource REST controller.
 *
 * @see EntrepriseResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = TaaProjectApp.class)

public class EntrepriseResourceIntTest {
    private static final String DEFAULT_NOM = "AAAAA";
    private static final String UPDATED_NOM = "BBBBB";
    private static final String DEFAULT_SIRET = "AAAAA";
    private static final String UPDATED_SIRET = "BBBBB";

    private static final Integer DEFAULT_EFFECTIF = 1;
    private static final Integer UPDATED_EFFECTIF = 2;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final ZonedDateTime DEFAULT_DATEMODIF = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATEMODIF = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATEMODIF_STR = dateTimeFormatter.format(DEFAULT_DATEMODIF);
    private static final String DEFAULT_ADRESSE = "AAAAA";
    private static final String UPDATED_ADRESSE = "BBBBB";
    private static final String DEFAULT_VILLE = "AAAAA";
    private static final String UPDATED_VILLE = "BBBBB";
    private static final String DEFAULT_CODEPOSTAL = "AAAAA";
    private static final String UPDATED_CODEPOSTAL = "BBBBB";
    private static final String DEFAULT_TEL = "AAAAA";
    private static final String UPDATED_TEL = "BBBBB";
    private static final String DEFAULT_URL = "AAAAA";
    private static final String UPDATED_URL = "BBBBB";
    private static final String DEFAULT_COMMENTAIRE = "AAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBB";
    private static final String DEFAULT_MAIL = "testApp@mythseur.fr";
    private static final String UPDATED_MAIL = "BBBBB";

    @Inject
    private EntrepriseRepository entrepriseRepository;

    @Inject
    private EntrepriseService entrepriseService;

    @Inject
    private EntrepriseSearchRepository entrepriseSearchRepository;

    @Inject
    private DonneesEntrepriseRepository donneesEntrepriseRepository;

    @Inject
    private DonneesEntrepriseService donneesEntrepriseService;

    @Inject
    private DonneesEntrepriseSearchRepository donneesEntrepriseSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEntrepriseMockMvc;

    private EntrepriseIHM entreprise;

    @Inject
    private UserService userService;

    @Inject
    private MailService mailService;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EntrepriseResource entrepriseResource = new EntrepriseResource();
        ReflectionTestUtils.setField(entrepriseResource, "entrepriseService", entrepriseService);
        ReflectionTestUtils.setField(entrepriseResource, "donneesEntrepriseService", donneesEntrepriseService);
        ReflectionTestUtils.setField(entrepriseResource, "userService", userService);
        ReflectionTestUtils.setField(entrepriseResource, "mailService", mailService);
        this.restEntrepriseMockMvc = MockMvcBuilders.standaloneSetup(entrepriseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntrepriseIHM createEntity(EntityManager em) {
        EntrepriseIHM entreprise = new EntrepriseIHM();
        entreprise.setNom(DEFAULT_NOM);
        entreprise.setSiret(DEFAULT_SIRET);
        entreprise.setEffectif(DEFAULT_EFFECTIF);
        entreprise.setDateModif(DEFAULT_DATEMODIF);
        entreprise.setAdresse(DEFAULT_ADRESSE);
        entreprise.setVille(DEFAULT_VILLE);
        entreprise.setCodepostal(DEFAULT_CODEPOSTAL);
        entreprise.setTel(DEFAULT_TEL);
        entreprise.setUrl(DEFAULT_URL);
        entreprise.setCommentaire(DEFAULT_COMMENTAIRE);
        entreprise.setMail(DEFAULT_MAIL);
        return entreprise;
    }

    @Before
    public void initTest() {
        entrepriseSearchRepository.deleteAll();
        entreprise = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntreprise() throws Exception {
        int databaseSizeBeforeCreate = entrepriseRepository.findAll().size();

        // Create the Entreprise

        restEntrepriseMockMvc.perform(post("/api/entreprises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entreprise)))
            .andExpect(status().isCreated());

        // Validate the Entreprise in the database
        List<Entreprise> entreprises = entrepriseRepository.findAll();
        assertThat(entreprises).hasSize(databaseSizeBeforeCreate + 1);
        Entreprise testEntreprise = entreprises.get(entreprises.size() - 1);
        assertThat(testEntreprise.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEntreprise.getSiret()).isEqualTo(DEFAULT_SIRET);
        assertThat(testEntreprise.getEffectif()).isEqualTo(DEFAULT_EFFECTIF);

        // Validate the Entreprise in ElasticSearch
        Entreprise entrepriseEs = entrepriseSearchRepository.findOne(testEntreprise.getId());
        assertThat(entrepriseEs).isEqualToComparingFieldByField(testEntreprise);
    }

    @Test
    @Transactional
    public void getAllEntreprises() throws Exception {
        // Initialize the database
        entreprise.setEntreprise(entrepriseRepository.saveAndFlush(entreprise.createEntreprise()));
        entreprise.setDonnees(donneesEntrepriseRepository.saveAndFlush(entreprise.createDonnees()));

        // Get all the entreprises
        restEntrepriseMockMvc.perform(get("/api/entreprises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entreprise.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].siret").value(hasItem(DEFAULT_SIRET.toString())))
            .andExpect(jsonPath("$.[*].effectif").value(hasItem(DEFAULT_EFFECTIF)));
    }

    @Test
    @Transactional
    public void getEntreprise() throws Exception {
        // Initialize the database
        entreprise.setEntreprise(entrepriseRepository.saveAndFlush(entreprise.createEntreprise()));
        entreprise.setDonnees(donneesEntrepriseRepository.saveAndFlush(entreprise.createDonnees()));

        // Get the entreprise
        restEntrepriseMockMvc.perform(get("/api/entreprises/{id}", entreprise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entreprise.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.siret").value(DEFAULT_SIRET.toString()))
            .andExpect(jsonPath("$.effectif").value(DEFAULT_EFFECTIF));
    }

    @Test
    @Transactional
    public void getNonExistingEntreprise() throws Exception {
        // Get the entreprise
        restEntrepriseMockMvc.perform(get("/api/entreprises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntreprise() throws Exception {
        // Initialize the database
        entreprise.setEntreprise(entrepriseService.save(entreprise.createEntreprise()));
        entreprise.setDonnees(donneesEntrepriseService.save(entreprise.createDonnees()));

        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();

        // Update the entreprise
        Entreprise updatedEntreprise = entrepriseRepository.findOne(entreprise.getId());
        updatedEntreprise.setNom(UPDATED_NOM);
        updatedEntreprise.setSiret(UPDATED_SIRET);
        updatedEntreprise.setEffectif(UPDATED_EFFECTIF);

        restEntrepriseMockMvc.perform(put("/api/entreprises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntreprise)))
            .andExpect(status().isOk());

        // Validate the Entreprise in the database
        List<Entreprise> entreprises = entrepriseRepository.findAll();
        assertThat(entreprises).hasSize(databaseSizeBeforeUpdate);
        Entreprise testEntreprise = entreprises.get(entreprises.size() - 1);
        assertThat(testEntreprise.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEntreprise.getSiret()).isEqualTo(UPDATED_SIRET);
        assertThat(testEntreprise.getEffectif()).isEqualTo(UPDATED_EFFECTIF);

        // Validate the Entreprise in ElasticSearch
        Entreprise entrepriseEs = entrepriseSearchRepository.findOne(testEntreprise.getId());
        assertThat(entrepriseEs).isEqualToComparingFieldByField(testEntreprise);
    }

    @Test
    @Transactional
    public void deleteEntreprise() throws Exception {
        // Initialize the database
        entreprise.setEntreprise(entrepriseService.save(entreprise.createEntreprise()));

        int databaseSizeBeforeDelete = entrepriseRepository.findAll().size();

        // Get the entreprise
        restEntrepriseMockMvc.perform(delete("/api/entreprises/{id}", entreprise.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean entrepriseExistsInEs = entrepriseSearchRepository.exists(entreprise.getId());
        assertThat(entrepriseExistsInEs).isFalse();

        // Validate the database is empty
        List<Entreprise> entreprises = entrepriseRepository.findAll();
        assertThat(entreprises).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEntreprise() throws Exception {
        // Initialize the database
        entreprise.setEntreprise(entrepriseService.save(entreprise.createEntreprise()));
        entreprise.setDonnees(donneesEntrepriseService.save(entreprise.createDonnees()));
        // Search the entreprise
        restEntrepriseMockMvc.perform(get("/api/_search/entreprises?query=id:" + entreprise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entreprise.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].siret").value(hasItem(DEFAULT_SIRET.toString())))
            .andExpect(jsonPath("$.[*].effectif").value(hasItem(DEFAULT_EFFECTIF)));
    }
}
