# Plexus I18N

[![Maven Central](https://img.shields.io/maven-central/v/org.codehaus.plexus/plexus-i18n.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/org.codehaus.plexus/plexus-i18n)
[![GitHub CI](https://github.com/codehaus-plexus/plexus-i18n/actions/workflows/maven.yml/badge.svg)](https://github.com/codehaus-plexus/plexus-i18n/actions)
[![License](https://img.shields.io/github/license/codehaus-plexus/plexus-i18n.svg?label=License)](https://www.apache.org/licenses/LICENSE-2.0)

Looks up localised messages from `ResourceBundle`s, with a default bundle and locale so callers don't have to pass them every time. Used by Maven reporting plugins to localise generated report text — `maven-project-info-reports-plugin`, `maven-surefire-report-plugin`, `maven-checkstyle-plugin`, `maven-pmd-plugin` and `maven-site-plugin` among them.

It is a thin convenience layer over `java.util.ResourceBundle`, not a translation framework.

## Status

Maintained, but quiet by design — the API is small, stable and finished. Expect dependency updates and build maintenance rather than new features.

Versions before `1.0.0` were published as `1.0-beta-*` for many years; `1.0.0` and `1.1.0` (November 2025) are the same component with the beta label dropped. If you are still on a `1.0-beta-*` version, moving to `1.1.0` should need no code changes.

## Using it

```xml
<dependency>
  <groupId>org.codehaus.plexus</groupId>
  <artifactId>plexus-i18n</artifactId>
  <version>1.1.0</version>
</dependency>
```

Check the badge above for the current version.

```java
@Inject
I18N i18n;

String greeting = i18n.getString("report.title");
String inFrench = i18n.getString("report.title", Locale.FRENCH);
String fromBundle = i18n.getString("my-bundle", Locale.FRENCH, "report.title");
```

`I18N` is a JSR-330 singleton, so any [Eclipse Sisu](https://www.eclipse.org/sisu/) or Guice context can inject it. No Plexus container is involved — that was retired long ago, despite the name.

## Requirements

Java 8 or later.

## Documentation

- [Project site](https://codehaus-plexus.github.io/plexus-i18n/)
- [Javadoc](https://javadoc.io/doc/org.codehaus.plexus/plexus-i18n)
- [Release notes](https://github.com/codehaus-plexus/plexus-i18n/releases)

## Contributing

See [CONTRIBUTING.md](https://github.com/codehaus-plexus/.github/blob/master/CONTRIBUTING.md). In short: `mvn verify` builds, and run `mvn spotless:apply` before pushing or CI will fail on formatting.

Please report security vulnerabilities privately — see [SECURITY.md](https://github.com/codehaus-plexus/.github/blob/master/SECURITY.md), not a public issue.
