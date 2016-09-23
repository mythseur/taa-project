package fr.istic.taa.web.rest;

import fr.istic.taa.TaaProjectApp;
import fr.istic.taa.domain.Enquete;
import fr.istic.taa.repository.EnqueteRepository;
import fr.istic.taa.repository.search.EnqueteSearchRepository;
import fr.istic.taa.service.EnqueteService;
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
 * Test class for the EnqueteResource REST controller.
 *
 * @see EnqueteResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = TaaProjectApp.class)

public class EnqueteResourceIntTest {
    private static final String DEFAULT_SITUATION = "AAAAA";
    private static final String UPDATED_SITUATION = "BBBBB";
    private static final String DEFAULT_MODE_OBTENTION = "AAAAA";
    private static final String UPDATED_MODE_OBTENTION = "BBBBB";
    private static final String DEFAULT_MODE_ENQUETE = "AAAAA";
    private static final String UPDATED_MODE_ENQUETE = "BBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_NOM_USUEL = "AAAAA";
    private static final String UPDATED_NOM_USUEL = "BBBBB";
    private static final String DEFAULT_RUE = "AAAAA";
    private static final String UPDATED_RUE = "BBBBB";
    private static final String DEFAULT_COMPLEMENT = "AAAAA";
    private static final String UPDATED_COMPLEMENT = "BBBBB";
    private static final String DEFAULT_VILLE = "AAAAA";
    private static final String UPDATED_VILLE = "BBBBB";
    private static final String DEFAULT_CODEPOSTAL = "AAAAA";
    private static final String UPDATED_CODEPOSTAL = "BBBBB";

    private static final LocalDate DEFAULT_DATEDEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEDEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DUREE_RECHERCHE = 1;
    private static final Integer UPDATED_DUREE_RECHERCHE = 2;

    private static final Integer DEFAULT_SALAIRE = 1;
    private static final Integer UPDATED_SALAIRE = 2;

    private static final Integer DEFAULT_SALAIRE_FIXE = 1;
    private static final Integer UPDATED_SALAIRE_FIXE = 2;

    private static final Integer DEFAULT_SALAIRE_VARIABLE = 1;
    private static final Integer UPDATED_SALAIRE_VARIABLE = 2;

    private static final Integer DEFAULT_POURCENTAGE = 1;
    private static final Integer UPDATED_POURCENTAGE = 2;

    private static final Integer DEFAULT_AVANTAGE = 1;
    private static final Integer UPDATED_AVANTAGE = 2;
    private static final String DEFAULT_DEVISE = "AAAAA";
    private static final String UPDATED_DEVISE = "BBBBB";

    @Inject
    private EnqueteRepository enqueteRepository;

    @Inject
    private EnqueteService enqueteService;

    @Inject
    private EnqueteSearchRepository enqueteSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEnqueteMockMvc;

