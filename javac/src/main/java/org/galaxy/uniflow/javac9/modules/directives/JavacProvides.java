package org.galaxy.uniflow.javac9.modules.directives;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.api.modules.directives.UniProvides;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.galaxy.uniflow.javac9.Reflection;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.jetbrains.annotations.NotNull;

public class JavacProvides extends JavacElement<JCTree> implements UniProvides {

    private static final ReflectField SERVICE_NAME;
    private static final ReflectField IMPLEMENTATION_NAMES;

    public JavacProvides(@NotNull JCTree tree) {
        super(tree);
    }

    @Override
    public void setServiceName(@NotNull UniExpression serviceName) {
        SERVICE_NAME.set(tree, JavacUnwrapper.unwrap(serviceName));
    }

    @Override
    public @NotNull UniExpression getServiceName() {
        return UniflowWrapper.wrap((JCTree.JCExpression) SERVICE_NAME.get(tree));
    }

    @Override
    public @NotNull UniList<@NotNull UniExpression> getImplementationNames() {
        return new JavacList<UniExpression, JCTree.JCExpression>(
                IMPLEMENTATION_NAMES.createGetter(tree),
                IMPLEMENTATION_NAMES.createSetter(tree),
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.PROVIDES_TYPE);
            SERVICE_NAME = type.field("serviceName");
            IMPLEMENTATION_NAMES = type.field("implNames");
        } catch (NoSuchFieldException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
