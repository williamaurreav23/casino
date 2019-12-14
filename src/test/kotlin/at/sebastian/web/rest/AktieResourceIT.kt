package at.sebastian.web.rest

import at.sebastian.CasinoApp
import at.sebastian.domain.Aktie
import at.sebastian.repository.AktieRepository
import at.sebastian.service.AktieService
import at.sebastian.service.dto.AktieDTO
import at.sebastian.service.mapper.AktieMapper
import at.sebastian.web.rest.errors.ExceptionTranslator
import javax.persistence.EntityManager
import kotlin.test.assertNotNull
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.hasItem
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.Validator

/**
 * Integration tests for the [AktieResource] REST controller.
 *
 * @see AktieResource
 */
@SpringBootTest(classes = [CasinoApp::class])
class AktieResourceIT {

    @Autowired
    private lateinit var aktieRepository: AktieRepository

    @Autowired
    private lateinit var aktieMapper: AktieMapper

    @Autowired
    private lateinit var aktieService: AktieService

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

    private lateinit var restAktieMockMvc: MockMvc

    private lateinit var aktie: Aktie

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val aktieResource = AktieResource(aktieService)
        this.restAktieMockMvc = MockMvcBuilders.standaloneSetup(aktieResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build()
    }

    @BeforeEach
    fun initTest() {
        aktie = createEntity(em)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun createAktie() {
        val databaseSizeBeforeCreate = aktieRepository.findAll().size

        // Create the Aktie
        val aktieDTO = aktieMapper.toDto(aktie)
        restAktieMockMvc.perform(
            post("/api/akties")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(aktieDTO))
        ).andExpect(status().isCreated)

        // Validate the Aktie in the database
        val aktieList = aktieRepository.findAll()
        assertThat(aktieList).hasSize(databaseSizeBeforeCreate + 1)
        val testAktie = aktieList[aktieList.size - 1]
        assertThat(testAktie.name).isEqualTo(DEFAULT_NAME)
    }

    @Test
    @Transactional
    fun createAktieWithExistingId() {
        val databaseSizeBeforeCreate = aktieRepository.findAll().size

        // Create the Aktie with an existing ID
        aktie.id = 1L
        val aktieDTO = aktieMapper.toDto(aktie)

        // An entity with an existing ID cannot be created, so this API call must fail
        restAktieMockMvc.perform(
            post("/api/akties")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(aktieDTO))
        ).andExpect(status().isBadRequest)

