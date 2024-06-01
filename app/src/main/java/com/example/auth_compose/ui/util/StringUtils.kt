package com.example.auth_compose.ui.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.PlatformSpanStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit

fun AnnotatedString.Builder.textSpan(text: String) {
    append(text)
}

fun AnnotatedString.Builder.textSpan(text: TextWithStyle) {
    withStyle(text.style) { append(text.text) }
}

fun String.styleWith(
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    fontStyle: FontStyle? = null,
    fontSynthesis: FontSynthesis? = null,
    fontFamily: FontFamily? = null,
    fontFeatureSettings: String? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    baselineShift: BaselineShift? = null,
    textGeometricTransform: TextGeometricTransform? = null,
    localeList: LocaleList? = null,
    background: Color = Color.Unspecified,
    textDecoration: TextDecoration? = null,
    shadow: Shadow? = null,
    platformStyle: PlatformSpanStyle? = null,
    drawStyle: DrawStyle? = null
) = TextWithStyle(
    text = this,
    style = SpanStyle(
        color,
        fontSize,
        fontWeight,
        fontStyle,
        fontSynthesis,
        fontFamily,
        fontFeatureSettings,
        letterSpacing,
        baselineShift,
        textGeometricTransform,
        localeList,
        background,
        textDecoration,
        shadow,
        platformStyle,
        drawStyle,
    ),
)

data class TextWithStyle(
    val text: String,
    val style: SpanStyle,
)