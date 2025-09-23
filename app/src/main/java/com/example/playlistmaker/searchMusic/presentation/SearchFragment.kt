package com.example.playlistmaker.searchMusic.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.FragmentSearchBinding
import com.example.playlistmaker.searchMusic.domain.models.Track
import com.example.playlistmaker.track.TrackActivity
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel

const val TRACK = "track"

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchMusicViewModel by viewModel()

    private var historyAdapter = TrackAdapter {
        onClickTrack(it)
    }
    private var textWatcher: TextWatcher? = null

    private val adapter = TrackAdapter {
        onClickTrack(it)
    }


    private fun onClickTrack(track: Track) {
        viewModel.addHistory(track)
        val intent = Intent(requireContext(), TrackActivity::class.java)
        intent.putExtra("TRACK_KEY", track)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tracksHistory.historyList.adapter = historyAdapter

        viewModel.updateHistory()

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

        binding.tracksHistory.btnClearHistory.setOnClickListener { viewModel.clearHistory() }
        binding.clearIcon.setOnClickListener {
            binding.searchInput.text.clear()
            binding.clearIcon.visibility = View.GONE
            binding.trackList.visibility = View.GONE
            clearFocus(binding.searchInput)
            binding.tracksHistory.historyList.visibility = View.VISIBLE
            viewModel.updateHistory()
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
                viewModel.updateHistory()
            } else {
                binding.tracksHistory.historyList.visibility = View.GONE
            }
        }

        viewModel.observeState().observe(viewLifecycleOwner) {
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
        viewModel.search(
            changedText = (binding.searchInput.text ?: "").toString()
        )
    }

    private fun searchDebounce() {
        viewModel.searchDebounce(
            changedText = (binding.searchInput.text ?: "").toString()
        )
    }

    private fun clearFocus(inputEditText: EditText) {
        inputEditText.clearFocus()
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(inputEditText.windowToken, 0)
        viewModel.updateHistory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        textWatcher?.let { binding.searchInput.removeTextChangedListener(it) }
    }
}