package com.rohitneel.dailynews.helper

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object KoinHelper : KoinComponent

inline fun <reified T : Any> koinInject(): T {
    return KoinHelper.inject<T>().value
}