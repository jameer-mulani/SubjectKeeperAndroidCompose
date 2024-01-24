package com.jameermulani.subjectkeepercompose.data.source.room.mapper

interface EntityModelMapper<Entity, Model> {

    fun convertEntityToModel(entity: Entity): Model

    fun convertModelToEntity(model: Model): Entity


}