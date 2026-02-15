package org.galaxy.uniflow.common.logger;

import org.galaxy.uniflow.api.logger.UniSystemLogger;
import org.jetbrains.annotations.NotNull;

public class NoOPSystemLogger implements UniSystemLogger {

    public static final NoOPSystemLogger INSTANCE = new NoOPSystemLogger();

    @Override
    public void debug(@NotNull CharSequence message) {}

    @Override
    public void info(@NotNull CharSequence message) {}

    @Override
    public void warn(@NotNull CharSequence message) {}

    @Override
    public void error(@NotNull CharSequence message) {}

    @Override
    public void error(@NotNull CharSequence message, @NotNull Throwable throwable) {}

}
