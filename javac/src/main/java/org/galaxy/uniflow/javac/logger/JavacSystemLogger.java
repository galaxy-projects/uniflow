package org.galaxy.uniflow.javac.logger;

import org.galaxy.uniflow.api.logger.UniSystemLogger;
import org.galaxy.uniflow.javac.JavacUniflow;
import org.jetbrains.annotations.NotNull;

import javax.tools.Diagnostic;

public class JavacSystemLogger implements UniSystemLogger {

    @Override
    public void debug(@NotNull CharSequence message) {
        JavacUniflow.getInstance().messager.printMessage(Diagnostic.Kind.NOTE, "[DEBUG] " + message);
    }

    @Override
    public void info(@NotNull CharSequence message) {
        JavacUniflow.getInstance().messager.printMessage(Diagnostic.Kind.NOTE, "[INFO] " + message);
    }

    @Override
    public void warn(@NotNull CharSequence message) {
        JavacUniflow.getInstance().messager.printMessage(Diagnostic.Kind.WARNING, message);
    }

    @Override
    public void error(@NotNull CharSequence message) {
        JavacUniflow.getInstance().messager.printMessage(Diagnostic.Kind.ERROR, message);
    }

    @Override
    public void error(@NotNull CharSequence message, @NotNull Throwable throwable) {
        JavacUniflow.getInstance().messager.printMessage(Diagnostic.Kind.ERROR, message);
        throwable.printStackTrace(System.err);
    }
}
