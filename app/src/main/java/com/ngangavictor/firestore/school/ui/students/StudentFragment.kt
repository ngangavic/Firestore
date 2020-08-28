package com.ngangavictor.firestore.school.ui.students

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.ngangavictor.firestore.adapter.StudentAdapter
import com.ngangavictor.firestore.dialogs.AddStudentDialog
import com.ngangavictor.firestore.listeners.ListenerStudent
import com.ngangavictor.firestore.models.StudentModel

class StudentFragment : Fragment(), ListenerStudent {

    lateinit var fabAddStudent: FloatingActionButton

    lateinit var root: View

    lateinit var recyclerViewStudents: RecyclerView

    lateinit var spinnerClass: Spinner

    private lateinit var viewModel: StudentViewModel

    private lateinit var classList: List<String>

    private lateinit var spinnerAdapter: ArrayAdapter<String>

    private lateinit var alert: AlertDialog

    private lateinit var database: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private lateinit var studentAdapter: StudentAdapter

    private lateinit var studentList: MutableList<StudentModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.student_fragment, container, false)

        viewModel = ViewModelProviders.of(this).get(StudentViewModel::class.java)

        fabAddStudent = root.findViewById(R.id.fabAddStudent)
        recyclerViewStudents = root.findViewById(R.id.recyclerViewStudents)
        spinnerClass = root.findViewById(R.id.spinnerClass)

        recyclerViewStudents.layoutManager =
            LinearLayoutManager(requireContext())
        recyclerViewStudents.setHasFixedSize(true)

        database = Firebase.firestore
        auth = Firebase.auth

        classList = ArrayList()
        studentList = ArrayList()

        (classList as ArrayList<String>).add("Select Class")

        spinnerAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, classList)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerClass.adapter = spinnerAdapter

        database.collection("classes").document(auth.currentUser!!.uid)
            .addSnapshotListener { value, error ->
                for (i in value!!.data!!.values) {
                    (classList as ArrayList<String>).add(i.toString())
                }

                spinnerAdapter.notifyDataSetChanged()

            }

        spinnerClass.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent!!.getItemAtPosition(position).toString() != "Select Class") {
                    viewModel.getStudentsData(parent.getItemAtPosition(position).toString())
                        .observe(viewLifecycleOwner,
                            Observer {
                                studentList = it as MutableList<StudentModel>

                                studentAdapter = StudentAdapter(
                                    requireContext(), studentList as ArrayList<StudentModel>
                                )

                                studentAdapter.notifyDataSetChanged()

                                recyclerViewStudents.adapter = studentAdapter
                            })
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }


        fabAddStudent.setOnClickListener {
            if (!checkPermission()) {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ), 200
                )
            } else {
                val addStudentDialog = AddStudentDialog(this).newInstance()
                addStudentDialog.isCancelable = true
                requireActivity().supportFragmentManager.let {
                    addStudentDialog.show(
                        it,
                        "dialog add student"
                    )
                }
            }
        }

        return root
    }

    private fun checkPermission(): Boolean {
        val resultReadExternalStorage =
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        val resultWriteExternalStorage =
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )

        return resultReadExternalStorage == PackageManager.PERMISSION_GRANTED && resultWriteExternalStorage == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 200 && grantResults.isNotEmpty()) {
            val readAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
            val writeAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED

            if (!readAccepted && !writeAccepted) {
                Snackbar.make(
                    requireView().findViewById(android.R.id.content),
                    "Storage permission required",
                    Snackbar.LENGTH_LONG
                ).show()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        showMessageOKCancel("You need to allow access to both the permissions",
                            DialogInterface.OnClickListener { dialog, which ->
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(
                                        arrayOf(
                                            Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                                        ),
                                        200
                                    )
                                }
                            })
                        return
                    }
                }
            } else {
                Snackbar.make(
                    requireView().findViewById(android.R.id.content),
                    "Permissions granted.",
                    Snackbar.LENGTH_LONG
                ).show()
            }

        }
    }

    private fun showMessageOKCancel(
        message: String,
        okListener: DialogInterface.OnClickListener
    ) {
        AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    override fun addStudent(message: String) {
        if (message === "complete") {
            Snackbar.make(requireView(), "Students added successfully", Snackbar.LENGTH_LONG).show()
        } else {
            Snackbar.make(requireView(), "Error: $message", Snackbar.LENGTH_LONG).show()
        }
    }

}