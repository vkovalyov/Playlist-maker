package com.example.playlistmaker.settings.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.playlistmaker.R
import com.example.playlistmaker.core.theme.AppTextStyle
import com.example.playlistmaker.core.theme.AppTheme
import com.example.playlistmaker.core.theme.LocalAppColors
import com.example.playlistmaker.core.theme.toTextStyle
import com.example.playlistmaker.core.ui.CustomTopBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {
    private val viewModel: SettingsViewModel by viewModel()


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val composeView = ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                AppTheme {
                    Scaffold(
                        topBar = {
                            CustomTopBar(title = stringResource(R.string.settings))
                        }) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = innerPadding.calculateTopPadding())
                        ) {
                            SettingsScreen(
                                viewModel = viewModel,
                                onShareClick = { shareLink() },
                                onSupportClick = { sendSupportEmail() },
                                onAgreementClick = { openAgreement() })
                        }

                    }
                }
            }
        }
        return composeView
    }

    private fun shareLink() {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, getString(R.string.yandex_link))
        }
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_link)))
    }

    private fun sendSupportEmail() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.mail_student)))
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject_title))
            putExtra(Intent.EXTRA_TEXT, getString(R.string.body_title))
        }
        startActivity(Intent.createChooser(intent, ""))
    }

    private fun openAgreement() {
        val url = getString(R.string.offer)
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onShareClick: () -> Unit,
    onSupportClick: () -> Unit,
    onAgreementClick: () -> Unit
) {
    val colors = LocalAppColors.current
    val isDarkTheme by viewModel.observeState().observeAsState(initial = false)

    LaunchedEffect(Unit) {
        viewModel.getAppTheme()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(61.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                stringResource(R.string.dark_theme),
                style = AppTextStyle.SettingMenuItem.toTextStyle()
            )
            SettingsSwitchRow(isDarkTheme, onCheckedChange = {
                viewModel.setAppTheme(it)
            })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(61.dp)
                .clickable(
                    onClick = onShareClick, indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                stringResource(R.string.share),
                style = AppTextStyle.SettingMenuItem.toTextStyle(),
            )
            Icon(
                painter = painterResource(id = R.drawable.share),
                contentDescription = null,
                tint = colors.icon,
            )

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(61.dp)
                .clickable(
                    onClick = onSupportClick,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                ),

            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                stringResource(R.string.write_support),
                style = AppTextStyle.SettingMenuItem.toTextStyle()
            )
            Icon(
                painter = painterResource(id = R.drawable.support),
                contentDescription = null,
                tint = colors.icon,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(61.dp)
                .clickable(
                    onClick = onAgreementClick, indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                stringResource(R.string.agreement),
                style = AppTextStyle.SettingMenuItem.toTextStyle()
            )
            Icon(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = null,
                tint = colors.icon,
            )

        }
    }

}

@Composable
fun SettingsSwitchRow(
    checked: Boolean, onCheckedChange: (Boolean) -> Unit
) {
    val colors = LocalAppColors.current

    androidx.compose.material.Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = androidx.compose.material.SwitchDefaults.colors(
            colors.switcher.thumbColor,
            checkedTrackColor = colors.switcher.trackColor,
            uncheckedThumbColor = colors.switcher.thumbColor,
            uncheckedTrackColor = colors.switcher.trackColor,
        ),
    )
}