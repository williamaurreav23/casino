package at.sebastian.web.rest

import at.sebastian.CasinoApp
import at.sebastian.domain.Spieler
import at.sebastian.repository.SpielerRepository
import at.sebastian.service.SpielerService
import at.sebastian.service.dto.SpielerDTO
import at.sebastian.service.mapper.SpielerMapper
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
 * Integration tests for the [SpielerResource] REST controller.
 *
 * @see SpielerResource
 */
@SpringBootTest(classes = [CasinoApp::class])
class SpielerResourceIT {

    @Autowired
    private lateinit var spielerRepository: SpielerRepository

    @Autowired
    private lateinit var spielerMapper: SpielerMapper

    @Autowired
    private lateinit var spielerService: SpielerService

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

    private lateinit var restSpielerMockMvc: MockMvc

    private lateinit var spieler: Spieler

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val spielerResource = SpielerResource(spielerService)
        this.restSpielerMockMvc = MockMvcBuilders.standaloneSetup(spielerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build()
    }

    @BeforeEach
    fun initTest() {
        spieler = createEntity(em)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun createSpieler() {
        val databaseSizeBeforeCreate = spielerRepository.findAll().size

        // Create the Spieler
        val spielerDTO = spielerMapper.toDto(spieler)
        restSpielerMockMvc.perform(
            post("/api/spielers")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(spielerDTO))
        ).andExpect(status().isCreated)

        // Validate the Spieler in the database
        val spielerList = spielerRepository.findAll()
        assertThat(spielerList).hasSize(databaseSizeBeforeCreate + 1)
        val testSpieler = spielerList[spielerList.size - 1]
        assertThat(testSpieler.vorname).isEqualTo(DEFAULT_VORNAME)
        assertThat(testSpieler.nachname).isEqualTo(DEFAULT_NACHNAME)
        assertThat(testSpieler.isKind).isEqualTo(DEFAULT_IS_KIND)
        assertThat(testSpieler.kennzahl).isEqualTo(DEFAULT_KENNZAHL)
    }

    @Test
    @Transactional
    fun createSpielerWithExistingId() {
        val databaseSizeBeforeCreate = spielerRepository.findAll().size

        // Create the Spieler with an existing ID
        spieler.id = 1L
        val spielerDTO = spielerMapper.toDto(spieler)

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpielerMockMvc.perform(
            post("/api/spielers")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(spielerDTO))
        ).andExpect(status().isBadRequest)

