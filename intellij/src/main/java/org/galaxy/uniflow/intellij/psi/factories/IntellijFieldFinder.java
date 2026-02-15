package org.galaxy.uniflow.intellij.psi.factories;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import org.galaxy.uniflow.api.signatures.UniFieldSignature;
import org.galaxy.uniflow.api.types.UniClassType;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.common.factories.CommonFieldFinder;
import org.galaxy.uniflow.intellij.psi.signature.IJFieldSignature;
import org.galaxy.uniflow.intellij.psi.types.IJClassType;
import org.galaxy.uniflow.intellij.psi.types.IJType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.galaxy.uniflow.intellij.psi.util.IJUtils.check;

public class IntellijFieldFinder extends CommonFieldFinder {

    @Override
    public @Nullable UniFieldSignature find(@NotNull UniClassType owner, @NotNull String name) {
        IJClassType ijOwner = check(owner, IJClassType.class);
        PsiClass resolved = ijOwner.getRawType().resolve();

        if (resolved == null) return null;
        PsiField field = resolved.findFieldByName(name, true);

        return field != null ? new IJFieldSignature(field) : null;
    }

    @Override
    public @NotNull List<UniFieldSignature> find(@NotNull UniClassType owner, @NotNull UniType fieldType) {
        IJClassType ijOwner = check(owner, IJClassType.class);
        IJType<?> ijFieldType = check(fieldType, IJType.class);
        PsiClass resolved = ijOwner.getRawType().resolve();

        if (resolved == null) return Collections.emptyList();
        List<UniFieldSignature> signatures = new ArrayList<>();

        for (PsiField field : resolved.getAllFields())
            signatures.add(new IJFieldSignature(field));
        return signatures;
    }
}
