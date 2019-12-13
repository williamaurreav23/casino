package at.sebastian.repository
import at.sebastian.domain.SpielerTransaktion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/**
 * Spring Data  repository for the [SpielerTransaktion] entity.
 */
@Suppress("unused")
@Repository
interface SpielerTransaktionRepository : JpaRepository<SpielerTransaktion, Long> {
}
