# Plexus I18N

[![Maven Central](https://img.shields.io/maven-central/v/org.codehaus.plexus/plexus-i18n.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/org.codehaus.plexus/plexus-i18n)
[![GitHub CI](https://github.com/codehaus-plexus/plexus-i18n/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/codehaus-plexus/plexus-i18n/actions/workflows/maven.yml?query=branch%3Amaster)
[![License](https://img.shields.io/github/license/codehaus-plexus/plexus-i18n.svg?label=License)](https://www.apache.org/licenses/LICENSE-2.0)

Looks up localised messages from `ResourceBundle`s, with a default bundle and locale so callers don't have to pass them every time. Used by Maven reporting plugins to localise generated report text — `maven-project-info-reports-plugin`, `maven-surefire-report-plugin`, `maven-checkstyle-plugin`, `maven-pmd-plugin` and `maven-site-plugin` among them.

It is a thin convenience layer over `java.util.ResourceBundle`, not a translation framework.

## Status

Maintained, but quiet by design — the API is small, stable and finished. Expect dependency updates and build maintenance rather than new features.

Versions before `1.0.0` were published as `1.0-beta-*` for many years; `1.0.0` (August 2025) and `1.1.0` (November 2025) are the same component with the beta label dropped. If you are still on a `1.0-beta-*` version, moving to `1.1.0` should need no code changes.

## Using it

```xml
<dependency>
  <groupId>org.codehaus.plexus</groupId>
  <artifactId>plexus-i18n</artifactId>
</dependency>
```

Check the badge above for the current version.

```java
@Inject
I18N i18n;

String title = i18n.getString("my.bundle", Locale.FRENCH, "report.title");
String sameInDefaultLocale = i18n.getString("my.bundle", null, "report.title");
```

The bundle name is the base name that `ResourceBundle.getBundle` takes, so `my.bundle` reads
`my/bundle.properties` and its `_fr` variants from the class path. A `null` locale means the JVM default
locale. The shorter `getString( key )` and `getString( key, locale )` forms read the default bundle, which
a plain JSR-330 injection leaves unset: they throw a `NullPointerException` unless a component
configuration sets `default-bundle-name`, so pass the bundle name as above.

`I18N` is a JSR-330 singleton, so any [Eclipse Sisu](https://www.eclipse.org/sisu/) or Guice context can inject it. No Plexus container is involved — that was retired long ago, despite the name.

## Requirements

Java 8 or later. The JAR declares the automatic module name `org.codehaus.plexus.i18n` (since 1.2.0).

## Documentation

- [Project site](https://codehaus-plexus.github.io/plexus-i18n/), generated from the POM
- [Javadoc](https://javadoc.io/doc/org.codehaus.plexus/plexus-i18n)
- [Release notes](https://github.com/codehaus-plexus/plexus-i18n/releases)

## Contributing

See [CONTRIBUTING.md](https://github.com/codehaus-plexus/.github/blob/master/CONTRIBUTING.md) and [AGENTS.md](AGENTS.md). In short: `mvn verify` builds, and run `mvn spotless:apply` before pushing or CI will fail on formatting.

Please report security vulnerabilities privately — see [SECURITY.md](https://github.com/codehaus-plexus/.github/blob/master/SECURITY.md), not a public issue.
