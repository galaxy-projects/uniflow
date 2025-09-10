package org.galaxy.uniflow.javac.pattern;

import com.sun.tools.javac.tree.JCTree.JCParenthesizedPattern;
import org.galaxy.uniflow.api.pattern.UniParenthesizedPattern;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public class JavacParenthesizedPattern extends JavacPattern<JCParenthesizedPattern>
        implements UniParenthesizedPattern {

    public JavacParenthesizedPattern(@NotNull JCParenthesizedPattern tree) {
        super(tree);
    }

    @Override
    public void setPattern(@NotNull UniPattern pattern) {
        tree.pattern = JavacUnwrapper.unwrap(pattern);
    }

    @Override
    public @NotNull UniPattern getPattern() {
        return UniflowWrapper.wrap(tree.pattern);
    }
}
