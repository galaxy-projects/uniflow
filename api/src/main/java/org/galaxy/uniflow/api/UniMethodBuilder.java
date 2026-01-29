package org.galaxy.uniflow.api;

import org.galaxy.uniflow.api.expressions.UniExpression;
import org.galaxy.uniflow.api.factories.UniTypeFactory;
import org.galaxy.uniflow.api.interfaces.UniAnnotationValueSupplier;
import org.galaxy.uniflow.api.interfaces.UniBlockSupplier;
import org.galaxy.uniflow.api.statements.UniParameter;
import org.galaxy.uniflow.api.statements.UniVariable;
import org.galaxy.uniflow.api.types.UniType;
import org.galaxy.uniflow.api.types.UniTypeParameter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class UniMethodBuilder {

    protected final UniClass owner;
    protected final String name;
    protected final boolean constructor;

    protected UniType returnType;
    protected UniModifiers modifiers;
    protected UniVariable receiveParameter;
    protected final List<UniTypeParameter> typeParameters;
    protected final List<UniParameter> parameters;
    protected final List<UniExpression> thrown;
    protected UniBlockSupplier body;
    protected UniAnnotationValueSupplier defaultValue;

    public UniMethodBuilder(UniClass owner, String name, boolean constructor) {
        this.owner = owner;
        this.name = name;
        this.constructor = constructor;
        typeParameters = new ArrayList<>();
        parameters = new ArrayList<>();
        thrown = new ArrayList<>();
    }

    @Contract(value = "_ -> this", mutates = "this")
    public UniMethodBuilder withReturnType(@NotNull UniType returnType) {
        this.returnType = returnType;
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public UniMethodBuilder withReturnType(@NotNull Class<?> returnType) {
        UniTypeFactory typeFactory = Uniflow.getInstance().getTypeFactory();

        this.returnType = typeFactory.createType(returnType);
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public UniMethodBuilder withModifiers(@NotNull UniModifiers modifiers) {
        this.modifiers = modifiers;
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public UniMethodBuilder withTypeParameter(@NotNull UniTypeParameter typeParameter) {
        typeParameters.add(typeParameter);
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public UniMethodBuilder withReceiveParameter(@NotNull UniVariable receiveParameter) {
        this.receiveParameter = receiveParameter;
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public UniMethodBuilder withParameter(@NotNull UniParameter parameter) {
        parameters.add(parameter);
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public UniMethodBuilder withThrown(@NotNull UniExpression thrown) {
        this.thrown.add(thrown);
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public UniMethodBuilder withBody(@NotNull UniBlockSupplier body) {
        this.body = body;
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public UniMethodBuilder withDefaultValue(@NotNull UniAnnotationValueSupplier defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    protected void checkArgs() {
        Objects.requireNonNull(owner, "Owner cannot be null");
        Objects.requireNonNull(name, "Name cannot be null");
        if (constructor && returnType != null)
            throw new IllegalArgumentException("Constructor cannot have return type");
        else if (!constructor && returnType == null)
            throw new IllegalArgumentException("Return type cannot be null");
        Objects.requireNonNull(modifiers, "Modifiers cannot be null");
        Objects.requireNonNull(typeParameters, "Type parameters cannot be null");
        Objects.requireNonNull(parameters, "Parameters cannot be null");
        Objects.requireNonNull(thrown, "Thrown cannot be null");

        if (body == null && defaultValue == null)
            throw new IllegalArgumentException("Both body and defaultValue cannot be null");
    }

    @Contract(value = "-> new", pure = true)
    public abstract @NotNull UniMethod build();

}
