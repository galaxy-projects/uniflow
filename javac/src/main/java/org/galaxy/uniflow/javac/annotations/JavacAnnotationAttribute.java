package org.galaxy.uniflow.javac.annotations;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.annotations.UniAnnotationAttribute;
import org.galaxy.uniflow.api.annotations.UniAnnotationValue;
import org.galaxy.uniflow.javac.JavacElement;
import org.jetbrains.annotations.NotNull;

public class JavacAnnotationAttribute extends JavacElement<JCTree.JCExpression> implements UniAnnotationAttribute {

    private final String name;
    private final UniAnnotationValue value;

    public JavacAnnotationAttribute(JCTree.JCExpression attribute, String name, UniAnnotationValue value) {
        super(attribute);
        this.name = name;
        this.value = value;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @NotNull UniAnnotationValue getValue() {
        return value;
    }
}
