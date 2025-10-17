package org.galaxy.uniflow.framework.javac.assertions;

import org.galaxy.uniflow.common.EnumUtils;
import org.galaxy.uniflow.framework.assertions.CompilationLog;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.util.Locale;

public record JavacCompilationLog(Diagnostic<? extends JavaFileObject> diagnostic) implements CompilationLog {

    @Override
    public LogKind getKind() {
        return EnumUtils.convert(LogKind.class, diagnostic.getKind());
    }

    @Override
    public long getPosition() {
        return diagnostic.getPosition();
    }

    @Override
    public long getLineNumber() {
        return diagnostic.getLineNumber();
    }

    @Override
    public long getColumnNumber() {
        return diagnostic.getColumnNumber();
    }

    @Override
    public String getMessage(Locale locale) {
        return diagnostic.getMessage(locale);
    }
}
