package com.example.playlistmaker.searchMusic.presentation.composables

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.playlistmaker.R
import com.example.playlistmaker.core.theme.AppTextStyle
import com.example.playlistmaker.core.theme.LocalAppColors
import com.example.playlistmaker.core.theme.toTextStyle
import com.example.playlistmaker.searchMusic.presentation.SearchMusicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(viewModel: SearchMusicViewModel, query: String, onValueChange: (String) -> Unit) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val colors = LocalAppColors.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(colors.texField.focusedContainerColor)
            .padding(start = 12.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.search),
            contentDescription = null,
            tint = colors.textFieldIcon
        )
        Box(
            modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = query,
                onValueChange = { newValue ->
                    viewModel.search(newValue)
                    onValueChange(newValue)
                },
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    viewModel.search(query)
                }),

                cursorBrush = SolidColor(colors.texField.cursorBrush),
                singleLine = true,
                modifier = Modifier
                    .height(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(colors.texField.focusedContainerColor)
                    .padding(
                        start = 12.dp, end = 12.dp, top = 10.dp, bottom = 10.dp
                    ),
                textStyle = LocalTextStyle.current.copy(color = Color.Black)
            )
            if (query.isEmpty()) {
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = stringResource(R.string.search),
                    style = AppTextStyle.HintText.toTextStyle()
                )
            }

        }
        if (query.isNotEmpty()) {
            Icon(
                modifier = Modifier.clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                ) {
                    val imm =
                        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    val view = (context as? Activity)?.currentFocus
                    view?.let {
                        imm.hideSoftInputFromWindow(it.windowToken, 0)
                    }
                    onValueChange("")
                    viewModel.updateHistory()

                },
                painter = painterResource(id = R.drawable.close),
                contentDescription = null,
                tint = colors.textFieldIcon
            )
        }
    }

}