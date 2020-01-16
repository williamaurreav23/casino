package at.steiner.casino.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, at.steiner.casino.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, at.steiner.casino.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, at.steiner.casino.domain.User.class.getName());
            createCache(cm, at.steiner.casino.domain.Authority.class.getName());
            createCache(cm, at.steiner.casino.domain.User.class.getName() + ".authorities");
            createCache(cm, at.steiner.casino.domain.Player.class.getName());
            createCache(cm, at.steiner.casino.domain.Player.class.getName() + ".playerMoneyTransactions");
            createCache(cm, at.steiner.casino.domain.Player.class.getName() + ".playerStockTransactions");
            createCache(cm, at.steiner.casino.domain.Player.class.getName() + ".playerStocks");
            createCache(cm, at.steiner.casino.domain.Stock.class.getName());
            createCache(cm, at.steiner.casino.domain.Stock.class.getName() + ".playerStockTransactions");
            createCache(cm, at.steiner.casino.domain.Stock.class.getName() + ".playerStocks");
            createCache(cm, at.steiner.casino.domain.Stock.class.getName() + ".stockValueChanges");
            createCache(cm, at.steiner.casino.domain.GameEnded.class.getName());
            createCache(cm, at.steiner.casino.domain.PlayerMoneyTransaction.class.getName());
            createCache(cm, at.steiner.casino.domain.PlayerStockTransaction.class.getName());
            createCache(cm, at.steiner.casino.domain.PlayerStock.class.getName());
            createCache(cm, at.steiner.casino.domain.StockValueChange.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
