package com.ngangavictor.firestore.school.ui.grades

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ngangavictor.firestore.models.ExamModel
import com.ngangavictor.firestore.models.GradeModel
import com.ngangavictor.firestore.repos.FirestoreRepository

class GradeViewModel : ViewModel() {

    private val firestoreRepository=FirestoreRepository()

    private val gradeList:MutableList<GradeModel> = mutableListOf()

    private val gradeData= MutableLiveData<List<GradeModel>>()


    fun getGradeData(selectedClass: String, selectedSubject: String): MutableLiveData<List<GradeModel>> {

firestoreRepository.getGrade(selectedClass,selectedSubject).addSnapshotListener { value, error ->

    gradeList.clear()
    for (i in value!!){
        gradeList.add(GradeModel(i.id,i.data.get("grade").toString(),i.data.get("start").toString(),i.data.get("end").toString()))
    }

    gradeData.value=gradeList
}

        return gradeData
    }


}