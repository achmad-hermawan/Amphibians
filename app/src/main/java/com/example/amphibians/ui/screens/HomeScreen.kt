package com.example.amphibians.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibians.R
import com.example.amphibians.model.Amphibian
import com.example.amphibians.ui.theme.AmphibiansTheme

@Composable
fun HomeScreen(
    amphibiansUiState: AmphibiansUiState, // State UI untuk HomeScreen
    retryAction: () -> Unit, // Aksi saat tombol retry ditekan
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (amphibiansUiState) { // Kondisional berdasarkan state
        is AmphibiansUiState.Loading -> LoadingScreen(modifier.size(200.dp)) // Tampilan loading
        is AmphibiansUiState.Success ->
            AmphibiansListScreen(
                amphibians = amphibiansUiState.amphibians, // Menampilkan daftar data jika sukses
                modifier = modifier
                    .padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        top = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.padding_medium)
                    ),
                contentPadding = contentPadding
            )
        else -> ErrorScreen(retryAction, modifier) // Tampilan error jika gagal
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) { // Tampilan loading dengan gambar
    Image(
        painter = painterResource(R.drawable.loading_img), // Gambar loading
        contentDescription = stringResource(R.string.loading), // Deskripsi untuk aksesibilitas
        modifier = modifier
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) { // Tampilan error dengan tombol retry
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center, // Elemen tersusun di tengah secara vertikal
        horizontalAlignment = Alignment.CenterHorizontally // Elemen tersusun di tengah secara horizontal
    ) {
        Text(stringResource(R.string.loading_failed)) // Pesan error
        Button(onClick = retryAction) { // Tombol untuk mencoba lagi
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun AmphibianCard(amphibian: Amphibian, modifier: Modifier = Modifier) { // Kartu untuk menampilkan data amphibian
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp) // Bentuk sudut kartu
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) { // Tata letak vertikal dengan elemen di tengah
            Text(
                text = stringResource(R.string.amphibian_title, amphibian.name, amphibian.type), // Judul kartu
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.titleLarge, // Gaya teks besar untuk judul
                fontWeight = FontWeight.Bold, // Teks tebal
                textAlign = TextAlign.Start // Teks rata kiri
            )
            AsyncImage(
                modifier = Modifier.fillMaxWidth(), // Gambar mengisi lebar penuh
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(amphibian.imgSrc) // URL gambar
                    .crossfade(true) // Efek transisi gambar
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth, // Gambar menyesuaikan lebar
                error = painterResource(id = R.drawable.ic_broken_image), // Gambar error
                placeholder = painterResource(id = R.drawable.loading_img) // Gambar placeholder saat loading
            )
            Text(
                text = amphibian.description, // Deskripsi amphibian
                style = MaterialTheme.typography.titleMedium, // Gaya teks medium untuk deskripsi
                textAlign = TextAlign.Justify, // Teks rata kanan-kiri
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
            )
        }
    }
}

@Composable
private fun AmphibiansListScreen(
    amphibians: List<Amphibian>, // Daftar data amphibian
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(24.dp) // Spasi antar item
    ) {
        items(
            items = amphibians,
            key = { amphibian ->
                amphibian.name // Kunci unik untuk tiap item
            }
        ) { amphibian ->
            AmphibianCard(amphibian = amphibian, modifier = Modifier.fillMaxSize()) // Menampilkan kartu untuk tiap item
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() { // Pratinjau tampilan loading
    AmphibiansTheme {
        LoadingScreen(
            Modifier
                .fillMaxSize()
                .size(200.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() { // Pratinjau tampilan error
    AmphibiansTheme {
        ErrorScreen({}, Modifier.fillMaxSize())
    }
}

@Preview(showBackground = true)
@Composable
fun AmphibiansListScreenPreview() { // Pratinjau daftar data amphibian
    AmphibiansTheme {
        val mockData = List(10) {
            Amphibian(
                "Lorem Ipsum - $it", // Nama mock data
                "$it", // Tipe mock data
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do" +
                        " eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad" +
                        " minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip" +
                        " ex ea commodo consequat.", // Deskripsi mock data
                imgSrc = ""
            )
        }
        AmphibiansListScreen(mockData, Modifier.fillMaxSize())
    }
}
