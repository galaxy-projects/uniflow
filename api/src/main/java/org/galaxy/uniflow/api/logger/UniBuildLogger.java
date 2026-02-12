package org.galaxy.uniflow.api.logger;

import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.jetbrains.annotations.NotNull;

public interface UniBuildLogger {

    void log(@NotNull MessageKind kind, @NotNull CharSequence msg);

    void log(@NotNull MessageKind kind, @NotNull CharSequence msg, @NotNull UniElement element);

    void log(@NotNull MessageKind kind,
             @NotNull CharSequence msg,
             @NotNull UniElement element,
             @NotNull UniAnnotation annotation);

    void log(@NotNull MessageKind kind,
             @NotNull CharSequence msg,
             @NotNull UniElement element,
             @NotNull UniAnnotation annotation,
             @NotNull String attributeName);

    enum MessageKind {
        /**
         * Problem which prevents the tool's normal completion.
         */
        ERROR,
        /**
         * Problem which does not usually prevent the tool from
         * completing normally.
         */
        WARNING,
        /**
         * Problem similar to a warning, but is mandated by the tool's
         * specification.  For example, the Java Language
         * Specification mandates warnings on certain
         * unchecked operations and the use of deprecated methods.
         */
        MANDATORY_WARNING,
        /**
         * Informative message from the tool.
         */
        NOTE,
        /**
         * Diagnostic which does not fit within the other kinds.
         */
        OTHER,
    }
}
