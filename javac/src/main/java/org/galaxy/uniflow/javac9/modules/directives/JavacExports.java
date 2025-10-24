package org.galaxy.uniflow.javac9.modules.directives;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.api.modules.directives.UniExports;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.galaxy.uniflow.javac9.Reflection;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.jetbrains.annotations.NotNull;

public class JavacExports extends JavacElement<JCTree> implements UniExports {

    private static final ReflectField QUALIFIER_ID;
    private static final ReflectField MODULE_NAMES;

    public JavacExports(@NotNull JCTree tree) {
        super(tree);
    }

    @Override
    public void setPackageName(@NotNull UniExpression packageName) {
        QUALIFIER_ID.set(tree, JavacUnwrapper.unwrap(packageName));
    }

    @Override
    public @NotNull UniExpression getPackageName() {
        return UniflowWrapper.wrap((JCTree.JCExpression) QUALIFIER_ID.get(tree));
    }

    @Override
    public @NotNull UniList<@NotNull String> getModuleNames() {
        return new JavacList<>(
                MODULE_NAMES.createGetter(tree),
                MODULE_NAMES.createSetter(tree),
                UniflowWrapper::expressionToString,
                JavacUnwrapper::expressionFromString
        );
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.EXPORTS_TYPE);
            QUALIFIER_ID = type.field("qualid");
            MODULE_NAMES = type.field("moduleNames");
        } catch (NoSuchFieldException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
