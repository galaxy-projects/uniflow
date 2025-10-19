package org.galaxy.uniflow.javac;

import com.sun.tools.javac.code.Scope;
import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniImport;
import org.galaxy.uniflow.api.factories.UniConstants;
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
    public boolean isGroup() {
        return tree.importScope instanceof Scope.StarImportScope;
    }

    @Override
    public boolean isStatic() {
        return tree.isStatic();
    }

    @Override
    public void setQualifiedElement(@NotNull UniElement qualifiedElement) {
        QUALIFIER_ID.set(tree, JavacUnwrapper.unwrap(qualifiedElement));
    }

    @Override
    public @NotNull UniElement getQualifiedElement() {
        return UniflowWrapper.wrap((JCTree) QUALIFIER_ID.get(tree));
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
