package com.indialone.workmanagerexample

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*

class PeriodicRequestWorker(
    private val context: Context,
    private val params: WorkerParameters
) : Worker(context, params) {
    override fun doWork(): Result {
        val date = getDate(System.currentTimeMillis())
        Log.e("tag date time" , "$date")
        return Result.success()
    }

    private fun getDate(milliSeconds: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS", Locale.getDefault())

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds

        return formatter.format(calendar.time)
    }

}