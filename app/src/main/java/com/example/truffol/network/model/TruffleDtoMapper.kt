package com.example.truffol.network.model

import com.example.truffol.domain.model.Truffle
import com.example.truffol.domain.util.DomainMapper

class TruffleDtoMapper : DomainMapper<TruffleDto, Truffle> {

    override fun mapToDomainModel(model: TruffleDto): Truffle {
        return Truffle(
            truffleId = model.id,
            tartufoName = model.tartufoName,
            description = model.description,
            image_url = model.image_url,
            rating = model.rating,
            price = model.price
        )
    }

    override fun mapFromDomainModel(domainModel: Truffle): TruffleDto {
        return TruffleDto(
            id = domainModel.truffleId,
            tartufoName = domainModel.tartufoName,
            description = domainModel.description,
            image_url = domainModel.image_url,
            rating = domainModel.rating,
            price = domainModel.price
        )
    }

    fun toDomainList(initial: List<TruffleDto>): List<Truffle>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Truffle>): List<TruffleDto>{
        return initial.map { mapFromDomainModel(it) }
    }
}