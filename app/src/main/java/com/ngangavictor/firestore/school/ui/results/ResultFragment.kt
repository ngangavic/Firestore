package com.ngangavictor.firestore.school.ui.results

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.adapter.ResultAdapter
import com.ngangavictor.firestore.dialogs.AddResultsDialog
import com.ngangavictor.firestore.listeners.ListenerResult
import com.ngangavictor.firestore.models.ResultModel
import com.ngangavictor.firestore.utils.LocalSharedPreferences

class ResultFragment : Fragment(), ListenerResult {

    private lateinit var viewModel: ResultViewModel

    private lateinit var root: View

    private lateinit var resultAdapter: ResultAdapter

    private lateinit var recyclerViewResults: RecyclerView

    private lateinit var resultList: MutableList<ResultModel>

    private lateinit var fabSaveResults:FloatingActionButton

    private lateinit var auth: FirebaseAuth

    private lateinit var database:FirebaseFirestore

    private lateinit var localSharedPreferences: LocalSharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.result_fragment, container, false)

        viewModel = ViewModelProviders.of(this).get(ResultViewModel::class.java)

        recyclerViewResults = root.findViewById(R.id.recyclerViewResults)
        fabSaveResults = root.findViewById(R.id.fabSaveResults)

        resultList = ArrayList()

        auth=Firebase.auth
        database=Firebase.firestore

        recyclerViewResults.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewResults.setHasFixedSize(true)

        val addResultsDialog = AddResultsDialog(this).newInstance()
        addResultsDialog.isCancelable = false
        requireActivity().supportFragmentManager.let {
            addResultsDialog.show(
                it,
                "dialog add results"
            )
        }

        fabSaveResults.setOnClickListener {

        }

        localSharedPreferences= LocalSharedPreferences(requireContext())

        return root
    }

    override fun selectedExam(examId: String, subjectName: String) {
        Log.e("RESULTS FRAG", examId + " " + subjectName)
        viewModel.getResultData(subjectName, examId).observe(viewLifecycleOwner, Observer {
            resultList = it as MutableList<ResultModel>

            resultAdapter = ResultAdapter(
                requireContext(), resultList as ArrayList<ResultModel>,this
            )

            resultAdapter.notifyDataSetChanged()

            recyclerViewResults.adapter = resultAdapter
        })

    }

    override fun saveMarks(marks: Int, adm: String) {
        val newMarks= hashMapOf(
            "marks" to marks
        )
        database.collection("schools").document(auth.currentUser!!.uid)
            .collection("results").document(localSharedPreferences.getSelectedExamPref("examKey").toString()).collection(localSharedPreferences.getSelectedExamPref("examSubject").toString()).document(adm).set(newMarks,
                SetOptions.merge())
    }

    override fun error(message: String) {
        Snackbar.make(requireView(),message,Snackbar.LENGTH_LONG).show()
    }

}