package at.steiner.casino.web.rest;

import at.steiner.casino.CasinoApp;
import at.steiner.casino.domain.PlayerMoneyTransaction;
import at.steiner.casino.repository.PlayerMoneyTransactionRepository;
import at.steiner.casino.service.PlayerMoneyTransactionService;
import at.steiner.casino.service.dto.PlayerMoneyTransactionDTO;
import at.steiner.casino.service.mapper.PlayerMoneyTransactionMapper;
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

import at.steiner.casino.domain.enumeration.Transaction;
/**
 * Integration tests for the {@link PlayerMoneyTransactionResource} REST controller.
 */
@SpringBootTest(classes = CasinoApp.class)
public class PlayerMoneyTransactionResourceIT {

    private static final Instant DEFAULT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Transaction DEFAULT_TRANSACTION = Transaction.START;
    private static final Transaction UPDATED_TRANSACTION = Transaction.ROULETTE;

    private static final Integer DEFAULT_VALUE = 1;
    private static final Integer UPDATED_VALUE = 2;

    @Autowired
    private PlayerMoneyTransactionRepository playerMoneyTransactionRepository;

    @Autowired
    private PlayerMoneyTransactionMapper playerMoneyTransactionMapper;

    @Autowired
    private PlayerMoneyTransactionService playerMoneyTransactionService;

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

    private MockMvc restPlayerMoneyTransactionMockMvc;

