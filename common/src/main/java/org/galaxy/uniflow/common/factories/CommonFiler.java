package org.galaxy.uniflow.common.factories;

import org.galaxy.uniflow.api.factories.UniFiler;
import org.galaxy.uniflow.api.files.UniFileLocation;
import org.galaxy.uniflow.api.files.UniJavaFile;
import org.galaxy.uniflow.common.files.CommonFileLocation;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.function.Supplier;

public abstract class CommonFiler implements UniFiler {

    @Override
    public @NotNull UniFileLocation createLocation(@NotNull CharSequence name) {
        return new CommonFileLocation(UniFileLocation.Location.CLASS, null, null, name);
    }

    @Override
    public @NotNull UniFileLocation createLocation(@NotNull CharSequence packageName, @NotNull CharSequence name) {
        return new CommonFileLocation(UniFileLocation.Location.CLASS, null, packageName, name);
    }

    @Override
    public @NotNull UniFileLocation createLocation(@NotNull CharSequence module,
                                                   @NotNull CharSequence packageName,
                                                   @NotNull CharSequence name) {
        return new CommonFileLocation(UniFileLocation.Location.CLASS, module, packageName, name);
    }

    @Override
    public @NotNull UniFileLocation createLocation(UniFileLocation.@NotNull Location location,
                                                   @NotNull CharSequence name) {
        return new CommonFileLocation(location, null, null, name);
    }

    @Override
    public @NotNull UniFileLocation createLocation(UniFileLocation.@NotNull Location location,
                                                   @NotNull CharSequence packageName,
                                                   @NotNull CharSequence name) {
        return new CommonFileLocation(location, null, packageName, name);
    }

    @Override
    public @NotNull UniFileLocation createLocation(UniFileLocation.@NotNull Location location,
                                                   @NotNull CharSequence module,
                                                   @NotNull CharSequence packageName,
                                                   @NotNull CharSequence name) {
        return new CommonFileLocation(location, module, packageName, name);
    }

    @Override
    public @NotNull UniJavaFile createSourceFile(@NotNull CharSequence name,
                                                 @NotNull Supplier<@NotNull String> contents) throws IOException {
        String qName = name.toString();
        int idx = qName.lastIndexOf('.');
        String moduleAndPkg = idx < 0 ? "" : qName.substring(0, idx);

        idx = moduleAndPkg.indexOf('/');

        String moduleName = idx < 0 ? null : moduleAndPkg.substring(0, idx);
        String packageName = idx < 0 ? moduleAndPkg : moduleAndPkg.substring(idx + 1);
        String simpleName = idx < 0 ? qName : qName.substring(idx + 1);

        return createSourceFile(
                new CommonFileLocation(UniFileLocation.Location.SOURCE, moduleName, packageName, simpleName), contents);
    }
}
