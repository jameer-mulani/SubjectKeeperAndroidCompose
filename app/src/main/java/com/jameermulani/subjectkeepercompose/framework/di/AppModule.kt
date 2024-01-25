package com.jameermulani.subjectkeepercompose.framework.di

import android.content.Context
import android.service.credentials.RemoteEntry
import androidx.compose.ui.unit.Constraints
import androidx.room.Room
import com.jameermulani.subjectkeepercompose.data.respository.NetworkRepositoryImpl
import com.jameermulani.subjectkeepercompose.data.respository.SubjectRepositoryImpl
import com.jameermulani.subjectkeepercompose.data.source.remote.RemoteApi
import com.jameermulani.subjectkeepercompose.data.source.room.SubjectDatabase
import com.jameermulani.subjectkeepercompose.data.source.room.dao.SubjectDao
import com.jameermulani.subjectkeepercompose.domain.repository.SubjectRepository
import com.jameermulani.subjectkeepercompose.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()

    @Provides
    @Singleton
    fun provideRemoteApi(retrofit: Retrofit) = retrofit.create(RemoteApi::class.java) as RemoteApi


    @Provides
    @Singleton
    fun provideNetworkRepository(remoteApi: RemoteApi) = NetworkRepositoryImpl(remoteApi)

}