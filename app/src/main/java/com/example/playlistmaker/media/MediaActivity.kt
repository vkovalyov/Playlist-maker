package com.example.playlistmaker.media

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.playlistmaker.R
import com.example.playlistmaker.core.data.db.domain.models.PlayList
import com.example.playlistmaker.core.theme.AppTextStyle
import com.example.playlistmaker.core.theme.AppTheme
import com.example.playlistmaker.core.theme.LocalAppColors
import com.example.playlistmaker.core.theme.toTextStyle
import com.example.playlistmaker.core.ui.CustomTopBar
import com.example.playlistmaker.create_playlist.CreatePlaylistActivity
import com.example.playlistmaker.media.favorite.FavoriteScreen
import com.example.playlistmaker.media.favorite.FavoritesViewModel
import com.example.playlistmaker.media.playlist.PlayListScreen
import com.example.playlistmaker.media.playlist.PlaylistViewModel
import com.example.playlistmaker.playList.PLAY_LIST_ID_KEY
import com.example.playlistmaker.playList.PlayListActivity
import com.example.playlistmaker.searchMusic.domain.models.Track
import com.example.playlistmaker.track.TrackActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class MediaActivity : Fragment() {
    private val playlistViewModel: PlaylistViewModel by viewModel()
    private val favoritesViewModel: FavoritesViewModel by viewModel()

    private fun onClickTrack(track: Track) {
        val intent = Intent(context, TrackActivity::class.java)
        intent.putExtra("TRACK_KEY", track)
        startActivity(intent)
    }

    private fun onClickCreatePlaylist() {
        val intent = Intent(requireContext(), CreatePlaylistActivity::class.java)
        addPlaylistLauncher.launch(intent)
    }

    private fun onClickPlaylist(playList: PlayList) {
        val intent = Intent(requireContext(), PlayListActivity::class.java)
        intent.putExtra(PLAY_LIST_ID_KEY, playList.id)
        startActivity(intent)
    }

    private val addPlaylistLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.getStringExtra("RESULT_MESSAGE")?.let { message ->
                playlistViewModel.getPlayList()
            }
        }
    }

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
                            CustomTopBar(title = stringResource(R.string.media))
                        }) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = innerPadding.calculateTopPadding())
                        ) {
                            MediaScreen(
                                playlistViewModel,
                                favoritesViewModel,
                                { onClickTrack(it) },
                                { onClickPlaylist(it) },
                                { onClickCreatePlaylist() })
                        }

                    }
                }
            }
        }
        return composeView

    }

}

@Composable
fun MediaScreen(
    playlistViewModel: PlaylistViewModel,
    favoritesViewModel: FavoritesViewModel,
    onClick: (Track) -> Unit,
    onClickPlayList: (PlayList) -> Unit,
    onClickCreatePlayList: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { 2 })
    val selectedTabIndex = pagerState.currentPage
    val colors = LocalAppColors.current

    val selectedTab by remember {
        derivedStateOf {
            selectedTabIndex == 0
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(16.dp)
    ) {
        TabRow(
            divider = {},
            indicator = { tabPositions ->
                if (selectedTabIndex < tabPositions.size) {
                    Box(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex])
                            .height(2.dp)
                            .background(colors.textPrimary)
                    )
                }
            },
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .fillMaxWidth()
                .background(colors.background),
        ) {
            Tab(
                modifier = Modifier.background(colors.background),
                selected = selectedTab,
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.outline,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                },
                text = {
                    Text(
                        text = stringResource(R.string.favorites),
                        style = AppTextStyle.TabText.toTextStyle()
                    )
                },
            )

            Tab(
                modifier = Modifier.background(colors.background),
                selected = selectedTab,
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.outline,
                onClick = {
                    scope.launch {

                        pagerState.animateScrollToPage(1)
                    }
                },
                text = {
                    Text(
                        text = stringResource(R.string.playlists),
                        style = AppTextStyle.TabText.toTextStyle()
                    )
                },
            )
        }

        HorizontalPager(
            state = pagerState, modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            when (page) {
                0 -> FavoriteScreen(favoritesViewModel, onClick)
                1 -> PlayListScreen(
                    playlistViewModel, onClickPlayList, onClickCreatePlayList
                )
            }
        }
    }
}



