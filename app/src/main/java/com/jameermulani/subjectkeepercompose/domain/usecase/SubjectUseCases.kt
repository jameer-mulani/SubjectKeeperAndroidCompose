package com.jameermulani.subjectkeepercompose.domain.usecase

import com.jameermulani.subjectkeepercompose.domain.model.Subject

interface GetAllSubjectsUseCase : SuspendUseCase<Int?, List<Subject>>

interface GetSubjectByIdUseCase : SuspendUseCase<Int, Subject?>

interface AddSubjectUseCase : SuspendUseCase<Subject, Boolean>

interface DeleteSubjectUseCase : SuspendUseCase<Int, Boolean>