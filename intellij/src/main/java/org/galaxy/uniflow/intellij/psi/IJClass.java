package org.galaxy.uniflow.intellij.psi;

import com.intellij.psi.*;
import org.galaxy.uniflow.api.*;
import org.galaxy.uniflow.api.factories.UniTypeFactory;
import org.galaxy.uniflow.api.interfaces.UniExpressionSupplier;
import org.galaxy.uniflow.api.lists.UniFieldList;
import org.galaxy.uniflow.api.lists.UniMethodList;
import org.galaxy.uniflow.api.statements.UniField;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.galaxy.uniflow.intellij.psi.expression.IJExpression;
import org.galaxy.uniflow.intellij.psi.lists.IJFieldList;
import org.galaxy.uniflow.intellij.psi.lists.IJLists;
import org.galaxy.uniflow.intellij.psi.lists.IJMethodList;
import org.galaxy.uniflow.intellij.psi.statements.IJField;
import org.galaxy.uniflow.intellij.psi.types.IJType;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static org.galaxy.uniflow.intellij.psi.util.IJUtils.check;

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
        return IJLists.referenceTypeList(element.getImplementsList());
    }

    @Override
    public @NotNull UniList<@NotNull UniTypeParameter> getTypeParameters() {
        return IJLists.typeParameters(element.getTypeParameterList());
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
        return IJLists.classInitializers(element);
    }

    @Override
    public @NotNull UniList<@NotNull UniClass> getInnerClasses() {
        return IJLists.innerClasses(element);
    }

    @Override
    public @NotNull UniModifiers getModifiers() {
        return new IJModifiers(element.getModifierList());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.CLASS;
    }

    @Override
    public @NotNull UniMethodBuilder createConstructor() {
        return new IJMethodBuilder(this, getName(), true);
    }

    @Override
    public @NotNull UniMethodBuilder createMethod(@NotNull String name) {
        return new IJMethodBuilder(this, name, false);
    }

    @Override
    public @NotNull UniField createField(@NotNull UniModifiers modifiers,
                                         @NotNull String name,
                                         @NotNull Class<?> type,
                                         @Nullable UniExpressionSupplier init) {
        UniTypeFactory typeFactory = Uniflow.getInstance().getTypeFactory();

        return createField(modifiers, name, typeFactory.createClassType(type), init);
    }

    @Override
    public @NotNull UniField createField(@NotNull UniModifiers modifiers,
                                         @NotNull String name,
                                         @NotNull UniType type,
                                         @Nullable UniExpressionSupplier init) {
        IJModifiers ijModifiers = check(modifiers, IJModifiers.class);
        IJType<?> ijType = check(type, IJType.class);
        IJExpression<?> ijInit = check(init, IJExpression.class);
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;

        PsiField field = factory.createField(name, ijType.getRawType());

        if (field.getModifierList() != null)
            field.getModifierList().replace(ijModifiers.getElement());
        else field.add(ijModifiers.getElement());

        if (ijInit != null)
            field.setInitializer(ijInit.getElement());

        addField(field);

        return new IJField(field);
    }

    private void addField(PsiField field) {
        PsiField[] fields = element.getFields();

        if (fields.length == 0)
            element.add(field);
        else
            element.addAfter(field, fields[fields.length - 1]);
    }
}
