package com.kakaopaysec.itemservicedb

import com.kakaopaysec.itemservicedb.common.config.MemoryConfig
import com.kakaopaysec.itemservicedb.common.config.MyBatisConfig
import com.kakaopaysec.itemservicedb.item.domain.repository.ItemRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile



//@Import(MemoryConfig::class)
@Import(MyBatisConfig::class)
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


