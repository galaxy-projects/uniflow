package org.galaxy.uniflow.api.elements;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public enum UniModifier {

    PUBLIC(1),
    PRIVATE(1 << 1),
    PROTECTED(1 << 2),
    STATIC(1 << 3),
    FINAL(1 << 4),
    SYNCHRONIZED(1 << 5),
    VOLATILE(1 << 6),
    TRANSIENT(1 << 7),
    NATIVE(1 << 8),
    INTERFACE(1 << 9),
    ABSTRACT(1 << 10),
    STRICTFP(1 << 11),
    SYNTHETIC(1 << 12),
    ANNOTATION(1 << 13),
    ENUM(1 << 14),

    DEFAULT(1L << 43),

    RECORD(1L << 61),
    SEALED(1L << 62L),
    NON_SEALED(1L << 63);

    private final long mask;

    UniModifier(long flag) {
        this.mask = flag;
    }

    public long getMask() {
        return mask;
    }

    public boolean hasModifier(long flags) {
        return (flags & mask) != 0;
    }

    public static long asLongFlags(@NotNull List<@NotNull UniModifier> modifiers) {
        long result = 0;

        for (UniModifier modifier : modifiers)
            result |= modifier.mask;
        return result;
    }
}
