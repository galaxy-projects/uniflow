package org.galaxy.uniflow.intellij.psi.factories;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiType;
import org.galaxy.uniflow.api.signatures.UniMethodSignature;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.common.factories.CommonMethodFinder;
import org.galaxy.uniflow.intellij.psi.signature.IJMethodSignature;
import org.galaxy.uniflow.intellij.psi.types.IJClassType;
import org.galaxy.uniflow.intellij.psi.types.IJType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

import static org.galaxy.uniflow.intellij.psi.util.IJUtils.check;
import static org.galaxy.uniflow.intellij.psi.util.IJUtils.checkList;

public class IntellijMethodFinder extends CommonMethodFinder {

    @Override
    @SuppressWarnings("rawtypes")
    public @Nullable UniMethodSignature find(@NotNull UniClassType owner,
                                             @NotNull String name,
                                             @NotNull UniType returnType,
                                             @NotNull List<UniType> parameterTypes) {
        IJClassType ijOwner = check(owner, IJClassType.class);
        IJType<?> ijReturnType = check(returnType, IJType.class);
        Stream<IJType> ijParameterTypes = checkList(parameterTypes, IJType.class);
        PsiClass resolved = ijOwner.getRawType().resolve();

        if (resolved == null) {
            ijParameterTypes.close();
            return null;
        }
        PsiMethod[] methods = resolved.findMethodsByName(name, true);
        PsiType rawReturnType = ijReturnType.getRawType();
        int parameterCount = parameterTypes.size();
        PsiType[] rawParameterTypes = ijParameterTypes.map(IJType::getRawType).toArray(PsiType[]::new);

        for (PsiMethod method : methods) {
            if (method.getReturnType() == null) continue;
            if (!method.getReturnType().isAssignableFrom(rawReturnType)) continue;
            PsiParameter[] parameters = method.getParameterList().getParameters();

            if (parameters.length != parameterCount) continue;
            if (areEquivalentTypes(parameters, rawParameterTypes))
                return new IJMethodSignature(method);
        }
        return null;
    }

    private boolean areEquivalentTypes(PsiParameter[] parameters, PsiType[] types) {
        for (int i = 0; i < types.length; i++) {
            if (!parameters[i].getType().isAssignableFrom(types[i]))
                return false;
        }
        return true;
    }
}
