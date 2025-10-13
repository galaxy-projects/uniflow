package org.galaxy.uniflow.intellij.psi;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiType;
import org.galaxy.uniflow.api.UniClass;
import org.galaxy.uniflow.api.UniClassInitializer;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.UniModifiers;
import org.galaxy.uniflow.api.lists.UniFieldList;
import org.galaxy.uniflow.api.lists.UniMethodList;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.galaxy.uniflow.intellij.psi.lists.*;
import org.galaxy.uniflow.intellij.psi.lists.statements.IJClassInitializerList;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class IJClass extends IJElement<PsiClass> implements UniClass {

    public IJClass(PsiClass element) {
        super(element);
    }

    @Override
    public @NotNull UniClassType asType() {
        return UniflowWrapper.wrapClassType(element);
    }

    @Override
    public @NotNull String getName() {
        return Objects.requireNonNull(element.getQualifiedName());
    }

    @Override
    public boolean isInterface() {
        return element.isInterface();
    }

    @Override
    public boolean isAnnotationType() {
        return element.isAnnotationType();
    }

    @Override
    public boolean isEnum() {
        return element.isEnum();
    }

    @Override
    public boolean isRecord() {
        return element.isRecord();
    }

    @Override
    public @NotNull UniType getExtends() {
        PsiClassType[] superTypes = element.getExtendsListTypes();
        PsiClassType type = superTypes.length > 0 ? superTypes[0] :
                PsiType.getJavaLangObject(element.getManager(), element.getResolveScope());

        return UniflowWrapper.wrapClassType(type);
    }

    @Override
    public @NotNull UniList<@NotNull UniType> getImplements() {
        return new IJReferenceTypeList(element.getImplementsList());
    }

    @Override
    public @NotNull UniList<@NotNull UniTypeParameter> getTypeParameters() {
        return new IJTypeParameterList(element.getTypeParameterList());
    }

    @Override
    public @NotNull UniFieldList getFields() {
        return new IJFieldList(element);
    }

    @Override
    public @NotNull UniMethodList getMethods() {
        return new IJMethodList(element, PsiClass::getMethods);
    }

    @Override
    public @NotNull UniMethodList getConstructors() {
        return new IJMethodList(element, PsiClass::getConstructors);
    }

    @Override
    public @NotNull UniList<@NotNull UniClassInitializer> getInitializers() {
        return new IJClassInitializerList(element);
    }

    @Override
    public @NotNull UniList<@NotNull UniClass> getInnerClasses() {
        return new IJInnerClassList(element);
    }

    @Override
    public @NotNull UniModifiers getModifiers() {
        return new IJModifiers(element.getModifierList());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.CLASS;
    }
}
