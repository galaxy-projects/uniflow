package org.galaxy.uniflow.api.elements;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public enum UniModifier {

    PUBLIC(1),
    PROTECTED(1 << 2),
    PRIVATE(1 << 1),
    ABSTRACT(1 << 10),
    STATIC(1 << 3),
    SEALED(1L << 62L),
    NON_SEALED(1L << 63),
    FINAL(1 << 4),
    NATIVE(1 << 8),
    SYNCHRONIZED(1 << 5),
    VOLATILE(1 << 6),
    TRANSIENT(1 << 7),
    STRICTFP(1 << 11),
    DEFAULT(1L << 43);

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
