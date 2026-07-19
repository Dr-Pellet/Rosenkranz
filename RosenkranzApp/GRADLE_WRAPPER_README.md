# Gradle Wrapper Hinweis

Die Datei `gradle/wrapper/gradle-wrapper.jar` ist eine binäre JAR-Datei und
kann nicht als Text generiert werden.

## Option 1: Android Studio (empfohlen)
Öffne das Projekt in Android Studio. Beim ersten Sync wird der Wrapper
automatisch generiert.

## Option 2: Manuell generieren
Falls du Gradle lokal installiert hast:
```bash
gradle wrapper --gradle-version 8.2
```

## Option 3: JAR herunterladen
Lade die Datei manuell herunter:
https://raw.githubusercontent.com/gradle/gradle/v8.2.0/gradle/wrapper/gradle-wrapper.jar

und speichere sie unter:
`RosenkranzApp/gradle/wrapper/gradle-wrapper.jar`
