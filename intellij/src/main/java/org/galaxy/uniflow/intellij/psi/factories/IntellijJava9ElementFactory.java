package org.galaxy.uniflow.intellij.psi.factories;

import com.intellij.psi.*;
import org.galaxy.uniflow.api.UniModifiers;
import org.galaxy.uniflow.api.factories.UniJdk9ElementFactory;
import org.galaxy.uniflow.api.modules.UniModule;
import org.galaxy.uniflow.api.modules.directives.*;
import org.galaxy.uniflow.intellij.psi.IJModifiers;
import org.galaxy.uniflow.intellij.psi.modules.IJModule;
import org.galaxy.uniflow.intellij.psi.modules.directives.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

import static org.galaxy.uniflow.intellij.psi.util.IJUtils.check;
import static org.galaxy.uniflow.intellij.psi.util.IJUtils.checkList;

public class IntellijJava9ElementFactory extends IntellijElementFactory implements UniJdk9ElementFactory {

    public IntellijJava9ElementFactory(PsiElementFactory factory, PsiJavaParserFacade parser, PsiFileFactory files) {
        super(factory, parser, files);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull UniModule createModule(@NotNull UniModifiers modifiers,
                                           UniModule.@NotNull ModuleKind kind,
                                           @NotNull String name,
                                           @NotNull List<@NotNull UniDirective> directives) {
        IJModifiers ijModifiers = check(modifiers, IJModifiers.class);
        Stream<IJDirective> ijDirectives = checkList(directives, IJDirective.class);

        PsiJavaModule module = factory.createModuleFromText("module %s {}".formatted(name), null);

        ijDirectives.map(IJDirective::getElement).forEach(module::add);

        return new IJModule(module);
    }

    @Override
    public @NotNull UniExports createExports(@NotNull String name, @NotNull List<@NotNull String> moduleNames) {
        String text;

        if (!moduleNames.isEmpty())
            text = "exports %s to %s;".formatted(name, String.join(", ", moduleNames));
        else
            text = "exports %s;".formatted(name);

        return new IJExports((PsiPackageAccessibilityStatement) factory.createModuleStatementFromText(text, null));
    }

    @Override
    public @NotNull UniOpens createOpens(@NotNull String name, @NotNull List<@NotNull String> moduleNames) {
        String text;

        if (!moduleNames.isEmpty())
            text = "opens %s to %s;".formatted(name, String.join(", ", moduleNames));
        else
            text = "opens %s;".formatted(name);

        return new IJOpens((PsiPackageAccessibilityStatement) factory.createModuleStatementFromText(text, null));
    }

    @Override
    public @NotNull UniProvides createProvides(@NotNull String serviceName,
                                               @NotNull List<@NotNull String> implementationNames) {
        if (implementationNames.isEmpty())
            throw new IllegalArgumentException("Implementation names cannot be empty");

        String text = "provides %s with %s;".formatted(serviceName, String.join(", ", implementationNames));

        return new IJProvides((PsiProvidesStatement) factory.createModuleStatementFromText(text, null));
    }

    @Override
    public @NotNull UniRequires createRequires(boolean isTransitive, boolean isStatic, @NotNull String name) {
        String fullName = isTransitive ? "transitive " + name : (isStatic ? "static " + name : name);
        String text = "requires %s".formatted(fullName);

        return new IJRequires((PsiRequiresStatement) factory.createModuleStatementFromText(text, null));
    }

    @Override
    public @NotNull UniUses createUses(@NotNull String serviceName) {
        String text = "uses %s".formatted(serviceName);

        return new IJUses((PsiUsesStatement) factory.createModuleStatementFromText(text, null));
    }
}
