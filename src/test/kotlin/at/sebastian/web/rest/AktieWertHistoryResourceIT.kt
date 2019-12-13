package at.sebastian.web.rest

import at.sebastian.CasinoApp
import at.sebastian.domain.AktieWertHistory
import at.sebastian.repository.AktieWertHistoryRepository
import at.sebastian.web.rest.errors.ExceptionTranslator

import kotlin.test.assertNotNull

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.Validator
import javax.persistence.EntityManager
import java.time.Instant
import java.time.temporal.ChronoUnit

import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.hasItem
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


/**
 * Integration tests for the [AktieWertHistoryResource] REST controller.
 *
 * @see AktieWertHistoryResource
 */
@SpringBootTest(classes = [CasinoApp::class])
class AktieWertHistoryResourceIT {

    @Autowired
    private lateinit var aktieWertHistoryRepository: AktieWertHistoryRepository

    @Autowired
    private lateinit var jacksonMessageConverter: MappingJackson2HttpMessageConverter

    @Autowired
    private lateinit var pageableArgumentResolver: PageableHandlerMethodArgumentResolver

    @Autowired
    private lateinit var exceptionTranslator: ExceptionTranslator

    @Autowired
    private lateinit var em: EntityManager

    @Autowired
    private lateinit var validator: Validator

    private lateinit var restAktieWertHistoryMockMvc: MockMvc

    private lateinit var aktieWertHistory: AktieWertHistory

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val aktieWertHistoryResource = AktieWertHistoryResource(aktieWertHistoryRepository)
        this.restAktieWertHistoryMockMvc = MockMvcBuilders.standaloneSetup(aktieWertHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build()
    }

    @BeforeEach
    fun initTest() {
        aktieWertHistory = createEntity(em)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun createAktieWertHistory() {
        val databaseSizeBeforeCreate = aktieWertHistoryRepository.findAll().size

        // Create the AktieWertHistory
        restAktieWertHistoryMockMvc.perform(
            post("/api/aktie-wert-histories")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(aktieWertHistory))
        ).andExpect(status().isCreated)

        // Validate the AktieWertHistory in the database
        val aktieWertHistoryList = aktieWertHistoryRepository.findAll()
        assertThat(aktieWertHistoryList).hasSize(databaseSizeBeforeCreate + 1)
        val testAktieWertHistory = aktieWertHistoryList[aktieWertHistoryList.size - 1]
        assertThat(testAktieWertHistory.wert).isEqualTo(DEFAULT_WERT)
        assertThat(testAktieWertHistory.creationTime).isEqualTo(DEFAULT_CREATION_TIME)
    }

    @Test
    @Transactional
    fun createAktieWertHistoryWithExistingId() {
        val databaseSizeBeforeCreate = aktieWertHistoryRepository.findAll().size

        // Create the AktieWertHistory with an existing ID
        aktieWertHistory.id = 1L

        // An entity with an existing ID cannot be created, so this API call must fail
        restAktieWertHistoryMockMvc.perform(
            post("/api/aktie-wert-histories")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(aktieWertHistory))
        ).andExpect(status().isBadRequest)

