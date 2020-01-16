package at.steiner.casino.web.rest;

import at.steiner.casino.CasinoApp;
import at.steiner.casino.domain.StockValueChange;
import at.steiner.casino.repository.StockValueChangeRepository;
import at.steiner.casino.service.StockValueChangeService;
import at.steiner.casino.service.dto.StockValueChangeDTO;
import at.steiner.casino.service.mapper.StockValueChangeMapper;
import at.steiner.casino.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static at.steiner.casino.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link StockValueChangeResource} REST controller.
 */
@SpringBootTest(classes = CasinoApp.class)
public class StockValueChangeResourceIT {

    private static final Instant DEFAULT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VALUE = 1;
    private static final Integer UPDATED_VALUE = 2;

    @Autowired
    private StockValueChangeRepository stockValueChangeRepository;

    @Autowired
    private StockValueChangeMapper stockValueChangeMapper;

    @Autowired
    private StockValueChangeService stockValueChangeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restStockValueChangeMockMvc;

    private StockValueChange stockValueChange;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockValueChangeResource stockValueChangeResource = new StockValueChangeResource(stockValueChangeService);
        this.restStockValueChangeMockMvc = MockMvcBuilders.standaloneSetup(stockValueChangeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockValueChange createEntity(EntityManager em) {
        StockValueChange stockValueChange = new StockValueChange()
            .time(DEFAULT_TIME)
            .value(DEFAULT_VALUE);
        return stockValueChange;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockValueChange createUpdatedEntity(EntityManager em) {
        StockValueChange stockValueChange = new StockValueChange()
            .time(UPDATED_TIME)
            .value(UPDATED_VALUE);
        return stockValueChange;
    }

    @BeforeEach
    public void initTest() {
        stockValueChange = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockValueChange() throws Exception {
        int databaseSizeBeforeCreate = stockValueChangeRepository.findAll().size();

        // Create the StockValueChange
        StockValueChangeDTO stockValueChangeDTO = stockValueChangeMapper.toDto(stockValueChange);
        restStockValueChangeMockMvc.perform(post("/api/stock-value-changes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockValueChangeDTO)))
            .andExpect(status().isCreated());

        // Validate the StockValueChange in the database
        List<StockValueChange> stockValueChangeList = stockValueChangeRepository.findAll();
        assertThat(stockValueChangeList).hasSize(databaseSizeBeforeCreate + 1);
        StockValueChange testStockValueChange = stockValueChangeList.get(stockValueChangeList.size() - 1);
        assertThat(testStockValueChange.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testStockValueChange.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createStockValueChangeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockValueChangeRepository.findAll().size();

        // Create the StockValueChange with an existing ID
        stockValueChange.setId(1L);
        StockValueChangeDTO stockValueChangeDTO = stockValueChangeMapper.toDto(stockValueChange);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockValueChangeMockMvc.perform(post("/api/stock-value-changes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockValueChangeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockValueChange in the database
        List<StockValueChange> stockValueChangeList = stockValueChangeRepository.findAll();
        assertThat(stockValueChangeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStockValueChanges() throws Exception {
        // Initialize the database
        stockValueChangeRepository.saveAndFlush(stockValueChange);

        // Get all the stockValueChangeList
        restStockValueChangeMockMvc.perform(get("/api/stock-value-changes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockValueChange.getId().intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getStockValueChange() throws Exception {
        // Initialize the database
        stockValueChangeRepository.saveAndFlush(stockValueChange);

        // Get the stockValueChange
        restStockValueChangeMockMvc.perform(get("/api/stock-value-changes/{id}", stockValueChange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stockValueChange.getId().intValue()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingStockValueChange() throws Exception {
        // Get the stockValueChange
        restStockValueChangeMockMvc.perform(get("/api/stock-value-changes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockValueChange() throws Exception {
        // Initialize the database
        stockValueChangeRepository.saveAndFlush(stockValueChange);

        int databaseSizeBeforeUpdate = stockValueChangeRepository.findAll().size();

        // Update the stockValueChange
        StockValueChange updatedStockValueChange = stockValueChangeRepository.findById(stockValueChange.getId()).get();
        // Disconnect from session so that the updates on updatedStockValueChange are not directly saved in db
        em.detach(updatedStockValueChange);
        updatedStockValueChange
            .time(UPDATED_TIME)
            .value(UPDATED_VALUE);
        StockValueChangeDTO stockValueChangeDTO = stockValueChangeMapper.toDto(updatedStockValueChange);

        restStockValueChangeMockMvc.perform(put("/api/stock-value-changes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockValueChangeDTO)))
            .andExpect(status().isOk());

        // Validate the StockValueChange in the database
        List<StockValueChange> stockValueChangeList = stockValueChangeRepository.findAll();
        assertThat(stockValueChangeList).hasSize(databaseSizeBeforeUpdate);
        StockValueChange testStockValueChange = stockValueChangeList.get(stockValueChangeList.size() - 1);
        assertThat(testStockValueChange.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testStockValueChange.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingStockValueChange() throws Exception {
        int databaseSizeBeforeUpdate = stockValueChangeRepository.findAll().size();

        // Create the StockValueChange
        StockValueChangeDTO stockValueChangeDTO = stockValueChangeMapper.toDto(stockValueChange);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockValueChangeMockMvc.perform(put("/api/stock-value-changes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockValueChangeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockValueChange in the database
        List<StockValueChange> stockValueChangeList = stockValueChangeRepository.findAll();
        assertThat(stockValueChangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStockValueChange() throws Exception {
        // Initialize the database
        stockValueChangeRepository.saveAndFlush(stockValueChange);

        int databaseSizeBeforeDelete = stockValueChangeRepository.findAll().size();

        // Delete the stockValueChange
        restStockValueChangeMockMvc.perform(delete("/api/stock-value-changes/{id}", stockValueChange.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StockValueChange> stockValueChangeList = stockValueChangeRepository.findAll();
        assertThat(stockValueChangeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
