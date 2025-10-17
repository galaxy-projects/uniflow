package org.galaxy.uniflow.javac9.modules.directives;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.api.modules.directives.UniRequires;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.galaxy.uniflow.javac9.Reflection;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.jetbrains.annotations.NotNull;

public class JavacRequires extends JavacElement<JCTree> implements UniRequires {

    private static final ReflectField STATIC_PHASE;
    private static final ReflectField TRANSITIVE;
    private static final ReflectField MODULE_NAME;

    public JavacRequires(@NotNull JCTree tree) {
        super(tree);
    }

    @Override
    public void setStatic(boolean isStatic) {
        STATIC_PHASE.set(tree, isStatic);
    }

    @Override
    public boolean isStatic() {
        return STATIC_PHASE.get(tree);
    }

    @Override
    public void setTransitive(boolean transitive) {
        TRANSITIVE.set(tree, transitive);
    }

    @Override
    public boolean isTransitive() {
        return TRANSITIVE.get(tree);
    }

    @Override
    public void setModuleName(@NotNull UniExpression moduleName) {
        MODULE_NAME.set(tree, JavacUnwrapper.unwrap(moduleName));
    }

    @Override
    public @NotNull UniExpression getModuleName() {
        return UniflowWrapper.wrap((JCTree.JCExpression) MODULE_NAME.get(tree));
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.REQUIRES_TYPE);
            STATIC_PHASE = type.field("isStaticPhase");
            TRANSITIVE = type.field("isTransitive");
            MODULE_NAME = type.field("moduleName");
        } catch (NoSuchFieldException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
