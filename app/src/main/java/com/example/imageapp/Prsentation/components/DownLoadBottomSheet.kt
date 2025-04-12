package com.example.imageapp.Prsentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class) @Composable
fun DownLoadBottomSheet(onDismissRequest: () -> Unit,
                        sheetState: SheetState,
                        isOpen: Boolean,
                        onLabelClick: (ImageDownloadOption) -> Unit,
                        option: List<ImageDownloadOption> = ImageDownloadOption.entries)
{
    if (isOpen)
    {
        ModalBottomSheet(
                modifier = Modifier,
                sheetState = sheetState,
                onDismissRequest = { onDismissRequest() },
        ) {


            option.forEach() { option ->


                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { onLabelClick(option) }) {
                    Text(text = option.label,
                         style = MaterialTheme.typography.bodyLarge,
                         modifier = Modifier.align(Alignment.Center))
                }



            }
            Spacer(modifier = Modifier.height(25.dp).padding(bottom = 40.dp))

        }
    }

}

enum class ImageDownloadOption(val label: String)
{
    SMALL(label = "Download Small Size"), MEDIUM(label = "Download Medium Size"), ORIGINAL(label = "Download Original Size"),
}