    private Enquete enquete;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EnqueteResource enqueteResource = new EnqueteResource();
        ReflectionTestUtils.setField(enqueteResource, "enqueteService", enqueteService);
        this.restEnqueteMockMvc = MockMvcBuilders.standaloneSetup(enqueteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enquete createEntity(EntityManager em) {
        Enquete enquete = new Enquete();
        enquete.setSituation(DEFAULT_SITUATION);
        enquete.setModeObtention(DEFAULT_MODE_OBTENTION);
        enquete.setModeEnquete(DEFAULT_MODE_ENQUETE);
        enquete.setDate(DEFAULT_DATE);
        enquete.setNomUsuel(DEFAULT_NOM_USUEL);
        enquete.setRue(DEFAULT_RUE);
        enquete.setComplement(DEFAULT_COMPLEMENT);
        enquete.setVille(DEFAULT_VILLE);
        enquete.setCodepostal(DEFAULT_CODEPOSTAL);
        enquete.setDatedebut(DEFAULT_DATEDEBUT);
        enquete.setDureeRecherche(DEFAULT_DUREE_RECHERCHE);
        enquete.setSalaire(DEFAULT_SALAIRE);
        enquete.setSalaireFixe(DEFAULT_SALAIRE_FIXE);
        enquete.setSalaireVariable(DEFAULT_SALAIRE_VARIABLE);
        enquete.setPourcentage(DEFAULT_POURCENTAGE);
        enquete.setAvantage(DEFAULT_AVANTAGE);
        enquete.setDevise(DEFAULT_DEVISE);
        return enquete;
    }

    @Before
    public void initTest() {
        enqueteSearchRepository.deleteAll();
        enquete = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnquete() throws Exception {
        int databaseSizeBeforeCreate = enqueteRepository.findAll().size();

        // Create the Enquete

        restEnqueteMockMvc.perform(post("/api/enquetes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enquete)))
            .andExpect(status().isCreated());

        // Validate the Enquete in the database
        List<Enquete> enquetes = enqueteRepository.findAll();
        assertThat(enquetes).hasSize(databaseSizeBeforeCreate + 1);
        Enquete testEnquete = enquetes.get(enquetes.size() - 1);
        assertThat(testEnquete.getSituation()).isEqualTo(DEFAULT_SITUATION);
        assertThat(testEnquete.getModeObtention()).isEqualTo(DEFAULT_MODE_OBTENTION);
        assertThat(testEnquete.getModeEnquete()).isEqualTo(DEFAULT_MODE_ENQUETE);
        assertThat(testEnquete.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testEnquete.getNomUsuel()).isEqualTo(DEFAULT_NOM_USUEL);
        assertThat(testEnquete.getRue()).isEqualTo(DEFAULT_RUE);
        assertThat(testEnquete.getComplement()).isEqualTo(DEFAULT_COMPLEMENT);
        assertThat(testEnquete.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testEnquete.getCodepostal()).isEqualTo(DEFAULT_CODEPOSTAL);
        assertThat(testEnquete.getDatedebut()).isEqualTo(DEFAULT_DATEDEBUT);
        assertThat(testEnquete.getDureeRecherche()).isEqualTo(DEFAULT_DUREE_RECHERCHE);
        assertThat(testEnquete.getSalaire()).isEqualTo(DEFAULT_SALAIRE);
        assertThat(testEnquete.getSalaireFixe()).isEqualTo(DEFAULT_SALAIRE_FIXE);
        assertThat(testEnquete.getSalaireVariable()).isEqualTo(DEFAULT_SALAIRE_VARIABLE);
        assertThat(testEnquete.getPourcentage()).isEqualTo(DEFAULT_POURCENTAGE);
        assertThat(testEnquete.getAvantage()).isEqualTo(DEFAULT_AVANTAGE);
        assertThat(testEnquete.getDevise()).isEqualTo(DEFAULT_DEVISE);

        // Validate the Enquete in ElasticSearch
        Enquete enqueteEs = enqueteSearchRepository.findOne(testEnquete.getId());
        assertThat(enqueteEs).isEqualToComparingFieldByField(testEnquete);
    }

    @Test
    @Transactional
    public void getAllEnquetes() throws Exception {
        // Initialize the database
        enqueteRepository.saveAndFlush(enquete);

        // Get all the enquetes
        restEnqueteMockMvc.perform(get("/api/enquetes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enquete.getId().intValue())))
            .andExpect(jsonPath("$.[*].situation").value(hasItem(DEFAULT_SITUATION.toString())))
            .andExpect(jsonPath("$.[*].modeObtention").value(hasItem(DEFAULT_MODE_OBTENTION.toString())))
            .andExpect(jsonPath("$.[*].modeEnquete").value(hasItem(DEFAULT_MODE_ENQUETE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].nomUsuel").value(hasItem(DEFAULT_NOM_USUEL.toString())))
            .andExpect(jsonPath("$.[*].rue").value(hasItem(DEFAULT_RUE.toString())))
            .andExpect(jsonPath("$.[*].complement").value(hasItem(DEFAULT_COMPLEMENT.toString())))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())))
            .andExpect(jsonPath("$.[*].codepostal").value(hasItem(DEFAULT_CODEPOSTAL.toString())))
            .andExpect(jsonPath("$.[*].datedebut").value(hasItem(DEFAULT_DATEDEBUT.toString())))
            .andExpect(jsonPath("$.[*].dureeRecherche").value(hasItem(DEFAULT_DUREE_RECHERCHE)))
            .andExpect(jsonPath("$.[*].salaire").value(hasItem(DEFAULT_SALAIRE)))
            .andExpect(jsonPath("$.[*].salaireFixe").value(hasItem(DEFAULT_SALAIRE_FIXE)))
            .andExpect(jsonPath("$.[*].salaireVariable").value(hasItem(DEFAULT_SALAIRE_VARIABLE)))
            .andExpect(jsonPath("$.[*].pourcentage").value(hasItem(DEFAULT_POURCENTAGE)))
            .andExpect(jsonPath("$.[*].avantage").value(hasItem(DEFAULT_AVANTAGE)))
            .andExpect(jsonPath("$.[*].devise").value(hasItem(DEFAULT_DEVISE.toString())));
    }

    @Test
    @Transactional
    public void getEnquete() throws Exception {
        // Initialize the database
        enqueteRepository.saveAndFlush(enquete);

        // Get the enquete
        restEnqueteMockMvc.perform(get("/api/enquetes/{id}", enquete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enquete.getId().intValue()))
            .andExpect(jsonPath("$.situation").value(DEFAULT_SITUATION.toString()))
            .andExpect(jsonPath("$.modeObtention").value(DEFAULT_MODE_OBTENTION.toString()))
            .andExpect(jsonPath("$.modeEnquete").value(DEFAULT_MODE_ENQUETE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.nomUsuel").value(DEFAULT_NOM_USUEL.toString()))
            .andExpect(jsonPath("$.rue").value(DEFAULT_RUE.toString()))
            .andExpect(jsonPath("$.complement").value(DEFAULT_COMPLEMENT.toString()))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE.toString()))
            .andExpect(jsonPath("$.codepostal").value(DEFAULT_CODEPOSTAL.toString()))
            .andExpect(jsonPath("$.datedebut").value(DEFAULT_DATEDEBUT.toString()))
            .andExpect(jsonPath("$.dureeRecherche").value(DEFAULT_DUREE_RECHERCHE))
            .andExpect(jsonPath("$.salaire").value(DEFAULT_SALAIRE))
            .andExpect(jsonPath("$.salaireFixe").value(DEFAULT_SALAIRE_FIXE))
            .andExpect(jsonPath("$.salaireVariable").value(DEFAULT_SALAIRE_VARIABLE))
            .andExpect(jsonPath("$.pourcentage").value(DEFAULT_POURCENTAGE))
            .andExpect(jsonPath("$.avantage").value(DEFAULT_AVANTAGE))
            .andExpect(jsonPath("$.devise").value(DEFAULT_DEVISE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEnquete() throws Exception {
        // Get the enquete
        restEnqueteMockMvc.perform(get("/api/enquetes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnquete() throws Exception {
        // Initialize the database
        enqueteService.save(enquete);

        int databaseSizeBeforeUpdate = enqueteRepository.findAll().size();

        // Update the enquete
        Enquete updatedEnquete = enqueteRepository.findOne(enquete.getId());
        updatedEnquete.setSituation(UPDATED_SITUATION);
        updatedEnquete.setModeObtention(UPDATED_MODE_OBTENTION);
        updatedEnquete.setModeEnquete(UPDATED_MODE_ENQUETE);
        updatedEnquete.setDate(UPDATED_DATE);
        updatedEnquete.setNomUsuel(UPDATED_NOM_USUEL);
        updatedEnquete.setRue(UPDATED_RUE);
        updatedEnquete.setComplement(UPDATED_COMPLEMENT);
        updatedEnquete.setVille(UPDATED_VILLE);
        updatedEnquete.setCodepostal(UPDATED_CODEPOSTAL);
        updatedEnquete.setDatedebut(UPDATED_DATEDEBUT);
        updatedEnquete.setDureeRecherche(UPDATED_DUREE_RECHERCHE);
        updatedEnquete.setSalaire(UPDATED_SALAIRE);
        updatedEnquete.setSalaireFixe(UPDATED_SALAIRE_FIXE);
        updatedEnquete.setSalaireVariable(UPDATED_SALAIRE_VARIABLE);
        updatedEnquete.setPourcentage(UPDATED_POURCENTAGE);
        updatedEnquete.setAvantage(UPDATED_AVANTAGE);
        updatedEnquete.setDevise(UPDATED_DEVISE);

        restEnqueteMockMvc.perform(put("/api/enquetes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEnquete)))
            .andExpect(status().isOk());

        // Validate the Enquete in the database
        List<Enquete> enquetes = enqueteRepository.findAll();
        assertThat(enquetes).hasSize(databaseSizeBeforeUpdate);
        Enquete testEnquete = enquetes.get(enquetes.size() - 1);
        assertThat(testEnquete.getSituation()).isEqualTo(UPDATED_SITUATION);
        assertThat(testEnquete.getModeObtention()).isEqualTo(UPDATED_MODE_OBTENTION);
        assertThat(testEnquete.getModeEnquete()).isEqualTo(UPDATED_MODE_ENQUETE);
        assertThat(testEnquete.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEnquete.getNomUsuel()).isEqualTo(UPDATED_NOM_USUEL);
        assertThat(testEnquete.getRue()).isEqualTo(UPDATED_RUE);
        assertThat(testEnquete.getComplement()).isEqualTo(UPDATED_COMPLEMENT);
        assertThat(testEnquete.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testEnquete.getCodepostal()).isEqualTo(UPDATED_CODEPOSTAL);
        assertThat(testEnquete.getDatedebut()).isEqualTo(UPDATED_DATEDEBUT);
        assertThat(testEnquete.getDureeRecherche()).isEqualTo(UPDATED_DUREE_RECHERCHE);
        assertThat(testEnquete.getSalaire()).isEqualTo(UPDATED_SALAIRE);
        assertThat(testEnquete.getSalaireFixe()).isEqualTo(UPDATED_SALAIRE_FIXE);
        assertThat(testEnquete.getSalaireVariable()).isEqualTo(UPDATED_SALAIRE_VARIABLE);
        assertThat(testEnquete.getPourcentage()).isEqualTo(UPDATED_POURCENTAGE);
        assertThat(testEnquete.getAvantage()).isEqualTo(UPDATED_AVANTAGE);
        assertThat(testEnquete.getDevise()).isEqualTo(UPDATED_DEVISE);

        // Validate the Enquete in ElasticSearch
        Enquete enqueteEs = enqueteSearchRepository.findOne(testEnquete.getId());
        assertThat(enqueteEs).isEqualToComparingFieldByField(testEnquete);
    }

    @Test
    @Transactional
    public void deleteEnquete() throws Exception {
        // Initialize the database
        enqueteService.save(enquete);

        int databaseSizeBeforeDelete = enqueteRepository.findAll().size();

        // Get the enquete
        restEnqueteMockMvc.perform(delete("/api/enquetes/{id}", enquete.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean enqueteExistsInEs = enqueteSearchRepository.exists(enquete.getId());
        assertThat(enqueteExistsInEs).isFalse();

        // Validate the database is empty
        List<Enquete> enquetes = enqueteRepository.findAll();
        assertThat(enquetes).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEnquete() throws Exception {
        // Initialize the database
        enqueteService.save(enquete);

        // Search the enquete
        restEnqueteMockMvc.perform(get("/api/_search/enquetes?query=id:" + enquete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enquete.getId().intValue())))
            .andExpect(jsonPath("$.[*].situation").value(hasItem(DEFAULT_SITUATION.toString())))
            .andExpect(jsonPath("$.[*].modeObtention").value(hasItem(DEFAULT_MODE_OBTENTION.toString())))
            .andExpect(jsonPath("$.[*].modeEnquete").value(hasItem(DEFAULT_MODE_ENQUETE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].nomUsuel").value(hasItem(DEFAULT_NOM_USUEL.toString())))
            .andExpect(jsonPath("$.[*].rue").value(hasItem(DEFAULT_RUE.toString())))
            .andExpect(jsonPath("$.[*].complement").value(hasItem(DEFAULT_COMPLEMENT.toString())))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())))
            .andExpect(jsonPath("$.[*].codepostal").value(hasItem(DEFAULT_CODEPOSTAL.toString())))
            .andExpect(jsonPath("$.[*].datedebut").value(hasItem(DEFAULT_DATEDEBUT.toString())))
            .andExpect(jsonPath("$.[*].dureeRecherche").value(hasItem(DEFAULT_DUREE_RECHERCHE)))
            .andExpect(jsonPath("$.[*].salaire").value(hasItem(DEFAULT_SALAIRE)))
            .andExpect(jsonPath("$.[*].salaireFixe").value(hasItem(DEFAULT_SALAIRE_FIXE)))
            .andExpect(jsonPath("$.[*].salaireVariable").value(hasItem(DEFAULT_SALAIRE_VARIABLE)))
            .andExpect(jsonPath("$.[*].pourcentage").value(hasItem(DEFAULT_POURCENTAGE)))
            .andExpect(jsonPath("$.[*].avantage").value(hasItem(DEFAULT_AVANTAGE)))
            .andExpect(jsonPath("$.[*].devise").value(hasItem(DEFAULT_DEVISE.toString())));
    }
}
