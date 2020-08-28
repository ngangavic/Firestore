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
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.adapter.ResultAdapter
import com.ngangavictor.firestore.dialogs.AddResultsDialog
import com.ngangavictor.firestore.listeners.ListenerResult
import com.ngangavictor.firestore.models.ResultModel

class ResultFragment : Fragment(), ListenerResult {

    private lateinit var viewModel: ResultViewModel

    private lateinit var root: View

    private lateinit var resultAdapter: ResultAdapter

    private lateinit var recyclerViewResults: RecyclerView

    private lateinit var resultList: MutableList<ResultModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.result_fragment, container, false)

        viewModel = ViewModelProviders.of(this).get(ResultViewModel::class.java)

        recyclerViewResults = root.findViewById(R.id.recyclerViewResults)

        resultList = ArrayList()

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

        return root
    }

    override fun selectedExam(examId: String, subjectName: String) {
        Log.e("RESULTS FRAG", examId + " " + subjectName)
        viewModel.getResultData(subjectName, examId).observe(viewLifecycleOwner, Observer {
            resultList = it as MutableList<ResultModel>

            resultAdapter = ResultAdapter(
                requireContext(), resultList as ArrayList<ResultModel>
            )

            resultAdapter.notifyDataSetChanged()

            recyclerViewResults.adapter = resultAdapter
        })

    }

}