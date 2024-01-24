package com.jameermulani.subjectkeepercompose.domain.repository

import com.jameermulani.subjectkeepercompose.domain.model.Subject

interface SubjectRepository {

    suspend fun getSubjects(): List<Subject>

    suspend fun getSubjectById(id: Int): Subject?

    suspend fun searchSubject(name: String): List<Subject>

    suspend fun addSubject(subjects: Subject): Boolean

    suspend fun addSubjects(subjects: List<Subject>): Boolean

    suspend fun deleteSubject(subjectId: Int): Boolean

    suspend fun updateSubject(subjectId: Int): Boolean

}