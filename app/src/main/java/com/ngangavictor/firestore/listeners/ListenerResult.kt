package com.ngangavictor.firestore.listeners

interface ListenerResult {

    fun selectedExam(examId: String, subjectName: String)

    fun saveMarks(marks:Int,adm:String)

    fun error(message:String)

}