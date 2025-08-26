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
import org.galaxy.uniflow.api.signatures.UniOperatorSignature;
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
}
