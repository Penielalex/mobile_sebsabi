# mobile_sebsabi

Short description
This is the Android client application for the "sebsabi" project, implemented in Kotlin. It contains the Android app module, Gradle build configuration and Android resources. It targets modern Android toolchains and is built with the Gradle Kotlin DSL.

Key goals
- Native Android app written in Kotlin
- Gradle Kotlin DSL build files
- Standard Android app module layout under `app/`

---

## Stack
- Language(s): Kotlin (primary)
- Build: Gradle (Gradle wrapper + Kotlin DSL - `build.gradle.kts`)
- Target: Android (Android SDK / Android Studio)

---

## Repository layout (top-level)
```
.
├── .gitignore
├── .idea/                 # IDE settings (optional)
├── app/                   # Android application module
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src/
│       ├── androidTest/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── ic_launcher-playstore.png
│       │   ├── java/.../com/gebeya/sebsab_mobile/   # Kotlin/Java sources
│       │   └── res/
│       └── test/
├── build.gradle.kts       # root Gradle build
├── gradle/                # gradle wrapper helper files
├── gradlew
├── gradlew.bat
├── gradle.properties
└── settings.gradle.kts
```

Notes:
- The app module is under `app/`. The actual application code lives under `app/src/main/java/com/gebeya/sebsab_mobile` (package path).
- There is no top-level README in the repo yet — this file is intended to be added at the repository root.

---

## How to build & run (shortest path)
Prerequisites
- JDK 11+ (or the version required by your Gradle `toolchain`)
- Android SDK and platform tools installed
- Android Studio (recommended) or command-line Gradle wrapper
- ANDROID_HOME / ANDROID_SDK_ROOT set to your SDK path

Common commands (from repo root)
```bash
# fetch Gradle wrapper and project dependencies
./gradlew :app:dependencies

# assemble debug APK
./gradlew :app:assembleDebug

# install debug APK on a connected device/emulator
./gradlew :app:installDebug

# run unit tests
./gradlew :app:testDebugUnitTest

# run instrumentation (connected) tests
./gradlew :app:connectedAndroidTest
```

Open in Android Studio
- File → Open → select the repository root or `app/` module. Let Android Studio import the Gradle project.

Signed release (overview)
1. Create or obtain a keystore (e.g., `key.jks`).
2. Add signing config to `app/build.gradle.kts` and keep secret properties (keystore password, key alias, key password) outside VCS (e.g., `gradle.properties` local file, environment variables).
3. Build AAB / APK:
```bash
./gradlew :app:bundleRelease   # Android App Bundle
./gradlew :app:assembleRelease # Signed APK (if configured)
```

Environment variables commonly used
- JAVA_HOME → JDK installation
- ANDROID_SDK_ROOT or ANDROID_HOME → Android SDK root
- For signing: KEYSTORE_PASSWORD, KEY_ALIAS, KEY_PASSWORD (or store in local properties)

---

## Testing and quality
- Unit tests: located under `app/src/test`. Run with `./gradlew :app:test`.
- Instrumentation/UI tests: `./gradlew :app:connectedAndroidTest` (requires device/emulator).
- Lint: `./gradlew :app:lint` (inspect Android Lint reports)

---

## Working with code
- App sources: `app/src/main/java/com/gebeya/sebsab_mobile` — look here for activities/fragments/viewmodels/services.
- Resources: `app/src/main/res` — layouts, drawables, strings, styles.
- Android manifest: `app/src/main/AndroidManifest.xml` — app entry points and permissions.

---

## Troubleshooting (common)
- Gradle / dependency issues: delete `~/.gradle/caches` and run `./gradlew --refresh-dependencies`.
- Build fails due to SDK: ensure required Android SDK platforms and build-tools are installed. Use SDK Manager in Android Studio.
- Signing errors: check that keystore paths and passwords are correctly referenced in `gradle.properties` / environment variables.

---

## Contributing
- Create a feature branch from `main` named `feature/<short-description>` or `fix/<issue-id>`.
- Keep commits small and focused; include tests where possible.
- Open a Pull Request describing the change and how to test it.
- Run `./gradlew :app:assembleDebug` and unit tests before opening a PR.

---

## Useful commands summary
```bash
# build
./gradlew :app:assembleDebug

# run tests
./gradlew :app:testDebugUnitTest

# lint
./gradlew :app:lint
```

---

## License & contact
Add your license file (e.g., `LICENSE`) and maintainers here. For questions or access, contact the repository owner.
