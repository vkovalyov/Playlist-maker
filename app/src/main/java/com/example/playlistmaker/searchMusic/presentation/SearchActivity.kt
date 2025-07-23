package com.example.playlistmaker.searchMusic.presentation

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.Creator
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.searchMusic.domain.models.Track
import com.example.playlistmaker.track.TrackViewModel
import com.example.playlistmaker.track.TrackActivity
import com.google.gson.Gson

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private var viewModel: SearchMusicViewModel? = null

    private var historyAdapter = TrackAdapter {
        onClickTrack(it)
    }
    private var textWatcher: TextWatcher? = null

    private val adapter = TrackAdapter {
        onClickTrack(it)
    }


    private fun onClickTrack(track: Track) {
        viewModel?.addHistory(track)
        val intent = Intent(this, TrackActivity::class.java)
        val gson = Gson()
        val json = gson.toJson(track)
        intent.putExtra("track", json)
        startActivity(intent)
    }

    override fun onResume() {
        binding.searchInput.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.searchInput, InputMethodManager.SHOW_IMPLICIT)
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(
            this,
            SearchMusicViewModel.getFactory(
                Creator.provideMusicInteractor(),
                Creator.provideSearchHistoryInteractor(this)
            )
        )[SearchMusicViewModel::class.java]


        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.tracksHistory.historyList.adapter = historyAdapter

        viewModel?.updateHistory()

        binding.trackList.adapter = adapter

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchDebounce()
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    binding.clearIcon.visibility = View.GONE
                } else {
                    binding.clearIcon.visibility = View.VISIBLE
                }
            }
        }

        textWatcher?.let { binding.searchInput.addTextChangedListener(it) }

        binding.searchError.refreshButton.setOnClickListener { search() }

        binding.tracksHistory.btnClearHistory.setOnClickListener { viewModel?.clearHistory() }
        binding.clearIcon.setOnClickListener {
            binding.searchInput.text.clear()
            binding.clearIcon.visibility = View.GONE
            binding.trackList.visibility = View.GONE
            clearFocus(binding.searchInput)
            binding.tracksHistory.historyList.visibility = View.VISIBLE
            viewModel?.updateHistory()
        }

        binding.searchInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                search()
            }
            false
        }

        binding.searchInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && binding.searchInput.text.isEmpty()) {
                binding.tracksHistory.historyList.visibility = View.VISIBLE
                viewModel?.updateHistory()
            } else {
                binding.tracksHistory.historyList.visibility = View.GONE
            }
        }

        viewModel?.observeState()?.observe(this) {
            when (it) {
                is SearchMusicState.ContentHistory -> {
                    binding.progressBar.visibility = View.GONE
                    if (it.tracks.isEmpty()) {
                        binding.tracksHistory.root.visibility = View.GONE
                    } else {
                        binding.tracksHistory.root.visibility = View.VISIBLE
                        historyAdapter.updateData(it.tracks)
                    }
                }

                is SearchMusicState.ContentSearchResult -> {
                    binding.apply {
                        progressBar.visibility = View.GONE
                        trackList.visibility = View.VISIBLE
                    }
                    adapter.updateData(it.tracks)
                }

                is SearchMusicState.Empty -> {
                    binding.apply {
                        progressBar.visibility = View.GONE
                        searchNotFound.root.visibility = View.VISIBLE
                    }
                }

                is SearchMusicState.Error -> {
                    binding.apply {
                        progressBar.visibility = View.GONE
                        searchError.root.visibility = View.VISIBLE
                    }
                }

                SearchMusicState.Loading -> {
                    binding.apply {
                        progressBar.visibility = View.VISIBLE
                        tracksHistory.root.visibility = View.GONE
                        searchError.root.visibility = View.GONE
                        searchNotFound.root.visibility = View.GONE
                        trackList.visibility = View.GONE
                    }

                }
            }
        }

    }

    private fun search() {
        viewModel?.search(
            changedText = (binding.searchInput.text ?: "").toString()
        )
    }

    private fun searchDebounce() {
        viewModel?.searchDebounce(
            changedText = (binding.searchInput.text ?: "").toString()
        )
    }

    private fun clearFocus(inputEditText: EditText) {
        inputEditText.clearFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(inputEditText.windowToken, 0)
        viewModel?.updateHistory()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        textWatcher?.let { binding.searchInput.removeTextChangedListener(it) }
    }
}