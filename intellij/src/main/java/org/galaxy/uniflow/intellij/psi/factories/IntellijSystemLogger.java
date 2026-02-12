package org.galaxy.uniflow.intellij.psi.factories;

import com.intellij.openapi.diagnostic.Logger;
import org.galaxy.uniflow.api.logger.UniSystemLogger;
import org.jetbrains.annotations.NotNull;

public class IntellijSystemLogger implements UniSystemLogger {

    private final Logger logger;

    public IntellijSystemLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void debug(@NotNull CharSequence message) {
        logger.debug(message.toString());
    }

    @Override
    public void info(@NotNull CharSequence message) {
        logger.info(message.toString());
    }

    @Override
    public void warn(@NotNull CharSequence message) {
        logger.warn(message.toString());
    }

    @Override
    public void error(@NotNull CharSequence message) {
        logger.error(message.toString());
    }

    @Override
    public void error(@NotNull CharSequence message, @NotNull Throwable throwable) {
        logger.error(message.toString(), throwable);
    }
}
