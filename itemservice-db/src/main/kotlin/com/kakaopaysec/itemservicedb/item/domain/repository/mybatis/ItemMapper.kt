package com.kakaopaysec.itemservicedb.item.domain.repository.mybatis

import com.kakaopaysec.itemservicedb.item.domain.entity.Item
import com.kakaopaysec.itemservicedb.item.domain.model.ItemSearchCond
import com.kakaopaysec.itemservicedb.item.domain.model.ItemUpdateDto
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface ItemMapper {

    fun save(item: Item)

    fun update(@Param("id") id: Long, @Param("updateParam") updateParam: ItemUpdateDto)

    fun findAll(itemSearchCond: ItemSearchCond): List<Item>

    fun findById(id: Long): Item?
}
