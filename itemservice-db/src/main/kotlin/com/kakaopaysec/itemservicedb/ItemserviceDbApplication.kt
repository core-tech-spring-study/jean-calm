package com.kakaopaysec.itemservicedb

import com.kakaopaysec.itemservicedb.item.domain.repository.ItemRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile

@SpringBootApplication
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


