plugins {
    id("org.jetbrains.intellij.platform")
}

repositories {
    intellijPlatform {
        defaultRepositories()
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(project(":api"))
    compileOnly(project(":common"))

    intellijPlatform {
        //  use this when JetBrains will do it
        //  intellijIdea {
        //      version.set("2025.2.3")
        //      edition.set(IntellijEdition.Community)
        //  }
        @Suppress("DEPRECATION")
        intellijIdeaCommunity("2025.2.3")
        bundledPlugin("com.intellij.java")
    }
}

intellijPlatform {
    buildSearchableOptions = false
    instrumentCode = true
    projectName = project.name

    pluginConfiguration {
        id = "uniflow"
        name = "Uniflow"
        version = project.version.toString()
    }
}
