package com.duldul.prakmobilekel7

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.duldul.prakmobilekel7.Model.ResponseResult
import com.duldul.prakmobilekel7.databinding.ActivityInsertBinding
import com.duldul.prakmobilekel7.rest.Client
import com.duldul.prakmobilekel7.rest.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InsertActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInsertBinding
    private var strColor = "#FFEBEE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)
<<<<<<< Updated upstream
        progressDialog = ProgressDialog(this)
=======

        binding.progressBarInsert.visibility = View.GONE // Set initial visibility to GONE

>>>>>>> Stashed changes
        binding.insertBtnInsert.setOnClickListener {
            insertUser(
                binding.insertTxtTitle.text.toString(),
                binding.insertTxtNote.text.toString(),
                strColor
            )
            binding.progressBarInsert.visibility = View.VISIBLE // Show ProgressBar
        }
    }

    private fun insertUser(username: String, password: String, email: String) {
        val apiInterface: Service = Client().getApiClient()!!
        val call: Call<ResponseResult> = apiInterface.insertUser(username, password, email)
        call.enqueue(object : Callback<ResponseResult?> {
            override fun onResponse(
                call: Call<ResponseResult?>,
                response: Response<ResponseResult?>
            ) {
                binding.progressBarInsert.visibility = View.GONE // Hide ProgressBar on success
                finish()
            }
            override fun onFailure(call: Call<ResponseResult?>, t: Throwable) {
                binding.progressBarInsert.visibility = View.GONE // Hide ProgressBar on failure
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getColor(view: View) {
        val viewColor = view.background as ColorDrawable
        val colorId = viewColor.color
        strColor = String.format("#%06X", 0xFFFFFF and colorId)
        binding.insertTxtTitle.setBackgroundColor(colorId)
        binding.insertTxtNote.setBackgroundColor(colorId)
    }
}
