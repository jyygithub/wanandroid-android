package com.jiangyy.common.utils

import java.text.SimpleDateFormat
import java.util.*

private val String.sdf get() = SimpleDateFormat(this, Locale.CHINA)
private const val DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss"

val NOW_MILLISECOND get() = System.currentTimeMillis()
val NOW_DATE get() = Date()
val NOW_STRING get() = DEFAULT_PATTERN.sdf.format(NOW_DATE)

fun Long.milli2string(pattern: String = DEFAULT_PATTERN): String = pattern.sdf.format(Date(this))
fun Long.milli2date(): Date = Date(this)
fun Date.date2milli(): Long = this.time
fun Date.date2string(pattern: String = DEFAULT_PATTERN): String = pattern.sdf.format(this)
fun String.string2date(pattern: String = DEFAULT_PATTERN): Date = pattern.sdf.parse(this)
fun String.string2mills(pattern: String = DEFAULT_PATTERN): Long = pattern.sdf.parse(this).time
