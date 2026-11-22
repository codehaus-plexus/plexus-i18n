# Agent guide for plexus-i18n

`org.codehaus.plexus:plexus-i18n` (inheriting the `plexus` parent) looks up localised messages from
`ResourceBundle`s with a default bundle and locale so callers need not pass them each time; Maven
reporting plugins use it to localise generated report text. It is a thin convenience layer over
`java.util.ResourceBundle`, not a translation framework, and its API is small, stable and finished.

## Build and test

- `mvn verify` builds. CI calls the shared `codehaus-plexus/.github` build workflow (default
  `mvn verify javadoc:javadoc`, on the shared JDK and operating system matrix) and then the shared
  deploy workflow.
- Main code targets Java 8 (`javaVersion` 8 from the `plexus` parent), and the JAR sets
  `Automatic-Module-Name: org.codehaus.plexus.i18n`; keep that name stable.
- Run `mvn spotless:apply` on JDK 17 to 21 before pushing, or the formatting check fails.
- Unit tests use JUnit 5. The bundles under `src/test/resources` are test fixtures that the tests
  read by name.
- Dependencies are deliberately minimal: `javax.inject` and the SLF4J API, whose version is the
  `slf4jVersion` property (a deliberate 1.7.x pin). Avoid pulling a framework into this small
  component.

## Documentation updates

A change that users or contributors can notice ships with the documentation it affects, in the
same pull request. A refactor with no visible effect needs none.

- There are no `src/site` pages: the published site is generated from the POM and the Javadoc, and
  the README is the user guide. A change to the `I18N` API or to how bundles and locales are
  resolved: the Javadoc, and the usage example in `README.md`.
- The Java requirement, or how to build and release: `README.md`.
- A change to the project description: the `<description>` in the POM and the opening of `README.md`.
- A new convention, build gate or test fixture: this file.

