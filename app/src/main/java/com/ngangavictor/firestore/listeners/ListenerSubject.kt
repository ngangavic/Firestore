package com.ngangavictor.firestore.listeners

interface ListenerSubject {

    fun addSubjectResponse(message:String)

    fun deleteSubject(key:String)

}