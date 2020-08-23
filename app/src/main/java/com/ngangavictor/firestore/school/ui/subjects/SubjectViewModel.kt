package com.ngangavictor.firestore.school.ui.subjects

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ngangavictor.firestore.models.ExamModel
import com.ngangavictor.firestore.models.SubjectModel
import com.ngangavictor.firestore.repos.FirestoreRepository

class SubjectViewModel : ViewModel() {

    private val firestoreRepository= FirestoreRepository()

    private var subjectList: MutableList<SubjectModel> = mutableListOf()

    private val subjectData = MutableLiveData<List<SubjectModel>>()

    fun getSubjectData(): MutableLiveData<List<SubjectModel>> {

        firestoreRepository.getSubject().addSnapshotListener { value, error ->

            for (i in value!!){
                subjectList.add(SubjectModel(i.id,i.data.get("subjectName").toString()))
            }

            subjectData.value=subjectList

        }

return subjectData
    }
}