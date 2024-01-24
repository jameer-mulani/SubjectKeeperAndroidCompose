package com.jameermulani.subjectkeepercompose.data.source.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jameermulani.subjectkeepercompose.data.source.room.entity.SubjectEntity
import com.jameermulani.subjectkeepercompose.domain.model.Subject

@Dao
interface SubjectDao {

    @Insert
    suspend fun insertSubject(vararg subject: SubjectEntity)

    @Delete
    suspend fun deleteSubject(subject: SubjectEntity)

    @Query("select * from subjects where subject_id=:id")
    suspend fun getSubjectById(id: Int): SubjectEntity?

    @Query("select * from subjects")
    suspend fun getSubjects(): List<SubjectEntity>

}