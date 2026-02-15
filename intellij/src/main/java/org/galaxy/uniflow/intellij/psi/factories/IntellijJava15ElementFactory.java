package org.galaxy.uniflow.intellij.psi.factories;

import com.intellij.psi.*;
import org.galaxy.uniflow.api.UniClass;
import org.galaxy.uniflow.api.UniClassInitializer;
import org.galaxy.uniflow.api.UniModifiers;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniInstanceOf;
import org.galaxy.uniflow.api.factories.UniJdk15ElementFactory;
import org.galaxy.uniflow.api.pattern.UniBindingPattern;
import org.galaxy.uniflow.api.pattern.UniGuardedPattern;
import org.galaxy.uniflow.api.pattern.UniParenthesizedPattern;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.galaxy.uniflow.intellij.psi.IJClass;
import org.galaxy.uniflow.intellij.psi.expression.IJExpression;
import org.galaxy.uniflow.intellij.psi.expression.IJInstanceOf;
import org.galaxy.uniflow.intellij.psi.pattern.IJBindingPattern;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

import static org.galaxy.uniflow.intellij.psi.util.IJUtils.checkList;

public class IntellijJava15ElementFactory extends IntellijJava12ElementFactory implements UniJdk15ElementFactory {

    public IntellijJava15ElementFactory(PsiElementFactory factory, PsiJavaParserFacade parser, PsiFileFactory files) {
        super(factory, parser, files);
    }

    @Override
    public @NotNull UniClass createRecord(@NotNull UniModifiers modifiers, @NotNull String name,
                                          @NotNull List<@NotNull UniTypeParameter> typeParameters,
                                          @NotNull List<@NotNull UniType> implementing,
                                          @NotNull List<@NotNull UniClassInitializer> initializers) {
        PsiClass result = factory.createRecord(name);

        setupClass(result, modifiers, typeParameters, implementing, initializers);

        return new IJClass(result);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniClass createClass(@NotNull UniModifiers modifiers,
                                         @NotNull String name,
                                         @NotNull List<@NotNull UniTypeParameter> typeParameters,
                                         @Nullable UniType extending,
                                         @NotNull List<@NotNull UniType> implementing,
                                         @NotNull List<@NotNull UniExpression> permitting,
                                         @NotNull List<@NotNull UniClassInitializer> initializers) {
        Stream<IJExpression> ijPermitting = checkList(permitting, IJExpression.class);
        PsiClass result = factory.createClass(name);

        setupClass(result, modifiers, typeParameters, implementing, initializers);

        if (!permitting.isEmpty()) {
            PsiReferenceList permittingList = factory.createReferenceList(
                    ijPermitting.map(IntellijUnwrapper::unwrapReference)
                            .toArray(PsiJavaCodeReferenceElement[]::new)
            );

            if (result.getPermitsList() != null)
                result.getPermitsList().replace(permittingList);
            else result.add(permittingList);
        }

        return new IJClass(result);
    }

    @Override
    public @NotNull UniBindingPattern createBindingPattern(@NotNull UniVariable variable) {
        PsiInstanceOfExpression instanceOf = (PsiInstanceOfExpression) factory.createExpressionFromText(
                "a instanceof String s", null);
        PsiPrimaryPattern pattern = instanceOf.getPattern();

        assert pattern != null;
        assert pattern instanceof PsiTypeTestPattern;
        PsiTypeTestPattern typeTest = (PsiTypeTestPattern) pattern.copy();

        assert typeTest.getPatternVariable() != null;

        typeTest.getPatternVariable().replace(IntellijUnwrapper.unwrap(variable));

        return new IJBindingPattern(typeTest);
    }

    @Override
    public @NotNull UniGuardedPattern createGuardedPattern(@NotNull UniPattern pattern,
                                                           @NotNull UniExpression expression) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public @NotNull UniParenthesizedPattern createParenthesizedPattern(@NotNull UniPattern pattern) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public @NotNull UniInstanceOf createInstanceOf(@NotNull UniExpression expression, @NotNull UniPattern pattern) {
        PsiInstanceOfExpression instanceOf = (PsiInstanceOfExpression) factory.createExpressionFromText(
                "a instanceof String s", null);

        assert instanceOf.getPattern() != null;

        instanceOf.getOperand().replace(IntellijUnwrapper.unwrap(expression));
        instanceOf.getPattern().replace(IntellijUnwrapper.unwrap(pattern));

        return new IJInstanceOf(instanceOf);
    }
}
