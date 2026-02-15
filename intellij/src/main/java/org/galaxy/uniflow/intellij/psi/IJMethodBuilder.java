package org.galaxy.uniflow.intellij.psi;

import com.intellij.psi.*;
import org.galaxy.uniflow.api.UniClass;
import org.galaxy.uniflow.api.UniMethod;
import org.galaxy.uniflow.api.UniMethodBuilder;
import org.galaxy.uniflow.intellij.psi.expression.IJExpression;
import org.galaxy.uniflow.intellij.psi.statements.IJBlock;
import org.galaxy.uniflow.intellij.psi.statements.IJParameter;
import org.galaxy.uniflow.intellij.psi.types.IJType;
import org.galaxy.uniflow.intellij.psi.types.elements.IJTypeParameter;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

import static org.galaxy.uniflow.intellij.psi.util.IJUtils.check;
import static org.galaxy.uniflow.intellij.psi.util.IJUtils.checkList;

public class IJMethodBuilder extends UniMethodBuilder {

    public IJMethodBuilder(UniClass owner, String name, boolean constructor) {
        super(owner, name, constructor);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniMethod build() {
        checkArgs();

        IJClass ijOwner = check(owner, IJClass.class);
        IJType<?> ijReturnType = check(returnType, IJType.class);
        IJModifiers ijModifiers = check(modifiers, IJModifiers.class);
        Stream<IJTypeParameter> ijTypeParameters = checkList(typeParameters, IJTypeParameter.class);
        Stream<IJParameter> ijParameters = checkList(parameters, IJParameter.class);
        Stream<IJExpression> ijThrown = checkList(thrown, IJExpression.class);

        PsiClass psiClass = ijOwner.getElement();
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;
        PsiMethod method;

        if (constructor)
            method = factory.createConstructor(name);
        else if (defaultValue != null) {
            String returnTypeText = ijReturnType.getRawType().getCanonicalText();

            method = factory.createMethodFromText(
                    "public %s %s() default true;".formatted(returnTypeText, name), psiClass);
        } else method = factory.createMethod(name, ijReturnType.getRawType(), psiClass);

        // apply type parameters
        if (!typeParameters.isEmpty()) {
            PsiTypeParameterList typeParameterList = factory.createTypeParameterList();

            ijTypeParameters.map(IJTypeParameter::getElement).forEach(typeParameterList::add);

            method.getModifierList().replace(ijModifiers.getElement());

            if (method.getTypeParameterList() != null)
                method.getTypeParameterList().replace(typeParameterList);
            else method.add(typeParameterList);
        }

        // apply parameters
        if (!parameters.isEmpty()) {
            PsiParameterList parameterList = method.getParameterList();

            ijParameters.map(IJParameter::getElement).forEach(parameterList::add);
        }
        // apply thrown
        if (!thrown.isEmpty()) {
            PsiReferenceList throwsList = method.getThrowsList();

            ijThrown.map(IntellijUnwrapper::unwrapReference).forEach(throwsList::add);
        }

        // set body if present
        if (body != null) {
            IJBlock ijBody = check(body.get(), IJBlock.class);

            if (ijBody != null) {
                if (method.getBody() != null)
                    method.getBody().replace(ijBody.getElement());
                else method.add(ijBody.getElement());
            }
        }
        // set default value on annotation method
        if (defaultValue != null && method instanceof PsiAnnotationMethod annotationMethod) {
            PsiAnnotationMemberValue psiDefaultValue = IntellijUnwrapper.unwrap(defaultValue.get());

            assert annotationMethod.getDefaultValue() != null;

            annotationMethod.getDefaultValue().replace(psiDefaultValue);
        }

        PsiMethod[] targets = constructor ? psiClass.getConstructors() : psiClass.getMethods();
        PsiMethod target = targets.length > 0 ? targets[targets.length - 1] : null;

        if (target == null)
            psiClass.add(method);
        else
            psiClass.addAfter(method, target);

        return new IJMethod(method);
    }
}
