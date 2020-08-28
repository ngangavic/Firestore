package com.ngangavictor.firestore.school.ui.results

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ngangavictor.firestore.models.ResultModel
import com.ngangavictor.firestore.repos.FirestoreRepository

class ResultViewModel : ViewModel() {

    private val firestoreRepository = FirestoreRepository()

    private var resultList: MutableList<ResultModel> = mutableListOf()

    private val resultData = MutableLiveData<List<ResultModel>>()

    fun getResultData(subjectName: String, examId: String): MutableLiveData<List<ResultModel>> {

        firestoreRepository.getResults(examId, subjectName).addSnapshotListener { value, error ->
            resultList.clear()
            for (i in value!!) {
                Log.e("RESULTS", i.id + " " + i.data["marks"].toString())
                resultList.add(
                    ResultModel(
                        i.id,
                        i.data["marks"].toString()
                    )
                )
            }
            resultData.value = resultList
        }
        return resultData
    }

}