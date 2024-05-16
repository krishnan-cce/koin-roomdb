package com.udyata.lifelog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.udyata.lifelog.data.local.DiaryEntry
import com.udyata.lifelog.presentation.DiaryViewModel
import com.udyata.lifelog.ui.theme.LifeLogTheme
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber
import java.text.DateFormat
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LifeLogTheme(
                darkTheme = false
            ) {
                MainScreen()
            }
        }
    }
}


@Composable
fun DairyEntries(viewModel: DiaryViewModel) {

    val uiState by viewModel.diaryEntriesState.collectAsState()

    if (uiState.isLoading()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            Box(modifier = Modifier.align(Alignment.Center)){
                CircularProgressIndicator()
            }
        }

    } else if (uiState.isSuccess()) {
        val data = uiState.getSuccessData()
        LazyColumn {
            items(
                count = data.size,
                key = {
                    data[it].id
                },
                itemContent = {
                    val dairy = data[it]
                    DiaryEntryItem(dairy)
                }
            )
        }
    } else if (uiState.isError()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            Text(text = uiState.getErrorMessage(), modifier = Modifier.align(Alignment.Center) )
        }

    }

}


@Composable
fun DiaryEntryItem(diary: DiaryEntry) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shadowElevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = diary.title, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = diary.content, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Location: ${diary.location}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Date: ${DateFormat.getDateInstance().format(Date(diary.date))}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = diary.mood.image),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = diary.mood.name, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}