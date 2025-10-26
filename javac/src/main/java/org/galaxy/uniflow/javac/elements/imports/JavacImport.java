package org.galaxy.uniflow.javac.elements.imports;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.elements.imports.UniImport;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.Reflection;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.jetbrains.annotations.NotNull;

public class JavacImport extends JavacElement<JCTree.JCImport> implements UniImport {

    private static final ReflectField QUALIFIER_ID;

    public JavacImport(JCTree.@NotNull JCImport tree) {
        super(tree);
    }

    @Override
    public void setClasses(@NotNull String className) {
        QUALIFIER_ID.set(tree, JavacUnwrapper.expressionFromString(className));
    }

    @Override
    public @NotNull String getClasses() {
        return UniflowWrapper.expressionToString(QUALIFIER_ID.get(tree));
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
