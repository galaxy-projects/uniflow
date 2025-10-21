package org.galaxy.uniflow.intellij.psi;

import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiTypeElement;
import org.galaxy.uniflow.api.UniClass;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.UniMethod;
import org.galaxy.uniflow.api.UniModifiers;
import org.galaxy.uniflow.api.lists.UniParameterList;
import org.galaxy.uniflow.api.signatures.UniMethodSignature;
import org.galaxy.uniflow.api.statements.UniBlock;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.galaxy.uniflow.intellij.psi.lists.IJLists;
import org.galaxy.uniflow.intellij.psi.lists.IJParameterList;
import org.galaxy.uniflow.intellij.psi.signature.IJMethodSignature;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IJMethod extends IJElement<PsiMethod> implements UniMethod {

    public IJMethod(PsiMethod element) {
        super(element);
    }

    @Override
    public @NotNull String getName() {
        return element.getName();
    }

    @Override
    public void setReturnType(@NotNull UniType type) {
        replace(IntellijUnwrapper.unwrap(type), element.getBody());
    }

    @Override
    public @NotNull UniType getReturnType() {
        return UniflowWrapper.wrap(element.getReturnType());
    }

    @Override
    public @NotNull UniList<@NotNull UniTypeParameter> getTypeParameters() {
        return IJLists.typeParameters(element.getTypeParameterList());
    }

    @Override
    public @NotNull UniParameterList getParameters() {
        return new IJParameterList(element.getParameterList());
    }

    @Override
    public @NotNull UniList<@NotNull UniType> getThrows() {
        return IJLists.referenceTypeList(element.getThrowsList());
    }

    @Override
    public void setBody(@NotNull UniBlock body) {
        replace(element.getReturnTypeElement(), IntellijUnwrapper.unwrap(body));
    }

    @Override
    public @NotNull UniBlock getBody() {
        return UniflowWrapper.wrap(element.getBody());
    }

    @Override
    public boolean isConstructor() {
        return element.isConstructor();
    }

    @Override
    public boolean isVarArgs() {
        return element.isVarArgs();
    }

    @Override
    public @NotNull UniMethodSignature asSignature() {
        return new IJMethodSignature(element);
    }

    @Override
    public @Nullable UniClass getEnclosingClass() {
        return new IJClass(element.getContainingClass());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.METHOD;
    }

    @Override
    public @NotNull UniModifiers getModifiers() {
        return new IJModifiers(element.getModifierList());
    }

    private void replace(PsiTypeElement returnType, PsiCodeBlock body) {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;

        assert returnType != null;
        PsiMethod newMethod = factory.createMethod(element.getName(), returnType.getType());

        assert newMethod.getTypeParameterList() != null;
        assert element.getTypeParameterList() != null;
        assert newMethod.getBody() != null;

        newMethod.getTypeParameterList().replace(element.getTypeParameterList());
        newMethod.getBody().replace(body);

        replace(newMethod);
    }
}
