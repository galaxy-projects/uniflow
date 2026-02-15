package org.galaxy.uniflow.intellij.psi.factories;

import com.intellij.psi.*;
import org.galaxy.uniflow.api.Uniflow;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.factories.UniJdk10ElementFactory;
import org.galaxy.uniflow.api.factories.UniTypeFactory;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.intellij.psi.elements.IJAnnotation;
import org.galaxy.uniflow.intellij.psi.expression.IJExpression;
import org.galaxy.uniflow.intellij.psi.statements.IJVariable;
import org.galaxy.uniflow.intellij.psi.types.IJType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

import static org.galaxy.uniflow.intellij.psi.util.IJUtils.check;
import static org.galaxy.uniflow.intellij.psi.util.IJUtils.checkList;

public class IntellijJava10ElementFactory extends IntellijJava9ElementFactory implements UniJdk10ElementFactory {

    public IntellijJava10ElementFactory(PsiElementFactory factory, PsiJavaParserFacade parser, PsiFileFactory files) {
        super(factory, parser, files);
    }

    @Override
    public @NotNull UniVariable createVarVariable(@NotNull List<@NotNull UniAnnotation> annotations,
                                                  @NotNull String name,
                                                  @NotNull Class<?> type,
                                                  @Nullable UniExpression init) {
        UniTypeFactory typeFactory = Uniflow.getInstance().getTypeFactory();

        return createVarVariable(annotations, name, typeFactory.createClassType(type), init);
    }

    @Override
    public @NotNull UniVariable createVarVariable(@NotNull List<@NotNull UniAnnotation> annotations,
                                                  @NotNull String name, @NotNull UniType type,
                                                  @Nullable UniExpression init) {
        Stream<IJAnnotation> ijAnnotations = checkList(annotations, IJAnnotation.class);
        IJType<?> ijType = check(type, IJType.class);
        IJExpression<?> ijInit = check(init, IJExpression.class);

        assert ijInit != null;

        PsiDeclarationStatement variable = factory.createVariableDeclarationStatement(
                name, ijType.getRawType(), ijInit.getElement());

        return new IJVariable((PsiVariable) variable.getDeclaredElements()[0]);
    }
}
