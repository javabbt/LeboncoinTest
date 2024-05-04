package com.yannick.leboncoin.feature.home.presentation.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.leboncoin.resources.R
import com.yannick.leboncoin.base.presentation.compose.composable.SpannedText
import com.yannick.leboncoin.feature.home.domain.model.AlbumUiModel

@Composable
fun ProfileCard(userProfile: AlbumUiModel, clickAction: (AlbumUiModel) -> Unit) {
    Card(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .clickable { clickAction(userProfile) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            ProfilePicture(userProfile.thumbnailUrl, 70.dp)
            ProfileContent(userProfile, Alignment.Start)
        }
    }
}

@Composable
fun ProfileContent(uiModel: AlbumUiModel, alignment: Alignment.Horizontal) {
    Column(
        modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = alignment,
    ) {
        SpannedText(
            text = stringResource(id = R.string.name),
            value = uiModel.title,
        )
        SpannedText(
            text = stringResource(id = R.string.type),
            value = uiModel.albumId,
        )
    }
}

@Composable
fun ProfilePicture(userProfilePic: String, size: Dp) {
    Card(
        shape = CircleShape,
        border = BorderStroke(
            width = 1.dp,
            color = Color.Black,
        ),
        modifier = Modifier.padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ),
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(userProfilePic)
                .addHeader("User-Agent", "Leboncoin")
                .error(android.R.drawable.ic_menu_close_clear_cancel)
                .size(Size.ORIGINAL)
                .build(),
        )

        Image(
            painter = painter,
            contentDescription = "",
            modifier = Modifier.size(size),
            contentScale = ContentScale.Crop,
        )
    }
}
