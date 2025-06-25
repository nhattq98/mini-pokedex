package com.tahn.domain.provider

import javax.crypto.SecretKey

interface KeyProvider {
    fun getSecretKey(): SecretKey
}

interface EncryptedProvider {
    fun encrypt(value: String): String
    fun decrypt(encryptedBase64: String): String
}