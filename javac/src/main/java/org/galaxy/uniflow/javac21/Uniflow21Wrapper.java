package org.galaxy.uniflow.javac21;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.javac15.Uniflow15Wrapper;
import org.galaxy.uniflow.javac15.pattern.JavacBindingPattern;
import org.galaxy.uniflow.javac21.pattern.Javac21AnyPattern;
import org.galaxy.uniflow.javac21.pattern.Javac21DeconstructionPattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Uniflow21Wrapper extends Uniflow15Wrapper {

    public static final Uniflow21Wrapper INSTANCE = new Uniflow21Wrapper();

    @Override
    public @Nullable UniElement wrap(JCTree element) {
        if (element instanceof JCTree.JCPattern)
            return wrap((JCTree.JCPattern) element);
        return super.wrap(element);
    }

    public static @NotNull UniPattern wrap(JCTree.JCPattern pattern) {
        if (Reflection.BINDING_PATTERN_TYPE.isInstance(pattern))
            return new JavacBindingPattern(pattern);
        else if (Reflection.ANY_PATTERN_TYPE.isInstance(pattern))
            return new Javac21AnyPattern(pattern);
        else if (Reflection.RECORD_PATTERN_TYPE.isInstance(pattern))
            return new Javac21DeconstructionPattern(pattern);
        throw new IllegalArgumentException("Unknown pattern: " + pattern);
    }
}
