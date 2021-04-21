package com.example.truffol.network.model

import com.example.truffol.domain.model.Shop
import junit.framework.TestCase

class ShopDtoMapperTest : TestCase() {

    // TODO: 23.02.21 : Mock this stuff
    val shopDtoMapper = ShopDtoMapper()

    val shop = Shop(
        id = 1,
        shopName = "Acqualagna tartufi",
        description = "Molto famoso",
        image_url = "https=//www.acqualagnatartufi.com/wp-content/uploads/2020/10/acqualagna-tartufi-logo-1.png",
        website = "https=//www.acqualagnatartufi.com/",
        location = "Acqualagna",
        email = "info@acqualagnatartufi.com",
        phone = "0721799310"
    )

    val shopDto = ShopDto(
        id = 1,
        shopName = "Acqualagna tartufi",
        description = "Molto famoso",
        image_url = "https=//www.acqualagnatartufi.com/wp-content/uploads/2020/10/acqualagna-tartufi-logo-1.png",
        website = "https=//www.acqualagnatartufi.com/",
        location = "Acqualagna",
        email = "info@acqualagnatartufi.com",
        phone = "0721799310"
    )

    val shopDtoList = listOf(shopDto, shopDto, shopDto)

    val shopList = listOf(shop, shop, shop)

    fun testMapToDomainModel() {
        val mappedShop: Shop = shopDtoMapper.mapToDomainModel(shopDto)
        assertEquals(shop, mappedShop)
    }

    fun testMapFromDomainModel() {
        val mappedShopDto: ShopDto = shopDtoMapper.mapFromDomainModel(shop)
        assertEquals(shopDto, mappedShopDto)
    }

    fun testToDomainList() {
        val mappedShopList = shopDtoMapper.toDomainList(shopDtoList)
        assertEquals(shopList, mappedShopList)
    }

    fun testFromDomainList() {
        val mappedShopDtoList = shopDtoMapper.fromDomainList(shopList)
        assertEquals(shopDtoList, mappedShopDtoList)
    }
}