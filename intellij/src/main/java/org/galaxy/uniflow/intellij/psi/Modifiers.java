package org.galaxy.uniflow.intellij.psi;

import com.intellij.psi.PsiModifier;
import org.galaxy.uniflow.api.elements.UniModifier;
import org.jetbrains.annotations.NotNull;

class Modifiers {

    public static UniModifier getUniflowModifier(@NotNull @PsiModifier.ModifierConstant String name) {
        return switch (name) {
            case PsiModifier.PUBLIC -> UniModifier.PUBLIC;
            case PsiModifier.PROTECTED -> UniModifier.PROTECTED;
            case PsiModifier.PRIVATE -> UniModifier.PRIVATE;
            case PsiModifier.STATIC -> UniModifier.STATIC;
            case PsiModifier.FINAL -> UniModifier.FINAL;
            case PsiModifier.NATIVE -> UniModifier.NATIVE;
            case PsiModifier.ABSTRACT -> UniModifier.ABSTRACT;
            case PsiModifier.STRICTFP -> UniModifier.STRICTFP;
            case PsiModifier.SYNCHRONIZED -> UniModifier.SYNCHRONIZED;
            case PsiModifier.VOLATILE -> UniModifier.VOLATILE;
            case PsiModifier.TRANSIENT -> UniModifier.TRANSIENT;
            case PsiModifier.DEFAULT -> UniModifier.DEFAULT;
            case PsiModifier.NON_SEALED -> UniModifier.NON_SEALED;
            case PsiModifier.SEALED -> UniModifier.SEALED;
            default -> throw new IllegalArgumentException("Unknown modifier: " + name);
        };
    }

    public static @PsiModifier.ModifierConstant String getPsiModifier(UniModifier modifier) {
        return switch (modifier) {
            case PUBLIC -> PsiModifier.PUBLIC;
            case PROTECTED -> PsiModifier.PROTECTED;
            case PRIVATE -> PsiModifier.PRIVATE;
            case STATIC -> PsiModifier.STATIC;
            case FINAL -> PsiModifier.FINAL;
            case NATIVE -> PsiModifier.NATIVE;
            case ABSTRACT -> PsiModifier.ABSTRACT;
            case STRICTFP -> PsiModifier.STRICTFP;
            case SYNCHRONIZED -> PsiModifier.SYNCHRONIZED;
            case VOLATILE -> PsiModifier.VOLATILE;
            case TRANSIENT -> PsiModifier.TRANSIENT;
            case DEFAULT -> PsiModifier.DEFAULT;
            case NON_SEALED -> PsiModifier.NON_SEALED;
            case SEALED -> PsiModifier.SEALED;
            default -> throw new IllegalArgumentException("Invalid modifier: " + modifier);
        };
    }
}
