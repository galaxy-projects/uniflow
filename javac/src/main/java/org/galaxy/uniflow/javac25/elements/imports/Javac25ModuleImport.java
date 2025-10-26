package org.galaxy.uniflow.javac25.elements.imports;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.elements.imports.UniModuleImport;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.galaxy.uniflow.javac25.Reflection;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.jetbrains.annotations.NotNull;

public class Javac25ModuleImport extends JavacElement<JCTree> implements UniModuleImport {

    private static final ReflectField MODULE;

    public Javac25ModuleImport(@NotNull JCTree tree) {
        super(tree);
    }

    @Override
    public void setModuleName(@NotNull String moduleName) {
        MODULE.set(tree, JavacUnwrapper.expressionFromString(moduleName));
    }

    @Override
    public @NotNull String getModuleName() {
        return UniflowWrapper.expressionToString(MODULE.get(tree));
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.MODULE_IMPORT);
            MODULE = type.field("module");
        } catch (NoSuchFieldException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
