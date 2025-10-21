package org.galaxy.uniflow.intellij.psi.signature;

import com.intellij.psi.PsiJavaToken;
import org.galaxy.uniflow.api.signatures.UniOperatorSignature;

public record IJOperatorSignature(PsiJavaToken token) implements UniOperatorSignature {}
