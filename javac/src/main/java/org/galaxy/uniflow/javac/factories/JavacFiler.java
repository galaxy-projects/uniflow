package org.galaxy.uniflow.javac.factories;

import com.sun.tools.javac.code.Source;
import org.galaxy.uniflow.api.factories.UniFiler;
import org.galaxy.uniflow.api.files.UniFile;
import org.galaxy.uniflow.api.files.UniFileLocation;
import org.galaxy.uniflow.api.files.UniJavaFile;
import org.galaxy.uniflow.javac.JavacUniflow;
import org.galaxy.uniflow.javac.files.JavacFile;
import org.galaxy.uniflow.javac.files.JavacFileLocation;
import org.galaxy.uniflow.javac.files.JavacJavaFile;
import org.jetbrains.annotations.NotNull;

import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.function.Supplier;

public class JavacFiler implements UniFiler {

    @Override
    public @NotNull UniFileLocation createLocation(@NotNull CharSequence name) {
        return new JavacFileLocation(UniFileLocation.Location.CLASS_OUTPUT, null, null, name);
    }

    @Override
    public @NotNull UniFileLocation createLocation(@NotNull CharSequence packageName, @NotNull CharSequence name) {
        return new JavacFileLocation(UniFileLocation.Location.CLASS_OUTPUT, null, packageName, name);
    }

    @Override
    public @NotNull UniFileLocation createLocation(@NotNull CharSequence module,
                                                   @NotNull CharSequence packageName,
                                                   @NotNull CharSequence name) {
        return new JavacFileLocation(UniFileLocation.Location.CLASS_OUTPUT, module, packageName, name);
    }

    @Override
    public @NotNull UniFileLocation createLocation(UniFileLocation.@NotNull Location location,
                                                   @NotNull CharSequence name) {
        return new JavacFileLocation(location, null, null, name);
    }

    @Override
    public @NotNull UniFileLocation createLocation(UniFileLocation.@NotNull Location location,
                                                   @NotNull CharSequence packageName,
                                                   @NotNull CharSequence name) {
        return new JavacFileLocation(location, null, packageName, name);
    }

    @Override
    public @NotNull UniFileLocation createLocation(UniFileLocation.@NotNull Location location,
                                                   @NotNull CharSequence module,
                                                   @NotNull CharSequence packageName,
                                                   @NotNull CharSequence name) {
        return new JavacFileLocation(location, module, packageName, name);
    }

    @Override
    public @NotNull UniJavaFile createSourceFile(@NotNull CharSequence name,
                                                 @NotNull Supplier<@NotNull String> contents)
            throws IOException {
        JavaFileObject sourceFile = JavacUniflow.getInstance().filer
                .createSourceFile(name);

        try (Writer writer = sourceFile.openWriter()) {
            writer.write(contents.get());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new JavacJavaFile(sourceFile);
    }

    @Override
    public @NotNull UniFile createResource(@NotNull UniFileLocation location,
                                           @NotNull Supplier<@NotNull String> contents)
            throws IOException {
        JavaFileManager.Location target = getLocation(location);
        FileObject resourceFile = JavacUniflow.getInstance().filer
                .createResource(target, createModuleAndPackage(location), location.getName());

        try (Writer writer = resourceFile.openWriter()) {
            writer.write(contents.get());
        }
        return new JavacFile<>(resourceFile);
    }

    @Override
    public @NotNull UniFile getResource(@NotNull UniFileLocation location) throws IOException {
        JavaFileManager.Location target = getLocation(location);
        FileObject resource = JavacUniflow.getInstance().filer
                .getResource(target, createModuleAndPackage(location), location.getName());

        return new JavacFile<>(resource);
    }

    private CharSequence createModuleAndPackage(UniFileLocation location) {
        if ((location.getModule() != null) && (JavacUniflow.getInstance().source.compareTo(Source.JDK9) >= 0))
            return location.getModule() + "/" + location.getPackage();
        return location.getPackage();
    }

    private JavaFileManager.Location getLocation(UniFileLocation location) {
        switch (location.getLocation()) {
            case SOURCE_OUTPUT:
                return StandardLocation.SOURCE_OUTPUT;
            case NATIVE_HEADER_OUTPUT:
                return StandardLocation.NATIVE_HEADER_OUTPUT;
            case CLASS_OUTPUT:
            default:
                return StandardLocation.CLASS_OUTPUT;
        }
    }
}
