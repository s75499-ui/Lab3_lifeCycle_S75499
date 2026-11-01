package com.example.lab3_lifecycle

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    private val TAG = "LC_Home"

    override fun onAttach(context: Context) { super.onAttach(context); Log.d(TAG, "onAttach") }
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); Log.d(TAG, "onCreate") }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        val view = inflater.inflate(R.layout.activity_home_fragment, container, false)
        view.findViewById<Button>(R.id.btnGoDetail)?.setOnClickListener {
            Log.d(TAG, "Detail button clicked")
        }
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { super.onViewCreated(view, savedInstanceState); Log.d(TAG, "onViewCreated") }
    override fun onStart() { super.onStart(); Log.d(TAG, "onStart") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop") }
    override fun onDestroyView() { super.onDestroyView(); Log.d(TAG, "onDestroyView") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy") }
    override fun onDetach() { super.onDetach(); Log.d(TAG, "onDetach") }
}
