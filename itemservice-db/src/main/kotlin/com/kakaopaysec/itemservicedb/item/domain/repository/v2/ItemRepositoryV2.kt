package com.kakaopaysec.itemservicedb.item.domain.repository.v2

import com.kakaopaysec.itemservicedb.item.domain.entity.Item
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepositoryV2: JpaRepository<Item, Long>
