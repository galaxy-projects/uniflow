package org.galaxy.uniflow.javac;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniClass;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.UniMethod;
import org.galaxy.uniflow.api.UniModifiers;
import org.galaxy.uniflow.api.lists.UniParameterList;
import org.galaxy.uniflow.api.signatures.UniMethodSignature;
import org.galaxy.uniflow.api.statements.UniBlock;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.lists.JavacParameterList;
import org.galaxy.uniflow.javac.signatures.JavacMethodSignature;
import org.galaxy.uniflow.javac.statements.JavacClass;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavacMethod extends JavacElement<JCTree.JCMethodDecl> implements UniMethod {

    public JavacMethod(JCTree.@NotNull JCMethodDecl tree) {
        super(tree);
    }

    @Override
    public @NotNull UniModifiers getModifiers() {
        return new JavacModifiers(tree.mods);
    }

    @Override
    public @NotNull String getName() {
        return NameUtils.nameToString(tree.name);
    }

    @Override
    public void setReturnType(@NotNull UniType type) {
        tree.restype = JavacUnwrapper.typeToTree(type);
        tree.sym.type.asMethodType().restype = tree.restype.type;
    }

    @Override
    public @NotNull UniType getReturnType() {
        return UniflowWrapper.typeFromTree(tree.restype);
    }

    @Override
    public @NotNull UniList<@NotNull UniTypeParameter> getTypeParameters() {
        return new JavacList<>(
                () -> tree.typarams,
                newList -> tree.typarams = newList,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }

    @Override
    public @NotNull UniParameterList getParameters() {
        return new JavacParameterList(
                () -> tree.params,
                newList -> {
                    tree.params = newList;
                    tree.sym.params = newList.map(var -> var.sym);
                    tree.sym.type.asMethodType().argtypes = newList.map(var -> var.type);
                },
                UniflowWrapper::wrapParameter,
                JavacUnwrapper::unwrap
        );
    }

    @Override
    public @NotNull UniList<@NotNull UniType> getThrows() {
        return new JavacList<>(
                () -> tree.thrown,
                newList -> {
                    tree.thrown = newList;
                    tree.sym.type.asMethodType().thrown = newList.map(exp -> exp.type);
                },
                UniflowWrapper::typeFromTree,
                JavacUnwrapper::typeToTree
        );
    }

    @Override
    public void setBody(@NotNull UniBlock body) {
        tree.body = JavacUnwrapper.unwrap(body);
    }

    @Override
    public @NotNull UniBlock getBody() {
        return UniflowWrapper.wrap(tree.body);
    }

    @Override
    public boolean isConstructor() {
        return tree.sym.isConstructor();
    }

    @Override
    public boolean isVarArgs() {
        return tree.sym.isVarArgs();
    }

    @Override
    public @NotNull UniMethodSignature asSignature() {
        return new JavacMethodSignature(tree.sym);
    }

    @Override
    public @Nullable UniClass getEnclosingClass() {
        if (tree.sym != null && tree.sym.owner instanceof Symbol.ClassSymbol) {
            Symbol.ClassSymbol ownerSymbol = (Symbol.ClassSymbol) tree.sym.owner;
            JCTree.JCClassDecl ownerClass = JavacUniflow.getInstance().trees.getTree(ownerSymbol);

            return new JavacClass(ownerClass);
        }
        throw new IllegalStateException("No owner for method " + NameUtils.nameToString(tree.name));
    }
}
