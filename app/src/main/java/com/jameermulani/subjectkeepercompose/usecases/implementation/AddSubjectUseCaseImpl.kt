package com.jameermulani.subjectkeepercompose.usecases.implementation

import com.jameermulani.subjectkeepercompose.domain.model.Subject
import com.jameermulani.subjectkeepercompose.domain.repository.SubjectRepository
import com.jameermulani.subjectkeepercompose.domain.usecase.AddSubjectUseCase
import javax.inject.Inject

class AddSubjectUseCaseImpl @Inject constructor(
    private val subjectRepository: SubjectRepository
): AddSubjectUseCase {
    override suspend fun invoke(input: Subject): Boolean {
        return subjectRepository.addSubject(input)
    }
}