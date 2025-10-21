package org.galaxy.uniflow.intellij.psi.types;

import com.intellij.lang.jvm.types.JvmPrimitiveTypeKind;
import com.intellij.psi.PsiPrimitiveType;
import org.galaxy.uniflow.api.types.TypeTag;
import org.galaxy.uniflow.api.types.UniPrimitiveType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("UnstableApiUsage")
public class IJPrimitiveType extends IJType<PsiPrimitiveType> implements UniPrimitiveType {

    private static final Map<JvmPrimitiveTypeKind, TypeTag> KIND_TO_TAG;

    public IJPrimitiveType(PsiPrimitiveType type) {
        super(type);
    }

    @Override
    public void setTag(@NotNull TypeTag typeTag) {}

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public @NotNull TypeTag getTag() {
        return KIND_TO_TAG.get(type.getKind());
    }

    static {
        KIND_TO_TAG = new HashMap<>();

        KIND_TO_TAG.put(JvmPrimitiveTypeKind.BOOLEAN, TypeTag.BOOLEAN);
        KIND_TO_TAG.put(JvmPrimitiveTypeKind.CHAR, TypeTag.CHAR);
        KIND_TO_TAG.put(JvmPrimitiveTypeKind.BYTE, TypeTag.BYTE);
        KIND_TO_TAG.put(JvmPrimitiveTypeKind.SHORT, TypeTag.SHORT);
        KIND_TO_TAG.put(JvmPrimitiveTypeKind.INT, TypeTag.INT);
        KIND_TO_TAG.put(JvmPrimitiveTypeKind.LONG, TypeTag.LONG);
        KIND_TO_TAG.put(JvmPrimitiveTypeKind.FLOAT, TypeTag.FLOAT);
        KIND_TO_TAG.put(JvmPrimitiveTypeKind.DOUBLE, TypeTag.DOUBLE);
        KIND_TO_TAG.put(JvmPrimitiveTypeKind.VOID, TypeTag.VOID);
    }
}
