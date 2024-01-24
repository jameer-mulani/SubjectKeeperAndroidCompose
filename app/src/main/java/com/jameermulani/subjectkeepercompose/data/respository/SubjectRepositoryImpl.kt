package com.jameermulani.subjectkeepercompose.data.respository

import com.jameermulani.subjectkeepercompose.data.source.room.dao.SubjectDao
import com.jameermulani.subjectkeepercompose.data.source.room.mapper.SubjectEntityModelMapper
import com.jameermulani.subjectkeepercompose.domain.model.Subject
import com.jameermulani.subjectkeepercompose.domain.repository.SubjectRepository
import javax.inject.Inject

class SubjectRepositoryImpl @Inject constructor(
    private val subjectDao: SubjectDao
) : SubjectRepository {
    override suspend fun getSubjects(): List<Subject> {
        return subjectDao.getSubjects().map {
            SubjectEntityModelMapper.convertEntityToModel(it)
        }
    }

    override suspend fun getSubjectById(id: Int): Subject? {
        return subjectDao.getSubjectById(id)?.let {
            SubjectEntityModelMapper.convertEntityToModel(it)
        }
    }

    override suspend fun searchSubject(name: String): List<Subject> {
        TODO("Not yet implemented")
    }

    override suspend fun addSubject(subjects: Subject): Boolean {
        subjectDao.insertSubject(SubjectEntityModelMapper.convertModelToEntity(subjects))
        return true
    }

    override suspend fun addSubjects(subjects: List<Subject>): Boolean {
        val subjectEntities = subjects.map {
            SubjectEntityModelMapper.convertModelToEntity(it)
        }
        subjectDao.insertSubject(*subjectEntities.toTypedArray())
        return true
    }

    override suspend fun deleteSubject(subjectId: Int): Boolean {
        val subject = subjectDao.getSubjectById(subjectId)
        subject?.let {
            subjectDao.deleteSubject(it)
            return true
        } ?: return false
    }

    override suspend fun updateSubject(subjectId: Int): Boolean {
        TODO("Not yet implemented")
    }
}