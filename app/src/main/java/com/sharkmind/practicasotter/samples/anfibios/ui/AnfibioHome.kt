package com.sharkmind.practicasotter.samples.anfibios.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.sharkmind.practicasotter.R
import com.sharkmind.practicasotter.samples.anfibios.network.AnfibioData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnfibioTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = "Anfibios",
                fontWeight = FontWeight.W500
            )
        },
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnfibioHome() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { AnfibioTopBar(scrollBehavior = scrollBehavior) }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            val anfibioViewModel: AnfibioVM = hiltViewModel()
            AnfibioHomeScreen(
                appState = anfibioViewModel.anfibioUiState,
                contentPadding = it
            )
        }
    }
}

@Composable
fun AnfibioHomeScreen(
    appState: AnfibioUiState,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (appState) {
        is AnfibioUiState.Loading -> AnfibioLoading()
        is AnfibioUiState.Error -> AnfibioError()
        is AnfibioUiState.Success ->
            AnfibioList(
                listaAnfibios = appState.cards,
                contentPadding = contentPadding
            )
    }
}

@Composable
fun AnfibioList(
    listaAnfibios: List<AnfibioData>,
    contentPadding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
        contentPadding = contentPadding
    ) {
        /*
        itemsIndexed(AnfibiosSampleData.sampleData) { indice, item ->
            val img = R.drawable.rana
            AnfibioCard(
                item.name,
                item.type,
                item.description
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
         */
        items(listaAnfibios) { anfibio ->
            AnfibioCard(
                anfibio = anfibio
            )
        }
    }
}

@Composable
fun AnfibioCard(
    anfibio: AnfibioData
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = anfibio.name + "(${anfibio.type})",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold

            )
            /*
            Image(
                painter = painterResource(R.drawable.rana),
                contentDescription = "Rana",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            */
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(anfibio.imgSrc)
                    .crossfade(true)
                    .build(),
                contentDescription = "Espécimen",
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = anfibio.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Composable
fun AnfibioError(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_connection_error),
            contentDescription = ""
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun AnfibioLoading(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading),
        modifier = modifier
    )
}