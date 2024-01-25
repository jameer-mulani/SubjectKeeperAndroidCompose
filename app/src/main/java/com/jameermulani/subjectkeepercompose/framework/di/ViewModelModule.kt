package com.jameermulani.subjectkeepercompose.framework.di

import com.jameermulani.subjectkeepercompose.domain.repository.SubjectRepository
import com.jameermulani.subjectkeepercompose.domain.usecase.AddSubjectUseCase
import com.jameermulani.subjectkeepercompose.domain.usecase.GetAllSubjectsUseCase
import com.jameermulani.subjectkeepercompose.usecases.implementation.AddSubjectUseCaseImpl
import com.jameermulani.subjectkeepercompose.usecases.implementation.GetAllSubjectUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {


    @Provides
    fun provideGetAllSubjectUseCase(subjectRepository: SubjectRepository): GetAllSubjectsUseCase =
        GetAllSubjectUseCaseImpl(subjectRepository)

    @Provides
    fun provideAddSubjectUseCase(subjectRepository: SubjectRepository) =
        AddSubjectUseCaseImpl(subjectRepository) as AddSubjectUseCase


}