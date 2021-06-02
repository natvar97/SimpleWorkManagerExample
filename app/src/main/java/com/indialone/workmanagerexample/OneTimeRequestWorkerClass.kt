package com.indialone.workmanagerexample

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class OneTimeRequestWorkerClass(
    private val context: Context,
    private val params: WorkerParameters
) : Worker(context, params) {
    override fun doWork(): Result {

        val inputValue = inputData.getString("inputKeys")
        Log.e("tag input value", "$inputValue")

        return Result.success(createOutputData())
    }


    private fun createOutputData(): Data {
        return Data.Builder().putString("outputKeys", "Output Value").build()
    }

    object Companion {
        fun logger(message: String) = Log.e("logger", message)
    }

}