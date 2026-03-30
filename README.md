# Compose macOS signing sample

Minimal Compose Desktop project for verifying macOS signing support in the
Compose Gradle plugin.

This sample is intended to validate:

* `createDistributable` with modern development and distribution certificates
* `packagePkg` with modern distribution certificates and installer certificates
* bare and fully qualified signing identities passed through Compose signing configuration

## Requirements

* macOS
* a JDK supported by the Compose Gradle plugin
* the Compose Gradle plugin published to `mavenLocal()` as `9999.0.0-SNAPSHOT`

If you are testing local changes from a checkout of `compose-multiplatform`:

```bash
cd ../compose-multiplatform/gradle-plugins
./gradlew publishToMavenLocal
```

## Build unsigned artifacts

```bash
./gradlew createDistributable
./gradlew packagePkg
```

## Build signed app image

Example using a development certificate:

```bash
./gradlew createDistributable \
  -Pcompose.desktop.mac.sign=true \
  -Pcompose.desktop.mac.signing.identity="Apple Development: Your Name (TEAMID)" \
  -Pcompose.desktop.mac.signing.keychain="$HOME/Library/Keychains/login.keychain-db"
```

## Build signed PKG

Example using a modern App Store style certificate:

```bash
./gradlew packagePkg \
  -Pcompose.desktop.mac.sign=true \
  -Pcompose.desktop.mac.signing.identity="Apple Distribution: Your Name (TEAMID)" \
  -Pcompose.desktop.mac.signing.keychain="$HOME/Library/Keychains/login.keychain-db"
```

For PKG signing you also need a matching installer certificate in the keychain:

* `Developer ID Installer` when using `Developer ID Application`
* `Mac Installer Distribution` or `3rd Party Mac Developer Installer` when using
  `Apple Distribution` or `Mac App Distribution`

Development certificates such as `Apple Development`, `Mac Development`, and
legacy `Mac Developer` can sign app bundles, but they cannot sign installer
packages.
