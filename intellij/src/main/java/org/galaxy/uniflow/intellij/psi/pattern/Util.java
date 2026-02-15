package org.galaxy.uniflow.intellij.psi.pattern;

import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiInstanceOfExpression;
import com.intellij.psi.PsiPrimaryPattern;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;

class Util {

    static PsiPrimaryPattern createPattern(String text) {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;
        PsiInstanceOfExpression instanceOf = (PsiInstanceOfExpression) factory.createExpressionFromText(
                "a instanceof " + text, null);

        return instanceOf.getPattern();
    }
}
