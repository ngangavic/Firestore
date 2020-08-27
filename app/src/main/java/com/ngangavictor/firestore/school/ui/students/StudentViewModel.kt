package com.ngangavictor.firestore.school.ui.students

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ngangavictor.firestore.models.StudentModel
import com.ngangavictor.firestore.repos.FirestoreRepository

class StudentViewModel : ViewModel() {

    private val firestoreRepository = FirestoreRepository()

    private var studentList: MutableList<StudentModel> = mutableListOf()

    private val studentData = MutableLiveData<List<StudentModel>>()

    fun getStudentsData(className: String): MutableLiveData<List<StudentModel>> {
        firestoreRepository.getStudents(className).addSnapshotListener { value, error ->
            studentList.clear()
            for(i in value!!){
                studentList.add(
                    StudentModel(
                    i.id,
                    i.data.get("NAME").toString(),
                    className,
                    i.data.get("KCPE").toString(),
                    i.data.get("PARENT-PHONE").toString()
                ))
            }
            studentData.value=studentList
        }
        return studentData
    }

}