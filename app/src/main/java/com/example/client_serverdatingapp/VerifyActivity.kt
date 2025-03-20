package com.example.client_serverdatingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyActivity : AppCompatActivity() {
    private lateinit var codeInput: EditText
    private lateinit var verifyButton: Button
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)

        codeInput = findViewById(R.id.codeInput)
        verifyButton = findViewById(R.id.verifyButton)

        email = intent.getStringExtra("email")

        verifyButton.setOnClickListener {
            val code = codeInput.text.toString().trim()

            if (code.isEmpty()) {
                Toast.makeText(this, "Введите код", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ApiClient.instance.verify(VerifyRequest(email!!, code))
                .enqueue(object : Callback<VerifyResponse> {
                    override fun onResponse(
                        call: Call<VerifyResponse>,
                        response: Response<VerifyResponse>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@VerifyActivity, "Успешно!", Toast.LENGTH_SHORT)
                                .show()
                            // Переход на главную страницу (MainActivity)
                            val intent = Intent(this@VerifyActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()  // Закрываем VerifyActivity, чтобы не вернуться назад
                        } else {
                            Toast.makeText(this@VerifyActivity, "Неверный код", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<VerifyResponse>, t: Throwable) {
                        Toast.makeText(this@VerifyActivity, "Ошибка сети", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
        }
    }}
