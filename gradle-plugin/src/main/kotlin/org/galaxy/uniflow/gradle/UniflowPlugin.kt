package org.galaxy.uniflow.gradle;

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test

class UniflowPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val javaVersion = JavaVersion.current()
        val args = mutableListOf<String>()

        if (javaVersion.isJava9Compatible) {
            args.addAll(
                listOf(
                    "--add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED",
                    "--add-exports=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED",
                    "--add-exports=jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED",
                    "--add-exports=jdk.compiler/com.sun.tools.javac.model=ALL-UNNAMED",
                    "--add-exports=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED",
                    "--add-exports=jdk.compiler/com.sun.tools.javac.processing=ALL-UNNAMED",
                    "--add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED",
                    "--add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED",
                    "--add-exports=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED",
                    "--add-exports=jdk.compiler/com.sun.tools.javac.comp=ALL-UNNAMED",
                    "--add-opens=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED",
                    "--add-opens=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED",
                    "--add-opens=jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED",
                    "--add-opens=jdk.compiler/com.sun.tools.javac.model=ALL-UNNAMED",
                    "--add-opens=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED",
                    "--add-opens=jdk.compiler/com.sun.tools.javac.processing=ALL-UNNAMED",
                    "--add-opens=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED",
                    "--add-opens=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED",
                    "--add-opens=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED",
                    "--add-opens=jdk.compiler/com.sun.tools.javac.comp=ALL-UNNAMED"
                )
            )
        }

        project.tasks.withType(JavaCompile::class.java).configureEach {
            options.compilerArgs.addAll(args)
        }

        project.tasks.withType(Test::class.java).configureEach {
            jvmArgs = jvmArgs + args
        }
        project.logger.lifecycle("Uniflow applied: detected JDK ${javaVersion.majorVersion}, added ${args.size} JVM args.")
    }
}