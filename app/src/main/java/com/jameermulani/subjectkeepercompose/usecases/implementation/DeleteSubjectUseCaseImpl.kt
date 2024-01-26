package com.jameermulani.subjectkeepercompose.usecases.implementation

import com.jameermulani.subjectkeepercompose.domain.repository.SubjectRepository
import com.jameermulani.subjectkeepercompose.domain.usecase.DeleteSubjectUseCase
import javax.inject.Inject

class DeleteSubjectUseCaseImpl @Inject constructor(
    private val subjectRepository: SubjectRepository
) : DeleteSubjectUseCase {
    override suspend fun invoke(input: Int): Boolean {
        return subjectRepository.deleteSubject(input)
    }
}