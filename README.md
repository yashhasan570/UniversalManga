# Universal Manga

A free manga reader for Android: read from thousands of sources, manage your library, track your reading, and download for offline.

Universal Manga is a fork of the Mihon codebase, maintained as a private build. The extension system is unchanged (sources built for Mihon/Tachiyomi keep working).

## Contact

Questions or feedback? Reach us on Telegram: [@johnconstantine570](https://t.me/johnconstantine570)

## Building

Requires the Android SDK and JDK (see `gradle/libs.versions.toml`):

```
./gradlew :app:assembleStandardDebug
```

## Sync

`.github/workflows/sync.yml` pulls upstream changes from the Mihon project daily, re-applies the Universal Manga branding via `scripts/migrate.py`, and pushes.
