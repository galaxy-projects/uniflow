package org.galaxy.uniflow.javac.annotations;

import com.sun.tools.javac.code.Attribute;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Pair;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.annotations.UniAnnotationAttribute;
import org.galaxy.uniflow.api.annotations.UniAnnotationValue;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.expressions.UniFieldAccess;
import org.galaxy.uniflow.api.expressions.UniLiteral;
import org.galaxy.uniflow.api.expressions.UniNewArray;
import org.galaxy.uniflow.javac.JavacUniflow;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.galaxy.uniflow.javac.util.SymbolUtils;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

class Util {

    public static @NotNull UniAnnotationAttribute attributeFromExpression(JCTree.JCExpression expression) {
        if (expression instanceof JCTree.JCAssign) {
            JCTree.JCAssign assign = (JCTree.JCAssign) expression;
            JCTree.JCIdent key = (JCTree.JCIdent) assign.lhs;
            UniAnnotationValue value = (UniAnnotationValue) UniflowWrapper.wrap(assign.rhs);

            return new JavacAnnotationAttribute(expression, NameUtils.nameToString(key.name), value);
        } else {
            // @Annotation(...) default uses 'value'
            UniAnnotationValue value = (UniAnnotationValue) UniflowWrapper.wrap(expression);

            return new JavacAnnotationAttribute(expression, "value", value);
        }
    }

    public static Attribute asAttribute(UniAnnotationValue value) {
        if (value instanceof UniLiteral) {
            UniLiteral literal = (UniLiteral) value;

            return new Attribute.Constant(JavacUnwrapper.tagToType(literal.getTypeTag()), literal.getValue());
        } else if (value instanceof UniFieldAccess) { // Class literal & enum
            UniFieldAccess fieldAccess = (UniFieldAccess) value;

            if (fieldAccess.getName().equals("class")) {
                return new Attribute.Class(JavacUniflow.getInstance().types,
                        JavacUnwrapper.unwrap(fieldAccess.getSelected()));
            } else {
                Type type = JavacUnwrapper.unwrap(fieldAccess.getSelected());
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
            return new Attribute.Array(JavacUnwrapper.unwrap(array.getType()), attributes);
        } else if (value instanceof UniAnnotation) {
            UniAnnotation annotation = (UniAnnotation) value;
            ListBuffer<Pair<Symbol.MethodSymbol, Attribute>> argsBuffer = new ListBuffer<>();
            Type type = JavacUnwrapper.unwrap(annotation.getType());

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
