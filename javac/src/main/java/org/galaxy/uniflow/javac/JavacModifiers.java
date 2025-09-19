package org.galaxy.uniflow.javac;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.UniModifiers;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.elements.UniModifier;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.common.EnumUtils;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavacModifiers extends JavacElement<JCTree.JCModifiers> implements UniModifiers {

    public JavacModifiers(JCTree.@NotNull JCModifiers tree) {
        super(tree);
    }

    @Override
    public @NotNull UniModifier @NotNull [] getModifiers() {
        return tree.getFlags().stream()
                .map(flag -> EnumUtils.convert(UniModifier.class, flag))
                .toArray(UniModifier[]::new);
    }

    @Override
    public boolean hasModifier(@NotNull UniModifier modifier) {
        return modifier.hasModifier(tree.flags);
    }

    @Override
    public void addModifier(@NotNull UniModifier modifier) {
        tree.flags |= modifier.getMask();
    }

    @Override
    public void removeModifier(@NotNull UniModifier modifier) {
        tree.flags &= ~modifier.getMask();
    }

    @Override
    public @NotNull UniList<@NotNull UniAnnotation> getAnnotations() {
        return new JavacList<>(
                () -> tree.annotations,
                newList -> tree.annotations = newList,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }

    @Override
    public @Nullable UniAnnotation getAnnotation(@NotNull UniClassType type) {
        return tree.annotations.stream()
                .filter(annotation -> UniflowWrapper.typeFromTree(annotation.annotationType).equals(type))
                .findFirst()
                .map(UniflowWrapper::wrap)
                .orElse(null);
    }

    @Override
    public @Nullable UniAnnotation @NotNull [] getAllAnnotations(@NotNull UniClassType type) {
        return tree.annotations.stream()
                .filter(annotation -> UniflowWrapper.typeFromTree(annotation.annotationType).equals(type))
                .map(UniflowWrapper::wrap)
                .toArray(UniAnnotation[]::new);
    }

    @Override
    public boolean hasAnnotation(@NotNull UniClassType type) {
        return tree.annotations.stream()
                .anyMatch(annotation -> UniflowWrapper.typeFromTree(annotation.annotationType).equals(type));
    }
}
