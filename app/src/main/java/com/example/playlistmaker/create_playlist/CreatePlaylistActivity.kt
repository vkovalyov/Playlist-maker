package com.example.playlistmaker.create_playlist

import android.Manifest
import android.R.attr.duration
import android.R.attr.text
import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityCreatePlaylistBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.markodevcic.peko.PermissionRequester
import com.markodevcic.peko.PermissionResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class CreatePlaylistActivity : AppCompatActivity() {
    private val viewModel: CreatePlayListViewModel by viewModel()

    private lateinit var binding: ActivityCreatePlaylistBinding
    private val requester = PermissionRequester.instance()

    private var nameWatcher: TextWatcher? = null
    private var descriptionWatcher: TextWatcher? = null

    private lateinit var dialogBuilder: MaterialAlertDialogBuilder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCreatePlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = this.getString(R.string.new_playlists)
        dialogBuilder = MaterialAlertDialogBuilder(this)


        viewModel.observePlayerState().observe(this) {
            when (it) {
                CreatePlayListState.Content -> {}
                is CreatePlayListState.Close -> close(it.name)
            }
        }

        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    binding.addImage.setImageURI(uri)
                    viewModel.path = uri
                }
            }

        binding.createPlaylist.setOnClickListener {
            viewModel.createPLayList()
        }

        binding.addImage.setOnClickListener {
            lifecycleScope.launch {
                requester.request(Manifest.permission.READ_MEDIA_IMAGES).collect { result ->
                    when (result) {
                        is PermissionResult.Granted -> {
                            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        }

                        is PermissionResult.Denied -> {
                            val intent =
                                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.fromParts("package", packageName, null)
                                }
                            startActivity(intent)
                        }

                        is PermissionResult.Cancelled -> {}
                    }
                }
            }

        }

        binding.trackName.setOnClickListener {
            binding.trackName.requestFocus()
            binding.scroll.postDelayed({
                binding.scroll.smoothScrollTo(0, binding.trackName.bottom)
            }, 200)
        }

        nameWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.name = (binding.trackName.text ?: "").toString()
                updateButtonState()
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        nameWatcher?.let { binding.trackName.addTextChangedListener(it) }


        descriptionWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.description = (binding.trackDescription.text ?: "").toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        descriptionWatcher?.let { binding.trackDescription.addTextChangedListener(it) }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            handleBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateButtonState() {
        binding.createPlaylist.backgroundTintList = ColorStateList.valueOf(
            if (viewModel.name.isNotEmpty()) {
                binding.createPlaylist.isEnabled = true
                ContextCompat.getColor(this, R.color.button_enabled_color)
            } else {
                binding.createPlaylist.isEnabled = false
                ContextCompat.getColor(this, R.color.button_disabled_color)
            }
        )
    }


    private fun close(name: String) {
        val resultIntent = Intent().apply {
            putExtra("RESULT_MESSAGE", application.getString(R.string.play_list_created, name))
        }
        setResult(Activity.RESULT_OK, resultIntent)
        onBackPressedDispatcher.onBackPressed()
    }

    private fun handleBackPressed() {
        if (viewModel.canBack()) {
            dialogBuilder
                .setTitle(this.getString(R.string.cancel_create_playlist))
                .setMessage(this.getString(R.string.unsaved_will_lost))
                .setNegativeButton(this.getString(R.string.cancel)) { dialog, which ->

                }
                .setPositiveButton(this.getString(R.string.complete)) { dialog, which ->
                    onBackPressedDispatcher.onBackPressed()
                }
                .show()
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}