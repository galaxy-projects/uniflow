package org.galaxy.uniflow.intellij.psi;

import com.intellij.psi.PsiModifier;
import org.galaxy.uniflow.api.elements.UniModifier;
import org.jetbrains.annotations.NotNull;

class Modifiers {

    public static UniModifier getUniflowModifier(@NotNull @PsiModifier.ModifierConstant String name) {
        switch (name) {
            case PsiModifier.PUBLIC:
                return UniModifier.PUBLIC;
            case PsiModifier.PROTECTED:
                return UniModifier.PROTECTED;
            case PsiModifier.PRIVATE:
                return UniModifier.PRIVATE;
            case PsiModifier.STATIC:
                return UniModifier.STATIC;
            case PsiModifier.FINAL:
                return UniModifier.FINAL;
            case PsiModifier.NATIVE:
                return UniModifier.NATIVE;
            case PsiModifier.ABSTRACT:
                return UniModifier.ABSTRACT;
            case PsiModifier.STRICTFP:
                return UniModifier.STRICTFP;
            case PsiModifier.SYNCHRONIZED:
                return UniModifier.SYNCHRONIZED;
            case PsiModifier.VOLATILE:
                return UniModifier.VOLATILE;
            case PsiModifier.TRANSIENT:
                return UniModifier.TRANSIENT;
            case PsiModifier.DEFAULT:
                return UniModifier.DEFAULT;
            case PsiModifier.NON_SEALED:
                return UniModifier.NON_SEALED;
            case PsiModifier.SEALED:
                return UniModifier.SEALED;
            default:
                throw new IllegalArgumentException("Unknown modifier: " + name);
        }
    }

    public static @PsiModifier.ModifierConstant String getPsiModifier(UniModifier modifier) {
        switch (modifier) {
            case PUBLIC:
                return PsiModifier.PUBLIC;
            case PROTECTED:
                return PsiModifier.PROTECTED;
            case PRIVATE:
                return PsiModifier.PRIVATE;
            case STATIC:
                return PsiModifier.STATIC;
            case FINAL:
                return PsiModifier.FINAL;
            case NATIVE:
                return PsiModifier.NATIVE;
            case ABSTRACT:
                return PsiModifier.ABSTRACT;
            case STRICTFP:
                return PsiModifier.STRICTFP;
            case SYNCHRONIZED:
                return PsiModifier.SYNCHRONIZED;
            case VOLATILE:
                return PsiModifier.VOLATILE;
            case TRANSIENT:
                return PsiModifier.TRANSIENT;
            case DEFAULT:
                return PsiModifier.DEFAULT;
            case NON_SEALED:
                return PsiModifier.NON_SEALED;
            case SEALED:
                return PsiModifier.SEALED;
            default:
                throw new IllegalArgumentException("Invalid modifier: " + modifier);
        }
    }
}
