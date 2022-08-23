package com.kakaopaysec.itemservicedb.common.config

import com.kakaopaysec.itemservicedb.item.domain.repository.ItemRepository
import com.kakaopaysec.itemservicedb.item.domain.repository.jpa.JpaItemRepositoryV3
import com.kakaopaysec.itemservicedb.item.service.ItemService
import com.kakaopaysec.itemservicedb.item.service.ItemServiceV1
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager

@Configuration
class QuerydslConfig(
    private val em: EntityManager
) {

    @Bean
    fun itemService(): ItemService {
        return ItemServiceV1(itemRepository())
    }

    @Bean
    fun itemRepository(): ItemRepository {
        return JpaItemRepositoryV3(em)
    }
}
