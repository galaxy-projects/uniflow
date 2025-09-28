package org.galaxy.uniflow.javac.statements;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.*;
import org.galaxy.uniflow.api.elements.UniModifier;
import org.galaxy.uniflow.api.lists.UniFieldList;
import org.galaxy.uniflow.api.lists.UniMethodList;
import org.galaxy.uniflow.api.statements.UniField;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.JavacModifiers;
import org.galaxy.uniflow.javac.JavacUniflow;
import org.galaxy.uniflow.javac.lists.JavacFieldList;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.lists.JavacMethodList;
import org.galaxy.uniflow.javac.types.JavacClassType;
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
        return new JavacClassType(JavacUniflow.getInstance().treeMaker.Ident(tree.sym), (Type.ClassType) tree.sym.type);
    }

    @Override
    public @NotNull String getName() {
        return NameUtils.nameToString(tree.name);
    }

    @Override
    public boolean isInterface() {
        return UniModifier.INTERFACE.hasModifier(tree.mods.flags);
    }

    @Override
    public boolean isAnnotationType() {
        return UniModifier.ANNOTATION.hasModifier(tree.mods.flags);
    }

    @Override
    public boolean isEnum() {
        return UniModifier.ENUM.hasModifier(tree.mods.flags);
    }

    @Override
    public boolean isRecord() {
        return UniModifier.RECORD.hasModifier(tree.mods.flags);
    }

    @Override
    public @NotNull UniType getExtends() {
        return UniflowWrapper.typeFromTree(tree.extending);
    }

    @Override
    public @NotNull UniList<@NotNull UniType> getImplements() {
        return new JavacList<>(
                () -> tree.implementing,
                newList -> tree.implementing = newList,
                UniflowWrapper::typeFromTree,
                JavacUnwrapper::typeToTree
        );
    }

    @Override
    public @NotNull UniList<@NotNull UniTypeParameter> getTypeParameters() {
        return new JavacList<>(
                () -> tree.typarams,
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
        JavacList<UniField, JCTree.JCVariableDecl> fields = elements().partial(
                JCTree.JCVariableDecl.class::isInstance,
                JCTree.JCVariableDecl.class::cast,
                UniflowWrapper::wrapField,
                JavacUnwrapper::unwrap
        );
        return JavacFieldList.from(tree, fields);
    }

    @Override
    public @NotNull UniMethodList getMethods() {
        JavacList<UniMethod, JCTree.JCMethodDecl> methods = elements().partial(
                method -> method instanceof JCTree.JCMethodDecl && !((JCTree.JCMethodDecl) method).sym.isConstructor(),
                JCTree.JCMethodDecl.class::cast,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
        return JavacMethodList.from(tree, methods);
    }

    @Override
    public @NotNull UniMethodList getConstructors() {
        JavacList<UniMethod, JCTree.JCMethodDecl> methods = elements().partial(
                method -> method instanceof JCTree.JCMethodDecl && ((JCTree.JCMethodDecl) method).sym.isConstructor(),
                JCTree.JCMethodDecl.class::cast,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
        return JavacMethodList.from(tree, methods);
    }

    @Override
    public @NotNull UniList<@NotNull UniClassInitializer> getInitializers() {
        return elements().partial(
                JCTree.JCBlock.class::isInstance,
                JCTree.JCBlock.class::cast,
                UniflowWrapper::blockToInitializer,
                JavacUnwrapper::unwrap
        );
    }

    @Override
    public @NotNull UniList<@NotNull UniClass> getInnerClasses() {
        return elements().partial(
                JCTree.JCClassDecl.class::isInstance,
                JCTree.JCClassDecl.class::cast,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }

    private void updateType(Consumer<Type.ClassType> consumer) {
        consumer.accept((Type.ClassType) tree.sym.type);
    }

    private JavacList<UniElement, JCTree> elements() {
        return new JavacList<>(
                () -> tree.defs,
                newList -> tree.defs = newList,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }
}
