package com.ivson.modelagemconceitual.services.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Classe padrao para uma anotação personalizada
 * @author Santo
 *
 */
@Constraint(validatedBy = ClienteUpdateValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClienteUpdate {

	String message() default "Erro de Validação";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {}; 
}