        // Validate the Spieler in the database
        val spielerList = spielerRepository.findAll()
        assertThat(spielerList).hasSize(databaseSizeBeforeCreate)
    }


    @Test
    @Transactional
    fun getAllSpielers() {
        // Initialize the database
        spielerRepository.saveAndFlush(spieler)

        // Get all the spielerList
        restSpielerMockMvc.perform(get("/api/spielers?sort=id,desc"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(spieler.id?.toInt())))
            .andExpect(jsonPath("$.[*].vorname").value(hasItem(DEFAULT_VORNAME)))
            .andExpect(jsonPath("$.[*].nachname").value(hasItem(DEFAULT_NACHNAME)))
            .andExpect(jsonPath("$.[*].isKind").value(hasItem(DEFAULT_IS_KIND)))
            .andExpect(jsonPath("$.[*].kennzahl").value(hasItem(DEFAULT_KENNZAHL)))
    }
    
    @Test
    @Transactional
    fun getSpieler() {
        // Initialize the database
        spielerRepository.saveAndFlush(spieler)

        val id = spieler.id
        assertNotNull(id)

        // Get the spieler
        restSpielerMockMvc.perform(get("/api/spielers/{id}", id))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(id.toInt()))
            .andExpect(jsonPath("$.vorname").value(DEFAULT_VORNAME))
            .andExpect(jsonPath("$.nachname").value(DEFAULT_NACHNAME))
            .andExpect(jsonPath("$.isKind").value(DEFAULT_IS_KIND))
            .andExpect(jsonPath("$.kennzahl").value(DEFAULT_KENNZAHL))
    }

    @Test
    @Transactional
    fun getNonExistingSpieler() {
        // Get the spieler
        restSpielerMockMvc.perform(get("/api/spielers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound)
    }
    @Test
    @Transactional
    fun updateSpieler() {
        // Initialize the database
        spielerRepository.saveAndFlush(spieler)

        val databaseSizeBeforeUpdate = spielerRepository.findAll().size

        // Update the spieler
        val id = spieler.id
        assertNotNull(id)
        val updatedSpieler = spielerRepository.findById(id).get()
        // Disconnect from session so that the updates on updatedSpieler are not directly saved in db
        em.detach(updatedSpieler)
        updatedSpieler.vorname = UPDATED_VORNAME
        updatedSpieler.nachname = UPDATED_NACHNAME
        updatedSpieler.isKind = UPDATED_IS_KIND
        updatedSpieler.kennzahl = UPDATED_KENNZAHL
        val spielerDTO = spielerMapper.toDto(updatedSpieler)

        restSpielerMockMvc.perform(
            put("/api/spielers")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(spielerDTO))
        ).andExpect(status().isOk)

        // Validate the Spieler in the database
        val spielerList = spielerRepository.findAll()
        assertThat(spielerList).hasSize(databaseSizeBeforeUpdate)
        val testSpieler = spielerList[spielerList.size - 1]
        assertThat(testSpieler.vorname).isEqualTo(UPDATED_VORNAME)
        assertThat(testSpieler.nachname).isEqualTo(UPDATED_NACHNAME)
        assertThat(testSpieler.isKind).isEqualTo(UPDATED_IS_KIND)
        assertThat(testSpieler.kennzahl).isEqualTo(UPDATED_KENNZAHL)
    }

    @Test
    @Transactional
    fun updateNonExistingSpieler() {
        val databaseSizeBeforeUpdate = spielerRepository.findAll().size

        // Create the Spieler
        val spielerDTO = spielerMapper.toDto(spieler)

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpielerMockMvc.perform(
            put("/api/spielers")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(spielerDTO))
        ).andExpect(status().isBadRequest)

        // Validate the Spieler in the database
        val spielerList = spielerRepository.findAll()
        assertThat(spielerList).hasSize(databaseSizeBeforeUpdate)
    }

    @Test
    @Transactional
    fun deleteSpieler() {
        // Initialize the database
        spielerRepository.saveAndFlush(spieler)

        val databaseSizeBeforeDelete = spielerRepository.findAll().size

        val id = spieler.id
        assertNotNull(id)

        // Delete the spieler
        restSpielerMockMvc.perform(
            delete("/api/spielers/{id}", id)
                .accept(APPLICATION_JSON_UTF8)
        ).andExpect(status().isNoContent)

        // Validate the database contains one less item
        val spielerList = spielerRepository.findAll()
        assertThat(spielerList).hasSize(databaseSizeBeforeDelete - 1)
    }

    @Test
    @Transactional
    fun equalsVerifier() {
        equalsVerifier(Spieler::class)
        val spieler1 = Spieler()
        spieler1.id = 1L
        val spieler2 = Spieler()
        spieler2.id = spieler1.id
        assertThat(spieler1).isEqualTo(spieler2)
        spieler2.id = 2L
        assertThat(spieler1).isNotEqualTo(spieler2)
        spieler1.id = null
        assertThat(spieler1).isNotEqualTo(spieler2)
    }

    @Test
    @Transactional
    fun dtoEqualsVerifier() {
        equalsVerifier(SpielerDTO::class)
        val spielerDTO1 = SpielerDTO()
        spielerDTO1.id = 1L
        val spielerDTO2 = SpielerDTO()
        assertThat(spielerDTO1).isNotEqualTo(spielerDTO2)
        spielerDTO2.id = spielerDTO1.id
        assertThat(spielerDTO1).isEqualTo(spielerDTO2)
        spielerDTO2.id = 2L
        assertThat(spielerDTO1).isNotEqualTo(spielerDTO2)
        spielerDTO1.id = null
        assertThat(spielerDTO1).isNotEqualTo(spielerDTO2)
    }

    @Test
    @Transactional
    fun testEntityFromId() {
        assertThat(spielerMapper.fromId(42L)?.id).isEqualTo(42)
        assertThat(spielerMapper.fromId(null)).isNull()
    }

    companion object {

        private const val DEFAULT_VORNAME: String = "AAAAAAAAAA"
        private const val UPDATED_VORNAME = "BBBBBBBBBB"

        private const val DEFAULT_NACHNAME: String = "AAAAAAAAAA"
        private const val UPDATED_NACHNAME = "BBBBBBBBBB"

        private const val DEFAULT_IS_KIND: Boolean = false
        private const val UPDATED_IS_KIND: Boolean = true

        private const val DEFAULT_KENNZAHL: Int = 1
        private const val UPDATED_KENNZAHL: Int = 2

        /**
         * Create an entity for this test.
         *
         * This is a static method, as tests for other entities might also need it,
         * if they test an entity which requires the current entity.
         */
        @JvmStatic
        fun createEntity(em: EntityManager): Spieler {
            val spieler = Spieler(
                vorname = DEFAULT_VORNAME,
                nachname = DEFAULT_NACHNAME,
                isKind = DEFAULT_IS_KIND,
                kennzahl = DEFAULT_KENNZAHL
            )

            return spieler
        }

        /**
         * Create an updated entity for this test.
         *
         * This is a static method, as tests for other entities might also need it,
         * if they test an entity which requires the current entity.
         */
        @JvmStatic
        fun createUpdatedEntity(em: EntityManager): Spieler {
            val spieler = Spieler(
                vorname = UPDATED_VORNAME,
                nachname = UPDATED_NACHNAME,
                isKind = UPDATED_IS_KIND,
                kennzahl = UPDATED_KENNZAHL
            )

            return spieler
        }
    }
}
