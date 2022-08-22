package com.kakaopaysec.itemservicedb.common.config

import com.kakaopaysec.itemservicedb.item.domain.repository.ItemRepository
import com.kakaopaysec.itemservicedb.item.domain.repository.mybatis.ItemMapper
import com.kakaopaysec.itemservicedb.item.domain.repository.mybatis.MyBatisItemRepository
import com.kakaopaysec.itemservicedb.item.service.ItemService
import com.kakaopaysec.itemservicedb.item.service.ItemServiceV1
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MyBatisConfig(
    private val mapper: ItemMapper
) {

    @Bean
    fun itemService(): ItemService {
        return ItemServiceV1(itemRepository())
    }

    @Bean
    fun itemRepository(): ItemRepository {
        return MyBatisItemRepository(mapper)
    }

}
