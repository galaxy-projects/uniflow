package org.galaxy.uniflow.intellij.psi.expression;

import com.intellij.psi.JavaTokenType;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiPostfixExpression;
import com.intellij.psi.PsiUnaryExpression;
import com.intellij.psi.tree.IElementType;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniUnary;
import org.galaxy.uniflow.api.signatures.UniOperatorSignature;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class IJUnary extends IJExpression<PsiUnaryExpression> implements UniUnary {

    public static final Map<IElementType, Kind> UNARY_KIND_MAP = new HashMap<>();

    public IJUnary(PsiUnaryExpression element) {
        super(element);
    }

    @Override
    public void setExpression(@NotNull UniExpression expression) {
        if (element.getOperand() != null)
            element.getOperand().replace(IntellijUnwrapper.unwrap(expression));
        else {
            PsiElementFactory factory = IntellijUniflow.getInstance().factory;
            PsiUnaryExpression dummy = (PsiUnaryExpression) factory.createTypeElementFromText("a++", null);

            assert dummy.getOperand() != null;
            dummy.getOperand().replace(IntellijUnwrapper.unwrap(expression));
            dummy.getOperationSign().replace(element.getOperationSign());
            replace(dummy);
        }
    }

    @Override
    public @NotNull UniExpression getExpression() {
        return UniflowWrapper.wrap(element.getOperand());
    }

    @Override
    public void setOperator(@NotNull UniOperatorSignature operator) {
        element.getOperationSign().replace(IntellijUnwrapper.unwrap(operator));
    }

    @Override
    public @NotNull UniOperatorSignature getOperator() {
        return UniflowWrapper.wrap(element.getOperationSign());
    }

    @Override
    public @NotNull Kind getKind() {
        IElementType token = element.getOperationSign().getTokenType();

        if (element instanceof PsiPostfixExpression) {
            if (token == JavaTokenType.PLUSPLUS)
                return Kind.POSTFIX_INCREMENT;
            else if (token == JavaTokenType.MINUSMINUS)
                return Kind.POSTFIX_DECREMENT;
        }
        Kind kind = UNARY_KIND_MAP.get(token);

        if (kind == null)
            throw new IllegalArgumentException("Unknown unary operator: " + token);
        return kind;
    }

    static {
        UNARY_KIND_MAP.put(JavaTokenType.PLUS, Kind.UNARY_PLUS);
        UNARY_KIND_MAP.put(JavaTokenType.MINUS, Kind.UNARY_MINUS);
        UNARY_KIND_MAP.put(JavaTokenType.EXCL, Kind.LOGICAL_COMPLEMENT);
        UNARY_KIND_MAP.put(JavaTokenType.TILDE, Kind.BITWISE_COMPLEMENT);
        UNARY_KIND_MAP.put(JavaTokenType.PLUSPLUS, Kind.PREFIX_INCREMENT);
        UNARY_KIND_MAP.put(JavaTokenType.MINUSMINUS, Kind.PREFIX_DECREMENT);
    }
}
