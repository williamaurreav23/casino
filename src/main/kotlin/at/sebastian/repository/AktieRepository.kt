package at.sebastian.repository
import at.sebastian.domain.Aktie
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Spring Data  repository for the [Aktie] entity.
 */
@Suppress("unused")
@Repository
interface AktieRepository : JpaRepository<Aktie, Long>
