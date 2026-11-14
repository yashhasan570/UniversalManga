#!/usr/bin/env python3
"""Re-apply the Universal Manga rebrand after each upstream sync (idempotent)."""
from pathlib import Path
import os, shutil
from PIL import Image

ROOT = Path(__file__).resolve().parent.parent

def main():
    # remove upstream telemetry module + wiring
    shutil.rmtree(ROOT/'telemetry', ignore_errors=True)
    def strip(path, patterns):
        p = ROOT/path
        if not p.exists(): return
        s = p.read_text(encoding='utf-8')
        keep = [l for l in s.splitlines(True) if not any(pat in l for pat in patterns)]
        p.write_text(''.join(keep), encoding='utf-8')
    strip('settings.gradle.kts', [':telemetry'])
    strip('app/build.gradle.kts', ['projects.telemetry'])
    strip('app/src/main/java/eu/kanade/tachiyomi/App.kt',
          ['mihon.telemetry','TelemetryConfig.init','TelemetryConfig::set'])
    strip('app/src/main/java/universalmanga/app/di/AppBindings.kt',
          ['mihon.telemetry','TelemetryConfig.recordException'])
    p = ROOT/'app/src/main/java/eu/kanade/tachiyomi/util/system/BuildConfig.kt'
    if p.exists():
        s = p.read_text(encoding='utf-8')
        s = s.replace('inline get() = BuildConfig.TELEMETRY_INCLUDED','inline get() = false')
        p.write_text(s, encoding='utf-8')
    # sweep
    REPL = [
        ('app.mihon','com.universalmanga.app'),
        ('https://github.com/mihonapp/mihon','https://github.com/yashhasan570/UniversalManga'),
        ('mihonapp/mihon-preview','yashhasan570/UniversalManga-preview'),
        ('mihonapp/mihon','yashhasan570/UniversalManga'),
        ('github.com/mihonapp','github.com/yashhasan570/UniversalManga'),
        ('Mihon','Universal Manga'),
    ]
    TEXT_EXTS = {'.kt','.kts','.xml','.md','.toml','.pro','.properties','.json','.txt','.yml','.yaml'}
    SKIP = {'.git','node_modules','scripts'}
    changed = 0
    for dirpath, dirnames, filenames in os.walk(ROOT):
        dirnames[:] = [d for d in dirnames if d not in SKIP]
        for fn in filenames:
            p2 = Path(dirpath)/fn
            if p2.suffix.lower() not in TEXT_EXTS: continue
            try: s = p2.read_text(encoding='utf-8')
            except Exception: continue
            o = s
            for a,b in REPL: s = s.replace(a,b)
            if s != o:
                p2.write_text(s, encoding='utf-8'); changed += 1
    # icons
    res = ROOT/'app/src/main/res'
    (res/'drawable/ic_launcher_foreground.xml').unlink(missing_ok=True)
    bg = res/'drawable/ic_launcher_background.xml'
    if bg.exists():
        bg.write_text('<?xml version="1.0" encoding="utf-8"?>\n<shape xmlns:android="http://schemas.android.com/apk/res/android" android:shape="rectangle">\n  <solid android:color="#0D1B2A"/>\n</shape>\n')
    badge = ROOT/'scripts/icons/badge.png'
    n = 0
    if badge.exists():
        img = Image.open(badge).convert('RGBA')
        for d,size in [('mdpi',108),('hdpi',162),('xhdpi',216),('xxhdpi',324),('xxxhdpi',432)]:
            dd = res/f'drawable-{d}'; dd.mkdir(exist_ok=True)
            canvas = Image.new('RGBA',(size,size),(0,0,0,0))
            inner = img.resize((int(size*0.66),)*2, Image.LANCZOS)
            canvas.paste(inner, ((size-inner.size[0])//2,)*2, inner)
            canvas.save(dd/'ic_launcher_foreground.png'); n+=1
    print(f'migrate: {changed} files swept, {n} icon files updated')

if __name__ == '__main__':
    main()
