package com.ada.pedidocompra.infraestrutura.configuracao.validacao;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {

    String message() default "Email já cadastrado.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