        // Validate the Aktie in the database
        val aktieList = aktieRepository.findAll()
        assertThat(aktieList).hasSize(databaseSizeBeforeCreate)
    }

    @Test
    @Transactional
    fun getAllAkties() {
        // Initialize the database
        aktieRepository.saveAndFlush(aktie)

        // Get all the aktieList
        restAktieMockMvc.perform(get("/api/akties?sort=id,desc"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aktie.id?.toInt())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
    }

    @Test
    @Transactional
    fun getAktie() {
        // Initialize the database
        aktieRepository.saveAndFlush(aktie)

        val id = aktie.id
        assertNotNull(id)

        // Get the aktie
        restAktieMockMvc.perform(get("/api/akties/{id}", id))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(id.toInt()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
    }

    @Test
    @Transactional
    fun getNonExistingAktie() {
        // Get the aktie
        restAktieMockMvc.perform(get("/api/akties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound)
    }
    @Test
    @Transactional
    fun updateAktie() {
        // Initialize the database
        aktieRepository.saveAndFlush(aktie)

        val databaseSizeBeforeUpdate = aktieRepository.findAll().size

        // Update the aktie
        val id = aktie.id
        assertNotNull(id)
        val updatedAktie = aktieRepository.findById(id).get()
        // Disconnect from session so that the updates on updatedAktie are not directly saved in db
        em.detach(updatedAktie)
        updatedAktie.name = UPDATED_NAME
        val aktieDTO = aktieMapper.toDto(updatedAktie)

        restAktieMockMvc.perform(
            put("/api/akties")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(aktieDTO))
        ).andExpect(status().isOk)

        // Validate the Aktie in the database
        val aktieList = aktieRepository.findAll()
        assertThat(aktieList).hasSize(databaseSizeBeforeUpdate)
        val testAktie = aktieList[aktieList.size - 1]
        assertThat(testAktie.name).isEqualTo(UPDATED_NAME)
    }

    @Test
    @Transactional
    fun updateNonExistingAktie() {
        val databaseSizeBeforeUpdate = aktieRepository.findAll().size

        // Create the Aktie
        val aktieDTO = aktieMapper.toDto(aktie)

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAktieMockMvc.perform(
            put("/api/akties")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(aktieDTO))
        ).andExpect(status().isBadRequest)

        // Validate the Aktie in the database
        val aktieList = aktieRepository.findAll()
        assertThat(aktieList).hasSize(databaseSizeBeforeUpdate)
    }

    @Test
    @Transactional
    fun deleteAktie() {
        // Initialize the database
        aktieRepository.saveAndFlush(aktie)

        val databaseSizeBeforeDelete = aktieRepository.findAll().size

        val id = aktie.id
        assertNotNull(id)

        // Delete the aktie
        restAktieMockMvc.perform(
            delete("/api/akties/{id}", id)
                .accept(APPLICATION_JSON_UTF8)
        ).andExpect(status().isNoContent)

        // Validate the database contains one less item
        val aktieList = aktieRepository.findAll()
        assertThat(aktieList).hasSize(databaseSizeBeforeDelete - 1)
    }

    @Test
    @Transactional
    fun equalsVerifier() {
        equalsVerifier(Aktie::class)
        val aktie1 = Aktie()
        aktie1.id = 1L
        val aktie2 = Aktie()
        aktie2.id = aktie1.id
        assertThat(aktie1).isEqualTo(aktie2)
        aktie2.id = 2L
        assertThat(aktie1).isNotEqualTo(aktie2)
        aktie1.id = null
        assertThat(aktie1).isNotEqualTo(aktie2)
    }

    @Test
    @Transactional
    fun dtoEqualsVerifier() {
        equalsVerifier(AktieDTO::class)
        val aktieDTO1 = AktieDTO()
        aktieDTO1.id = 1L
        val aktieDTO2 = AktieDTO()
        assertThat(aktieDTO1).isNotEqualTo(aktieDTO2)
        aktieDTO2.id = aktieDTO1.id
        assertThat(aktieDTO1).isEqualTo(aktieDTO2)
        aktieDTO2.id = 2L
        assertThat(aktieDTO1).isNotEqualTo(aktieDTO2)
        aktieDTO1.id = null
        assertThat(aktieDTO1).isNotEqualTo(aktieDTO2)
    }

    @Test
    @Transactional
    fun testEntityFromId() {
        assertThat(aktieMapper.fromId(42L)?.id).isEqualTo(42)
        assertThat(aktieMapper.fromId(null)).isNull()
    }

    companion object {

        private const val DEFAULT_NAME: String = "AAAAAAAAAA"
        private const val UPDATED_NAME = "BBBBBBBBBB"

        /**
         * Create an entity for this test.
         *
         * This is a static method, as tests for other entities might also need it,
         * if they test an entity which requires the current entity.
         */
        @JvmStatic
        fun createEntity(em: EntityManager): Aktie {
            val aktie = Aktie(
                name = DEFAULT_NAME
            )

            return aktie
        }

        /**
         * Create an updated entity for this test.
         *
         * This is a static method, as tests for other entities might also need it,
         * if they test an entity which requires the current entity.
         */
        @JvmStatic
        fun createUpdatedEntity(em: EntityManager): Aktie {
            val aktie = Aktie(
                name = UPDATED_NAME
            )

            return aktie
        }
    }
}
