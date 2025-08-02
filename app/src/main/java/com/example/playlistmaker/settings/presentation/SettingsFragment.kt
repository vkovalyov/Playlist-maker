package com.example.playlistmaker.settings.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentSettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        viewModel.observeState().observe(viewLifecycleOwner) {
            binding.switch1.setChecked(it)
        }

        viewModel.getAppTheme()

        binding.switch1.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setAppTheme(isChecked)
        }
    }
}