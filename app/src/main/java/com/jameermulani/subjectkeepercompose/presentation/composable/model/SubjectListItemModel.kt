package com.jameermulani.subjectkeepercompose.presentation.composable.model

data class SubjectListItemModel(
    val subjectId : Int = 0,
    val subjectName : String,
    val subjectCoverUrl : String,
    val lastCreatedOn : String,
    val numberOfChapters : Int = 0,
    val selected : Boolean = false
)