package com.example.tartufozon.network.model

import com.example.tartufozon.domain.model.Truffle
import junit.framework.TestCase

class TruffleDtoMapperTest : TestCase() {

    // TODO: 23.02.21 : Mock this stuff
    val TruffleDtoMapper = TruffleDtoMapper()

    val truffle = Truffle(
        1,
        "Tartufinho",
        "Buonisssimo",
        "https://www.moscatotartufi.it/wp-content/uploads/2015/03/vendita-tartufo-bianco-pregiato.jpg",
        9
    )

    val truffleDto = TruffleDto(
        1,
        "Tartufinho",
        "Buonisssimo",
        "https://www.moscatotartufi.it/wp-content/uploads/2015/03/vendita-tartufo-bianco-pregiato.jpg",
        9
    )

    val TruffleDtoList = listOf(truffleDto, truffleDto, truffleDto)

    val TruffleList = listOf(truffle, truffle, truffle)

    fun testMapToDomainModel() {
        val mappedTruffle: Truffle = TruffleDtoMapper.mapToDomainModel(truffleDto)
        assertEquals(truffle, mappedTruffle)
    }

    fun testMapFromDomainModel() {
        val mappedTruffleDto: TruffleDto = TruffleDtoMapper.mapFromDomainModel(truffle)
        assertEquals(truffleDto, mappedTruffleDto)
    }

    fun testToDomainList() {
        val mappedTruffleList = TruffleDtoMapper.toDomainList(TruffleDtoList)
        assertEquals(TruffleList, mappedTruffleList)
    }

    fun testFromDomainList() {
        val mappedTruffleDtoList = TruffleDtoMapper.fromDomainList(TruffleList)
        assertEquals(TruffleDtoList, mappedTruffleDtoList)
    }
}