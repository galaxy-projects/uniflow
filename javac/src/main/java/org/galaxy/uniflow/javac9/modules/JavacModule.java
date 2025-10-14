package org.galaxy.uniflow.javac9.modules;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.UniModifiers;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.modules.UniModule;
import org.galaxy.uniflow.api.modules.directives.UniDirective;
import org.galaxy.uniflow.common.EnumUtils;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.JavacModifiers;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.galaxy.uniflow.javac9.Javac9Unwrapper;
import org.galaxy.uniflow.javac9.Reflection;
import org.galaxy.uniflow.javac9.Uniflow9Wrapper;
import org.galaxy.uniflow.reflection.Constants;
import org.galaxy.uniflow.reflection.ReflectClass;
import org.galaxy.uniflow.reflection.ReflectField;
import org.galaxy.uniflow.reflection.ReflectMethod;
import org.jetbrains.annotations.NotNull;

public class JavacModule extends JavacElement<JCTree> implements UniModule {

    private static final ReflectMethod GET_MODULE_TYPE;
    private static final ReflectField QUALIFIER_ID;
    private static final ReflectField MODIFIERS;
    private static final ReflectField DIRECTIVES;

    public JavacModule(@NotNull JCTree tree) {
        super(tree);
    }

    @Override
    public @NotNull UniModifiers getModifiers() {
        return new JavacModifiers(MODIFIERS.get(tree));
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public @NotNull ModuleKind getModuleKind() {
        return EnumUtils.convert(ModuleKind.class, (Enum) GET_MODULE_TYPE.run(tree));
    }

    @Override
    public void setName(@NotNull UniExpression name) {
        QUALIFIER_ID.set(tree, JavacUnwrapper.unwrap(name));
    }

    @Override
    public @NotNull UniExpression getName() {
        return UniflowWrapper.wrap((JCTree.JCExpression) QUALIFIER_ID.get(tree));
    }

    @Override
    public @NotNull UniList<UniDirective> getDirectives() {
        return new JavacList<>(
                DIRECTIVES.createGetter(tree),
                DIRECTIVES.createSetter(tree),
                Uniflow9Wrapper::wrapDirective,
                Javac9Unwrapper::unwrap
        );
    }

    static {
        try {
            ReflectClass type = new ReflectClass(Reflection.MODULE_TYPE);

            GET_MODULE_TYPE = type.method("getModuleType");
            QUALIFIER_ID = type.field("qualId");
            MODIFIERS = type.field("mods");
            DIRECTIVES = type.field("directives");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
            throw new UnsupportedOperationException(Constants.ERROR_MESSAGE, e);
        }
    }
}
