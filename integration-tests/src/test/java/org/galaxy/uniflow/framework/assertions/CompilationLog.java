package org.galaxy.uniflow.framework.assertions;

import java.util.Locale;

public interface CompilationLog {

    LogKind getKind();

    long getPosition();

    long getLineNumber();

    long getColumnNumber();

    String getMessage(Locale locale);

    enum LogKind {
        ERROR,
        WARNING,
        MANDATORY_WARNING,
        NOTE,
        OTHER
    }
}
