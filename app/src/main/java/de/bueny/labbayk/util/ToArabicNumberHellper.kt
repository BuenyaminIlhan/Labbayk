package de.bueny.labbayk.util

fun toArabicNumber(number: Int): String {
    val arabicDigits = arrayOf('٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩')
    return number.toString().map { arabicDigits[it.digitToInt()] }.joinToString("")
}