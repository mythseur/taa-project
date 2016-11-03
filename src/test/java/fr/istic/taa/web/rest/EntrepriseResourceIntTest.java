package fr.istic.taa.web.rest;

import fr.istic.taa.TaaProjectApp;
import fr.istic.taa.domain.Entreprise;
import fr.istic.taa.repository.EntrepriseRepository;
import fr.istic.taa.repository.search.EntrepriseSearchRepository;
import fr.istic.taa.service.EntrepriseService;
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

    @Inject
    private EntrepriseRepository entrepriseRepository;

    @Inject
    private EntrepriseService entrepriseService;

    @Inject
    private EntrepriseSearchRepository entrepriseSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEntrepriseMockMvc;

    private Entreprise entreprise;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EntrepriseResource entrepriseResource = new EntrepriseResource();
        ReflectionTestUtils.setField(entrepriseResource, "entrepriseService", entrepriseService);
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
    public static Entreprise createEntity(EntityManager em) {
        Entreprise entreprise = new Entreprise();
        entreprise.setNom(DEFAULT_NOM);
        entreprise.setSiret(DEFAULT_SIRET);
        entreprise.setEffectif(DEFAULT_EFFECTIF);
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
        entrepriseRepository.saveAndFlush(entreprise);

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
        entrepriseRepository.saveAndFlush(entreprise);

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
//        entrepriseService.save(entreprise);

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
//        entrepriseService.save(entreprise);

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
//        entrepriseService.save(entreprise);

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
