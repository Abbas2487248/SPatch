# SPatch Custom SDK (standalone foundation)

This branch removes the source-level dependency on the unavailable
`net_62v.external` virtual-app SDK and introduces a small compatibility layer
backed by public Android APIs.

## Included in this phase

- `SPatchCore`: immediate lifecycle callback for standalone mode.
- `SPatchPackageManager`: resolves launch intents through Android
  `PackageManager`.
- `SPatchActivityManager`: launcher activity-intent helper.
- `VirtualSpaceActivity`: first-stage Arabic app catalogue where users can select installed apps and persist their selection locally.
- Launcher shortcuts for ordinary installed apps use Android launch intents.
- Uninstalling a downloaded app opens Android's standard package uninstall UI.
- The manifest declares launcher-intent visibility for installed apps.
- ABI filters and ABI split configuration are moved to their valid Gradle
  locations.

## Important limitations

This is **not** a replacement for the proprietary virtual-app engine. The new virtual-space screen is only a saved app-selection catalogue; it does not clone, sandbox, install, or run a second instance of any app.
Standalone mode does not provide app cloning, isolated virtual installations,
virtual package storage, Xposed/module injection, or the original engine's
splash/inner-app behavior. `SPatchCore.isVirtualizationAvailable()` returns
`false` deliberately so callers can detect this limitation.

## Build prerequisites

- Android Studio / Android SDK with API 37 installed.
- JDK 21 (matching the project's Java/Kotlin bytecode target).
- A Gradle installation compatible with Android Gradle Plugin 9.3.2.

The repository currently has no Gradle wrapper files. Generate or install a
compatible Gradle wrapper locally before running the build. No APK has been
built or device-tested as part of this source-only change.
