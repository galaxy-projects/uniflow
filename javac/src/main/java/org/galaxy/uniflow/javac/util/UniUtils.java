package org.galaxy.uniflow.javac.util;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.annotations.UniAnnotationHolder;
import org.galaxy.uniflow.api.elements.UniCase;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.factories.UniElementFactory;
import org.galaxy.uniflow.api.signatures.UniOperatorSignature;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.api.types.TypeTag;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.javac.JavacUniflow;
import org.jetbrains.annotations.NotNull;

// TODO
public class UniUtils {

    public static @NotNull UniExpression uni(JCTree.JCExpression expression) {
        return null;
    }

    public static @NotNull UniAnnotation uni(JCTree.JCAnnotation annotation) {
        return null;
    }

    public static @NotNull UniOperatorSignature uni(Symbol.OperatorSymbol operator) {
        return null;
    }

    public static @NotNull UniVariable uni(JCTree.JCVariableDecl varDecl) {
        return null;
    }

    public static @NotNull UniAnnotationHolder uni(List<JCTree.JCAnnotation> annotations) {
        return null;
    }

    public static @NotNull UniCase uni(JCTree.JCCase jcCase) {
        return null;
    }

    public static @NotNull UniElement uni(JCTree element) {
        UniElementFactory factory = JavacUniflow.getInstance().getElementFactory();

        return null;
//        if (expression instanceof JCTree.JCLiteral) { // @Annotation("")
//            JCTree.JCLiteral literal = (JCTree.JCLiteral) expression;
//
//            return factory.createLiteral(UniUtils.typeToTag(literal.type), literal.value);
//        } else if (expression instanceof JCTree.JCNewArray) { // @Annotation({})
//            // dims is null, because dims requires new Object[]: impossible in annotations
//            JCTree.JCNewArray newArray = (JCTree.JCNewArray) expression;
//            java.util.List<UniExpression> values;
//
//            if (newArray.elems != null) {
//                values = new ArrayList<>(newArray.elems.size());
//
//                for (JCTree.JCExpression elem : newArray.elems) // use foreach, don't use get (linked list)
//                    values.add(attributeValue(elem));
//            } else values = Collections.emptyList();
//
//            return factory.createNewArrayWithElements(fromType(newArray.elemtype), values);
//        } else if (expression instanceof JCTree.JCAnnotation) { // @Annotation(@AnotherAnnotation)
//            JCTree.JCAnnotation annotation = (JCTree.JCAnnotation) expression;
//            java.util.List<UniAnnotationAttribute> attributes = new ArrayList<>(annotation.args.size());
//
//            for (JCTree.JCExpression arg : annotation.args)
//                attributes.add(attributeFromExpression(arg));
//
//            return factory.createAnnotation(fromType(annotation.annotationType), attributes);
//        } else if (expression instanceof JCTree.JCFieldAccess) { // @Annotation(Mode.ON)
//            JCTree.JCFieldAccess fieldAccess = (JCTree.JCFieldAccess) expression;
//
//            return factory.createFieldAccess(UniUtils.type(fieldAccess.type), NameUtils.nameToString(fieldAccess.name));
//        }
    }

    public static @NotNull UniType type(Type type) {
        return null;
    }

    public static @NotNull TypeTag typeToTag(Type type) {
        return null;
    }

    public static @NotNull UniType typeFromTree(JCTree annotationType) {
        return null;
    }
}
