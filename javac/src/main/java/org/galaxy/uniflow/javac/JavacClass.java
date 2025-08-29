package org.galaxy.uniflow.javac;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.*;
import org.galaxy.uniflow.api.lists.UniFieldList;
import org.galaxy.uniflow.api.lists.UniIndexedList;
import org.galaxy.uniflow.api.lists.UniMethodList;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.galaxy.uniflow.javac.lists.JavacFieldList;
import org.galaxy.uniflow.javac.lists.JavacIndexedList;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.lists.JavacMethodList;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
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
        return (UniClassType) UniUtils.type(tree.type);
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
        return UniUtils.typeFromTree(tree.extending);
    }

    @Override
    public @NotNull UniList<@NotNull UniType> getImplements() {
        return new JavacList<>(
                tree.implementing,
                newList -> tree.implementing = newList,
                UniUtils::typeFromTree,
                JavacUtils::typeToTree
        );
    }

    @Override
    public @NotNull UniIndexedList<@NotNull UniTypeParameter> getTypeParameters() {
        return new JavacIndexedList<>(
                tree.typarams,
                newList -> {
                    tree.typarams = newList;
                    updateType(type -> type.typarams_field = newList.map(param -> param.type));
                },
                UniUtils::uni,
                JavacUtils::javac
        );
    }

    @Override
    public @NotNull UniFieldList getFields() {
        JavacList<UniVariable, JCTree.JCVariableDecl> fields = new JavacList<>(
                tree.defs,
                newList -> tree.defs = newList,
                UniUtils::uni,
                JavacUtils::javac
        ).partial(
                UniVariable.class::isInstance,
                element -> (UniVariable) element,
                var -> var,
                JavacUtils::javac
        );
        return JavacFieldList.from(fields);
    }

    @Override
    public @NotNull UniMethodList getMethods() {
        JavacList<UniMethod, JCTree.JCMethodDecl> methods = new JavacList<>(
                tree.defs,
                newList -> tree.defs = newList,
                UniUtils::uni,
                JavacUtils::javac
        ).partial(
                method -> method instanceof UniMethod && !((UniMethod) method).isConstructor(),
                element -> (UniMethod) element,
                var -> var,
                JavacUtils::javac
        );
        return JavacMethodList.from(methods);
    }

    @Override
    public @NotNull UniMethodList getConstructors() {
        JavacList<UniMethod, JCTree.JCMethodDecl> methods = new JavacList<>(
                tree.defs,
                newList -> tree.defs = newList,
                UniUtils::uni,
                JavacUtils::javac
        ).partial(
                method -> method instanceof UniMethod && ((UniMethod) method).isConstructor(),
                element -> (UniMethod) element,
                var -> var,
                JavacUtils::javac
        );
        return JavacMethodList.from(methods);
    }

    @Override
    public @NotNull UniList<UniClassInitializer> getInitializers() {
        return null; // TODO: find javac class initializers: methods ?
    }

    @Override
    public @NotNull UniClass @NotNull [] getInnerClasses() {
        return new UniClass[0];
    }

    @Override
    public void addInnerClass(@NotNull UniClass innerClass) {

    }

    @Override
    public void removeInnerClass(@NotNull UniClass innerClass) {

    }

    private void updateType(Consumer<Type.ClassType> consumer) {
        consumer.accept((Type.ClassType) tree.sym.type);
    }
}
