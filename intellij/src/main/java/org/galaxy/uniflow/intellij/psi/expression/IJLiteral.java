package org.galaxy.uniflow.intellij.psi.expression;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiLiteralExpression;
import org.galaxy.uniflow.api.expressions.UniLiteral;
import org.galaxy.uniflow.api.types.TypeTag;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IJLiteral extends IJExpression<PsiLiteralExpression> implements UniLiteral {

    public IJLiteral(PsiLiteralExpression element) {
        super(element);
    }

    @Override
    public @NotNull TypeTag getTypeTag() {
        if (element.getValue() == null)
            throw new NullPointerException();
        return TypeTag.fromPrimitiveType(element.getValue().getClass());
    }

    @Override
    public void setValue(@Nullable Object value) {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;
        PsiLiteralExpression literal = (PsiLiteralExpression) factory.createExpressionFromText(
                value != null ? value.toString() : "null", null);

        replace(literal);
    }

    @Override
    public @Nullable Object getValue() {
        return element.getValue();
    }

    @Override
    public @NotNull Kind getKind() {
        Object value = element.getValue();

        if (value == null)
            return Kind.NULL_LITERAL;
        else if (value instanceof Byte || value instanceof Short || value instanceof Integer)
            return Kind.INT_LITERAL;
        else if (value instanceof Long)
            return Kind.LONG_LITERAL;
        else if (value instanceof Float)
            return Kind.FLOAT_LITERAL;
        else if (value instanceof Double)
            return Kind.DOUBLE_LITERAL;
        else if (value instanceof Character)
            return Kind.CHAR_LITERAL;
        else if (value instanceof Boolean)
            return Kind.BOOLEAN_LITERAL;
        else if (value instanceof String)
            return Kind.STRING_LITERAL;
        throw new IllegalArgumentException("Invalid literal type");
    }
}
