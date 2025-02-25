package de.bueny.labbayk.ui.screens

import android.text.BidiFormatter
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.bueny.labbayk.data.remote.ChapterResponse
import de.bueny.labbayk.util.customTextStyle
import de.bueny.labbayk.util.toArabicNumber


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QuranScreen(
    modifier: Modifier = Modifier,
    chapter: State<ChapterResponse?>,
    onVerseClick: (String) -> Unit
) {
    val bismillah = "بِسْمِ ٱللَّهِ ٱلرَّحْمَـٰنِ ٱلرَّحِيمِ"

//    val textStyle = TextStyle(
//        fontSize = 24.sp,
//        lineHeight = 30.sp,
//        fontWeight = FontWeight.Bold,
//        fontFamily = FontFamily.Serif,
//        textAlign = TextAlign.Right
//    )

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Text(
                text = bismillah,
                style = customTextStyle().copy(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                chapter.value?.arabic1.orEmpty().forEachIndexed { index, verse ->

                    if(verse == bismillah) {
                        return@forEachIndexed
                    }

                    val arabicIndex = toArabicNumber(index + 1)
                    val formattedText = BidiFormatter.getInstance(true).unicodeWrap(verse)

                    val words = formattedText.split(" ")
                    val lines = mutableListOf<String>()
                    var currentLine = ""

                    words.forEach { word ->
                        if (("$currentLine $word").length < 5) {
                            currentLine = "$currentLine $word"
                        } else {
                            lines.add(currentLine.trim())
                            currentLine = word
                        }
                    }

                    if (currentLine.isNotEmpty()) {
                        lines.add(currentLine.trim())
                    }

                    lines.forEach { line ->
                        Text(
                            text = line,
                            modifier = Modifier
                                .padding(1.dp)
                                .clickable { chapter.value?.let { onVerseClick(it.english[index]) } },
                            style = customTextStyle()
                        )
                    }

                    Box(
                        modifier = Modifier
                            .height(30.dp)
                            .width(15.dp)
                            .background(
                                color = Color(0xBE2A4073),
                                shape = CircleShape
                            )
                            .wrapContentSize(Alignment.Center)
                    ) {
                        Text(
                            text = arabicIndex,
                            color = Color.White,
                            style = customTextStyle().copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}