package universalmanga.app.di

import android.content.Context
import universalmanga.core.metro.metroGraph

val Context.appGraph get() = metroGraph<AppGraph>()
