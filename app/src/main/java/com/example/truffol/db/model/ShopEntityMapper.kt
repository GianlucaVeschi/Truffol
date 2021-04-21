package com.example.truffol.db.model

import com.example.truffol.domain.model.Shop
import com.example.truffol.domain.util.DomainMapper

class ShopEntityMapper : DomainMapper<Shop, ShopEntity> {

    override fun mapToDomainModel(model: Shop): ShopEntity {
        return ShopEntity(
            id = model.id,
            shopName = model.shopName,
            description = model.description,
            image_url = model.image_url,
            website = model.website,
            location = model.location,
            email = model.email,
            phone = model.phone
        )
    }

    override fun mapFromDomainModel(domainModel: ShopEntity): Shop {
        return Shop(
            id = domainModel.id,
            shopName = domainModel.shopName,
            description = domainModel.description,
            image_url = domainModel.image_url,
            website = domainModel.website,
            location = domainModel.location,
            email = domainModel.email,
            phone = domainModel.phone
        )
    }

    fun toEntityList(initial: List<Shop>): List<ShopEntity>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromEntityList(initial: List<ShopEntity>): List<Shop>{
        return initial.map { mapFromDomainModel(it) }
    }
}