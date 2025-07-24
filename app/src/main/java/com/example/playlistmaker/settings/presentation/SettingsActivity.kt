package com.example.playlistmaker.settings.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.Creator
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private var viewModel: SettingsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        enableEdgeToEdge()

        viewModel = ViewModelProvider(
            this,
            SettingsViewModel.getFactory(
                Creator.provideThemeInteractor(context = this),
            )
        )[SettingsViewModel::class.java]


        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.setting)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.share.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.setType("text/plain")
            val link = getString(R.string.yandex_link)
            shareIntent.putExtra(Intent.EXTRA_TEXT, link)
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_link)))
        }
        binding.support.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.mail_student)))
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject_title)) // Тема
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.body_title)) // Тело письма
            startActivity(Intent.createChooser(intent, ""))
        }
        binding.agreement.setOnClickListener {
            val url = getString(R.string.offer)
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }


        viewModel?.observeState()?.observe(this) {
            binding.switch1.setChecked(it)
        }

        viewModel?.getAppTheme()

        binding.switch1.setOnCheckedChangeListener { _, isChecked ->
            viewModel?.setAppTheme(isChecked)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}