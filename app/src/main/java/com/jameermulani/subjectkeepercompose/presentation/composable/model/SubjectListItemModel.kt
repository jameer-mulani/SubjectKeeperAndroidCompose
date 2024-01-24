package com.jameermulani.subjectkeepercompose.presentation.composable.model

data class SubjectListItemModel(
    val subjectName : String,
    val subjectCoverUrl : String,
    val lastCreatedOn : String,
    val numberOfChapters : Int = 0
)