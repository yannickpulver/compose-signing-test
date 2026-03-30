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
* a local `compose-multiplatform` checkout that includes the macOS signing fixes from
  [PR #5238](https://github.com/JetBrains/compose-multiplatform/pull/5238)

## Prepare the Compose plugin locally

This sample is meant to be used against a local checkout of
`compose-multiplatform` that contains the signing changes from PR `#5238`
or newer.

If needed, check out the PR branch in your `compose-multiplatform` clone:

```bash
cd ../compose-multiplatform
gh pr checkout 5238
```

Then publish the Compose Gradle plugin locally:

```bash
cd ../compose-multiplatform/gradle-plugins
./gradlew publishToMavenLocal
```

## Run locally

From the sample project directory:

```bash
./gradlew run
```

## Typical verification flow

```bash
./gradlew clean createDistributable
./gradlew packagePkg
```

The generated artifacts are written to:

* `build/compose/binaries/main/app`
* `build/compose/binaries/main/pkg`

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
