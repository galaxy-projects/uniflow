package org.galaxy.uniflow.api.logger;

import org.jetbrains.annotations.NotNull;

public interface UniSystemLogger {

    void debug(@NotNull CharSequence message);

    void info(@NotNull CharSequence message);

    void warn(@NotNull CharSequence message);

    void error(@NotNull CharSequence message);

    void error(@NotNull CharSequence message, @NotNull Throwable throwable);

}
