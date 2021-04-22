package com.example.truffol.db.model

import com.example.truffol.domain.model.Truffle
import com.example.truffol.domain.util.DomainMapper

class TruffleEntityMapper : DomainMapper<Truffle, TruffleEntity> {

    override fun mapToDomainModel(model: Truffle): TruffleEntity {
        return TruffleEntity(
            id = model.id,
            tartufoName = model.tartufoName,
            description = model.description,
            image_url = model.image_url,
            rating = model.rating,
            price = model.price
        )
    }

    override fun mapFromDomainModel(entityModel: TruffleEntity): Truffle {
        return Truffle(
            id = entityModel.id,
            tartufoName = entityModel.tartufoName,
            description = entityModel.description,
            image_url = entityModel.image_url,
            rating = entityModel.rating,
            price = entityModel.price
        )
    }

    fun toEntityList(initial: List<Truffle>): List<TruffleEntity>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromEntityList(initial: List<TruffleEntity>): List<Truffle>{
        return initial.map { mapFromDomainModel(it) }
    }
}