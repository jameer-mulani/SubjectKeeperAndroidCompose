package com.jameermulani.subjectkeepercompose.domain.model

import java.util.Date

data class Subject(
    val id: Int = 0,
    val subjectName: String,
    val subjectCoverUrl: String,
    val createdDate: Date
)
