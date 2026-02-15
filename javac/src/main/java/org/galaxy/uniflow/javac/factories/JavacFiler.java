package org.galaxy.uniflow.javac.factories;

import com.sun.tools.javac.code.Source;
import org.galaxy.uniflow.api.files.UniFile;
import org.galaxy.uniflow.api.files.UniFileLocation;
import org.galaxy.uniflow.api.files.UniJavaFile;
import org.galaxy.uniflow.common.factories.CommonFiler;
import org.galaxy.uniflow.javac.JavacUniflow;
import org.galaxy.uniflow.javac.files.JavacFile;
import org.galaxy.uniflow.javac.files.JavacJavaFile;
import org.jetbrains.annotations.NotNull;

import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.function.Supplier;

public class JavacFiler extends CommonFiler {

    @Override
    public boolean doesSupports(UniFileLocation.@NotNull Location location) {
        return true;
    }

    @Override
    public @NotNull UniJavaFile createSourceFile(@NotNull UniFileLocation location,
                                                 @NotNull Supplier<@NotNull String> contents) throws IOException {
        JavaFileObject sourceFile = JavacUniflow.getInstance().filer
                .createSourceFile(location.getPackage() + "." + location.getName());

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
            case SOURCE:
                return StandardLocation.SOURCE_OUTPUT;
            case NATIVE_HEADER:
                return StandardLocation.NATIVE_HEADER_OUTPUT;
            case CLASS:
            default:
                return StandardLocation.CLASS_OUTPUT;
        }
    }
}
