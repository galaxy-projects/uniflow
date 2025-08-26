package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.annotations.UniAnnotationHolder;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniNewArray;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavacNewArray extends JavacExpression<JCTree.JCNewArray> implements UniNewArray {

    public JavacNewArray(JCTree.@NotNull JCNewArray tree) {
        super(tree);
    }

    @Override
    public @Nullable UniType getType() {
        return UniUtils.typeFromTree(tree.elemtype);
    }

    @Override
    public @NotNull UniList<UniExpression> getDimensions() {
        return new JavacList<>(
                tree.dims,
                newList -> tree.dims = newList,
                UniUtils::uni,
                JavacUtils::javac
        );
    }

    @Override
    public @NotNull UniList<UniExpression> getInitializers() {
        return new JavacList<>(
                tree.elems,
                newList -> tree.elems = newList,
                UniUtils::uni,
                JavacUtils::javac
        );
    }

    @Override
    public @NotNull UniList<UniAnnotationHolder> getDimAnnotations() {
        return new JavacList<>(
                tree.dimAnnotations,
                newList -> tree.dimAnnotations = newList,
                UniUtils::uni,
                JavacUtils::javac
        );
    }

    @Override
    public @NotNull UniList<@NotNull UniAnnotation> getAnnotations() {
        return new JavacList<>(
                tree.annotations,
                newList -> tree.annotations = newList,
                UniUtils::uni,
                JavacUtils::javac
        );
    }

    @Override
    public @Nullable UniAnnotation getAnnotation(@NotNull UniClassType type) {
        return tree.annotations.stream()
                .filter(annotation -> UniUtils.typeFromTree(annotation.annotationType).equals(type))
                .findFirst()
                .map(UniUtils::uni).orElse(null);
    }

    @Override
    public @Nullable UniAnnotation @NotNull [] getAllAnnotations(@NotNull UniClassType type) {
        return tree.annotations.stream()
                .filter(annotation -> UniUtils.typeFromTree(annotation.annotationType).equals(type))
                .map(UniUtils::uni)
                .toArray(UniAnnotation[]::new);
    }

    @Override
    public boolean hasAnnotation(@NotNull UniClassType type) {
        return tree.annotations.stream()
                .anyMatch(annotation -> UniUtils.typeFromTree(annotation.annotationType).equals(type));
    }
}
