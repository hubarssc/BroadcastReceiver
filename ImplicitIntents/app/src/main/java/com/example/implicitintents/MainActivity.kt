package com.example.implicitintents

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var mWebsiteEditText: EditText
    private lateinit var mLocationEditText: EditText
    private lateinit var mShareTextEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mWebsiteEditText = findViewById(R.id.website_edittext)
        mLocationEditText = findViewById(R.id.location_edittext)
        mShareTextEditText = findViewById(R.id.share_edittext)
    }

    fun openWebsite(view: View?) {
        Log.d("ImplicitIntents", "Open Website button clicked")
        val url: String = mWebsiteEditText.text.toString()
        val webpage = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)

        val chooserIntent = Intent.createChooser(intent, "Open website with:")

        if (chooserIntent.resolveActivity(packageManager) != null) {
            startActivity(chooserIntent)
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!")
        }
    }

    fun openLocation(view: View) {
        Log.d("ImplicitIntents", "Open Location button clicked")
        val loc = mLocationEditText!!.text.toString()
        val addressUri = Uri.parse("geo:0,0?q=$loc")
        val intent = Intent(Intent.ACTION_VIEW, addressUri)

        val chooserIntent = Intent.createChooser(intent, "Open location with:")
        if (chooserIntent.resolveActivity(packageManager) != null) {
            startActivity(chooserIntent)
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!")
        }
    }

    fun shareText(view: View) {
        val txt = mShareTextEditText.text.toString()
        val mimeType = "text/plain"

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = mimeType
        intent.putExtra(Intent.EXTRA_TEXT, txt)

        val chooserIntent = Intent.createChooser(intent, "Share this text with:")
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(chooserIntent)
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!")
        }
    }
}