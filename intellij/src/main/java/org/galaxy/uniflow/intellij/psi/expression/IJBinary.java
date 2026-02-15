package org.galaxy.uniflow.intellij.psi.expression;

import com.intellij.psi.JavaTokenType;
import com.intellij.psi.PsiBinaryExpression;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.tree.IElementType;
import org.galaxy.uniflow.api.Opcode;
import org.galaxy.uniflow.api.expressions.UniBinary;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.signatures.UniOperatorSignature;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class IJBinary extends IJExpression<PsiBinaryExpression> implements UniBinary {

    private static final Map<IElementType, Kind> BINARY_KIND_MAP = new HashMap<>();
    public static final Map<Opcode, String> OPCODE_CHAR_MAP = new EnumMap<>(Opcode.class);

    public IJBinary(PsiBinaryExpression element) {
        super(element);
    }

    @Override
    public void setLeftOperand(@NotNull UniExpression leftOperand) {
        element.getLOperand().replace(IntellijUnwrapper.unwrap(leftOperand));
    }

    @Override
    public @NotNull UniExpression getLeftOperand() {
        return UniflowWrapper.wrap(element.getLOperand());
    }

    @Override
    public void setRightOperand(@NotNull UniExpression rightOperand) {
        if (element.getROperand() != null)
            element.getROperand().replace(IntellijUnwrapper.unwrap(rightOperand));
        else {
            PsiElementFactory factory = IntellijUniflow.getInstance().factory;
            PsiBinaryExpression dummy = (PsiBinaryExpression) factory.createTypeElementFromText(
                    element.getLOperand().getText() + " * 0", null);

            assert dummy.getROperand() != null;
            dummy.getROperand().replace(IntellijUnwrapper.unwrap(rightOperand));
            replace(dummy);
        }
    }

    @Override
    public @NotNull UniExpression getRightOperand() {
        return UniflowWrapper.wrap(element.getROperand());
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
        IElementType sign = element.getOperationSign().getTokenType();
        Kind kind = BINARY_KIND_MAP.get(sign);

        if (kind == null)
            throw new IllegalArgumentException("Unsupported binary operator: " + sign);
        return kind;
    }

    static {
        BINARY_KIND_MAP.put(JavaTokenType.PLUS, Kind.PLUS);
        BINARY_KIND_MAP.put(JavaTokenType.MINUS, Kind.MINUS);
        BINARY_KIND_MAP.put(JavaTokenType.ASTERISK, Kind.MULTIPLY);
        BINARY_KIND_MAP.put(JavaTokenType.DIV, Kind.DIVIDE);
        BINARY_KIND_MAP.put(JavaTokenType.PERC, Kind.REMAINDER);

        BINARY_KIND_MAP.put(JavaTokenType.EQEQ, Kind.EQUAL_TO);
        BINARY_KIND_MAP.put(JavaTokenType.NE, Kind.NOT_EQUAL_TO);
        BINARY_KIND_MAP.put(JavaTokenType.LT, Kind.LESS_THAN);
        BINARY_KIND_MAP.put(JavaTokenType.LE, Kind.LESS_THAN_EQUAL);
        BINARY_KIND_MAP.put(JavaTokenType.GT, Kind.GREATER_THAN);
        BINARY_KIND_MAP.put(JavaTokenType.GE, Kind.GREATER_THAN_EQUAL);

        BINARY_KIND_MAP.put(JavaTokenType.ANDAND, Kind.CONDITIONAL_AND);
        BINARY_KIND_MAP.put(JavaTokenType.OROR, Kind.CONDITIONAL_OR);
        BINARY_KIND_MAP.put(JavaTokenType.AND, Kind.AND);
        BINARY_KIND_MAP.put(JavaTokenType.OR, Kind.OR);
        BINARY_KIND_MAP.put(JavaTokenType.XOR, Kind.XOR);
        BINARY_KIND_MAP.put(JavaTokenType.LTLT, Kind.LEFT_SHIFT);
        BINARY_KIND_MAP.put(JavaTokenType.GTGT, Kind.RIGHT_SHIFT);
        BINARY_KIND_MAP.put(JavaTokenType.GTGTGT, Kind.UNSIGNED_RIGHT_SHIFT);

        OPCODE_CHAR_MAP.put(Opcode.OR, "a || b");
        OPCODE_CHAR_MAP.put(Opcode.AND, "a && b");
        OPCODE_CHAR_MAP.put(Opcode.BITOR, "a | b");
        OPCODE_CHAR_MAP.put(Opcode.BITAND, "a & b");
        OPCODE_CHAR_MAP.put(Opcode.BITXOR, "a ^ b");
        OPCODE_CHAR_MAP.put(Opcode.EQ, "a == b");
        OPCODE_CHAR_MAP.put(Opcode.NE, "a != b");
        OPCODE_CHAR_MAP.put(Opcode.LT, "a < b");
        OPCODE_CHAR_MAP.put(Opcode.LE, "a <= b");
        OPCODE_CHAR_MAP.put(Opcode.GT, "a > b");
        OPCODE_CHAR_MAP.put(Opcode.GE, "a >= b");
        OPCODE_CHAR_MAP.put(Opcode.SL, "a << b");
        OPCODE_CHAR_MAP.put(Opcode.SR, "a >> b");
        OPCODE_CHAR_MAP.put(Opcode.USR, "a >>> b");
        OPCODE_CHAR_MAP.put(Opcode.PLUS, "a + b");
        OPCODE_CHAR_MAP.put(Opcode.MINUS, "a - b");
        OPCODE_CHAR_MAP.put(Opcode.MUL, "a * b");
        OPCODE_CHAR_MAP.put(Opcode.DIV, "a / b");
        OPCODE_CHAR_MAP.put(Opcode.MOD, "a % b");
    }
}
