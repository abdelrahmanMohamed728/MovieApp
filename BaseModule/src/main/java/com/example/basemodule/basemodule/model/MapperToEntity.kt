package com.example.basemodule.basemodule.model

interface MapperToEntity <Entity : BaseModel,Domain : DTOModel> {
    @Throws(Exception::class)
    fun fromDomainModelToEntity(model : Domain) :  Entity

    fun fromOptionalDomainModelToOptionalEntity(model : Domain?) :  Entity?{
        return if (model != null){
            fromDomainModelToEntity(model)
        }else{
            null
        }
    }

    fun mapArray(models: List<Domain>): List<Entity>{
        return models.map { fromDomainModelToEntity(it) }
    }

    fun mapOptionalToArray(models: List<Domain?>?): List<Entity>{
        return models?.mapNotNull { fromOptionalDomainModelToOptionalEntity(it) } ?: listOf()
    }
}

