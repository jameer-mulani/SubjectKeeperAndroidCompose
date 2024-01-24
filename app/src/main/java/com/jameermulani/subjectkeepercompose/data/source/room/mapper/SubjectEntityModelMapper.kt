package com.jameermulani.subjectkeepercompose.data.source.room.mapper

import com.jameermulani.subjectkeepercompose.data.source.room.entity.SubjectEntity
import com.jameermulani.subjectkeepercompose.domain.model.Subject

object SubjectEntityModelMapper : EntityModelMapper<SubjectEntity, Subject> {
    override fun convertEntityToModel(entity: SubjectEntity): Subject {
        return Subject(
            id = entity.subjectId,
            subjectName = entity.subjectName,
            subjectCoverUrl = entity.subjectCoverUrl,
            createdDate = entity.subjectCreatedDate
        )
    }

    override fun convertModelToEntity(model: Subject): SubjectEntity {
        return SubjectEntity(
            subjectId = model.id,
            subjectName = model.subjectName,
            subjectCoverUrl = model.subjectCoverUrl,
            subjectCreatedDate = model.createdDate
        )

    }

}