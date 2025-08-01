plugins {
    `java-library`
}

group = "org.galaxy"
version = "0.0.1"

allprojects {
    apply(plugin = "java-library")

    group = rootProject.group
    version = rootProject.version

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(8))
        }
    }

    tasks.withType<JavaCompile> {
        options.release.set(8)
        options.encoding = "UTF-8"
    }

    repositories {
        mavenCentral()
    }
}
