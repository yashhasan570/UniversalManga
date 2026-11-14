package eu.kanade.presentation.browse.components

import androidx.compose.runtime.Composable
import universalmanga.icons.materialsymbols.MaterialSymbols
import universalmanga.icons.materialsymbols.rounded.CollectionsBookmark
import tachiyomi.presentation.core.components.Badge

@Composable
internal fun InLibraryBadge(enabled: Boolean) {
    if (enabled) {
        Badge(
            imageVector = MaterialSymbols.Rounded.CollectionsBookmark,
        )
    }
}
