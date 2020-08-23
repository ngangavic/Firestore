package com.ngangavictor.firestore.school.ui.subjects

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.adapter.ExamAdapter
import com.ngangavictor.firestore.adapter.SubjectAdapter
import com.ngangavictor.firestore.dialogs.AddExamDialog
import com.ngangavictor.firestore.dialogs.AddSubjectDialog
import com.ngangavictor.firestore.listeners.ListenerSubject
import com.ngangavictor.firestore.models.ExamModel
import com.ngangavictor.firestore.models.SubjectModel

class SubjectFragment : Fragment(),ListenerSubject {

    private lateinit var subjectViewModel: SubjectViewModel

    private lateinit var root:View

    private lateinit var recyclerViewSubjects:RecyclerView

    private lateinit var fabAddSubject:FloatingActionButton

    private lateinit var subjectList: MutableList<SubjectModel>

    private lateinit var subjectAdapter: SubjectAdapter

    private lateinit var database:FirebaseFirestore

    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        subjectViewModel =
            ViewModelProviders.of(this).get(SubjectViewModel::class.java)

        root = inflater.inflate(R.layout.fragment_subject, container, false)

        recyclerViewSubjects=root.findViewById(R.id.recyclerViewSubjects)

        fabAddSubject=root.findViewById(R.id.fabAddSubject)

        recyclerViewSubjects.layoutManager =
            LinearLayoutManager(requireContext())
        recyclerViewSubjects.setHasFixedSize(true)

        subjectList=ArrayList()

        database=Firebase.firestore
        auth=Firebase.auth

        fabAddSubject.setOnClickListener {
            val addSubjectDialog = AddSubjectDialog(this).newInstance()
            addSubjectDialog.isCancelable = false
            requireActivity().supportFragmentManager.let {
                addSubjectDialog.show(
                    it,
                    "dialog add subject"
                )
            }
        }

        subjectViewModel.getSubjectData().observe(viewLifecycleOwner, Observer {

            subjectList = it as MutableList<SubjectModel>

            subjectAdapter = SubjectAdapter(
                requireContext(),subjectList as ArrayList<SubjectModel>,this
            )

            subjectAdapter.notifyDataSetChanged()

            recyclerViewSubjects.adapter = subjectAdapter

        })
        return root
    }

    override fun addSubjectResponse(message: String) {

        if (message=="success"){
            Snackbar.make(requireView(),"Subject Added",Snackbar.LENGTH_LONG).show()
        }else{
            Snackbar.make(requireView(),"Error: Subject not added. Try again",Snackbar.LENGTH_LONG).show()
        }

    }

    override fun deleteSubject(key: String) {
        database.collection("schools").document(auth.currentUser!!.uid).collection("subjects").document(key)
            .delete()
            .addOnSuccessListener {
                Snackbar.make(requireView(),"Subject deleted",Snackbar.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Snackbar.make(requireView(),"Error deleting subject",Snackbar.LENGTH_LONG).show()
            }
    }

}