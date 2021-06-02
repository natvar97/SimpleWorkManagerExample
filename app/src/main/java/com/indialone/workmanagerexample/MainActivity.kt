package com.indialone.workmanagerexample

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var btnSendRequest: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
        btnSendRequest = findViewById(R.id.btn_send_request)


        btnSendRequest.setOnClickListener {

            val oneTimeRequestConstraints = Constraints.Builder()
                .setRequiresCharging(false)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val data = Data.Builder()
            data.putString("inputKeys", "input value")

            val sampleWork = OneTimeWorkRequest.Builder(OneTimeRequestWorkerClass::class.java)
                .setInputData(data.build())
                .setConstraints(oneTimeRequestConstraints)
                .build()

            WorkManager.getInstance(this).enqueue(sampleWork)

            WorkManager.getInstance(this)
                .getWorkInfoByIdLiveData(sampleWork.id)
                .observe(this, { workInfo ->
                    OneTimeRequestWorkerClass.Companion.logger(workInfo.state.name)

                    workInfo?.let {
                        when (it.state) {
                            WorkInfo.State.ENQUEUED -> {
                                textView.text = "Task Enqueued"
                            }
                            WorkInfo.State.BLOCKED -> {
                                textView.text = "Task Blocked"
                            }
                            WorkInfo.State.RUNNING -> {
                                textView.text = "Task Running"
                            }
                            else -> {
                                textView.text = "Task else apart"
                            }
                        }
                    }

                    if (workInfo != null && workInfo.state.isFinished) {
                        when (workInfo.state) {
                            WorkInfo.State.SUCCEEDED -> {
                                textView.text = "Task Successful"

                                val successInputData = workInfo.outputData
                                val outPutText = successInputData.getString("outputKeys")
                                Log.e("tag output text", "$outPutText")
                            }
                            WorkInfo.State.CANCELLED -> {
                                textView.text = "Task Cancelled"
                            }
                            WorkInfo.State.FAILED -> {
                                textView.text = "Task Failed"
                            }
                            else -> {
                                textView.text = "Task else apart"
                            }
                        }
                    }

                })

        }

    }
}