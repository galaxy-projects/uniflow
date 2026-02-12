package org.galaxy.uniflow.common.logger;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.logger.UniBuildLogger;
import org.jetbrains.annotations.NotNull;

public class NoOPBuildLogger implements UniBuildLogger {

    public static final NoOPBuildLogger INSTANCE = new NoOPBuildLogger();

    @Override
    public void log(@NotNull MessageKind kind, @NotNull CharSequence msg) {}

    @Override
    public void log(@NotNull MessageKind kind, @NotNull CharSequence msg, @NotNull UniElement element) {}

    @Override
    public void log(@NotNull MessageKind kind,
                    @NotNull CharSequence msg,
                    @NotNull UniElement element,
                    @NotNull UniAnnotation annotation) {}

    @Override
    public void log(@NotNull MessageKind kind,
                    @NotNull CharSequence msg,
                    @NotNull UniElement element,
                    @NotNull UniAnnotation annotation,
                    @NotNull String attributeName) {}

}
