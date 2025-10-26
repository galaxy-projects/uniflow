package org.galaxy.uniflow.javac.elements.imports;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import org.galaxy.uniflow.api.elements.imports.UniStaticImport;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.JavacUniflow;
import org.galaxy.uniflow.javac.Reflection;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.jetbrains.annotations.NotNull;

public class JavacStaticImport extends JavacElement<JCTree.JCImport> implements UniStaticImport {

    private static final ReflectField QUALIFIER_ID;

    public JavacStaticImport(JCTree.@NotNull JCImport tree) {
        super(tree);
    }

    @Override
    public void setTarget(@NotNull String className, @NotNull String qualifiedElement) {
        TreeMaker treeMaker = JavacUniflow.getInstance().treeMaker;
        JCTree target = treeMaker.Select(
                JavacUnwrapper.expressionFromString(className),
                NameUtils.name(qualifiedElement)
        );

        QUALIFIER_ID.set(tree, target);
    }

    @Override
    public @NotNull String getTargetClass() {
        JCTree qualId = QUALIFIER_ID.get(tree);

        if (qualId instanceof JCTree.JCFieldAccess)
            return UniflowWrapper.expressionToString(((JCTree.JCFieldAccess) qualId).selected);
        throw new IllegalArgumentException();
    }

    @Override
    public @NotNull String getTargetElement() {
        JCTree qualId = QUALIFIER_ID.get(tree);

        if (qualId instanceof JCTree.JCFieldAccess)
            return NameUtils.nameToString(((JCTree.JCFieldAccess) qualId).name);
        throw new IllegalArgumentException();
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.IMPORT_TYPE);
            QUALIFIER_ID = type.field("qualid");
        } catch (NoSuchFieldException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
