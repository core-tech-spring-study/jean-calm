package com.kakaopaysec.itemservicedb.common.config

import com.kakaopaysec.itemservicedb.item.domain.repository.ItemRepository
import com.kakaopaysec.itemservicedb.item.domain.repository.jpa.JpaItemRepositoryV3
import com.kakaopaysec.itemservicedb.item.domain.repository.v2.ItemQueryRepositoryV2
import com.kakaopaysec.itemservicedb.item.domain.repository.v2.ItemRepositoryV2
import com.kakaopaysec.itemservicedb.item.service.ItemService
import com.kakaopaysec.itemservicedb.item.service.ItemServiceV2
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager

@Configuration
class V2Config(
    private val em: EntityManager,
    private val itemRepositoryV2: ItemRepositoryV2 // Spring Data Jpa Bean
) {

    @Bean
    fun itemService(): ItemService {
        return ItemServiceV2(itemRepositoryV2, itemQueryRepositoryV2())
    }

    @Bean
    fun itemQueryRepositoryV2(): ItemQueryRepositoryV2 {
        return ItemQueryRepositoryV2(em)
    }

    @Bean
    fun itemRepository(): ItemRepository {
        return JpaItemRepositoryV3(em)
    }
}
