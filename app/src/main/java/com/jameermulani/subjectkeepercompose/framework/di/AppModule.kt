package com.jameermulani.subjectkeepercompose.framework.di

import android.content.Context
import androidx.room.Room
import com.jameermulani.subjectkeepercompose.data.respository.SubjectRepositoryImpl
import com.jameermulani.subjectkeepercompose.data.source.room.SubjectDatabase
import com.jameermulani.subjectkeepercompose.data.source.room.dao.SubjectDao
import com.jameermulani.subjectkeepercompose.domain.repository.SubjectRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun getSubjectDatabase(@ApplicationContext context: Context): SubjectDatabase {
        return Room.databaseBuilder(context, SubjectDatabase::class.java, "subjectdb.db").build()
    }

    @Provides
    @Singleton
    fun provideSubjectDao(subjectDatabase: SubjectDatabase) = subjectDatabase.getSubjectDao()


    @Provides
    @Singleton
    fun provideSubjectRepository(subjectDao: SubjectDao): SubjectRepository =
        SubjectRepositoryImpl(subjectDao)


}