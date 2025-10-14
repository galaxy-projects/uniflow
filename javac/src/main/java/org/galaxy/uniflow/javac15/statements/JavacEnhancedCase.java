package org.galaxy.uniflow.javac15.statements;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniElement;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.elements.UniCaseLabel;
import org.galaxy.uniflow.api.statements.UniEnhancedCase;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class JavacEnhancedCase extends JavacElement<JCTree.JCCase> implements UniEnhancedCase {

    public JavacEnhancedCase(JCTree.@NotNull JCCase tree) {
        super(tree);
    }

    @Override
    public @NotNull UniList<UniCaseLabel> getLabels() {
        return new JavacList<>(
                () -> tree.labels,
                newList -> tree.labels = newList,
                UniflowWrapper::wrap,
                JavacUnwrapper::unwrap
        );
    }

    public static class JavacStatementEnhancedCase extends JavacEnhancedCase implements UniStatementCase {

        public JavacStatementEnhancedCase(JCTree.@NotNull JCCase tree) {
            super(tree);
        }

        @Override
        public @NotNull UniList<UniStatement> getStatements() {
            return new JavacList<>(
                    tree::getStatements,
                    newList -> tree.stats = newList,
                    UniflowWrapper::wrap,
                    JavacUnwrapper::unwrap
            );
        }

        @Override
        public @NotNull CaseKind getCaseKind() {
            return CaseKind.STATEMENT;
        }
    }

    public static class JavacRuleEnhancedCase extends JavacEnhancedCase implements UniRuleCase {

        public JavacRuleEnhancedCase(JCTree.@NotNull JCCase tree) {
            super(tree);
        }

        @Override
        public void setBody(@NotNull UniElement body) {
            tree.body = JavacUnwrapper.unwrap(body);
        }

        @Override
        public @Nullable UniElement getBody() {
            return UniflowWrapper.wrap(tree.body);
        }

        @Override
        public @NotNull CaseKind getCaseKind() {
            return CaseKind.RULE;
        }
    }
}
