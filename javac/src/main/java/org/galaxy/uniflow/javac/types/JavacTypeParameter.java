package org.galaxy.uniflow.javac.types;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavacTypeParameter extends JavacElement<JCTree.JCTypeParameter> implements UniTypeParameter {

    public JavacTypeParameter(JCTree.@NotNull JCTypeParameter tree) {
        super(tree);
    }

    @Override
    public void setName(@NotNull String name) {
        tree.name = NameUtils.name(name);
    }

    @Override
    public @NotNull String getName() {
        return NameUtils.nameToString(tree.name);
    }

    @Override
    public @NotNull UniList<@NotNull UniType> getExtends() {
        return new JavacList<>(
                () -> tree.bounds,
                newList -> tree.bounds = newList,
                UniflowWrapper::typeFromTree,
                JavacUnwrapper::typeToTree
        );
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
                .findFirst().map(UniflowWrapper::wrap).orElse(null);
    }

    @Override
    public @Nullable UniAnnotation @NotNull [] getAllAnnotations(@NotNull UniClassType type) {
        return tree.annotations.stream()
                .filter(annotation -> UniflowWrapper.typeFromTree(annotation.annotationType).equals(type))
                .map(UniflowWrapper::wrap).toArray(UniAnnotation[]::new);
    }

    @Override
    public boolean hasAnnotation(@NotNull UniClassType type) {
        return tree.annotations.stream()
                .anyMatch(annotation -> UniflowWrapper.typeFromTree(annotation.annotationType).equals(type));
    }
}
