package com.ngangavictor.firestore.utils

import android.content.Context
import android.content.SharedPreferences

class LocalSharedPreferences(context: Context) {

    companion object {
        const val school_details_pref = "school_det_pref"
        const val selected_exam_pref = "selected_exam_pref"
    }

    private val sharedPrefAccDetails: SharedPreferences =
        context.getSharedPreferences(school_details_pref, 0)

    private val selectedExamPref: SharedPreferences =
        context.getSharedPreferences(selected_exam_pref, 0)

    fun saveSchoolDetailsPref(KEY_NAME: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPrefAccDetails.edit()
        editor.putString(KEY_NAME, value)
        editor.apply()
    }

    fun getSchoolDetailsPref(KEY_NAME: String): String? {
        return sharedPrefAccDetails.getString(KEY_NAME, null)
    }

    fun clearSchoolDetailsPref() {
        val editor: SharedPreferences.Editor = sharedPrefAccDetails.edit()
        editor.clear()
        editor.apply()
    }

    fun saveSelectedExamPref(KEY_NAME: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPrefAccDetails.edit()
        editor.putString(KEY_NAME, value)
        editor.apply()
    }

    fun getSelectedExamPref(KEY_NAME: String): String? {
        return sharedPrefAccDetails.getString(KEY_NAME, null)
    }

    fun clearSelectedExamPref() {
        val editor: SharedPreferences.Editor = sharedPrefAccDetails.edit()
        editor.clear()
        editor.apply()
    }


}