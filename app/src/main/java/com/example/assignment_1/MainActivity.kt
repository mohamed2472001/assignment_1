package com.example.assignment_1


import android.R
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.assignment_1.databinding.ActivityMainBinding

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    var db = Firebase.firestore
    lateinit var binding: ActivityMainBinding
    lateinit var listView: ListView
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.SaveBtn.setOnClickListener {
            val name = binding.NameTxt.text.toString()
            val number = binding.NumberTxt.text.toString()
            val address = binding.AddressTxt.text.toString()

            // Create a new user with a first and last name
            val contact = hashMapOf(
                "name" to name,
                "number" to number,
                "address" to address
            )

            // Add a new document with a generated ID
            db.collection("contacts")
                .add(contact)
                .addOnSuccessListener { documentReference ->
                    //  Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    Toast.makeText(
                        applicationContext,
                        "${documentReference.id}",
                        Toast.LENGTH_SHORT
                    ).show()

                    val data = arrayOf(name, number, address)

                    val myadapter = ArrayAdapter(this, R.layout.simple_list_item_1, data)

                    listView = binding.listViewContacts

                    listView.adapter = myadapter

                    if (data.isEmpty()) {
                         progressBar = binding.progressBar
                        progressBar.visibility = View.VISIBLE
                    }


                }
                .addOnFailureListener { e ->
                    //  Log.w(TAG, "Error adding document", e)
                    Toast.makeText(applicationContext, "${e}", Toast.LENGTH_SHORT).show()
                }


        }

        binding.deleteBtn.setOnClickListener {
            db = FirebaseFirestore.getInstance()
            val collectionRef = db.collection("contacts")

            collectionRef.document("name").delete()
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(applicationContext, "Delete Successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(applicationContext, "${e}", Toast.LENGTH_SHORT).show()
                }
        }


    }


}