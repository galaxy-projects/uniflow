package org.galaxy.uniflow.framework.assertions;

import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public record CompilationLogList(List<? extends CompilationLog> logs) {

    public void assertEmpty() {
        Assertions.assertTrue(logs.isEmpty(), "There should be no logs");
    }

    public void assertNotEmpty() {
        Assertions.assertFalse(logs.isEmpty(), "There should be no logs");
    }

    public void assertLog(CompilationLog.LogKind kind, Locale locale, String message) {
        if (logs.stream()
                .noneMatch(log -> log.getKind().equals(kind) && Objects.equals(log.getMessage(locale), message))) {
            Assertions.fail("Missing log: " + message);
        }
    }
}
