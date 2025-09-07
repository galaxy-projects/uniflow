package org.galaxy.uniflow.javac.annotations;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.annotations.UniAnnotationHolder;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class JavacSimpleAnnotationHolder implements UniAnnotationHolder {

    private final Consumer<List<JCTree.JCAnnotation>> updater;
    private List<JCTree.JCAnnotation> annotations;

    public JavacSimpleAnnotationHolder(Consumer<List<JCTree.JCAnnotation>> updater,
                                       List<JCTree.JCAnnotation> annotations) {
        this.updater = updater;
        this.annotations = annotations;
    }

    @Override
    public @NotNull UniList<@NotNull UniAnnotation> getAnnotations() {
        return new JavacList<>(
                annotations,
                newList -> {
                    annotations = newList;
                    updater.accept(newList);
                },
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }

    @Override
    public @Nullable UniAnnotation getAnnotation(@NotNull UniClassType type) {
        return annotations.stream()
                .filter(annotation -> UniflowWrapper.typeFromTree(annotation.annotationType).equals(type))
                .findFirst()
                .map(UniflowWrapper::wrap).orElse(null);
    }

    @Override
    public @Nullable UniAnnotation @NotNull [] getAllAnnotations(@NotNull UniClassType type) {
        return annotations.stream()
                .filter(annotation -> UniflowWrapper.typeFromTree(annotation.annotationType).equals(type))
                .map(UniflowWrapper::wrap)
                .toArray(UniAnnotation[]::new);
    }

    @Override
    public boolean hasAnnotation(@NotNull UniClassType type) {
        return annotations.stream()
                .anyMatch(annotation -> UniflowWrapper.typeFromTree(annotation.annotationType).equals(type));
    }
}
