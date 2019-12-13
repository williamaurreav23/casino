package at.sebastian.repository
import at.sebastian.domain.SpielerAktieHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/**
 * Spring Data  repository for the [SpielerAktieHistory] entity.
 */
@Suppress("unused")
@Repository
interface SpielerAktieHistoryRepository : JpaRepository<SpielerAktieHistory, Long> {
}
