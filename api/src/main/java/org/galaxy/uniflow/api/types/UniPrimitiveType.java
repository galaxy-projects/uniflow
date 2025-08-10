package org.galaxy.uniflow.api.types;

import org.galaxy.uniflow.api.UniElement;
import org.jetbrains.annotations.NotNull;

import javax.lang.model.type.NoType;

public interface UniPrimitiveType extends UniElement {

    void setKind(@NotNull TypeKind kind);

    @NotNull TypeKind getKind();

    enum TypeKind {
        BOOLEAN,

        /**
         * The primitive type {@code byte}.
         */
        BYTE,

        /**
         * The primitive type {@code short}.
         */
        SHORT,

        /**
         * The primitive type {@code int}.
         */
        INT,

        /**
         * The primitive type {@code long}.
         */
        LONG,

        /**
         * The primitive type {@code char}.
         */
        CHAR,

        /**
         * The primitive type {@code float}.
         */
        FLOAT,

        /**
         * The primitive type {@code double}.
         */
        DOUBLE,

        /**
         * The pseudo-type corresponding to the keyword {@code void}.
         *
         * @see NoType
         */
        VOID,

        /**
         * A pseudo-type used where no actual type is appropriate.
         *
         * @see NoType
         */
        NONE,

        /**
         * The null type.
         */
        NULL,

        /**
         * An array type.
         */
        ARRAY,

        /**
         * A class or interface type.
         */
        DECLARED,

        /**
         * A class or interface type that could not be resolved.
         */
        ERROR,

        /**
         * A type variable.
         */
        TYPEVAR,

        /**
         * A wildcard type argument.
         */
        WILDCARD,

        /**
         * A pseudo-type corresponding to a package element.
         *
         * @see NoType
         */
        PACKAGE,

        /**
         * A method, constructor, or initializer.
         */
        EXECUTABLE,

        /**
         * A union type.
         *
         * @since 1.7
         */
        UNION,

        /**
         * An intersection type.
         *
         * @since 1.8
         */
        INTERSECTION,

        /**
         * A pseudo-type corresponding to a module element.
         *
         * @see NoType
         * @since 9
         */
        MODULE;

        public boolean isPrimitive() {
            switch (this) {
                case BOOLEAN:
                case BYTE:
                case SHORT:
                case INT:
                case LONG:
                case CHAR:
                case FLOAT:
                case DOUBLE:
                    return true;

                default:
                    return false;
            }
        }
    }
}
