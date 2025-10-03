plugins {
    id("org.jetbrains.intellij") version "1.17.4"
}

intellij {
    version.set("2025.1.3")
    type.set("IC")
    plugins.set(listOf("java"))
}

tasks.buildSearchableOptions {
    enabled = false
}