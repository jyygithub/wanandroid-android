package com.jiangyy.common.utils

inline fun Int?.orZero() = this ?: 0

inline fun Long?.orZero() = this ?: 0L

inline fun String?.orDefault(value: String) = this ?: value

inline fun Long?.isNullOrZero(): Boolean {
    return this == null || this == 0L
}

inline fun Int?.isNullOrZero(): Boolean {
    return this == null || this == 0
}

inline fun Boolean?.orDefault(value: Boolean = false) = this ?: value