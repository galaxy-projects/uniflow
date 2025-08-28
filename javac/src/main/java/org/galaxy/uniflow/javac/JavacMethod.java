package org.galaxy.uniflow.javac;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.UniMethod;
import org.galaxy.uniflow.api.UniModifiers;
import org.galaxy.uniflow.api.lists.UniIndexedList;
import org.galaxy.uniflow.api.lists.UniParameterList;
import org.galaxy.uniflow.api.signatures.UniMethodSignature;
import org.galaxy.uniflow.api.statements.UniBlock;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.galaxy.uniflow.javac.lists.JavacIndexedList;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.lists.JavacParameterList;
import org.galaxy.uniflow.javac.signatures.JavacMethodSignature;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

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
        tree.restype = JavacUtils.typeToTree(type);
        tree.sym.type.asMethodType().restype = tree.restype.type;
    }

    @Override
    public @NotNull UniType getReturnType() {
        return UniUtils.typeFromTree(tree.restype);
    }

    @Override
    public @NotNull UniIndexedList<@NotNull UniTypeParameter> getTypeParameters() {
        return new JavacIndexedList<>(
                tree.typarams,
                newList -> tree.typarams = newList,
                UniUtils::uni,
                JavacUtils::javac
        );
    }

    @Override
    public @NotNull UniParameterList getParameters() {
        return new JavacParameterList(new JavacList<>(
                tree.params,
                newList -> {
                    tree.params = newList;
                    tree.sym.params = newList.map(var -> var.sym);
                    tree.sym.type.asMethodType().argtypes = newList.map(var -> var.type);
                },
                UniUtils::uni,
                JavacUtils::javac
        ));
    }

    @Override
    public @NotNull UniList<@NotNull UniType> getThrows() {
        return new JavacList<>(
                tree.thrown,
                newList -> {
                    tree.thrown = newList;
                    tree.sym.type.asMethodType().thrown = newList.map(exp -> exp.type);
                },
                UniUtils::typeFromTree,
                JavacUtils::typeToTree
        );
    }

    @Override
    public void setBody(@NotNull UniBlock body) {
        tree.body = JavacUtils.javac(body);
    }

    @Override
    public @NotNull UniBlock getBody() {
        return UniUtils.uni(tree.body);
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
    public @NotNull UniClassType getContainingClass() {
        return UniUtils.symbolToType(tree.sym.owner);
    }
}
