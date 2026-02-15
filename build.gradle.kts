plugins {
    `java-library`
    id("com.gradleup.shadow") version "9.0.1"
}

group = "org.galaxy"
version = "0.0.1"

allprojects {
    apply(plugin = "java-library")

    group = rootProject.group
    version = rootProject.version

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    repositories {
        mavenCentral()
    }
}

dependencies {
    implementation(project(":api"))
    implementation(project(":common"))
    implementation(project(":javac"))
    implementation(project(":javac-api"))
}

tasks.shadowJar {
    archiveClassifier.set(null as String?)
}

tasks.build {
    dependsOn(tasks.shadowJar)
}