package fr.istic.taa.web.rest;

import fr.istic.taa.TaaProjectApp;
import fr.istic.taa.domain.DonneesEtudiant;
import fr.istic.taa.domain.Etudiant;
import fr.istic.taa.domain.enumeration.Sexe;
import fr.istic.taa.dto.EtudiantIHM;
import fr.istic.taa.repository.DonneesEtudiantRepository;
import fr.istic.taa.repository.EtudiantRepository;
import fr.istic.taa.repository.search.DonneesEtudiantSearchRepository;
import fr.istic.taa.repository.search.EtudiantSearchRepository;
import fr.istic.taa.service.DonneesEtudiantService;
import fr.istic.taa.service.EtudiantService;
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
 * Test class for the EtudiantResource REST controller.
 *
 * @see EtudiantResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = TaaProjectApp.class)

public class EtudiantResourceIntTest {
    private static final String DEFAULT_I_NE = "AAAAA";
    private static final String UPDATED_I_NE = "BBBBB";
    private static final String DEFAULT_NOM = "AAAAA";
    private static final String UPDATED_NOM = "BBBBB";
    private static final String DEFAULT_PRENOM = "AAAAA";
    private static final String UPDATED_PRENOM = "BBBBB";

    private static final Sexe DEFAULT_SEXE = Sexe.HOMME;
    private static final Sexe UPDATED_SEXE = Sexe.FEMME;
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
    private static final String DEFAULT_TELPERSO = "AAAAA";
    private static final String UPDATED_TELPERSO = "BBBBB";
    private static final String DEFAULT_TELMOBILE = "AAAAA";
    private static final String UPDATED_TELMOBILE = "BBBBB";
    private static final String DEFAULT_MAIL = "AAAAA";
    private static final String UPDATED_MAIL = "BBBBB";

    @Inject
    private EtudiantRepository etudiantRepository;

    @Inject
    private EtudiantService etudiantService;

    @Inject
    private EtudiantSearchRepository etudiantSearchRepository;

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

    private MockMvc restEtudiantMockMvc;