    private PlayerMoneyTransaction playerMoneyTransaction;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlayerMoneyTransactionResource playerMoneyTransactionResource = new PlayerMoneyTransactionResource(playerMoneyTransactionService);
        this.restPlayerMoneyTransactionMockMvc = MockMvcBuilders.standaloneSetup(playerMoneyTransactionResource)
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
    public static PlayerMoneyTransaction createEntity(EntityManager em) {
        PlayerMoneyTransaction playerMoneyTransaction = new PlayerMoneyTransaction()
            .time(DEFAULT_TIME)
            .transaction(DEFAULT_TRANSACTION)
            .value(DEFAULT_VALUE);
        return playerMoneyTransaction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlayerMoneyTransaction createUpdatedEntity(EntityManager em) {
        PlayerMoneyTransaction playerMoneyTransaction = new PlayerMoneyTransaction()
            .time(UPDATED_TIME)
            .transaction(UPDATED_TRANSACTION)
            .value(UPDATED_VALUE);
        return playerMoneyTransaction;
    }

    @BeforeEach
    public void initTest() {
        playerMoneyTransaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlayerMoneyTransaction() throws Exception {
        int databaseSizeBeforeCreate = playerMoneyTransactionRepository.findAll().size();

        // Create the PlayerMoneyTransaction
        PlayerMoneyTransactionDTO playerMoneyTransactionDTO = playerMoneyTransactionMapper.toDto(playerMoneyTransaction);
        restPlayerMoneyTransactionMockMvc.perform(post("/api/player-money-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerMoneyTransactionDTO)))
            .andExpect(status().isCreated());

        // Validate the PlayerMoneyTransaction in the database
        List<PlayerMoneyTransaction> playerMoneyTransactionList = playerMoneyTransactionRepository.findAll();
        assertThat(playerMoneyTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        PlayerMoneyTransaction testPlayerMoneyTransaction = playerMoneyTransactionList.get(playerMoneyTransactionList.size() - 1);
        assertThat(testPlayerMoneyTransaction.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testPlayerMoneyTransaction.getTransaction()).isEqualTo(DEFAULT_TRANSACTION);
        assertThat(testPlayerMoneyTransaction.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createPlayerMoneyTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = playerMoneyTransactionRepository.findAll().size();

        // Create the PlayerMoneyTransaction with an existing ID
        playerMoneyTransaction.setId(1L);
        PlayerMoneyTransactionDTO playerMoneyTransactionDTO = playerMoneyTransactionMapper.toDto(playerMoneyTransaction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlayerMoneyTransactionMockMvc.perform(post("/api/player-money-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerMoneyTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlayerMoneyTransaction in the database
        List<PlayerMoneyTransaction> playerMoneyTransactionList = playerMoneyTransactionRepository.findAll();
        assertThat(playerMoneyTransactionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPlayerMoneyTransactions() throws Exception {
        // Initialize the database
        playerMoneyTransactionRepository.saveAndFlush(playerMoneyTransaction);

        // Get all the playerMoneyTransactionList
        restPlayerMoneyTransactionMockMvc.perform(get("/api/player-money-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(playerMoneyTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())))
            .andExpect(jsonPath("$.[*].transaction").value(hasItem(DEFAULT_TRANSACTION.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getPlayerMoneyTransaction() throws Exception {
        // Initialize the database
        playerMoneyTransactionRepository.saveAndFlush(playerMoneyTransaction);

        // Get the playerMoneyTransaction
        restPlayerMoneyTransactionMockMvc.perform(get("/api/player-money-transactions/{id}", playerMoneyTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(playerMoneyTransaction.getId().intValue()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()))
            .andExpect(jsonPath("$.transaction").value(DEFAULT_TRANSACTION.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingPlayerMoneyTransaction() throws Exception {
        // Get the playerMoneyTransaction
        restPlayerMoneyTransactionMockMvc.perform(get("/api/player-money-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlayerMoneyTransaction() throws Exception {
        // Initialize the database
        playerMoneyTransactionRepository.saveAndFlush(playerMoneyTransaction);

        int databaseSizeBeforeUpdate = playerMoneyTransactionRepository.findAll().size();

        // Update the playerMoneyTransaction
        PlayerMoneyTransaction updatedPlayerMoneyTransaction = playerMoneyTransactionRepository.findById(playerMoneyTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedPlayerMoneyTransaction are not directly saved in db
        em.detach(updatedPlayerMoneyTransaction);
        updatedPlayerMoneyTransaction
            .time(UPDATED_TIME)
            .transaction(UPDATED_TRANSACTION)
            .value(UPDATED_VALUE);
        PlayerMoneyTransactionDTO playerMoneyTransactionDTO = playerMoneyTransactionMapper.toDto(updatedPlayerMoneyTransaction);

        restPlayerMoneyTransactionMockMvc.perform(put("/api/player-money-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerMoneyTransactionDTO)))
            .andExpect(status().isOk());

        // Validate the PlayerMoneyTransaction in the database
        List<PlayerMoneyTransaction> playerMoneyTransactionList = playerMoneyTransactionRepository.findAll();
        assertThat(playerMoneyTransactionList).hasSize(databaseSizeBeforeUpdate);
        PlayerMoneyTransaction testPlayerMoneyTransaction = playerMoneyTransactionList.get(playerMoneyTransactionList.size() - 1);
        assertThat(testPlayerMoneyTransaction.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testPlayerMoneyTransaction.getTransaction()).isEqualTo(UPDATED_TRANSACTION);
        assertThat(testPlayerMoneyTransaction.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingPlayerMoneyTransaction() throws Exception {
        int databaseSizeBeforeUpdate = playerMoneyTransactionRepository.findAll().size();

        // Create the PlayerMoneyTransaction
        PlayerMoneyTransactionDTO playerMoneyTransactionDTO = playerMoneyTransactionMapper.toDto(playerMoneyTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayerMoneyTransactionMockMvc.perform(put("/api/player-money-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerMoneyTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlayerMoneyTransaction in the database
        List<PlayerMoneyTransaction> playerMoneyTransactionList = playerMoneyTransactionRepository.findAll();
        assertThat(playerMoneyTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlayerMoneyTransaction() throws Exception {
        // Initialize the database
        playerMoneyTransactionRepository.saveAndFlush(playerMoneyTransaction);

        int databaseSizeBeforeDelete = playerMoneyTransactionRepository.findAll().size();

        // Delete the playerMoneyTransaction
        restPlayerMoneyTransactionMockMvc.perform(delete("/api/player-money-transactions/{id}", playerMoneyTransaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlayerMoneyTransaction> playerMoneyTransactionList = playerMoneyTransactionRepository.findAll();
        assertThat(playerMoneyTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
