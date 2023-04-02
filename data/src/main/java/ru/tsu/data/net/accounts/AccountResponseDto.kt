package ru.tsu.data.net.accounts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.tsu.domain.account.model.AccountModel
import ru.tsu.domain.account.model.Currency

@Serializable
data class AccountResponseDto(
    @SerialName("id")
    val id:String,
    @SerialName("ownerId")
    val ownerId:String,
    @SerialName("balance")
    val balance:Float,
  /*  @SerialName("currency")
    val currency:String,

   */
    @SerialName("isBlocked")
    val isBlocked: Boolean,
    @SerialName("isDeleted")
    val isDeleted:Boolean,
){
    fun toDomain()= AccountModel(id, balance,ownerId,isDeleted,isBlocked)
}