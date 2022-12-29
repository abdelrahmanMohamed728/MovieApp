package com.example.drdbasemodule.model

/**
 * Created by Shaza Hassan on 27/Jun/2022.
 */
interface MapperToDomain<Entity: BaseModel,DomainModel : DTOModel> {
    @Throws(Exception::class)
    fun fromModelToDomain(model : Entity) :  DomainModel
}
