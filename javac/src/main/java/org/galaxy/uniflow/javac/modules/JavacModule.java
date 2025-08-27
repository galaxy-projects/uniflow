package org.galaxy.uniflow.javac.modules;

import com.sun.tools.javac.tree.JCTree;
import org.galaxy.uniflow.api.UniList;
import org.galaxy.uniflow.api.annotations.UniAnnotation;
import org.galaxy.uniflow.api.elements.UniModifier;
import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.modules.UniModule;
import org.galaxy.uniflow.api.modules.directives.UniDirective;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.common.EnumUtils;
import org.galaxy.uniflow.javac.JavacElement;
import org.galaxy.uniflow.javac.lists.JavacList;
import org.galaxy.uniflow.javac.util.JavacUtils;
import org.galaxy.uniflow.javac.util.UniUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavacModule extends JavacElement<JCTree.JCModuleDecl> implements UniModule {

    public JavacModule(JCTree.@NotNull JCModuleDecl tree) {
        super(tree);
    }

    @Override
    public @NotNull ModuleKind getModuleKind() {
        return EnumUtils.convert(ModuleKind.class, tree.getModuleType());
    }

    @Override
    public void setName(@NotNull UniExpression name) {
        tree.qualId = JavacUtils.javac(name);
    }

    @Override
    public @NotNull UniExpression getName() {
        return UniUtils.uni(tree.qualId);
    }

    @Override
    public @NotNull UniList<UniDirective> getDirectives() {
        return new JavacList<>(
                tree.directives,
                newList -> tree.directives = newList,
                UniUtils::uni,
                JavacUtils::javac
        );
    }

    @Override
    public @NotNull UniList<@NotNull UniAnnotation> getAnnotations() {
        return new JavacList<>(
                tree.mods.annotations,
                newList -> tree.mods.annotations = newList,
                UniUtils::uni,
                JavacUtils::javac
        );
    }

    @Override
    public @Nullable UniAnnotation getAnnotation(@NotNull UniClassType type) {
        return tree.mods.annotations.stream()
                .filter(annotation -> UniUtils.typeFromTree(annotation.annotationType).equals(type))
                .findFirst()
                .map(UniUtils::uni)
                .orElse(null);
    }

    @Override
    public @Nullable UniAnnotation @NotNull [] getAllAnnotations(@NotNull UniClassType type) {
        return tree.mods.annotations.stream()
                .filter(annotation -> UniUtils.typeFromTree(annotation.annotationType).equals(type))
                .map(UniUtils::uni)
                .toArray(UniAnnotation[]::new);
    }

    @Override
    public boolean hasAnnotation(@NotNull UniClassType type) {
        return tree.mods.annotations.stream()
                .anyMatch(annotation -> UniUtils.typeFromTree(annotation.annotationType).equals(type));
    }

    @Override
    public @NotNull UniModifier @NotNull [] getModifiers() {
        return tree.mods.getFlags().stream()
                .map(flag -> EnumUtils.convert(UniModifier.class, flag))
                .toArray(UniModifier[]::new);
    }

    @Override
    public boolean hasModifier(@NotNull UniModifier modifier) {
        return modifier.hasModifier(tree.mods.flags);
    }

    @Override
    public void addModifier(@NotNull UniModifier modifier) {
        tree.mods.flags |= modifier.getMask();
    }

    @Override
    public void removeModifier(@NotNull UniModifier modifier) {
        tree.mods.flags &= ~modifier.getMask();
    }
}
