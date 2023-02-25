package ru.tsu.domain

interface UseCase<in P,R> {
    suspend fun execute(param:P):R
}