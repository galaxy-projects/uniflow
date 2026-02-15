package org.galaxy.uniflow.javac9;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.factories.UniConstants;
import org.galaxy.uniflow.api.modules.UniModule;
import org.galaxy.uniflow.javac.JavacCompilationUnit;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectMethod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Javac9CompilationUnit extends JavacCompilationUnit {

    private static final ReflectMethod GET_MODULE;

    public Javac9CompilationUnit(JCTree.@NotNull JCCompilationUnit tree) {
        super(tree);
    }

    @Override
    public @Nullable UniModule getModule() {
        JCTree module = (JCTree) GET_MODULE.run(tree);

        return module != null ? Uniflow9Wrapper.wrapModule(module) : null;
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.COMPILATION_UNIT);
            GET_MODULE = type.method("getModuleDecl");
        } catch (NoSuchMethodException e) {
            throw new UnsupportedOperationException(UniConstants.JAVA_VERSION_ERROR_MESSAGE, e);
        }
    }
}
