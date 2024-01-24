package com.jameermulani.subjectkeepercompose.data.source.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jameermulani.subjectkeepercompose.data.source.room.dao.SubjectDao
import com.jameermulani.subjectkeepercompose.data.source.room.entity.SubjectEntity

@Database(entities = [SubjectEntity::class], version = 1, exportSchema = false,)
@TypeConverters(Converters::class)
abstract class SubjectDatabase : RoomDatabase() {
    abstract fun getSubjectDao(): SubjectDao

}