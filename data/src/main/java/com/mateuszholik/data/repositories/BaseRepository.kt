package com.mateuszholik.data.repositories

import com.mateuszholik.data.repositories.models.Resource

abstract class BaseRepository {

    fun <T, R> createResource(param: List<T>, map: (List<T>) -> List<R>): Resource<List<R>> =
        when {
            param.isEmpty() -> Resource.EmptyBody()
            else -> Resource.Success(map(param))
        }
}