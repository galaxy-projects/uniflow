package org.galaxy.uniflow.javac12.factories;

import org.galaxy.uniflow.api.elements.UniCase;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniSwitchExpression;
import org.galaxy.uniflow.api.factories.UniJdk12ElementFactory;
import org.galaxy.uniflow.javac.expression.JavacExpression;
import org.galaxy.uniflow.javac.statements.JavacCase;
import org.galaxy.uniflow.javac10.factories.Javac10ElementFactory;
import org.galaxy.uniflow.javac12.expression.JavacSwitchExpression;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

public class Javac12ElementFactory extends Javac10ElementFactory implements UniJdk12ElementFactory {

    @Override
    public boolean supportsJdk12() {
        return true;
    }

    @Override
    public @NotNull UniJdk12ElementFactory asJdk12() {
        return this;
    }

    @Override
    public @NotNull UniSwitchExpression createSwitchExpression(@NotNull UniExpression selector,
                                                               @NotNull List<@NotNull UniCase> cases) {
        JavacExpression<?> javacSelector = check(selector, JavacExpression.class);
        Stream<JavacCase> javacCases = checkList(cases, JavacCase.class);

        return new JavacSwitchExpression(treeMaker.SwitchExpression(
                javacSelector.getTree(),
                mapToList(javacCases, JavacCase::getTree)
        ));
    }
}
