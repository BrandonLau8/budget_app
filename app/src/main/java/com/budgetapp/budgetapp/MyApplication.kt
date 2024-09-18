package com.budgetapp.budgetapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp //By adding @HiltAndroidApp, you enable Hilt’s dependency injection capabilities for the entire application.
class MyApplication: Application() {
}