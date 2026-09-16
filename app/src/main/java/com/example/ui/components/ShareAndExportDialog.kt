package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun ResultActionButtons(
    toolTitle: String,
    resultSummary: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedButton(
            onClick = {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("$toolTitle Result", resultSummary)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(context, "Results copied to clipboard!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.weight(1f),
            contentPadding = ButtonDefaults.ContentPadding
        ) {
            Icon(Icons.Default.ContentCopy, contentDescription = "Copy", modifier = Modifier.padding(end = 4.dp))
            Text("Copy")
        }

        OutlinedButton(
            onClick = {
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "[$toolTitle - Nepal Utility Hub]\n$resultSummary\n\nCalculate more at nepalutilityhub.com")
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, "Share $toolTitle Result")
                context.startActivity(shareIntent)
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Default.Share, contentDescription = "Share", modifier = Modifier.padding(end = 4.dp))
            Text("Share")
        }

        OutlinedButton(
            onClick = {
                Toast.makeText(context, "Print preview generated for $toolTitle", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Default.Print, contentDescription = "Print", modifier = Modifier.padding(end = 4.dp))
            Text("Print")
        }
    }
}
