package fr.istic.taa.web.rest;

import fr.istic.taa.TaaProjectApp;
import fr.istic.taa.domain.DonneesEtudiant;
import fr.istic.taa.repository.DonneesEtudiantRepository;
import fr.istic.taa.repository.search.DonneesEtudiantSearchRepository;
import fr.istic.taa.service.DonneesEtudiantService;
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static fr.istic.taa.config.JacksonConfiguration.ISO_FIXED_FORMAT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DonneesEtudiantResource REST controller.
 *
 * @see DonneesEtudiantResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = TaaProjectApp.class)

public class DonneesEtudiantResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATEMODIF = ZonedDateTime.now();
    private static final ZonedDateTime UPDATED_DATEMODIF = ZonedDateTime.now(ZoneId.systemDefault());
    private static final String DEFAULT_ADRESSE = "AAAAA";
    private static final String UPDATED_ADRESSE = "BBBBB";
    private static final String DEFAULT_VILLE = "AAAAA";
    private static final String UPDATED_VILLE = "BBBBB";
    private static final String DEFAULT_CODEPOSTAL = "AAAAA";
    private static final String UPDATED_CODEPOSTAL = "BBBBB";
    private static final String DEFAULT_TELPERSO = "AAAAA";
    private static final String UPDATED_TELPERSO = "BBBBB";
    private static final String DEFAULT_TELMOBILE = "AAAAA";
    private static final String UPDATED_TELMOBILE = "BBBBB";

    @Inject
    private DonneesEtudiantRepository donneesEtudiantRepository;

    @Inject
    private DonneesEtudiantService donneesEtudiantService;

    @Inject
    private DonneesEtudiantSearchRepository donneesEtudiantSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDonneesEtudiantMockMvc;

    private DonneesEtudiant donneesEtudiant;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DonneesEtudiantResource donneesEtudiantResource = new DonneesEtudiantResource();
        ReflectionTestUtils.setField(donneesEtudiantResource, "donneesEtudiantService", donneesEtudiantService);
        this.restDonneesEtudiantMockMvc = MockMvcBuilders.standaloneSetup(donneesEtudiantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DonneesEtudiant createEntity(EntityManager em) {
        DonneesEtudiant donneesEtudiant = new DonneesEtudiant();
        donneesEtudiant.setDatemodif(DEFAULT_DATEMODIF);
        donneesEtudiant.setAdresse(DEFAULT_ADRESSE);
        donneesEtudiant.setVille(DEFAULT_VILLE);
        donneesEtudiant.setCodepostal(DEFAULT_CODEPOSTAL);
        donneesEtudiant.setTelperso(DEFAULT_TELPERSO);
        donneesEtudiant.setTelmobile(DEFAULT_TELMOBILE);
        return donneesEtudiant;
    }

    @Before
    public void initTest() {
        donneesEtudiantSearchRepository.deleteAll();
        donneesEtudiant = createEntity(em);
    }

    @Test
    @Transactional
    public void createDonneesEtudiant() throws Exception {
        int databaseSizeBeforeCreate = donneesEtudiantRepository.findAll().size();

        // Create the DonneesEtudiant

        restDonneesEtudiantMockMvc.perform(post("/api/donnees-etudiants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donneesEtudiant)))
            .andExpect(status().isCreated());

        // Validate the DonneesEtudiant in the database
        List<DonneesEtudiant> donneesEtudiants = donneesEtudiantRepository.findAll();
        assertThat(donneesEtudiants).hasSize(databaseSizeBeforeCreate + 1);
        DonneesEtudiant testDonneesEtudiant = donneesEtudiants.get(donneesEtudiants.size() - 1);
        assertThat(testDonneesEtudiant.getDatemodif()).isEqualTo(DEFAULT_DATEMODIF);
        assertThat(testDonneesEtudiant.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testDonneesEtudiant.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testDonneesEtudiant.getCodepostal()).isEqualTo(DEFAULT_CODEPOSTAL);
        assertThat(testDonneesEtudiant.getTelperso()).isEqualTo(DEFAULT_TELPERSO);
        assertThat(testDonneesEtudiant.getTelmobile()).isEqualTo(DEFAULT_TELMOBILE);

        // Validate the DonneesEtudiant in ElasticSearch
        DonneesEtudiant donneesEtudiantEs = donneesEtudiantSearchRepository.findOne(testDonneesEtudiant.getId());
        assertThat(donneesEtudiantEs).isEqualToComparingFieldByField(testDonneesEtudiant);
    }

    @Test
    @Transactional
    public void getAllDonneesEtudiants() throws Exception {
        // Initialize the database
        donneesEtudiantRepository.saveAndFlush(donneesEtudiant);

        // Get all the donneesEtudiants
        restDonneesEtudiantMockMvc.perform(get("/api/donnees-etudiants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donneesEtudiant.getId().intValue())))
            .andExpect(jsonPath("$.[*].datemodif").value(hasItem(DEFAULT_DATEMODIF.format(ISO_FIXED_FORMAT))))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())))
            .andExpect(jsonPath("$.[*].codepostal").value(hasItem(DEFAULT_CODEPOSTAL.toString())))
            .andExpect(jsonPath("$.[*].telperso").value(hasItem(DEFAULT_TELPERSO.toString())))
            .andExpect(jsonPath("$.[*].telmobile").value(hasItem(DEFAULT_TELMOBILE.toString())));
    }

    @Test
    @Transactional
    public void getDonneesEtudiant() throws Exception {
        // Initialize the database
        donneesEtudiantRepository.saveAndFlush(donneesEtudiant);

        // Get the donneesEtudiant
        restDonneesEtudiantMockMvc.perform(get("/api/donnees-etudiants/{id}", donneesEtudiant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(donneesEtudiant.getId().intValue()))
            .andExpect(jsonPath("$.datemodif").value(DEFAULT_DATEMODIF.format(ISO_FIXED_FORMAT)))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE.toString()))
            .andExpect(jsonPath("$.codepostal").value(DEFAULT_CODEPOSTAL.toString()))
            .andExpect(jsonPath("$.telperso").value(DEFAULT_TELPERSO.toString()))
            .andExpect(jsonPath("$.telmobile").value(DEFAULT_TELMOBILE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDonneesEtudiant() throws Exception {
        // Get the donneesEtudiant
        restDonneesEtudiantMockMvc.perform(get("/api/donnees-etudiants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDonneesEtudiant() throws Exception {
        // Initialize the database
        donneesEtudiantService.save(donneesEtudiant);

        int databaseSizeBeforeUpdate = donneesEtudiantRepository.findAll().size();

        // Update the donneesEtudiant
        DonneesEtudiant updatedDonneesEtudiant = donneesEtudiantRepository.findOne(donneesEtudiant.getId());
        updatedDonneesEtudiant.setDatemodif(UPDATED_DATEMODIF);
        updatedDonneesEtudiant.setAdresse(UPDATED_ADRESSE);
        updatedDonneesEtudiant.setVille(UPDATED_VILLE);
        updatedDonneesEtudiant.setCodepostal(UPDATED_CODEPOSTAL);
        updatedDonneesEtudiant.setTelperso(UPDATED_TELPERSO);
        updatedDonneesEtudiant.setTelmobile(UPDATED_TELMOBILE);

        restDonneesEtudiantMockMvc.perform(put("/api/donnees-etudiants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDonneesEtudiant)))
            .andExpect(status().isOk());

        // Validate the DonneesEtudiant in the database
        List<DonneesEtudiant> donneesEtudiants = donneesEtudiantRepository.findAll();
        assertThat(donneesEtudiants).hasSize(databaseSizeBeforeUpdate);
        DonneesEtudiant testDonneesEtudiant = donneesEtudiants.get(donneesEtudiants.size() - 1);
        assertThat(testDonneesEtudiant.getDatemodif()).isEqualTo(UPDATED_DATEMODIF);
        assertThat(testDonneesEtudiant.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testDonneesEtudiant.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testDonneesEtudiant.getCodepostal()).isEqualTo(UPDATED_CODEPOSTAL);
        assertThat(testDonneesEtudiant.getTelperso()).isEqualTo(UPDATED_TELPERSO);
        assertThat(testDonneesEtudiant.getTelmobile()).isEqualTo(UPDATED_TELMOBILE);

        // Validate the DonneesEtudiant in ElasticSearch
        DonneesEtudiant donneesEtudiantEs = donneesEtudiantSearchRepository.findOne(testDonneesEtudiant.getId());
        assertThat(donneesEtudiantEs).isEqualToComparingFieldByField(testDonneesEtudiant);
    }

    @Test
    @Transactional
    public void deleteDonneesEtudiant() throws Exception {
        // Initialize the database
        donneesEtudiantService.save(donneesEtudiant);

        int databaseSizeBeforeDelete = donneesEtudiantRepository.findAll().size();

        // Get the donneesEtudiant
        restDonneesEtudiantMockMvc.perform(delete("/api/donnees-etudiants/{id}", donneesEtudiant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean donneesEtudiantExistsInEs = donneesEtudiantSearchRepository.exists(donneesEtudiant.getId());
        assertThat(donneesEtudiantExistsInEs).isFalse();

        // Validate the database is empty
        List<DonneesEtudiant> donneesEtudiants = donneesEtudiantRepository.findAll();
        assertThat(donneesEtudiants).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDonneesEtudiant() throws Exception {
        // Initialize the database
        donneesEtudiantService.save(donneesEtudiant);

        // Search the donneesEtudiant
        restDonneesEtudiantMockMvc.perform(get("/api/_search/donnees-etudiants?query=id:" + donneesEtudiant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donneesEtudiant.getId().intValue())))
            .andExpect(jsonPath("$.[*].datemodif").value(hasItem(DEFAULT_DATEMODIF.format(ISO_FIXED_FORMAT))))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())))
            .andExpect(jsonPath("$.[*].codepostal").value(hasItem(DEFAULT_CODEPOSTAL.toString())))
            .andExpect(jsonPath("$.[*].telperso").value(hasItem(DEFAULT_TELPERSO.toString())))
            .andExpect(jsonPath("$.[*].telmobile").value(hasItem(DEFAULT_TELMOBILE.toString())));
    }
}
