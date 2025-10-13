package org.galaxy.uniflow.intellij.psi.signature;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import org.galaxy.uniflow.api.signatures.UniFieldSignature;
import org.galaxy.uniflow.api.signatures.UniMethodSignature;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

public record IJMethodSignature(PsiMethod method) implements UniMethodSignature {

    @Override
    public @NotNull UniClassType getOwner() {
        if (method.getParent() instanceof PsiClass)
            return UniflowWrapper.wrapClassType((PsiClass) method.getParent());
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull String getName() {
        return method.getName();
    }

    @Override
    public @NotNull UniType getReturnType() {
        return UniflowWrapper.wrap(method.getReturnType());
    }

    @Override
    public @NotNull UniFieldSignature @NotNull [] getParameters() {
        PsiParameter[] parameterArray = method.getParameterList().getParameters();
        UniFieldSignature[] parameters = new UniFieldSignature[parameterArray.length];

        for (int i = 0; i < parameterArray.length; i++)
            parameters[i] = new IJFieldSignature(parameterArray[i]);
        return parameters;
    }

    @Override
    public @NotNull UniType @NotNull [] getParameterTypes() {
        PsiParameter[] parameters = method.getParameterList().getParameters();
        UniType[] types = new UniType[parameters.length];

        for (int i = 0; i < types.length; i++)
            types[i] = UniflowWrapper.wrap(parameters[i].getType());
        return types;
    }

    @Override
    public @NotNull UniType @NotNull [] getThrownTypes() {
        PsiClassType[] classTypes = method.getThrowsList().getReferencedTypes();
        UniType[] types = new UniType[classTypes.length];

        for (int i = 0; i < types.length; i++)
            types[i] = UniflowWrapper.wrapClassType(classTypes[i]);
        return types;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IJMethodSignature other)
            return other.method.isEquivalentTo(method);
        return false;
    }
}
