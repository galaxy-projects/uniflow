package org.galaxy.uniflow.framework.assertions;

import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.function.Consumer;

public class AssertStream {

    public static void assertStdout(String expected, Runnable task) {
        PrintStream prevOut = System.out;

        assertStream(expected, task, System::setOut);
        System.setOut(prevOut);
    }

    public static void assertStderr(String expected, Runnable task) {
        PrintStream prevErr = System.err;

        assertStream(expected, task, System::setErr);
        System.setErr(prevErr);
    }

    private static void assertStream(String expected, Runnable task, Consumer<PrintStream> setter) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            setter.accept(new PrintStream(out));
            task.run();

            Assertions.assertEquals(expected, out.toString());
        } catch (IOException e) {
            Assertions.fail(e);
        }
    }
}
