package com.jameermulani.subjectkeepercompose.data.source.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "subjects")
data class SubjectEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("subject_id")
    val subjectId: Int = 0,

    @ColumnInfo(name = "subject_name")
    val subjectName: String,

    @ColumnInfo(name = "subject_cover_url")
    val subjectCoverUrl: String,

    @ColumnInfo(name = "subject_created_on")
    val subjectCreatedDate: Date

)
