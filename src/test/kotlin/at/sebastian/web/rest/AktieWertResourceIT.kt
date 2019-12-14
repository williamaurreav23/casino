package at.sebastian.web.rest

import at.sebastian.CasinoApp
import at.sebastian.domain.AktieWert
import at.sebastian.repository.AktieWertRepository
import at.sebastian.service.AktieWertService
import at.sebastian.service.dto.AktieWertDTO
import at.sebastian.service.mapper.AktieWertMapper
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
 * Integration tests for the [AktieWertResource] REST controller.
 *
 * @see AktieWertResource
 */
@SpringBootTest(classes = [CasinoApp::class])
class AktieWertResourceIT {

    @Autowired
    private lateinit var aktieWertRepository: AktieWertRepository

    @Autowired
    private lateinit var aktieWertMapper: AktieWertMapper

    @Autowired
    private lateinit var aktieWertService: AktieWertService

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

    private lateinit var restAktieWertMockMvc: MockMvc

    private lateinit var aktieWert: AktieWert

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val aktieWertResource = AktieWertResource(aktieWertService)
        this.restAktieWertMockMvc = MockMvcBuilders.standaloneSetup(aktieWertResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build()
    }

    @BeforeEach
    fun initTest() {
        aktieWert = createEntity(em)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun createAktieWert() {
        val databaseSizeBeforeCreate = aktieWertRepository.findAll().size

        // Create the AktieWert
        val aktieWertDTO = aktieWertMapper.toDto(aktieWert)
        restAktieWertMockMvc.perform(
            post("/api/aktie-werts")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(aktieWertDTO))
        ).andExpect(status().isCreated)

        // Validate the AktieWert in the database
        val aktieWertList = aktieWertRepository.findAll()
        assertThat(aktieWertList).hasSize(databaseSizeBeforeCreate + 1)
        val testAktieWert = aktieWertList[aktieWertList.size - 1]
        assertThat(testAktieWert.wert).isEqualTo(DEFAULT_WERT)
    }

    @Test
    @Transactional
    fun createAktieWertWithExistingId() {
        val databaseSizeBeforeCreate = aktieWertRepository.findAll().size

        // Create the AktieWert with an existing ID
        aktieWert.id = 1L
        val aktieWertDTO = aktieWertMapper.toDto(aktieWert)

        // An entity with an existing ID cannot be created, so this API call must fail
        restAktieWertMockMvc.perform(
            post("/api/aktie-werts")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(aktieWertDTO))
        ).andExpect(status().isBadRequest)

