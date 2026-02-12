package org.galaxy.uniflow.intellij.psi.factories;

import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.SourceFolder;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiManager;
import org.galaxy.uniflow.api.files.UniFile;
import org.galaxy.uniflow.api.files.UniFileLocation;
import org.galaxy.uniflow.api.files.UniJavaFile;
import org.galaxy.uniflow.common.factories.CommonFiler;
import org.galaxy.uniflow.intellij.psi.files.IJFile;
import org.galaxy.uniflow.intellij.psi.files.IJJavaFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.java.JavaResourceRootProperties;
import org.jetbrains.jps.model.java.JavaResourceRootType;
import org.jetbrains.jps.model.java.JavaSourceRootProperties;
import org.jetbrains.jps.model.java.JavaSourceRootType;
import org.jetbrains.jps.model.module.JpsModuleSourceRootType;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

public final class IntellijFiler extends CommonFiler {

    private final Module module;
    private final PsiManager manager;
    private final PsiFileFactory fileFactory;

    public IntellijFiler(Module module) {
        this.module = module;
        this.manager = PsiManager.getInstance(module.getProject());
        this.fileFactory = PsiFileFactory.getInstance(module.getProject());
    }

    @Override
    public boolean doesSupports(UniFileLocation.@NotNull Location location) {
        return location == UniFileLocation.Location.SOURCE;
    }

    @Override
    public @NotNull UniJavaFile createSourceFile(@NotNull UniFileLocation location,
                                                 @NotNull Supplier<@NotNull String> contents) {
        PsiDirectory directory = resolvePackageDirectory(module, location.getPackage().toString(), JavaSource.INSTANCE);

        return WriteAction.compute(() -> {
            String fileName = location.getName() + ".java";
            PsiFile existing = directory.findFile(fileName);

            if (existing != null)
                existing.delete();

            PsiFile newFile = fileFactory.createFileFromText(
                    fileName,
                    JavaLanguage.INSTANCE,
                    contents.get()
            );
            newFile = (PsiFile) directory.add(newFile);

            return new IJJavaFile(newFile.getVirtualFile());
        });
    }

    @Override
    public @NotNull UniFile createResource(@NotNull UniFileLocation location,
                                           @NotNull Supplier<@NotNull String> contents) {
        if (location.getLocation() != UniFileLocation.Location.SOURCE)
            throw new UnsupportedOperationException("Only SOURCE is supported");

        PsiDirectory directory = resolvePackageDirectory(module, location.getPackage().toString(), Resources.INSTANCE);

        return WriteAction.compute(() -> {
            PsiFile existing = directory.findFile(location.getName().toString());

            if (existing != null)
                existing.delete();

            PsiFile newFile = directory.createFile(location.getName().toString());

            newFile.getViewProvider().getDocument().setText(contents.get());
            return new IJFile(newFile.getVirtualFile());
        });
    }

    @Override
    public @NotNull UniFile getResource(@NotNull UniFileLocation location) throws IOException {
        PsiDirectory directory = resolvePackageDirectory(module, location.getPackage().toString(), Resources.INSTANCE);
        PsiFile file = directory.findFile(location.getName().toString());

        if (file == null)
            throw new FileNotFoundException(location.toString());
        return new IJFile(file.getVirtualFile());
    }

    private PsiDirectory resolvePackageDirectory(Module module, String packageName, GeneratedSource source) {
        VirtualFile root = selectSourceRoot(module, source);
        PsiDirectory dir = manager.findDirectory(root);

        if (dir == null)
            throw new IllegalStateException("Directory not found: " + root.getPath());

        for (String part : packageName.split("\\.")) {
            PsiDirectory next = dir.findSubdirectory(part);
            PsiDirectory directory = dir;

            if (next == null)
                next = WriteAction.compute(() -> directory.createSubdirectory(part));
            dir = next;
        }
        return dir;
    }

    private VirtualFile selectSourceRoot(Module module, GeneratedSource source) {
        ContentEntry[] entries = ModuleRootManager.getInstance(module).getContentEntries();

        for (ContentEntry entry : entries) {
            for (SourceFolder folder : entry.getSourceFolders()) {
                JpsModuleSourceRootType<?> rootType = folder.getRootType();

                if (source.isSupportedType(rootType)) {
                    if (source.isForGeneratedSources(rootType, folder)) {
                        return folder.getFile();
                    }
                }
            }
        }

        List<VirtualFile> roots = ModuleRootManager.getInstance(module).getSourceRoots(source.getDefaultRootType());

        if (!roots.isEmpty()) return roots.getFirst();
        throw new IllegalStateException("No source root found for module " + module.getName());
    }

    sealed interface GeneratedSource {

        JpsModuleSourceRootType<?> getDefaultRootType();

        boolean isSupportedType(JpsModuleSourceRootType<?> type);

        boolean isForGeneratedSources(JpsModuleSourceRootType<?> type, SourceFolder folder);

    }

    static final class JavaSource implements GeneratedSource {

        public static final JavaSource INSTANCE = new JavaSource();

        @Override
        public JpsModuleSourceRootType<?> getDefaultRootType() {
            return JavaSourceRootType.SOURCE;
        }

        @Override
        public boolean isSupportedType(JpsModuleSourceRootType<?> type) {
            return type == JavaSourceRootType.SOURCE;
        }

        @Override
        public boolean isForGeneratedSources(JpsModuleSourceRootType<?> type, SourceFolder folder) {
            JavaSourceRootProperties properties = folder.getJpsElement().getProperties(JavaSourceRootType.SOURCE);

            return properties != null && properties.isForGeneratedSources();
        }
    }

    static final class Resources implements GeneratedSource {

        public static final Resources INSTANCE = new Resources();

        @Override
        public JpsModuleSourceRootType<?> getDefaultRootType() {
            return JavaResourceRootType.RESOURCE;
        }

        @Override
        public boolean isSupportedType(JpsModuleSourceRootType<?> type) {
            return type == JavaResourceRootType.RESOURCE;
        }

        @Override
        public boolean isForGeneratedSources(JpsModuleSourceRootType<?> type, SourceFolder folder) {
            JavaResourceRootProperties properties = folder.getJpsElement().getProperties(JavaResourceRootType.RESOURCE);

            return properties != null && properties.isForGeneratedSources();
        }
    }
}
