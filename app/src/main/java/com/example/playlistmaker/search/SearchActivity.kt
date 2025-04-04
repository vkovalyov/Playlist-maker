package com.example.playlistmaker.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.models.ITunesResponse
import com.example.playlistmaker.retrofit.musicApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val SEARCH_TEXT = "search_text"
const val MUSIC_TRACK = "musicTrack"

class SearchActivity : AppCompatActivity() {

    private lateinit var progressBar: LinearLayout
    private lateinit var searchError: LinearLayout
    private lateinit var searchNotFound: LinearLayout
    private lateinit var recycler: RecyclerView
    private lateinit var inputEditText: EditText

    private lateinit var adapter: TrackAdapter

    private var searchText: String = ""

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_TEXT, searchText)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState != null) {
            searchText = savedInstanceState.getString(SEARCH_TEXT).toString()
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        searchError = findViewById(R.id.search_error)
        searchNotFound = findViewById(R.id.search_not_found)
        progressBar = findViewById(R.id.progressBar)

        val refreshButton = findViewById<Button>(R.id.refresh_button)
        refreshButton.setOnClickListener { sendRequest() }

        recycler = findViewById(R.id.trackList)
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = TrackAdapter(listOf())
        recycler.adapter = adapter

        val clearButton = findViewById<ImageView>(R.id.clear_icon)
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    clearButton.visibility =
                        View.GONE
                } else {
                    clearButton.visibility = View.VISIBLE
                    searchText = s.toString()
                }
            }
        }
        inputEditText = findViewById(R.id.searchInput)
        inputEditText.addTextChangedListener(simpleTextWatcher)

        clearButton.setOnClickListener {
            inputEditText.text.clear()
            clearButton.visibility = View.GONE
            clearFocus(inputEditText)
            recycler.visibility = View.GONE
        }

        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                sendRequest()
            }
            false
        }


    }

    private fun sendRequest() {
        progressBar.visibility = View.VISIBLE
        searchError.visibility = View.GONE
        searchNotFound.visibility = View.GONE
        recycler.visibility = View.GONE

        musicApiService.searchMusic(searchText.trim(), MUSIC_TRACK)
            .enqueue(object : Callback<ITunesResponse> {
                override fun onResponse(
                    call: Call<ITunesResponse>,
                    response: Response<ITunesResponse>
                ) {
                    progressBar.visibility = View.GONE

                    if (response.isSuccessful) {
                        val music = response.body()?.results

                        if (music != null) {
                            if (music.isEmpty()) {
                                searchNotFound.visibility = View.VISIBLE
                            } else {
                                recycler.visibility = View.VISIBLE
                                adapter.updateData(music)

                            }
                        }

                    } else {
                        searchError.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(p0: Call<ITunesResponse>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    searchError.visibility = View.VISIBLE
                }
            })
    }


    private fun clearFocus(inputEditText: EditText) {
        inputEditText.clearFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(inputEditText.windowToken, 0)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}