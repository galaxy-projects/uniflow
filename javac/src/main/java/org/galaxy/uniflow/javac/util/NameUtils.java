package org.galaxy.uniflow.javac.util;

import com.sun.tools.javac.util.Name;
import org.galaxy.uniflow.javac.JavacUniflow;

public class NameUtils {

    public static Name name(String name) {
        if (name == null) return null;
        return JavacUniflow.getInstance().names.fromString(name);
    }

    public static String nameToString(Name name) {
        return name != null ? name.toString() : null;
    }
}
