package org.galaxy.uniflow.intellij.psi.util;

import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiJavaToken;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.signatures.UniOperatorSignature;
import org.galaxy.uniflow.api.types.UniType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class UniflowWrapper {

    public static @NotNull UniOperatorSignature wrap(PsiJavaToken token) {
        return null;
    }

    public static @NotNull UniExpression wrap(PsiExpression expression) {
        Objects.requireNonNull(expression, "Expression is null");
        return null;
    }

    public static @NotNull UniType wrapAsType(@Nullable PsiExpression expression) {
        return null;
    }
}
