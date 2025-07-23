package com.example.playlistmaker.searchMusic.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySearchBinding
    private var viewModel: SearchMusicViewModel? = null

    // private lateinit var viewModel: SearchMusicViewModel
//    private lateinit var progressBar: LinearLayout
//    private lateinit var searchError: LinearLayout
//    private lateinit var searchNotFound: LinearLayout
//    private lateinit var tracksHistory: ScrollView
//    private lateinit var recycler: RecyclerView
//    private lateinit var inputEditText: EditText
    private lateinit var adapter: TrackAdapter
    private lateinit var historyAdapter: TrackAdapter

    private var textWatcher: TextWatcher? = null

//    private val adapter = TrackAdapter {
//        if (clickDebounce()) {
//            val intent = Intent(this, PosterActivity::class.java)
//            intent.putExtra("poster", it.image)
//            startActivity(intent)
//        }
//    }

//    private var searchText: String = ""
//
//    private val searchRunnable = Runnable { sendRequest() }
//    private val handler = Handler(Looper.getMainLooper())

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //  outState.putString(SEARCH_TEXT, searchText)
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

        viewModel = SearchMusicViewModel()
//        viewModel = ViewModelProvider(
//            this,
//            SearchMusicViewModel.getFactory(Creator.provideSearchHistoryInteractor())
//        ).get(SearchMusicViewModel::class.java)
//
//
//        if (savedInstanceState != null) {
//            binding.searchInput.text = savedInstanceState.getString(SEARCH_TEXT).toString()
//        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.clearIcon.setOnClickListener(this@SearchActivity)

        historyAdapter = TrackAdapter(emptyList())
        binding.tracksHistory.historyList.adapter = historyAdapter
        viewModel?.updateHistory()

        val refreshButton = findViewById<Button>(R.id.refresh_button)
        refreshButton.setOnClickListener { viewModel?.searchDebounce(binding.searchInput.text.toString()) }


        adapter = TrackAdapter(listOf())
        binding.trackList.adapter = adapter


        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel?.searchDebounce(
                    changedText = s?.toString() ?: ""
                )
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

        binding.clearIcon.setOnClickListener {
            binding.searchInput.text.clear()
            binding.clearIcon.visibility = View.GONE
            clearFocus(binding.searchInput)
            binding.trackList.visibility = View.GONE
            binding.tracksHistory.historyList.visibility = View.GONE
            viewModel?.updateHistory()
        }

        binding.searchInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel?.searchDebounce(
                    changedText = (binding.searchInput.text ?: "").toString()
                )
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
                    binding.progressBar.visibility = View.GONE
                    binding.trackList.visibility = View.VISIBLE
                    adapter.updateData(it.tracks)
                }

                is SearchMusicState.Empty -> {
                    binding.progressBar.visibility = View.GONE
                    binding.searchNotFound.root.visibility = View.VISIBLE
                }

                is SearchMusicState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.searchError.root.visibility = View.VISIBLE
                }

                SearchMusicState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.tracksHistory.root.visibility = View.GONE
                    binding.searchError.root.visibility = View.GONE
                    binding.searchNotFound.root.visibility = View.GONE
                    binding.trackList.visibility = View.GONE
                }
            }
        }

    }

    private fun clearFocus(inputEditText: EditText) {
        inputEditText.clearFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(inputEditText.windowToken, 0)
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_clear_history -> {
                viewModel?.clearHistory()
                binding.tracksHistory.historyList.visibility = View.GONE
            }

        }
    }
}