plugins {
    `java-gradle-plugin`
    `maven-publish`
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        create("uniflow") {
            id = "org.galaxy.uniflow"
            implementationClass = "org.galaxy.uniflow.gradle.UniflowPlugin"
        }
    }
}