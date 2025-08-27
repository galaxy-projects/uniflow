package org.galaxy.uniflow.javac.util;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.annotations.UniAnnotationHolder;
import org.galaxy.uniflow.api.elements.UniCase;
import org.galaxy.uniflow.api.elements.UniCaseLabel;
import org.galaxy.uniflow.api.elements.UniCatch;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.modules.directives.UniDirective;
import org.galaxy.uniflow.api.pattern.UniPattern;
import org.galaxy.uniflow.api.signatures.UniOperatorSignature;
import org.galaxy.uniflow.api.statements.UniBlock;
import org.galaxy.uniflow.api.statements.UniExpressionStatement;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.api.types.TypeTag;
import org.galaxy.uniflow.api.types.UniType;
import org.jetbrains.annotations.NotNull;

// TODO
public class JavacUtils {

    public static @NotNull Type javac(UniType type) {
        throw new UnsupportedOperationException();
    }

    public static @NotNull JCTree.JCVariableDecl javac(UniVariable variable) {
        throw new UnsupportedOperationException();
    }

    public static @NotNull JCTree javac(UniElement element) {
        return null;
    }

    public static @NotNull JCTree.JCCaseLabel javac(UniCaseLabel caseLabel) {
        return null;
    }

    public static @NotNull JCTree.JCCatch javac(UniCatch catcher) {
        return null;
    }

    public static @NotNull JCTree.JCBlock javac(UniBlock block) {
        return null;
    }

    public static @NotNull JCTree.JCStatement javac(UniStatement statement) {
        return null;
    }

    public static @NotNull JCTree.JCExpressionStatement javac(UniExpressionStatement statement) {
        return null;
    }

    public static @NotNull JCTree.JCDirective javac(UniDirective directive) {
        return null;
    }

    public static @NotNull JCTree.JCPattern javac(UniPattern pattern) {
        return null;
    }

    public static @NotNull JCTree.JCCase javac(UniCase uniCase) {
        return null;
    }

    public static @NotNull JCTree.JCAnnotation javac(UniAnnotation annotation) {
        return null;
    }

    public static @NotNull List<JCTree.JCAnnotation> javac(UniAnnotationHolder holder) {
        return null;
    }

    public static @NotNull JCTree.JCExpression javac(UniExpression type) {
        return null;
    }

    public static @NotNull Type tagToType(@NotNull TypeTag typeTag) {
        return null;
    }

    public static @NotNull Symbol.OperatorSymbol javac(@NotNull UniOperatorSignature operator) {
        return null;
    }

//    public static JCTree.JCExpression asExpression(TreeMaker treeMaker, UniAnnotationValue value) {
//        if (value instanceof UniLiteral) {
//            UniLiteral literal = (UniLiteral) value;
//
//            return treeMaker.Literal(literal.getValue());
//        } else if (value instanceof UniFieldAccess) { // Class literal & enum
//            UniFieldAccess fieldAccess = (UniFieldAccess) value;
//
//            return treeMaker.Select(
//                    treeMaker.Type(JavacUtils.javac(fieldAccess.getSelected())),
//                    NameUtils.name(fieldAccess.getName())
//            );
//        } else if (value instanceof UniNewArray) {
//            UniNewArray array = (UniNewArray) value;
//            @NotNull UniExpression[] values = array.getInitializers().get();
//            JCTree.JCExpression[] contents = new JCTree.JCExpression[values.length];
//
//            for (int i = 0; i < values.length; i++) {
//                if (values[i] instanceof UniAnnotationValue)
//                    contents[i] = asExpression(treeMaker, (UniAnnotationValue) values[i]);
//                else throw new IllegalArgumentException("Non annotation value in annotation");
//            }
//            return treeMaker.NewArray(
//                    treeMaker.Type(JavacUtils.javac(array.getType())),
//                    List.nil(),
//                    List.from(contents)
//            );
//        } else if (value instanceof UniAnnotation) {
//            UniAnnotation annotation = (UniAnnotation) value;
//            @NotNull UniAnnotationAttribute[] attributes = annotation.getAttributes();
//            List<JCTree.JCExpression> args;
//
//            if (attributes.length == 1 && attributes[0].getName().equals("value")) {
//                // only 'value'
//                args = List.of(asExpression(treeMaker, attributes[0].getValue()));
//            } else {
//                ListBuffer<JCTree.JCExpression> argsBuffer = new ListBuffer<>();
//
//                for (UniAnnotationAttribute attribute : attributes) {
//                    argsBuffer.append(treeMaker.Assign(
//                            treeMaker.Ident(NameUtils.name(attribute.getName())),
//                            asExpression(treeMaker, attribute.getValue())
//                    ));
//                }
//                args = argsBuffer.toList();
//            }
//
//            return treeMaker.Annotation(treeMaker.Type(JavacUtils.javac(annotation.getType())), args);
//        } else if (value instanceof UniErroneous) return treeMaker.Erroneous();
//        throw new UnsupportedOperationException();
//    }
}
