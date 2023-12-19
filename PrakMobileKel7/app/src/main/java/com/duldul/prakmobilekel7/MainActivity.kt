package com.duldul.prakmobilekel7

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.duldul.prakmobilekel7.Adapter.RecyclerViewAdapter
import com.duldul.prakmobilekel7.Model.Note
import com.duldul.prakmobilekel7.databinding.ActivityMainBinding
import com.duldul.prakmobilekel7.rest.Client
import com.duldul.prakmobilekel7.rest.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var progressBar: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressBar = binding.progressBar // Assuming you have a ProgressBar in your layout

        binding.btnInsert.setOnClickListener {
            startActivity(Intent(baseContext, InsertActivity::class.java))
        }

        fetchInformation()
    }

    private fun fetchInformation() {
        val apiInterface: Service = Client().getApiClient()!!
        val call: Call<List<Note>> = apiInterface.getNote()

        // Show the ProgressBar
        progressBar.visibility = View.VISIBLE

        call.enqueue(object : Callback<List<Note>> {
            override fun onResponse(call: Call<List<Note>>, response: Response<List<Note>>) {
                // Hide the ProgressBar on a successful response
                progressBar.visibility = View.GONE

                binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                val noteList = response.body()
                noteList?.let {
                    binding.recyclerView.adapter = RecyclerViewAdapter(this@MainActivity, it)
                }
            }

            override fun onFailure(call: Call<List<Note>>, t: Throwable) {
                // Hide the ProgressBar on failure
                progressBar.visibility = View.GONE

                Log.e("zzzzzzzzzz", t.toString())
                Toast.makeText(baseContext, t.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_refresh) {
            // Show the ProgressBar
            progressBar.visibility = View.VISIBLE

            fetchInformation()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        // Show the ProgressBar
        progressBar.visibility = View.VISIBLE

        fetchInformation()
    }
}
