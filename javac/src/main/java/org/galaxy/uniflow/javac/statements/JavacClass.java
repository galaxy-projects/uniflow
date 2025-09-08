package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.*;
import org.galaxy.uniflow.api.lists.UniFieldList;
import org.galaxy.uniflow.api.lists.UniIndexedList;
import org.galaxy.uniflow.api.lists.UniMethodList;
import org.galaxy.uniflow.api.statements.UniBlock;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.JavacModifiers;
import org.galaxy.uniflow.javac.lists.JavacFieldList;
import org.galaxy.uniflow.javac.lists.JavacIndexedList;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.lists.JavacMethodList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class JavacClass extends JavacElement<JCTree.JCClassDecl> implements UniClass {

    public JavacClass(JCTree.@NotNull JCClassDecl tree) {
        super(tree);
    }

    @Override
    public @NotNull UniModifiers getModifiers() {
        return new JavacModifiers(tree.mods);
    }

    @Override
    public @NotNull UniClassType asType() {
        return (UniClassType) UniflowWrapper.typeFromTree(tree);
    }

    @Override
    public @NotNull String getName() {
        return NameUtils.nameToString(tree.name);
    }

    @Override
    public boolean isInterface() {
        return tree.sym.isInterface();
    }

    @Override
    public boolean isAnnotationType() {
        return tree.sym.isAnnotationType();
    }

    @Override
    public boolean isEnum() {
        return tree.sym.isEnum();
    }

    @Override
    public boolean isRecord() {
        return false;
    }

    @Override
    public @NotNull UniType getExtends() {
        return UniflowWrapper.typeFromTree(tree.extending);
    }

    @Override
    public @NotNull UniList<@NotNull UniType> getImplements() {
        return new JavacList<>(
                tree.implementing,
                newList -> tree.implementing = newList,
                UniflowWrapper::typeFromTree,
                JavacUnwrapper::typeToTree
        );
    }

    @Override
    public @NotNull UniIndexedList<@NotNull UniTypeParameter> getTypeParameters() {
        return JavacIndexedList.of(
                tree.typarams,
                newList -> {
                    tree.typarams = newList;
                    updateType(type -> type.typarams_field = newList.map(param -> param.type));
                },
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }

    @Override
    public @NotNull UniFieldList getFields() {
        JavacList<UniVariable, JCTree.JCVariableDecl> fields = elements().partial(
                UniVariable.class::isInstance,
                element -> (UniVariable) element,
                var -> var,
                JavacUnwrapper::unwrap
        );
        return JavacFieldList.from(fields);
    }

    @Override
    public @NotNull UniMethodList getMethods() {
        JavacList<UniMethod, JCTree.JCMethodDecl> methods = elements().partial(
                method -> method instanceof UniMethod && !((UniMethod) method).isConstructor(),
                element -> (UniMethod) element,
                var -> var,
                JavacUnwrapper::unwrap
        );
        return JavacMethodList.from(methods);
    }

    @Override
    public @NotNull UniMethodList getConstructors() {
        JavacList<UniMethod, JCTree.JCMethodDecl> methods = elements().partial(
                method -> method instanceof UniMethod && ((UniMethod) method).isConstructor(),
                element -> (UniMethod) element,
                var -> var,
                JavacUnwrapper::unwrap
        );
        return JavacMethodList.from(methods);
    }

    @Override
    public @NotNull UniList<UniClassInitializer> getInitializers() {
        return elements().partial(
                UniBlock.class::isInstance,
                element -> UniflowWrapper.blockToInitializer((UniBlock) element),
                var -> var,
                JavacUnwrapper::unwrap
        );
    }

    @Override
    public @NotNull UniList<@NotNull UniClass> getInnerClasses() {
        return elements().partial(
                UniClass.class::isInstance,
                element -> (UniClass) element,
                var -> var,
                JavacUnwrapper::unwrap
        );
    }

    private void updateType(Consumer<Type.ClassType> consumer) {
        consumer.accept((Type.ClassType) tree.sym.type);
    }

    private JavacList<UniElement, JCTree> elements() {
        return new JavacList<>(
                tree.defs,
                newList -> tree.defs = newList,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }
}
