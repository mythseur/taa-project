package fr.istic.taa.web.rest;

import fr.istic.taa.TaaProjectApp;
import fr.istic.taa.domain.DonneesEntreprise;
import fr.istic.taa.repository.DonneesEntrepriseRepository;
import fr.istic.taa.repository.search.DonneesEntrepriseSearchRepository;
import fr.istic.taa.service.DonneesEntrepriseService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DonneesEntrepriseResource REST controller.
 *
 * @see DonneesEntrepriseResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = TaaProjectApp.class)

public class DonneesEntrepriseResourceIntTest {

    private static final LocalDate DEFAULT_DATEMODIF = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEMODIF = LocalDate.now(ZoneId.systemDefault());
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

    private MockMvc restDonneesEntrepriseMockMvc;

    private DonneesEntreprise donneesEntreprise;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DonneesEntrepriseResource donneesEntrepriseResource = new DonneesEntrepriseResource();
        ReflectionTestUtils.setField(donneesEntrepriseResource, "donneesEntrepriseService", donneesEntrepriseService);
        this.restDonneesEntrepriseMockMvc = MockMvcBuilders.standaloneSetup(donneesEntrepriseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DonneesEntreprise createEntity(EntityManager em) {
        DonneesEntreprise donneesEntreprise = new DonneesEntreprise();
        donneesEntreprise.setDatemodif(DEFAULT_DATEMODIF);
        donneesEntreprise.setAdresse(DEFAULT_ADRESSE);
        donneesEntreprise.setVille(DEFAULT_VILLE);
        donneesEntreprise.setCodepostal(DEFAULT_CODEPOSTAL);
        donneesEntreprise.setTel(DEFAULT_TEL);
        donneesEntreprise.setUrl(DEFAULT_URL);
        donneesEntreprise.setCommentaire(DEFAULT_COMMENTAIRE);
        return donneesEntreprise;
    }

    @Before
    public void initTest() {
        donneesEntrepriseSearchRepository.deleteAll();
        donneesEntreprise = createEntity(em);
    }

    @Test
    @Transactional
    public void createDonneesEntreprise() throws Exception {
        int databaseSizeBeforeCreate = donneesEntrepriseRepository.findAll().size();

        // Create the DonneesEntreprise

        restDonneesEntrepriseMockMvc.perform(post("/api/donnees-entreprises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donneesEntreprise)))
            .andExpect(status().isCreated());

        // Validate the DonneesEntreprise in the database
        List<DonneesEntreprise> donneesEntreprises = donneesEntrepriseRepository.findAll();
        assertThat(donneesEntreprises).hasSize(databaseSizeBeforeCreate + 1);
        DonneesEntreprise testDonneesEntreprise = donneesEntreprises.get(donneesEntreprises.size() - 1);
        assertThat(testDonneesEntreprise.getDatemodif()).isEqualTo(DEFAULT_DATEMODIF);
        assertThat(testDonneesEntreprise.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testDonneesEntreprise.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testDonneesEntreprise.getCodepostal()).isEqualTo(DEFAULT_CODEPOSTAL);
        assertThat(testDonneesEntreprise.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testDonneesEntreprise.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testDonneesEntreprise.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);

        // Validate the DonneesEntreprise in ElasticSearch
        DonneesEntreprise donneesEntrepriseEs = donneesEntrepriseSearchRepository.findOne(testDonneesEntreprise.getId());
        assertThat(donneesEntrepriseEs).isEqualToComparingFieldByField(testDonneesEntreprise);
    }

    @Test
    @Transactional
    public void getAllDonneesEntreprises() throws Exception {
        // Initialize the database
        donneesEntrepriseRepository.saveAndFlush(donneesEntreprise);

        // Get all the donneesEntreprises
        restDonneesEntrepriseMockMvc.perform(get("/api/donnees-entreprises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donneesEntreprise.getId().intValue())))
            .andExpect(jsonPath("$.[*].datemodif").value(hasItem(DEFAULT_DATEMODIF.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())))
            .andExpect(jsonPath("$.[*].codepostal").value(hasItem(DEFAULT_CODEPOSTAL.toString())))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())));
    }

    @Test
    @Transactional
    public void getDonneesEntreprise() throws Exception {
        // Initialize the database
        donneesEntrepriseRepository.saveAndFlush(donneesEntreprise);

        // Get the donneesEntreprise
        restDonneesEntrepriseMockMvc.perform(get("/api/donnees-entreprises/{id}", donneesEntreprise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(donneesEntreprise.getId().intValue()))
            .andExpect(jsonPath("$.datemodif").value(DEFAULT_DATEMODIF.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE.toString()))
            .andExpect(jsonPath("$.codepostal").value(DEFAULT_CODEPOSTAL.toString()))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDonneesEntreprise() throws Exception {
        // Get the donneesEntreprise
        restDonneesEntrepriseMockMvc.perform(get("/api/donnees-entreprises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDonneesEntreprise() throws Exception {
        // Initialize the database
        donneesEntrepriseService.save(donneesEntreprise);

        int databaseSizeBeforeUpdate = donneesEntrepriseRepository.findAll().size();

        // Update the donneesEntreprise
        DonneesEntreprise updatedDonneesEntreprise = donneesEntrepriseRepository.findOne(donneesEntreprise.getId());
        updatedDonneesEntreprise.setDatemodif(UPDATED_DATEMODIF);
        updatedDonneesEntreprise.setAdresse(UPDATED_ADRESSE);
        updatedDonneesEntreprise.setVille(UPDATED_VILLE);
        updatedDonneesEntreprise.setCodepostal(UPDATED_CODEPOSTAL);
        updatedDonneesEntreprise.setTel(UPDATED_TEL);
        updatedDonneesEntreprise.setUrl(UPDATED_URL);
        updatedDonneesEntreprise.setCommentaire(UPDATED_COMMENTAIRE);

        restDonneesEntrepriseMockMvc.perform(put("/api/donnees-entreprises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDonneesEntreprise)))
            .andExpect(status().isOk());

        // Validate the DonneesEntreprise in the database
        List<DonneesEntreprise> donneesEntreprises = donneesEntrepriseRepository.findAll();
        assertThat(donneesEntreprises).hasSize(databaseSizeBeforeUpdate);
        DonneesEntreprise testDonneesEntreprise = donneesEntreprises.get(donneesEntreprises.size() - 1);
        assertThat(testDonneesEntreprise.getDatemodif()).isEqualTo(UPDATED_DATEMODIF);
        assertThat(testDonneesEntreprise.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testDonneesEntreprise.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testDonneesEntreprise.getCodepostal()).isEqualTo(UPDATED_CODEPOSTAL);
        assertThat(testDonneesEntreprise.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testDonneesEntreprise.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testDonneesEntreprise.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);

        // Validate the DonneesEntreprise in ElasticSearch
        DonneesEntreprise donneesEntrepriseEs = donneesEntrepriseSearchRepository.findOne(testDonneesEntreprise.getId());
        assertThat(donneesEntrepriseEs).isEqualToComparingFieldByField(testDonneesEntreprise);
    }

    @Test
    @Transactional
    public void deleteDonneesEntreprise() throws Exception {
        // Initialize the database
        donneesEntrepriseService.save(donneesEntreprise);

        int databaseSizeBeforeDelete = donneesEntrepriseRepository.findAll().size();

        // Get the donneesEntreprise
        restDonneesEntrepriseMockMvc.perform(delete("/api/donnees-entreprises/{id}", donneesEntreprise.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean donneesEntrepriseExistsInEs = donneesEntrepriseSearchRepository.exists(donneesEntreprise.getId());
        assertThat(donneesEntrepriseExistsInEs).isFalse();

        // Validate the database is empty
        List<DonneesEntreprise> donneesEntreprises = donneesEntrepriseRepository.findAll();
        assertThat(donneesEntreprises).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDonneesEntreprise() throws Exception {
        // Initialize the database
        donneesEntrepriseService.save(donneesEntreprise);

        // Search the donneesEntreprise
        restDonneesEntrepriseMockMvc.perform(get("/api/_search/donnees-entreprises?query=id:" + donneesEntreprise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donneesEntreprise.getId().intValue())))
            .andExpect(jsonPath("$.[*].datemodif").value(hasItem(DEFAULT_DATEMODIF.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())))
            .andExpect(jsonPath("$.[*].codepostal").value(hasItem(DEFAULT_CODEPOSTAL.toString())))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())));
    }
}
