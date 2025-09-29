<div align="center">
  <img src=".github/uniflow.png" alt="Uniflow logo" width="256"/>
</div>

![GitHub](https://img.shields.io/github/license/galaxy-projects/uniflow)
![Java Version](https://img.shields.io/badge/Java-8%2B-blue)

## 🌌 What is Uniflow?

> _“Uniflow bridges the gap between parallel universes in Java code analysis.”_

**Uniflow** is a Java library that unifies source code processing between **javac** (AST) and **IntelliJ IDEA** (PSI)
and their environment

It connects different code-processing platforms through a unified API, enabling developers to write once and deploy
across multiple environments.

## 🚀 Goals

- Provide a **platform-agnostic** API for manipulating Java code elements.
- Enable **shared logic** between annotation processors and IntelliJ plugins.
- Abstract over platform-specific APIs like `TreeMaker` (javac) and `PsiElementFactory` (IntelliJ).

## 🧩 Features

- 🧱 Common model: `UniElement`, `UniClass`, `UniMethod`, etc.
- 🔧 Factory abstraction for code generation: `UniElementFactory`
- 💬 Unified logging and diagnostics: `UniMessager`
- 📁 Cross-platform file generation: `UniFiler`
- ☕ Support for both `javac` and `IntelliJ PSI`

---

## 📦 Installation

### Gradle

Add Uniflow to your ``build.gradle``

```groovy
repositories {
    mavenCentral()
    maven {
        name = "Uniflow"
        url = uri("https://maven.pkg.github.com/galaxy-projects/uniflow")
    }
}

dependencies {
    implementation 'org.galaxy:uniflow:1.0.0'
}
```

### Maven

Add Uniflow to your ``pom.xml``:

```xml

<repositories>
    <repository>
        <id>uniflow</id>
        <url>https://maven.pkg.github.com/galaxy-projects/uniflow</url>
    </repository>
</repositories>
```

```xml

<dependency>
    <groupId>org.galaxy</groupId>
    <artifactId>uniflow</artifactId>
    <version>1.0.0</version>
</dependency>
```

---

## 🧪 Example Usage

### Common part

```java
// Common part for PSI and Javac
public class CommonProcessor implements UniProcessor {
    @Override
    public boolean process(@NotNull Uniflow uniflow, @NotNull UniProcessingEnvironment environment) {
        UniElementFactory factory = uniflow.getElementFactory();
        // ...
    }
}
```

### Annotation Processing

```java
// Just a bridge to Java Annotation Processing
public class MyProcessor extends UniflowAnnotationProcessor {
    public MyProcessor() {
        super(new CommonProcessor());
    }
}
```

### IntelliJ Plugin (TODO)

---

## 🧑‍💻 Contributing

Contributions are welcome!

- Fork the repo
- Create a feature branch (feature/my-feature)
- Push and open a pull request to `develop` branch 🚀

---

## 📜 License

MIT License — do whatever you want, but don’t forget to give credit 😊