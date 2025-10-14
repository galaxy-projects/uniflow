package org.galaxy.uniflow.javac9.factories;

import com.sun.source.tree.ModuleTree;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.ListBuffer;
import org.galaxy.uniflow.api.UniCompilationUnit;
import org.galaxy.uniflow.api.UniModifiers;
import org.galaxy.uniflow.api.UniPackage;
import org.galaxy.uniflow.api.factories.UniJdk9ElementFactory;
import org.galaxy.uniflow.api.modules.UniModule;
import org.galaxy.uniflow.api.modules.directives.*;
import org.galaxy.uniflow.common.EnumUtils;
import org.galaxy.uniflow.javac.JavacPackage;
import org.galaxy.uniflow.javac.JavacUniflow;
import org.galaxy.uniflow.javac.factories.JavacElementFactory;
import org.galaxy.uniflow.javac.util.JavacUnwrapper;
import org.galaxy.uniflow.javac.util.NameUtils;
import org.galaxy.uniflow.javac9.Javac9CompilationUnit;
import org.galaxy.uniflow.javac9.Javac9Unwrapper;
import org.galaxy.uniflow.javac9.modules.JavacModule;
import org.galaxy.uniflow.javac9.modules.directives.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

public class Javac9ElementFactory extends JavacElementFactory implements UniJdk9ElementFactory {

    @Override
    public @NotNull UniCompilationUnit createTopLevel(@NotNull UniPackage packageDecl,
                                                      @NotNull List<@NotNull UniModule> modules) {
        JavacPackage javacPackage = check(packageDecl, JavacPackage.class);
        Stream<JavacModule> javacModules = checkList(modules, JavacModule.class);
        ListBuffer<JCTree> buffer = new ListBuffer<>();

        buffer.add(javacPackage.getTree());
        javacModules.map(JavacModule::getTree).forEach(buffer::add);
        return new Javac9CompilationUnit(treeMaker.TopLevel(buffer.toList()));
    }

    @Override
    public @NotNull UniModule createModule(@NotNull UniModifiers modifiers,
                                           UniModule.@NotNull ModuleKind kind,
                                           @NotNull String name,
                                           @NotNull List<@NotNull UniDirective> directives) {
        TreeMaker treeMaker = JavacUniflow.getInstance().treeMaker;

        //noinspection Since15
        return new JavacModule(treeMaker.ModuleDef(
                JavacUnwrapper.unwrap(modifiers),
                EnumUtils.convert(ModuleTree.ModuleKind.class, kind),
                treeMaker.Ident(NameUtils.name(name)),
                directives.stream().map(Javac9Unwrapper::unwrap).collect(com.sun.tools.javac.util.List.collector())
        ));
    }

    @Override
    public @NotNull UniExports createExports(@NotNull String name, @NotNull List<@NotNull String> moduleNames) {
        TreeMaker treeMaker = JavacUniflow.getInstance().treeMaker;

        return new JavacExports(treeMaker.Exports(
                treeMaker.Ident(NameUtils.name(name)),
                moduleNames.stream()
                        .map(n -> treeMaker.Ident(NameUtils.name(n)))
                        .collect(com.sun.tools.javac.util.List.collector())
        ));
    }

    @Override
    public @NotNull UniOpens createOpens(@NotNull String name, @NotNull List<@NotNull String> moduleNames) {
        TreeMaker treeMaker = JavacUniflow.getInstance().treeMaker;

        return new JavacOpens(treeMaker.Opens(
                treeMaker.Ident(NameUtils.name(name)),
                moduleNames.stream()
                        .map(n -> treeMaker.Ident(NameUtils.name(n)))
                        .collect(com.sun.tools.javac.util.List.collector())
        ));
    }

    @Override
    public @NotNull UniProvides createProvides(@NotNull String serviceName,
                                               @NotNull List<@NotNull String> implementationNames) {
        TreeMaker treeMaker = JavacUniflow.getInstance().treeMaker;

        return new JavacProvides(treeMaker.Provides(
                treeMaker.Ident(NameUtils.name(serviceName)),
                implementationNames.stream()
                        .map(n -> treeMaker.Ident(NameUtils.name(n)))
                        .collect(com.sun.tools.javac.util.List.collector())
        ));
    }

    @Override
    public @NotNull UniRequires createRequires(boolean isTransitive, boolean isStatic, @NotNull String name) {
        TreeMaker treeMaker = JavacUniflow.getInstance().treeMaker;

        return new JavacRequires(treeMaker.Requires(isTransitive, isStatic, treeMaker.Ident(NameUtils.name(name))));
    }

    @Override
    public @NotNull UniUses createUses(@NotNull String serviceName) {
        TreeMaker treeMaker = JavacUniflow.getInstance().treeMaker;

        return new JavacUses(treeMaker.Uses(treeMaker.Ident(NameUtils.name(serviceName))));
    }
}
