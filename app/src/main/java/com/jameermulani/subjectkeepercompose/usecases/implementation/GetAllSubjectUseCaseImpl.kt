package com.jameermulani.subjectkeepercompose.usecases.implementation

import com.jameermulani.subjectkeepercompose.domain.model.Subject
import com.jameermulani.subjectkeepercompose.domain.repository.SubjectRepository
import com.jameermulani.subjectkeepercompose.domain.usecase.GetAllSubjectsUseCase
import javax.inject.Inject

class GetAllSubjectUseCaseImpl @Inject constructor(
    private val subjectRepository: SubjectRepository
) : GetAllSubjectsUseCase {
    override suspend fun invoke(input: Int?): List<Subject> {
        return subjectRepository.getSubjects()
    }
}