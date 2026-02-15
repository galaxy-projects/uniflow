package org.galaxy.uniflow.javac.logger;

import com.sun.tools.javac.code.Attribute;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeInfo;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.logger.UniBuildLogger;
import org.galaxy.uniflow.common.EnumUtils;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.JavacUniflow;
import org.galaxy.uniflow.javac.annotations.JavacAnnotation;
import org.jetbrains.annotations.NotNull;

import javax.tools.Diagnostic;

public class JavacBuildLogger implements UniBuildLogger {

    @Override
    public void log(@NotNull MessageKind kind, @NotNull CharSequence msg) {
        JavacUniflow.getInstance().messager.printMessage(parseKind(kind), msg);
    }

    @Override
    public void log(@NotNull MessageKind kind, @NotNull CharSequence msg, @NotNull UniElement element) {
        if (!(element instanceof JavacElement))
            throw new IllegalArgumentException("Invalid element type");
        JavacElement<?> javacElement = (JavacElement<?>) element;
        JCTree tree = javacElement.getTree();

        JavacUniflow.getInstance().messager
                .printMessage(parseKind(kind), msg, TreeInfo.symbol(tree));
    }

    @Override
    public void log(@NotNull MessageKind kind,
                    @NotNull CharSequence msg,
                    @NotNull UniElement element,
                    @NotNull UniAnnotation annotation) {
        checkElements(element, annotation);
        JCTree elementTree = ((JavacElement<?>) element).getTree();
        JCTree.JCAnnotation annotationTree = ((JavacAnnotation) annotation).getTree();

        JavacUniflow.getInstance().messager
                .printMessage(parseKind(kind), msg, TreeInfo.symbol(elementTree), annotationTree.attribute);
    }

    @Override
    public void log(@NotNull MessageKind kind,
                    @NotNull CharSequence msg,
                    @NotNull UniElement element,
                    @NotNull UniAnnotation annotation,
                    @NotNull String attributeName) {
        checkElements(element, annotation);
        JCTree elementTree = ((JavacElement<?>) element).getTree();
        JCTree.JCAnnotation annotationTree = ((JavacAnnotation) annotation).getTree();
        Attribute attribute = annotationTree.attribute.values.stream()
                .filter(pair -> pair.fst.name.contentEquals(attributeName)).findFirst()
                .map(pair -> pair.snd)
                .orElse(null);

        JavacUniflow.getInstance().messager
                .printMessage(parseKind(kind), msg, TreeInfo.symbol(elementTree), annotationTree.attribute, attribute);
    }

    private void checkElements(UniElement element, UniAnnotation annotation) {
        if (!(element instanceof JavacElement))
            throw new IllegalArgumentException("Invalid element type");
        if (!(annotation instanceof JavacAnnotation))
            throw new IllegalArgumentException("Invalid annotation type");
    }

    private Diagnostic.Kind parseKind(@NotNull UniBuildLogger.MessageKind kind) {
        return EnumUtils.convert(Diagnostic.Kind.class, kind);
    }
}
