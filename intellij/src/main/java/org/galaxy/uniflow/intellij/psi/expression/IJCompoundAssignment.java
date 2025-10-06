package org.galaxy.uniflow.intellij.psi.expression;

import com.intellij.psi.JavaTokenType;
import com.intellij.psi.PsiAssignmentExpression;
import com.intellij.psi.tree.IElementType;
import org.galaxy.uniflow.api.expressions.UniCompoundAssignment;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.signatures.UniOperatorSignature;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class IJCompoundAssignment extends IJExpression<PsiAssignmentExpression> implements UniCompoundAssignment {

    private static final Map<IElementType, Kind> ASSIGNMENT_KIND_MAP = new HashMap<>();

    public IJCompoundAssignment(PsiAssignmentExpression element) {
        super(element);
    }

    @Override
    public void setVariable(@NotNull UniExpression variable) {
        element.getLExpression().replace(IntellijUnwrapper.unwrap(variable));
    }

    @Override
    public @NotNull UniExpression getVariable() {
        return UniflowWrapper.wrap(element.getLExpression());
    }

    @Override
    public void setExpression(@NotNull UniExpression expression) {
        IJAssignment.setExpression(element, expression, this::replace);
    }

    @Override
    public @NotNull UniExpression getExpression() {
        return UniflowWrapper.wrap(element.getRExpression());
    }

    @Override
    public void setOperator(@NotNull UniOperatorSignature operator) {
        element.getLExpression().replace(IntellijUnwrapper.unwrap(operator));
    }

    @Override
    public @NotNull UniOperatorSignature getOperator() {
        return UniflowWrapper.wrap(element.getOperationSign());
    }

    @Override
    public @NotNull Kind getKind() {
        IElementType sign = element.getOperationSign().getTokenType();
        Kind kind = ASSIGNMENT_KIND_MAP.get(sign);

        if (kind == null)
            throw new IllegalArgumentException("Unsupported assignment operator: " + sign);
        return kind;
    }

    static {
        ASSIGNMENT_KIND_MAP.put(JavaTokenType.PLUSEQ, Kind.PLUS_ASSIGNMENT);
        ASSIGNMENT_KIND_MAP.put(JavaTokenType.MINUSEQ, Kind.MINUS_ASSIGNMENT);
        ASSIGNMENT_KIND_MAP.put(JavaTokenType.ASTERISKEQ, Kind.MULTIPLY_ASSIGNMENT);
        ASSIGNMENT_KIND_MAP.put(JavaTokenType.DIVEQ, Kind.DIVIDE_ASSIGNMENT);
        ASSIGNMENT_KIND_MAP.put(JavaTokenType.PERCEQ, Kind.REMAINDER_ASSIGNMENT);
        ASSIGNMENT_KIND_MAP.put(JavaTokenType.ANDEQ, Kind.AND_ASSIGNMENT);
        ASSIGNMENT_KIND_MAP.put(JavaTokenType.OREQ, Kind.OR_ASSIGNMENT);
        ASSIGNMENT_KIND_MAP.put(JavaTokenType.XOREQ, Kind.XOR_ASSIGNMENT);
        ASSIGNMENT_KIND_MAP.put(JavaTokenType.LTLTEQ, Kind.LEFT_SHIFT_ASSIGNMENT);
        ASSIGNMENT_KIND_MAP.put(JavaTokenType.GTGTEQ, Kind.RIGHT_SHIFT_ASSIGNMENT);
        ASSIGNMENT_KIND_MAP.put(JavaTokenType.GTGTGTEQ, Kind.UNSIGNED_RIGHT_SHIFT_ASSIGNMENT);
    }
}
