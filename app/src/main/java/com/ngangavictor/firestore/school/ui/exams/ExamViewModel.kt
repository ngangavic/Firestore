package com.ngangavictor.firestore.school.ui.exams

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ngangavictor.firestore.models.ExamModel
import com.ngangavictor.firestore.repos.FirestoreRepository

class ExamViewModel : ViewModel() {

    private var firestoreRepository = FirestoreRepository()

    private var examList: MutableList<ExamModel> = mutableListOf()
    private val examData = MutableLiveData<List<ExamModel>>()


    fun getExamData(): MutableLiveData<List<ExamModel>> {

        firestoreRepository.getExams().addSnapshotListener { value, error ->

            examList.clear()

            for (i in value!!) {
                examList.add(
                    ExamModel(
                        i.id,
                        i.data.get("examName").toString(),
                        i.data.get("examTerm").toString(),
                        i.data.get("class").toString(),
                        i.data.get("examYear").toString()
                    )
                )
                examData.value = examList
            }

        }

        return examData
    }

}