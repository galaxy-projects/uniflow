package org.galaxy.uniflow.intellij.psi.factories;

import com.intellij.openapi.module.Module;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.factories.UniTypeFactory;
import org.galaxy.uniflow.api.types.*;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.elements.IJAnnotation;
import org.galaxy.uniflow.intellij.psi.types.*;
import org.galaxy.uniflow.intellij.psi.types.elements.IJTypeParameter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.galaxy.uniflow.intellij.psi.util.IJUtils.check;
import static org.galaxy.uniflow.intellij.psi.util.IJUtils.checkList;

public class IntellijTypeFactory implements UniTypeFactory {

    @Override
    public @NotNull UniType createVoidType() {
        return new IJPrimitiveType(PsiTypes.voidType());
    }

    @Override
    public @NotNull UniPrimitiveType createPrimitiveType(@NotNull TypeTag tag) {
        return switch (tag) {
            case VOID -> new IJPrimitiveType(PsiTypes.voidType());
            case BYTE -> new IJPrimitiveType(PsiTypes.byteType());
            case SHORT -> new IJPrimitiveType(PsiTypes.shortType());
            case INT -> new IJPrimitiveType(PsiTypes.intType());
            case LONG -> new IJPrimitiveType(PsiTypes.longType());
            case FLOAT -> new IJPrimitiveType(PsiTypes.floatType());
            case DOUBLE -> new IJPrimitiveType(PsiTypes.doubleType());
            case CHAR -> new IJPrimitiveType(PsiTypes.charType());
            case BOOLEAN -> new IJPrimitiveType(PsiTypes.booleanType());
        };
    }

    @Override
    public @NotNull UniClassType createClassType(@NotNull Class<?> clazz) {
        return createClassType(clazz.getName());
    }

    @Override
    public @NotNull UniClassType createClassType(@NotNull String name) {
        Module module = IntellijUniflow.getInstance().module;
        GlobalSearchScope scope = GlobalSearchScope.moduleScope(module);

        return new IJClassType(PsiClassType.getTypeByName(name, module.getProject(), scope));
    }

    @Override
    public @NotNull UniArrayType createArrayType(@NotNull Class<?> elementType) {
        return createArrayType(createClassType(elementType));
    }

    @Override
    public @NotNull UniArrayType createArrayType(@NotNull UniType elementType) {
        IJType<?> ijElementType = check(elementType, IJType.class);

        return new IJArrayType(new PsiArrayType(ijElementType.getRawType(), new PsiAnnotation[0]));
    }

    @Override
    public @NotNull UniWildcardType createUnboundedWildcardType() {
        return new IJWildcardType(PsiWildcardType.createUnbounded(IntellijUniflow.getInstance().manager));
    }

    @Override
    public @NotNull UniWildcardType createWildcardType(UniWildcardType.@NotNull BoundKind kind,
                                                       @NotNull UniType bound) {
        IJType<?> ijBound = check(bound, IJType.class);
        PsiManager manager = IntellijUniflow.getInstance().manager;

        PsiWildcardType type = switch (kind) {
            case UNBOUND -> PsiWildcardType.createUnbounded(manager);
            case EXTENDS -> PsiWildcardType.createExtends(manager, ijBound.getRawType());
            case SUPER -> PsiWildcardType.createSuper(manager, ijBound.getRawType());
        };

        return new IJWildcardType(type);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniParameterizedType createParameterizedType(@NotNull UniType elementType,
                                                                 @NotNull List<@NotNull UniType> argumentTypes) {
        IJClassType ijElementType = check(elementType, IJClassType.class);
        PsiClassType type = ijElementType.getRawType().rawType();
        Stream<IJType> ijArgumentTypes = checkList(argumentTypes, IJType.class);
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;

        return new IJParameterizedType(factory.createType(Objects.requireNonNull(type.resolve()),
                ijArgumentTypes.map(IJType::getRawType).toArray(PsiType[]::new)));
    }

    @Override
    public @NotNull UniTypeParameter createTypeParameter(@NotNull String name, @NotNull List<@NotNull UniType> bounds) {
        Stream<IJClassType> ijBounds = checkList(bounds, IJClassType.class);
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;
        PsiClassType[] boundTypes = ijBounds.map(IJClassType::getRawType).toArray(PsiClassType[]::new);

        return new IJTypeParameter(factory.createTypeParameter(name, boundTypes));
    }

    @Override
    public @NotNull UniTypeParameter createTypeParameter(@NotNull String name, @NotNull List<@NotNull UniType> bounds,
                                                         @NotNull List<@NotNull UniAnnotation> annotations) {
        IJTypeParameter type = (IJTypeParameter) createTypeParameter(name, bounds);
        Stream<IJAnnotation> ijAnnotations = checkList(annotations, IJAnnotation.class);
        PsiTypeParameter element = type.getElement();

        ijAnnotations.map(IJAnnotation::getElement).forEach(element::add);
        return type;
    }
}
