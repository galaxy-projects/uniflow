package org.galaxy.uniflow.javac9;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import org.galaxy.uniflow.api.modules.UniModule;
import org.galaxy.uniflow.javac.JavacCompilationUnit;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.reflection.Constants;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.galaxy.uniflow.reflection.ReflectMethod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Javac9CompilationUnit extends JavacCompilationUnit {

    private static final ReflectField DEFINITIONS;
    private static final ReflectMethod GET_MODULE;

    public Javac9CompilationUnit(JCTree.@NotNull JCCompilationUnit tree) {
        super(tree);
    }

    @Override
    public void setModule(@Nullable UniModule module) {
        JCTree current = (JCTree) GET_MODULE.run(tree);
        List<JCTree> defs = DEFINITIONS.get(tree);

        if (current != null)
            defs = defs.stream().filter(e -> e != current).collect(List.collector());
        defs = defs.append(JavacUnwrapper.unwrap(module));
        DEFINITIONS.set(tree, defs);
    }

    @Override
    public @Nullable UniModule getModule() {
        JCTree module = (JCTree) GET_MODULE.run(tree);

        return module != null ? Uniflow9Wrapper.wrapModule(module) : null;
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.COMPILATION_UNIT);
            DEFINITIONS = type.field("defs");
            GET_MODULE = type.method("getModuleDecl");
        } catch (NoSuchFieldException | NoSuchMethodException e) {
            throw new UnsupportedOperationException(Constants.ERROR_MESSAGE, e);
        }
    }
}
