package org.galaxy.uniflow.javac.expression;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.annotations.UniAnnotationHolder;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniNewArray;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavacNewArray extends JavacExpression<JCTree.JCNewArray> implements UniNewArray {

    public JavacNewArray(JCTree.@NotNull JCNewArray tree) {
        super(tree);
    }

    @Override
    public @Nullable UniType getType() {
        return UniflowWrapper.typeFromTree(tree.elemtype);
    }

    @Override
    public @NotNull UniList<UniExpression> getDimensions() {
        return new JavacList<>(
                tree.dims,
                newList -> tree.dims = newList,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }

    @Override
    public @NotNull UniList<UniExpression> getInitializers() {
        return new JavacList<>(
                tree.elems,
                newList -> tree.elems = newList,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }

    @Override
    public @NotNull UniList<UniAnnotationHolder> getDimAnnotations() {
        return new JavacList<>(
                tree.dimAnnotations,
                newList -> tree.dimAnnotations = newList,
                list -> UniflowWrapper.wrap(newList -> {
                    int index = tree.dimAnnotations.indexOf(list);
                    ListBuffer<List<JCTree.JCAnnotation>> dimBuffer = new ListBuffer<>();
                    int current = 0;

                    for (List<JCTree.JCAnnotation> dim : tree.dimAnnotations) {
                        dimBuffer.append(index == current ? newList : dim);
                        current++;
                    }
                    tree.dimAnnotations = dimBuffer.toList();
                }, list),
                JavacUnwrapper::unwrap
        );
    }

    @Override
    public @NotNull UniList<@NotNull UniAnnotation> getAnnotations() {
        return new JavacList<>(
                tree.annotations,
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
                .map(UniflowWrapper::wrap).orElse(null);
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
