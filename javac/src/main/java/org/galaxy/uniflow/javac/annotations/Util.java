package org.galaxy.uniflow.javac.annotations;

import com.sun.tools.javac.code.Attribute;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Pair;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.annotations.UniAnnotationAttribute;
import org.galaxy.uniflow.api.annotations.UniAnnotationValue;
import org.galaxy.uniflow.api.expressions.*;
import org.galaxy.uniflow.javac.JavacUniflow;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.galaxy.uniflow.javac.util.SymbolUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;

class Util {

    public static @NotNull UniAnnotationAttribute attributeFromExpression(JCTree.JCExpression expression) {
        if (expression instanceof JCTree.JCAssign) {
            JCTree.JCAssign assign = (JCTree.JCAssign) expression;
            JCTree.JCIdent key = (JCTree.JCIdent) assign.lhs;
            UniAnnotationValue value = (UniAnnotationValue) UniUtils.uni(assign.rhs);

            return new JavacAnnotationAttribute(expression, NameUtils.nameToString(key.name), value);
        } else {
            // @Annotation(...) default uses 'value'
            UniAnnotationValue value = (UniAnnotationValue) UniUtils.uni(expression);

            return new JavacAnnotationAttribute(expression, "value", value);
        }
    }

    public static JCTree.JCExpression asExpression(TreeMaker treeMaker, UniAnnotationValue value) {
        if (value instanceof UniLiteral) {
            UniLiteral literal = (UniLiteral) value;

            return treeMaker.Literal(literal.getValue());
        } else if (value instanceof UniFieldAccess) { // Class literal & enum
            UniFieldAccess fieldAccess = (UniFieldAccess) value;

            return treeMaker.Select(
                    treeMaker.Type(JavacUtils.javac(fieldAccess.getSelected())),
                    NameUtils.name(fieldAccess.getName())
            );
        } else if (value instanceof UniNewArray) {
            UniNewArray array = (UniNewArray) value;
            @NotNull UniExpression[] values = array.getInitializers().get();
            JCTree.JCExpression[] contents = new JCTree.JCExpression[values.length];

            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof UniAnnotationValue)
                    contents[i] = asExpression(treeMaker, (UniAnnotationValue) values[i]);
                else throw new IllegalArgumentException("Non annotation value in annotation");
            }
            return treeMaker.NewArray(
                    treeMaker.Type(JavacUtils.javac(array.getType())),
                    List.nil(),
                    List.from(contents)
            );
        } else if (value instanceof UniAnnotation) {
            UniAnnotation annotation = (UniAnnotation) value;
            @NotNull UniAnnotationAttribute[] attributes = annotation.getAttributes();
            List<JCTree.JCExpression> args;

            if (attributes.length == 1 && attributes[0].getName().equals("value")) {
                // only 'value'
                args = List.of(asExpression(treeMaker, attributes[0].getValue()));
            } else {
                ListBuffer<JCTree.JCExpression> argsBuffer = new ListBuffer<>();

                for (UniAnnotationAttribute attribute : attributes) {
                    argsBuffer.append(treeMaker.Assign(
                            treeMaker.Ident(NameUtils.name(attribute.getName())),
                            asExpression(treeMaker, attribute.getValue())
                    ));
                }
                args = argsBuffer.toList();
            }

            return treeMaker.Annotation(treeMaker.Type(JavacUtils.javac(annotation.getType())), args);
        } else if (value instanceof UniErroneous) return treeMaker.Erroneous();
        throw new UnsupportedOperationException();
    }

    public static Attribute asAttribute(UniAnnotationValue value) {
        if (value instanceof UniLiteral) {
            UniLiteral literal = (UniLiteral) value;

            return new Attribute.Constant(JavacUtils.tagToType(literal.getTypeTag()), literal.getValue());
        } else if (value instanceof UniFieldAccess) { // Class literal & enum
            UniFieldAccess fieldAccess = (UniFieldAccess) value;

            if (fieldAccess.getName().equals("class")) {
                return new Attribute.Class(JavacUniflow.getInstance().types,
                        JavacUtils.javac(fieldAccess.getSelected()));
            } else {
                Type type = JavacUtils.javac(fieldAccess.getSelected());
                Symbol.VarSymbol element = SymbolUtils.findFieldByName(type, fieldAccess.getName());

                return new Attribute.Enum(type, element);
            }
        } else if (value instanceof UniNewArray) {
            UniNewArray array = (UniNewArray) value;
            @NotNull UniExpression[] values = array.getInitializers().get();
            Attribute[] attributes = new Attribute[values.length];

            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof UniAnnotationValue)
                    attributes[i] = asAttribute((UniAnnotationValue) values[i]);
                else throw new IllegalArgumentException("Non annotation value in annotation");
            }
            return new Attribute.Array(JavacUtils.javac(array.getType()), attributes);
        } else if (value instanceof UniAnnotation) {
            UniAnnotation annotation = (UniAnnotation) value;
            ListBuffer<Pair<Symbol.MethodSymbol, Attribute>> argsBuffer = new ListBuffer<>();
            Type type = JavacUtils.javac(annotation.getType());

            for (UniAnnotationAttribute attribute : annotation.getAttributes()) {
                // annotation = methods with 0 params
                Symbol.MethodSymbol method = SymbolUtils.findMethodByName(type, attribute.getName());

                argsBuffer.append(new Pair<>(method, asAttribute(attribute.getValue())));
            }
            return new Attribute.Compound(type, argsBuffer.toList());
        }
        return new Attribute.Error(JavacUniflow.getInstance().symtab.errType);
    }
}
