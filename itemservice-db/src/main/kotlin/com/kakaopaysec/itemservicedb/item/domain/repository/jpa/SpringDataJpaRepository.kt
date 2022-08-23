package com.kakaopaysec.itemservicedb.item.domain.repository.jpa

import com.kakaopaysec.itemservicedb.item.domain.entity.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SpringDataJpaRepository: JpaRepository<Item, Long> {

    fun findByItemNameLike(itemName: String): List<Item>

    fun findByPriceLessThanEqual(price: Int): List<Item>

    fun findByItemNameLikeAndPriceIsLessThanEqual(itemName: String, price: Int): List<Item>

    // 쿼리 직접 실행
    @Query("select i from Item i where i.itemName like :itemName and i.price <= :price")
    fun findItems(@Param("itemName") itemName: String, @Param("price") price: Int): List<Item>
}
