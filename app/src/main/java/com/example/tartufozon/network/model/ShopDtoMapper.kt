package com.example.tartufozon.network.model

import com.example.tartufozon.domain.model.Shop
import com.example.tartufozon.domain.util.DomainMapper

class ShopDtoMapper : DomainMapper<ShopDto, Shop> {

    override fun mapToDomainModel(model: ShopDto): Shop {
        return Shop(
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



    override fun mapFromDomainModel(domainModel: Shop): ShopDto {
        return ShopDto(
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

    fun toDomainList(initial: List<ShopDto>): List<Shop>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Shop>): List<ShopDto>{
        return initial.map { mapFromDomainModel(it) }
    }
}