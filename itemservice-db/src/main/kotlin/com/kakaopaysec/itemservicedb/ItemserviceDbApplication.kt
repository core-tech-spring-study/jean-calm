package com.kakaopaysec.itemservicedb

import com.kakaopaysec.itemservicedb.common.config.*
import com.kakaopaysec.itemservicedb.item.domain.repository.ItemRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile



//@Import(MemoryConfig::class)
//@Import(MyBatisConfig::class)
//@Import(JpaConfig::class)
//@Import(SpringDataJpaConfig::class)
//@Import(QuerydslConfig::class)
@Import(V2Config::class)
@SpringBootApplication(scanBasePackages = ["com.kakaopaysec.itemservicedb.item.adapter.in.web"])
class ItemserviceDbApplication {

	@Profile("local")
	@Bean
	fun testDataInit(itemRepository: ItemRepository): TestDataInit {
		println("bean init")
		return TestDataInit(itemRepository)
	}
}

fun main(args: Array<String>) {
	runApplication<ItemserviceDbApplication>(*args)
}


