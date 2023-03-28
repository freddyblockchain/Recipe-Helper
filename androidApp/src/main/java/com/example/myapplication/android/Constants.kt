package com.example.myapplication.android

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun getMainArea(): Int{
    return LocalConfiguration.current.screenHeightDp / 100 * 80
}