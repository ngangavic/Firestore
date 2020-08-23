package com.ngangavictor.firestore.school.ui.classes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ngangavictor.firestore.models.ClassModel
import com.ngangavictor.firestore.repos.FirestoreRepository

class ClassViewModel : ViewModel() {

    var firestoreRepository = FirestoreRepository()

    var classList: MutableList<ClassModel> = mutableListOf()

    private val classData = MutableLiveData<List<ClassModel>>()

    fun getClasses(): LiveData<List<ClassModel>> {


        firestoreRepository.getClasses().addSnapshotListener { value, error ->
            classList.clear()
            for (i in value!!.data!!.values) {
                classList.add(ClassModel(i.toString().take(1), i.toString()))
            }

            classData.value = classList

        }

        return classData
    }

}