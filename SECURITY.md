# Security Policy

## Supported Versions

Security fixes are released for the latest minor version of the current major release.

| Version | Supported |
|---------|-----------|
| 0.1.x   | Yes       |

## Reporting a Vulnerability

**Do not open a public issue for a security problem.**

Report it privately through GitHub's
[private vulnerability reporting](https://github.com/davils-com/kiro/security/advisories/new),
or by email to development@davils.com.

Please include:

- the affected Kiro version, module and JDK,
- a description of the impact — what an attacker gains,
- the steps to reproduce it, ideally as a minimal Kotlin snippet or test,
- any mitigation you are already aware of.

We acknowledge a report within three working days and agree on a disclosure date with you.

## Scope

Kiro is a library: it runs with the privileges of the application that uses it. It shows what the application hands
it, so the application decides what is trusted.

The following are **in scope**:

- input to a component, such as text, a list or a date, that makes Kiro use unbounded memory or CPU or crash,
- a component that executes, opens or loads something the application did not ask for, such as a link or a file,
- a generated icon that differs from the SVG of the pinned Lucide release it was generated from,
- a weakness in the build that lets an artifact other than the pinned and verified ones reach a release.

The following are **not** vulnerabilities in Kiro:

- a vulnerability in Compose Multiplatform, Skiko, the JDK or a library of JetBrains; report those to the respective
  project,
- text that the application shows through a component and that looks misleading; the application decides what it
  shows.

## Verifying a Release

Releases are published to `https://repo.davils.com/releases` and signed with the release key of Davils; its
fingerprint is published in this section with the first release, so a consumer can pin it as a `trusted-key` in its
own `gradle/verification-metadata.xml`. A release also carries a signed build provenance attestation made by the
GitHub Actions run that built it:

```bash
gh attestation verify kiro-ui-components-jvm-0.1.0.jar --repo davils-com/kiro --signer-repo davils-com/kotlin-workflow
```

Snapshots in `https://repo.davils.com/snapshots` are neither signed nor attested and are not meant for production.