        // Validate the AktieWert in the database
        val aktieWertList = aktieWertRepository.findAll()
        assertThat(aktieWertList).hasSize(databaseSizeBeforeCreate)
    }

    @Test
    @Transactional
    fun getAllAktieWerts() {
        // Initialize the database
        aktieWertRepository.saveAndFlush(aktieWert)

        // Get all the aktieWertList
        restAktieWertMockMvc.perform(get("/api/aktie-werts?sort=id,desc"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aktieWert.id?.toInt())))
            .andExpect(jsonPath("$.[*].wert").value(hasItem(DEFAULT_WERT)))
    }

    @Test
    @Transactional
    fun getAktieWert() {
        // Initialize the database
        aktieWertRepository.saveAndFlush(aktieWert)

        val id = aktieWert.id
        assertNotNull(id)

        // Get the aktieWert
        restAktieWertMockMvc.perform(get("/api/aktie-werts/{id}", id))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(id.toInt()))
            .andExpect(jsonPath("$.wert").value(DEFAULT_WERT))
    }

    @Test
    @Transactional
    fun getNonExistingAktieWert() {
        // Get the aktieWert
        restAktieWertMockMvc.perform(get("/api/aktie-werts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound)
    }
    @Test
    @Transactional
    fun updateAktieWert() {
        // Initialize the database
        aktieWertRepository.saveAndFlush(aktieWert)

        val databaseSizeBeforeUpdate = aktieWertRepository.findAll().size

        // Update the aktieWert
        val id = aktieWert.id
        assertNotNull(id)
        val updatedAktieWert = aktieWertRepository.findById(id).get()
        // Disconnect from session so that the updates on updatedAktieWert are not directly saved in db
        em.detach(updatedAktieWert)
        updatedAktieWert.wert = UPDATED_WERT
        val aktieWertDTO = aktieWertMapper.toDto(updatedAktieWert)

        restAktieWertMockMvc.perform(
            put("/api/aktie-werts")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(aktieWertDTO))
        ).andExpect(status().isOk)

        // Validate the AktieWert in the database
        val aktieWertList = aktieWertRepository.findAll()
        assertThat(aktieWertList).hasSize(databaseSizeBeforeUpdate)
        val testAktieWert = aktieWertList[aktieWertList.size - 1]
        assertThat(testAktieWert.wert).isEqualTo(UPDATED_WERT)
    }

    @Test
    @Transactional
    fun updateNonExistingAktieWert() {
        val databaseSizeBeforeUpdate = aktieWertRepository.findAll().size

        // Create the AktieWert
        val aktieWertDTO = aktieWertMapper.toDto(aktieWert)

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAktieWertMockMvc.perform(
            put("/api/aktie-werts")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(aktieWertDTO))
        ).andExpect(status().isBadRequest)

        // Validate the AktieWert in the database
        val aktieWertList = aktieWertRepository.findAll()
        assertThat(aktieWertList).hasSize(databaseSizeBeforeUpdate)
    }

    @Test
    @Transactional
    fun deleteAktieWert() {
        // Initialize the database
        aktieWertRepository.saveAndFlush(aktieWert)

        val databaseSizeBeforeDelete = aktieWertRepository.findAll().size

        val id = aktieWert.id
        assertNotNull(id)

        // Delete the aktieWert
        restAktieWertMockMvc.perform(
            delete("/api/aktie-werts/{id}", id)
                .accept(APPLICATION_JSON_UTF8)
        ).andExpect(status().isNoContent)

        // Validate the database contains one less item
        val aktieWertList = aktieWertRepository.findAll()
        assertThat(aktieWertList).hasSize(databaseSizeBeforeDelete - 1)
    }

    @Test
    @Transactional
    fun equalsVerifier() {
        equalsVerifier(AktieWert::class)
        val aktieWert1 = AktieWert()
        aktieWert1.id = 1L
        val aktieWert2 = AktieWert()
        aktieWert2.id = aktieWert1.id
        assertThat(aktieWert1).isEqualTo(aktieWert2)
        aktieWert2.id = 2L
        assertThat(aktieWert1).isNotEqualTo(aktieWert2)
        aktieWert1.id = null
        assertThat(aktieWert1).isNotEqualTo(aktieWert2)
    }

    @Test
    @Transactional
    fun dtoEqualsVerifier() {
        equalsVerifier(AktieWertDTO::class)
        val aktieWertDTO1 = AktieWertDTO()
        aktieWertDTO1.id = 1L
        val aktieWertDTO2 = AktieWertDTO()
        assertThat(aktieWertDTO1).isNotEqualTo(aktieWertDTO2)
        aktieWertDTO2.id = aktieWertDTO1.id
        assertThat(aktieWertDTO1).isEqualTo(aktieWertDTO2)
        aktieWertDTO2.id = 2L
        assertThat(aktieWertDTO1).isNotEqualTo(aktieWertDTO2)
        aktieWertDTO1.id = null
        assertThat(aktieWertDTO1).isNotEqualTo(aktieWertDTO2)
    }

    @Test
    @Transactional
    fun testEntityFromId() {
        assertThat(aktieWertMapper.fromId(42L)?.id).isEqualTo(42)
        assertThat(aktieWertMapper.fromId(null)).isNull()
    }

    companion object {

        private const val DEFAULT_WERT: Int = 1
        private const val UPDATED_WERT: Int = 2

        /**
         * Create an entity for this test.
         *
         * This is a static method, as tests for other entities might also need it,
         * if they test an entity which requires the current entity.
         */
        @JvmStatic
        fun createEntity(em: EntityManager): AktieWert {
            val aktieWert = AktieWert(
                wert = DEFAULT_WERT
            )

            return aktieWert
        }

        /**
         * Create an updated entity for this test.
         *
         * This is a static method, as tests for other entities might also need it,
         * if they test an entity which requires the current entity.
         */
        @JvmStatic
        fun createUpdatedEntity(em: EntityManager): AktieWert {
            val aktieWert = AktieWert(
                wert = UPDATED_WERT
            )

            return aktieWert
        }
    }
}
