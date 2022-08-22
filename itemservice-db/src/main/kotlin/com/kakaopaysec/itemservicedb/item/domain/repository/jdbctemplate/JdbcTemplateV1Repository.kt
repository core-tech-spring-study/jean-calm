package com.kakaopaysec.itemservicedb.item.domain.repository.jdbctemplate

import com.kakaopaysec.itemservicedb.item.domain.entity.Item
import com.kakaopaysec.itemservicedb.item.domain.model.ItemRes
import com.kakaopaysec.itemservicedb.item.domain.model.ItemSearchCond
import com.kakaopaysec.itemservicedb.item.domain.model.ItemUpdateDto
import com.kakaopaysec.itemservicedb.item.domain.repository.ItemRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import java.sql.Connection
import javax.sql.DataSource


class JdbcTemplateV1Repository(
    private val dataSource: DataSource
): ItemRepository {

    private val template = JdbcTemplate(dataSource)

    override fun save(item: Item): Item {
        val sql = "insert into item(item_name, price, quantity) values(?,?,?)"
        val keyHolder = GeneratedKeyHolder()

        template.update({ connection: Connection ->
            val ps = connection.prepareStatement(sql, arrayOf("id"))
            ps.setString(1, item.itemName)
            ps.setInt(2, item.price)
            ps.setInt(3, item.quantity)
            ps
        }, keyHolder)

        var key = keyHolder.key!!.toLong()
        item.id = key
        return item
    }

    override fun update(itemId: Long, updateParam: ItemUpdateDto) {
        val sql = "update item set item_name=?, price=?, quantity=? where id=?"
        template.update(sql, updateParam.itemName, updateParam.price, updateParam.quantity, itemId)
    }

    override fun findById(id: Long): Item? {
        val sql = "select id, item_name, price, quantity where id = ?"
        return template.queryForObject(sql, itemRowMapper(), id)
    }

    private fun itemRowMapper(): RowMapper<Item> {
        val mapper = RowMapper<Item> { resultSet, rowNum ->
            val createItem = Item.createItem(
                resultSet.getString("item_name"),
                resultSet.getInt("price"),
                resultSet.getInt("quantity")
            )
            createItem.id = resultSet.getLong("id")
            createItem
        }

        return mapper
    }

    override fun findAll(cond: ItemSearchCond): List<ItemRes> {
        val itemName = cond.itemName
        val maxPrice = cond.maxPrice

        val sql = "select id, item_name, price, quantity from item"

        val result = template.query(sql, itemRowMapper()).map {
            val itemRes = ItemRes(it.id, it.itemName, it.price, it.quantity)
            itemRes
        }

        return result
    }
}