    private EtudiantIHM etudiant;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EtudiantResource etudiantResource = new EtudiantResource();
        ReflectionTestUtils.setField(etudiantResource, "etudiantService", etudiantService);
        ReflectionTestUtils.setField(etudiantResource, "donneesEtudiantService", donneesEtudiantService);
        this.restEtudiantMockMvc = MockMvcBuilders.standaloneSetup(etudiantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtudiantIHM createEntity(EntityManager em) {
        EtudiantIHM etudiant = new EtudiantIHM();
        etudiant.setiNe(DEFAULT_I_NE);
        etudiant.setNom(DEFAULT_NOM);
        etudiant.setPrenom(DEFAULT_PRENOM);
        etudiant.setSexe(DEFAULT_SEXE);
        etudiant.setAdresse(DEFAULT_ADRESSE);
        etudiant.setVille(DEFAULT_VILLE);
        etudiant.setCodepostal(DEFAULT_CODEPOSTAL);
        etudiant.setTelperso(DEFAULT_TELPERSO);
        etudiant.setTelmobile(DEFAULT_TELMOBILE);
        etudiant.setMail(DEFAULT_MAIL);
        etudiant.setDateModif(DEFAULT_DATEMODIF);
        return etudiant;
    }

    @Before
    public void initTest() {
        etudiantSearchRepository.deleteAll();
        etudiant = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtudiant() throws Exception {
        int databaseSizeBeforeCreate = etudiantRepository.findAll().size();

        // Create the Etudiant
        restEtudiantMockMvc.perform(post("/api/etudiants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etudiant)))
            .andExpect(status().isCreated());

        // Validate the Etudiant in the database
        List<Etudiant> etudiants = etudiantRepository.findAll();
        assertThat(etudiants).hasSize(databaseSizeBeforeCreate + 1);
        Etudiant testEtudiant = etudiants.get(etudiants.size() - 1);
        assertThat(testEtudiant.getiNe()).isEqualTo(DEFAULT_I_NE);
        assertThat(testEtudiant.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEtudiant.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testEtudiant.getSexe()).isEqualTo(DEFAULT_SEXE);

        // Validate the DonneesEtudiant in the database
        DonneesEtudiant testDonneesEtudiant = donneesEtudiantRepository.findLastByIdEtudiant(testEtudiant.getId());
        assertThat(testDonneesEtudiant.getDatemodif()).isEqualTo(DEFAULT_DATEMODIF);
        assertThat(testDonneesEtudiant.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testDonneesEtudiant.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testDonneesEtudiant.getCodepostal()).isEqualTo(DEFAULT_CODEPOSTAL);
        assertThat(testDonneesEtudiant.getTelperso()).isEqualTo(DEFAULT_TELPERSO);
        assertThat(testDonneesEtudiant.getTelmobile()).isEqualTo(DEFAULT_TELMOBILE);
        assertThat(testDonneesEtudiant.getMail()).isEqualTo(DEFAULT_MAIL);

        // Validate the Etudiant in ElasticSearch
        Etudiant etudiantEs = etudiantSearchRepository.findOne(testEtudiant.getId());
        assertThat(etudiantEs).isEqualToComparingFieldByField(testEtudiant);

        DonneesEtudiant donneesEtudiant = donneesEtudiantSearchRepository.findOne(testDonneesEtudiant.getId());
        assertThat(donneesEtudiant).isEqualToComparingFieldByField(testDonneesEtudiant);
    }

    @Test
    @Transactional
    public void getAllEtudiants() throws Exception {
        // Initialize the database
        etudiant.setEtudiant(etudiantRepository.saveAndFlush(etudiant.createEtudiant()));
        etudiant.setDonnees(donneesEtudiantRepository.saveAndFlush(etudiant.createDonnees()));

        // Get all the etudiants
        restEtudiantMockMvc.perform(get("/api/etudiants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etudiant.getId().intValue())))
            .andExpect(jsonPath("$.[*].iNe").value(hasItem(DEFAULT_I_NE.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())));
    }

    @Test
    @Transactional
    public void getEtudiant() throws Exception {
        // Initialize the database
        etudiant.setEtudiant(etudiantRepository.saveAndFlush(etudiant.createEtudiant()));
        etudiant.setDonnees(donneesEtudiantRepository.saveAndFlush(etudiant.createDonnees()));

        // Get the etudiant
        restEtudiantMockMvc.perform(get("/api/etudiants/{id}", etudiant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(etudiant.getId().intValue()))
            .andExpect(jsonPath("$.iNe").value(DEFAULT_I_NE.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEtudiant() throws Exception {
        // Get the etudiant
        restEtudiantMockMvc.perform(get("/api/etudiants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtudiant() throws Exception {
        // Initialize the database
        Etudiant etud = etudiantService.save(etudiant.createEtudiant());
        etudiant.setEtudiant(etud);
        DonneesEtudiant donn = donneesEtudiantService.save(etudiant.createDonnees());
        etudiant.setDonnees(donn);

        int databaseSizeBeforeUpdate = etudiantRepository.findAll().size();

        // Update the etudiant
        Etudiant updatedEtudiant = etudiantRepository.findOne(etudiant.getId());
        updatedEtudiant.setiNe(UPDATED_I_NE);
        updatedEtudiant.setNom(UPDATED_NOM);
        updatedEtudiant.setPrenom(UPDATED_PRENOM);
        updatedEtudiant.setSexe(UPDATED_SEXE);

        restEtudiantMockMvc.perform(put("/api/etudiants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEtudiant)))
            .andExpect(status().isOk());

        // Validate the Etudiant in the database
        List<Etudiant> etudiants = etudiantRepository.findAll();
        assertThat(etudiants).hasSize(databaseSizeBeforeUpdate);
        Etudiant testEtudiant = etudiants.get(etudiants.size() - 1);
        assertThat(testEtudiant.getiNe()).isEqualTo(UPDATED_I_NE);
        assertThat(testEtudiant.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEtudiant.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testEtudiant.getSexe()).isEqualTo(UPDATED_SEXE);

        // Validate the Etudiant in ElasticSearch
        Etudiant etudiantEs = etudiantSearchRepository.findOne(testEtudiant.getId());
        assertThat(etudiantEs).isEqualToComparingFieldByField(testEtudiant);
    }

    @Test
    @Transactional
    public void deleteEtudiant() throws Exception {
        // Initialize the database
        etudiant.setEtudiant(etudiantService.save(etudiant.createEtudiant()));

        int databaseSizeBeforeDelete = etudiantRepository.findAll().size();

        // Get the etudiant
        restEtudiantMockMvc.perform(delete("/api/etudiants/{id}", etudiant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean etudiantExistsInEs = etudiantSearchRepository.exists(etudiant.getId());
        assertThat(etudiantExistsInEs).isFalse();

        // Validate the database is empty
        List<Etudiant> etudiants = etudiantRepository.findAll();
        assertThat(etudiants).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEtudiant() throws Exception {
        // Initialize the database
        etudiant.setEtudiant(etudiantService.save(etudiant.createEtudiant()));
        etudiant.setDonnees(donneesEtudiantService.save(etudiant.createDonnees()));

        // Search the etudiant
        restEtudiantMockMvc.perform(get("/api/_search/etudiants?query=id:" + etudiant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etudiant.getId().intValue())))
            .andExpect(jsonPath("$.[*].iNe").value(hasItem(DEFAULT_I_NE.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())));
    }
}
