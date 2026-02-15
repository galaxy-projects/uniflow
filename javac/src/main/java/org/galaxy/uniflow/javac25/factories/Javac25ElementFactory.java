package org.galaxy.uniflow.javac25.factories;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.elements.imports.UniModuleImport;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.api.factories.UniJdk25ElementFactory;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac21.factories.Javac21ElementFactory;
import org.galaxy.uniflow.javac25.Reflection;
import org.galaxy.uniflow.javac25.elements.imports.Javac25ModuleImport;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectMethod;
import org.jetbrains.annotations.NotNull;

public class Javac25ElementFactory extends Javac21ElementFactory implements UniJdk25ElementFactory {

    private static final ReflectMethod CREATE_MODULE_IMPORT;

    @Override
    public @NotNull UniModuleImport createModuleImport(@NotNull String moduleName) {
        return new Javac25ModuleImport((JCTree) CREATE_MODULE_IMPORT.run(treeMaker,
                JavacUnwrapper.expressionFromString(moduleName)
        ));
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.TREE_MAKER);
            CREATE_MODULE_IMPORT = type.method("ModuleImport", Reflection.EXPRESSION_TYPE);
        } catch (NoSuchMethodException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
