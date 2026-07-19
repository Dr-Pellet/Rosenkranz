# Rosenkranz Android App

Eine native Android-App für den täglichen katholischen Rosenkranz, basierend auf
der vorhandenen HTML-Seite. Die App läuft als WebView mit nativen Android-Erweiterungen.

## Features

- **4 Home-Screen-Widgets**
  - Klein (1×1): Wochentag + heutige Geheimnis-Reihe
  - Medium (2×1): Die ersten 3 Geheimnisse der Reihe
  - Groß (3×2): Alle 5 heutigen Geheimnisse
  - Erinnerung (2×1): Zeigt Erinnerungs-Status und Uhrzeit

- **Tägliche Push-Erinnerung**
  - Einstellbare Uhrzeit (Standard: 20:00 Uhr)
  - Zuverlässiger AlarmManager (funktioniert auch nach Neustart)
  - Benachrichtigung mit dem Namen der heutigen Geheimnisse

- **Offline-fähig**
  - Alle Gebetsinhalte sind in der App enthalten
  - Keine Internetverbindung nötig

## Voraussetzungen

- Android Studio Hedgehog (2023.1.1) oder neuer
- JDK 17
- Android SDK 34

## Projekt öffnen & bauen

### 1. Projekt in Android Studio öffnen
```
File → Open → [Pfad zum Ordner RosenkranzApp]
```

### 2. Gradle Wrapper generieren (falls nötig)
Android Studio erkennt automatisch, dass der Gradle Wrapper fehlt, und bietet an,
ihn zu generieren. Klicke auf **"Sync Project with Gradle Files"** (Elefant-Symbol).

Falls das nicht funktioniert, öffne ein Terminal im Projektordner:
```bash
# Falls Gradle lokal installiert:
gradle wrapper --gradle-version 8.2

# Oder lade das Wrapper-JAR manuell herunter:
# https://services.gradle.org/distributions/gradle-8.2-bin.zip
# und entpacke es, dann führe aus:
./gradlew wrapper
```

### 3. APK erstellen
```
Build → Build Bundle(s) / APK(s) → Build APK(s)
```

Die APK findest du unter:
```
app/build/outputs/apk/debug/app-debug.apk
```

Für eine signierte Release-APK:
```
Build → Generate Signed App Bundle or APK → APK
```

## App-Icons

Die App verwendet **adaptive Icons** (API 26+) mit einem stilisierten Kreuz und
Rosenkranz-Perlen. Für ältere Geräte (API 24-25) sind Legacy-Vector-Drawables
als Fallback enthalten.

Das Icon-Set besteht aus:
- `drawable/ic_launcher_foreground.xml` — Vordergrund (Kreuz + Perlen)
- `values/ic_launcher_background.xml` — Hintergrundfarbe (#EEF3F5)
- `mipmap-anydpi-v26/ic_launcher.xml` — Adaptive Icon Definition
- `mipmap-*/ic_launcher.xml` — Legacy Icons für ältere Geräte

## Widgets hinzufügen

1. Lange auf den Home-Screen drücken
2. "Widgets" oder "Widget hinzufügen" wählen
3. Nach "Rosenkranz" suchen
4. Das gewünschte Widget-Format auswählen und platzieren

## Erinnerung einstellen

1. In der App auf das Zahnrad-Symbol (oben rechts) tippen
2. "Tägliche Erinnerung" aktivieren/deaktivieren
3. Uhrzeit wählen
4. Android wird nach Benachrichtigungs-Berechtigung fragen (ab Android 13)

## Projektstruktur

```
RosenkranzApp/
├── gradlew / gradlew.bat         # Gradle Wrapper Skripte
├── gradle/wrapper/
│   └── gradle-wrapper.properties # Gradle 8.2
├── build.gradle                  # Project-Level
├── settings.gradle
├── app/
│   ├── build.gradle              # App-Level (SDK 34, Kotlin 1.9)
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── assets/
│       │   └── rosenkranz.html   # Die Rosenkranz-Webseite
│       ├── java/com/rosenkranz/app/
│       │   ├── MainActivity.kt
│       │   ├── SettingsActivity.kt
│       │   ├── widget/
│       │   │   ├── WidgetDataHelper.kt
│       │   │   ├── SmallWidgetProvider.kt
│       │   │   ├── MediumWidgetProvider.kt
│       │   │   ├── LargeWidgetProvider.kt
│       │   │   └── ReminderWidgetProvider.kt
│       │   └── reminder/
│       │       ├── ReminderReceiver.kt
│       │       ├── ReminderHelper.kt
│       │       └── BootReceiver.kt
│       └── res/
│           ├── layout/
│           ├── xml/              # Widget-Provider-Infos
│           ├── values/           # Strings, Colors, Themes
│           └── drawable/         # Icons & Widget-Hintergründe
```

## Technische Details

- **minSdk:** 24 (Android 7.0)
- **targetSdk:** 34 (Android 14)
- **Architektur:** Single-Activity mit WebView + native Widgets
- **Erinnerung:** AlarmManager mit BroadcastReceiver (kein WorkManager)
- **Daten:** Alle Rosenkranz-Daten sind hardcoded in Kotlin (keine API nötig)

## Hinweise

- Die App benötigt die Berechtigung `POST_NOTIFICATIONS` ab Android 13.
- Exakte Alarme (`SCHEDULE_EXACT_ALARM`) werden für die tägliche Erinnerung verwendet.
- Nach einem Geräte-Neustart wird die Erinnerung automatisch wiederhergestellt.

---

Soli Deo Gloria