        // Validate the AktieWertHistory in the database
        val aktieWertHistoryList = aktieWertHistoryRepository.findAll()
        assertThat(aktieWertHistoryList).hasSize(databaseSizeBeforeCreate)
    }


    @Test
    @Transactional
    fun getAllAktieWertHistories() {
        // Initialize the database
        aktieWertHistoryRepository.saveAndFlush(aktieWertHistory)

        // Get all the aktieWertHistoryList
        restAktieWertHistoryMockMvc.perform(get("/api/aktie-wert-histories?sort=id,desc"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aktieWertHistory.id?.toInt())))
            .andExpect(jsonPath("$.[*].wert").value(hasItem(DEFAULT_WERT)))
            .andExpect(jsonPath("$.[*].creationTime").value(hasItem(DEFAULT_CREATION_TIME.toString())))
    }
    
    @Test
    @Transactional
    fun getAktieWertHistory() {
        // Initialize the database
        aktieWertHistoryRepository.saveAndFlush(aktieWertHistory)

        val id = aktieWertHistory.id
        assertNotNull(id)

        // Get the aktieWertHistory
        restAktieWertHistoryMockMvc.perform(get("/api/aktie-wert-histories/{id}", id))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(id.toInt()))
            .andExpect(jsonPath("$.wert").value(DEFAULT_WERT))
            .andExpect(jsonPath("$.creationTime").value(DEFAULT_CREATION_TIME.toString()))
    }

    @Test
    @Transactional
    fun getNonExistingAktieWertHistory() {
        // Get the aktieWertHistory
        restAktieWertHistoryMockMvc.perform(get("/api/aktie-wert-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound)
    }
    @Test
    @Transactional
    fun updateAktieWertHistory() {
        // Initialize the database
        aktieWertHistoryRepository.saveAndFlush(aktieWertHistory)

        val databaseSizeBeforeUpdate = aktieWertHistoryRepository.findAll().size

        // Update the aktieWertHistory
        val id = aktieWertHistory.id
        assertNotNull(id)
        val updatedAktieWertHistory = aktieWertHistoryRepository.findById(id).get()
        // Disconnect from session so that the updates on updatedAktieWertHistory are not directly saved in db
        em.detach(updatedAktieWertHistory)
        updatedAktieWertHistory.wert = UPDATED_WERT
        updatedAktieWertHistory.creationTime = UPDATED_CREATION_TIME

        restAktieWertHistoryMockMvc.perform(
            put("/api/aktie-wert-histories")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(updatedAktieWertHistory))
        ).andExpect(status().isOk)

        // Validate the AktieWertHistory in the database
        val aktieWertHistoryList = aktieWertHistoryRepository.findAll()
        assertThat(aktieWertHistoryList).hasSize(databaseSizeBeforeUpdate)
        val testAktieWertHistory = aktieWertHistoryList[aktieWertHistoryList.size - 1]
        assertThat(testAktieWertHistory.wert).isEqualTo(UPDATED_WERT)
        assertThat(testAktieWertHistory.creationTime).isEqualTo(UPDATED_CREATION_TIME)
    }

    @Test
    @Transactional
    fun updateNonExistingAktieWertHistory() {
        val databaseSizeBeforeUpdate = aktieWertHistoryRepository.findAll().size

        // Create the AktieWertHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAktieWertHistoryMockMvc.perform(
            put("/api/aktie-wert-histories")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(aktieWertHistory))
        ).andExpect(status().isBadRequest)

        // Validate the AktieWertHistory in the database
        val aktieWertHistoryList = aktieWertHistoryRepository.findAll()
        assertThat(aktieWertHistoryList).hasSize(databaseSizeBeforeUpdate)
    }

    @Test
    @Transactional
    fun deleteAktieWertHistory() {
        // Initialize the database
        aktieWertHistoryRepository.saveAndFlush(aktieWertHistory)

        val databaseSizeBeforeDelete = aktieWertHistoryRepository.findAll().size

        val id = aktieWertHistory.id
        assertNotNull(id)

        // Delete the aktieWertHistory
        restAktieWertHistoryMockMvc.perform(
            delete("/api/aktie-wert-histories/{id}", id)
                .accept(APPLICATION_JSON_UTF8)
        ).andExpect(status().isNoContent)

        // Validate the database contains one less item
        val aktieWertHistoryList = aktieWertHistoryRepository.findAll()
        assertThat(aktieWertHistoryList).hasSize(databaseSizeBeforeDelete - 1)
    }

    @Test
    @Transactional
    fun equalsVerifier() {
        equalsVerifier(AktieWertHistory::class)
        val aktieWertHistory1 = AktieWertHistory()
        aktieWertHistory1.id = 1L
        val aktieWertHistory2 = AktieWertHistory()
        aktieWertHistory2.id = aktieWertHistory1.id
        assertThat(aktieWertHistory1).isEqualTo(aktieWertHistory2)
        aktieWertHistory2.id = 2L
        assertThat(aktieWertHistory1).isNotEqualTo(aktieWertHistory2)
        aktieWertHistory1.id = null
        assertThat(aktieWertHistory1).isNotEqualTo(aktieWertHistory2)
    }

    companion object {

        private const val DEFAULT_WERT: Int = 1
        private const val UPDATED_WERT: Int = 2

        private val DEFAULT_CREATION_TIME: Instant = Instant.ofEpochMilli(0L)
        private val UPDATED_CREATION_TIME: Instant = Instant.now().truncatedTo(ChronoUnit.MILLIS)

        /**
         * Create an entity for this test.
         *
         * This is a static method, as tests for other entities might also need it,
         * if they test an entity which requires the current entity.
         */
        @JvmStatic
        fun createEntity(em: EntityManager): AktieWertHistory {
            val aktieWertHistory = AktieWertHistory(
                wert = DEFAULT_WERT,
                creationTime = DEFAULT_CREATION_TIME
            )

            return aktieWertHistory
        }

        /**
         * Create an updated entity for this test.
         *
         * This is a static method, as tests for other entities might also need it,
         * if they test an entity which requires the current entity.
         */
        @JvmStatic
        fun createUpdatedEntity(em: EntityManager): AktieWertHistory {
            val aktieWertHistory = AktieWertHistory(
                wert = UPDATED_WERT,
                creationTime = UPDATED_CREATION_TIME
            )

            return aktieWertHistory
        }
    }
}
