package org.galaxy.uniflow.javac9.modules.directives;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.api.modules.directives.UniUses;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.galaxy.uniflow.javac9.Reflection;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.jetbrains.annotations.NotNull;

public class JavacUses extends JavacElement<JCTree> implements UniUses {

    private static final ReflectField QUALIFIER_ID;

    public JavacUses(JCTree tree) {
        super(tree);
    }

    @Override
    public void setServiceName(@NotNull UniExpression serviceName) {
        QUALIFIER_ID.set(tree, JavacUnwrapper.unwrap(serviceName));
    }

    @Override
    public @NotNull UniExpression getServiceName() {
        return UniflowWrapper.wrap((JCTree.JCExpression) QUALIFIER_ID.get(tree));
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.USES_TYPE);
            QUALIFIER_ID = type.field("qualid");
        } catch (NoSuchFieldException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
