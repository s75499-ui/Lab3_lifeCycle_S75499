package com.example.lab3_lifecycle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import LoggingActivity

// --- C3 & D1: ViewModel & SavedStateHandle ---
class CounterViewModel(private val state: SavedStateHandle) : ViewModel() {
    var count: Int
        get() = state["count"] ?: 0
        set(value) { state["count"] = value }

    init {
        Log.d("LC_VM", "ViewModel created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("LC_VM", "ViewModel destroyed")
    }
}

// --- E1: Lifecycle Observer ---
class LogObserver : DefaultLifecycleObserver {
    override fun onStart(owner: LifecycleOwner) {
        Log.d("LC_OBS", "start")
    }

    override fun onStop(owner: LifecycleOwner) {
        Log.d("LC_OBS", "stop")
    }
}

class MainActivity : LoggingActivity() {

    private val vm: CounterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // D2: Log SharedPreferences
        val savedVal = getSharedPreferences("lab", MODE_PRIVATE).getInt("saved", -1)
        Log.d("LC_PREF", "saved=$savedVal")

        val tv = findViewById<TextView>(R.id.tvCount)
        val btnAdd = findViewById<Button>(R.id.btnOnPlus)
        val btnDialog = findViewById<Button>(R.id.btnOnDialog)
        val btnTranslucent = findViewById<Button>(R.id.bynOnTransparent)
        val btnSecond = findViewById<Button>(R.id.btnOnSecond)
        val btnClose = findViewById<Button>(R.id.btnOnFinish)

        // Kemaskini paparan Count mengikut ViewModel
        tv.text = "Count: ${vm.count}"

        btnAdd.setOnClickListener {
            vm.count++
            tv.text = "Count: ${vm.count}"
        }

        btnClose.setOnClickListener {
            finish()
        }

        btnDialog.setOnClickListener {
            android.app.AlertDialog.Builder(this)
                .setTitle("Dialog Test")
                .setMessage("Ini ialah dialog biasa.")
                .setPositiveButton("OK", null)
                .show()
        }

        btnTranslucent.setOnClickListener {
            startActivity(Intent(this, TransparentActivity::class.java))
        }

        btnSecond.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

        // E1: Daftar LifecycleObserver
        lifecycle.addObserver(LogObserver())

        // E3: Safe Flow collection dengan repeatOnLifecycle
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                ticker().collect {
                    Log.d("LC_FLOW", "tick $it")
                }
            }
        }
    }

    // D2: Simpan ke SharedPreferences dalam onStop
    override fun onStop() {
        super.onStop()
        getSharedPreferences("lab", MODE_PRIVATE).edit().putInt("saved", vm.count).apply()
    }

    // E2: Ticker Flow function
    private fun ticker() = flow {
        var i = 0
        while (true) {
            emit(i++)
            delay(1000)
        }
    }
}
