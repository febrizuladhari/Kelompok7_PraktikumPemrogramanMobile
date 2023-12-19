package com.duldul.prakmobilekel7

import com.duldul.prakmobilekel7.Model.Note
import com.duldul.prakmobilekel7.Model.ResponseResult
import com.duldul.prakmobilekel7.databinding.ActivityUpdateBinding
import com.duldul.prakmobilekel7.rest.Client

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var strId: String
    private lateinit var strColor: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBarUpdate.visibility = View.GONE

        var note = Note(
            intent.getStringExtra("itemId").toString(),
            intent.getStringExtra("itemTitle").toString(),
            intent.getStringExtra("itemNote").toString(),
            intent.getStringExtra("itemColor").toString(),
            ""
        )

        strColor = intent.getStringExtra("itemColor").toString()

        strId = note.getId()
        binding.updateTxtTitle.setText(note.getTitle())
        binding.updateTxtNote.setText(note.getNote())
        binding.updateTxtTitle.setBackgroundColor(Color.parseColor(strColor))
        binding.updateTxtNote.setBackgroundColor(Color.parseColor(strColor))

        binding.updateBtnUpdate.setOnClickListener {
            updateUser(strId, binding.updateTxtTitle.text.toString(), binding.updateTxtNote.text.toString(), strColor)
            binding.progressBarUpdate.visibility = View.VISIBLE
        }

        binding.updateBtnDelete.setOnClickListener {
            deleteUser(note.getId())
            binding.progressBarUpdate.visibility = View.VISIBLE
        }
    }

    private fun updateUser(id: String, title: String, note: String, color: String) {
        val apiInterface = Client().getApiClient()!!
        val call: Call<ResponseResult> = apiInterface.updateUser(id, title, note, color)
        call.enqueue(object : Callback<ResponseResult> {
            override fun onResponse(call: Call<ResponseResult>, response: Response<ResponseResult>) {
                binding.progressBarUpdate.visibility = View.GONE
                finish()
            }

            override fun onFailure(call: Call<ResponseResult>, t: Throwable) {
                binding.progressBarUpdate.visibility = View.GONE
                Log.e("zzzzzzzzzz", t.toString())
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteUser(id: String) {
        val apiInterface = Client().getApiClient()!!
        val call: Call<ResponseResult> = apiInterface.deleteUser(id)
        call.enqueue(object : Callback<ResponseResult> {
            override fun onResponse(call: Call<ResponseResult>, response: Response<ResponseResult>) {
                binding.progressBarUpdate.visibility = View.GONE
                finish()
            }

            override fun onFailure(call: Call<ResponseResult>, t: Throwable) {
                binding.progressBarUpdate.visibility = View.GONE
                Log.e("zzzzzzzzzz", t.toString())
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getColor(view: View) {
        val viewColor = view.background as ColorDrawable
        val colorId = viewColor.color
        strColor = String.format("#%06X", 0xFFFFFF and colorId)
        binding.updateTxtTitle.setBackgroundColor(colorId)
        binding.updateTxtNote.setBackgroundColor(colorId)
    }
}
