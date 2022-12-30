package com.example.basemodule.basemodule.model

interface MapperToDomain<Entity: BaseModel,DomainModel : DTOModel> {
    @Throws(Exception::class)
    fun fromModelToDomain(model : Entity) :  DomainModel
}
