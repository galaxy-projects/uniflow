package org.galaxy.uniflow.intellij.psi.statements;

import com.intellij.psi.*;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.statements.UniExpressionStatement;
import org.galaxy.uniflow.api.statements.UniForLoop;
import org.galaxy.uniflow.api.statements.UniStatement;
import org.galaxy.uniflow.intellij.psi.IntellijUniflow;
import org.galaxy.uniflow.intellij.psi.lists.statements.IJForStatementList;
import org.galaxy.uniflow.intellij.psi.util.IntellijUnwrapper;
import org.galaxy.uniflow.intellij.psi.util.UniflowWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public class IJForLoop extends IJStatement<PsiForStatement> implements UniForLoop {

    public IJForLoop(PsiForStatement element) {
        super(element);
    }

    @Override
    public @NotNull UniList<@NotNull UniStatement> getInitializer() {
        return createList(element.getInitialization(), UniStatement.class, UniStatement[]::new,
                init -> replace(init, element.getCondition(), element.getUpdate(), element.getBody()));
    }

    @Override
    public void setCondition(@NotNull UniExpression condition) {
        replace(element.getInitialization(),
                IntellijUnwrapper.unwrap(condition),
                element.getUpdate(),
                element.getBody());
    }

    @Override
    public @NotNull UniExpression getCondition() {
        return UniflowWrapper.wrap(element.getCondition());
    }

    @Override
    public @NotNull UniList<@NotNull UniExpressionStatement> getUpdate() {
        return createList(element.getUpdate(), UniExpressionStatement.class, UniExpressionStatement[]::new,
                update -> replace(element.getInitialization(), element.getCondition(), update, element.getBody()));
    }

    @Override
    public void setBody(@NotNull UniStatement body) {
        replace(element.getInitialization(),
                element.getCondition(),
                element.getUpdate(),
                IntellijUnwrapper.unwrap(body));
    }

    @Override
    public @NotNull UniStatement getBody() {
        return UniflowWrapper.wrap(element.getBody());
    }

    @Override
    public @NotNull Kind getKind() {
        return Kind.FOR_LOOP;
    }

    private void replace(PsiElement init, PsiExpression condition, PsiElement update, PsiStatement body) {
        PsiElementFactory factory = IntellijUniflow.getInstance().factory;
        PsiForStatement newFor = (PsiForStatement) factory.createStatementFromText(
                "for (int i = 0; i < 100; i++) {}", null);

        assert newFor.getInitialization() != null;
        assert newFor.getCondition() != null;
        assert newFor.getUpdate() != null;
        assert newFor.getBody() != null;

        replaceOrDelete(newFor, PsiForStatement::getInitialization, init);
        replaceOrDelete(newFor, PsiForStatement::getCondition, condition);
        replaceOrDelete(newFor, PsiForStatement::getUpdate, update);
        replaceOrDelete(newFor, PsiForStatement::getBody, body);

        replace(newFor);
    }

    private <T extends PsiElement> void replaceOrDelete(PsiForStatement forStatement,
                                                        Function<PsiForStatement, T> getter,
                                                        T newValue) {
        T current = getter.apply(forStatement);

        if (newValue != null)
            current.replace(newValue);
        else current.delete();
    }

    private <T extends UniStatement> UniList<T> createList(PsiElement element,
                                                           Class<T> componentType,
                                                           IntFunction<T[]> arrayGenerator,
                                                           Consumer<PsiElement> updater) {
        Stream<PsiElement> elements;

        if (element instanceof PsiDeclarationStatement) // multiple
            elements = Arrays.stream(((PsiDeclarationStatement) element).getDeclaredElements());
        else elements = Stream.ofNullable(element); // unique & null
        return new IJForStatementList<>(
                elements.map(UniflowWrapper::wrap).map(componentType::cast),
                arrayGenerator,
                createConsumer(updater));
    }

    public static <T extends UniStatement> Consumer<List<T>> createConsumer(Consumer<PsiElement> updater) {
        return elements -> {
            if (elements.isEmpty())
                updater.accept(null);
            else if (elements.size() == 1)
                updater.accept(IntellijUnwrapper.unwrap(elements.getFirst()));
            else {
                StringBuilder sb = new StringBuilder(3 * elements.size() - 2);

                for (int i = 0; i < elements.size(); i++) {
                    if (i != 0)
                        sb.append(", ");
                    sb.append('a');
                }
                PsiElementFactory factory = IntellijUniflow.getInstance().factory;
                PsiDeclarationStatement declaration = (PsiDeclarationStatement) factory.createStatementFromText(
                        sb.toString(), null);
                PsiElement[] declaredElements = declaration.getDeclaredElements();

                for (int i = 0; i < elements.size(); i++)
                    declaredElements[i].replace(IntellijUnwrapper.unwrap(elements.get(i)));
                updater.accept(declaration);
            }
        };
    }
}
