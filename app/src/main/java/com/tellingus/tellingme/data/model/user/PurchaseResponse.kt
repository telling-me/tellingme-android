package com.tellingus.tellingme.data.model.user

data class PurchaseResponse(
    val code: Int = 0,
    val message: String = "",
    val data: Purchase = Purchase()
)

data class Purchase(
    val product_code: String = ""
)