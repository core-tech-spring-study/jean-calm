package com.kakaopaysec.itemservicedb

import com.kakaopaysec.itemservicedb.item.domain.repository.ItemRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile

@SpringBootApplication
class ItemserviceDbApplication

fun main(args: Array<String>) {
	runApplication<ItemserviceDbApplication>(*args)
}

@Bean
@Profile("local")
fun testDataInit(itemRepository: ItemRepository): TestDataInit {
	return TestDataInit(itemRepository)
}
