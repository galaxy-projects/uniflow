package org.galaxy.uniflow.javac.annotations;

import com.sun.tools.javac.code.Attribute;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Pair;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.annotations.UniAnnotationAttribute;
import org.galaxy.uniflow.api.annotations.UniAnnotationValue;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.JavacUniflow;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.SymbolUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.stream.Stream;

public class JavacAnnotation extends JavacElement<JCTree.JCAnnotation> implements UniAnnotation {

    public JavacAnnotation(JCTree.@NotNull JCAnnotation tree) {
        super(tree);
    }

    @Override
    public @NotNull UniClassType getType() {
        return (UniClassType) UniUtils.typeFromTree(tree.annotationType);
    }

    @Override
    public @NotNull UniAnnotationAttribute @NotNull [] getAttributes() {
        return attributes().toArray(UniAnnotationAttribute[]::new);
    }

    @Override
    public boolean hasAttribute(@NotNull String name) {
        return attributes().anyMatch(attr -> attr.getName().equals(name));
    }

    @Override
    public @Nullable UniAnnotationValue getAttribute(@NotNull String name) {
        return attributes()
                .filter(attr -> attr.getName().equals(name))
                .findFirst()
                .map(UniAnnotationAttribute::getValue)
                .orElse(null);
    }

    @Override
    public void addAttribute(@NotNull String name, @NotNull UniAnnotationValue value) {
        JavacUniflow uniflow = JavacUniflow.getInstance();
        TreeMaker treeMaker = uniflow.treeMaker;
        Symbol.MethodSymbol method = SymbolUtils.findMethodByName(tree.type, name);

        // convert non-assigned 'value' (@Annotation("value") e.g) to assign if present
        tree.args.stream()
                .filter(exp -> !(exp instanceof JCTree.JCAssign))
                .findFirst().ifPresent(valueExpr -> {
                    removeAttribute("value");
                    tree.args = tree.args.append(treeMaker.Assign(treeMaker.Ident(uniflow.names.value), valueExpr));
                });

        JCTree.JCAssign assign = treeMaker.Assign(treeMaker.Ident(method), JavacUtils.javac(value));

        tree.args = tree.args.append(assign);

        if (tree.attribute != null) {
            List<Pair<Symbol.MethodSymbol, Attribute>> values = tree.attribute.values;

            tree.attribute =
                    new Attribute.Compound(tree.type, values.append(new Pair<>(method, Util.asAttribute(value))));
        }
    }

    @Override
    public void addAttribute(@NotNull UniAnnotationAttribute attribute) {
        addAttribute(attribute.getName(), attribute.getValue());
    }

    @Override
    public void removeAttribute(@NotNull String name) {
        tree.args = tree.args.stream().filter(expr -> isAttribute(expr, name)).collect(List.collector());

        if (tree.attribute != null) {
            List<Pair<Symbol.MethodSymbol, Attribute>> values = tree.attribute.values;
            java.util.List<Pair<Symbol.MethodSymbol, Attribute>> newList = new ArrayList<>(values.size() - 1);

            for (Pair<Symbol.MethodSymbol, Attribute> pair : values)
                if (!pair.fst.getSimpleName().contentEquals(name))
                    newList.add(pair);
            tree.attribute = new Attribute.Compound(tree.type, List.from(newList));
        }
    }

    @Override
    public void removeAttribute(@NotNull UniAnnotationAttribute attribute) {
        removeAttribute(attribute.getName());
    }

    private boolean isAttribute(JCTree.JCExpression expression, String name) {
        if (expression instanceof JCTree.JCAssign) {
            JCTree.JCAssign assign = (JCTree.JCAssign) expression;
            JCTree.JCIdent key = (JCTree.JCIdent) assign.lhs;

            return key.name.contentEquals(name);
        }
        return name.equals("value");
    }

    private Stream<UniAnnotationAttribute> attributes() {
        return tree.getArguments().stream().map(Util::attributeFromExpression);
    }
}
