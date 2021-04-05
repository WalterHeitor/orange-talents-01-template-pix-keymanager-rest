package br.com.edu.vadidate

import br.com.edu.controller.cadastra.NovaChavePixRequest
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import javax.inject.Singleton
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.KClass

@MustBeDocumented
@Target(CLASS, TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = [ValidPixKeyValidator::class])
annotation class ValidPixKey(
    val message: String = "Chave invalida (\${validatedValue.tipoDeChave})",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
){

}
@Singleton                  // do Micronaut contraintValidator
class ValidPixKeyValidator : ConstraintValidator<ValidPixKey, NovaChavePixRequest> {

    override fun isValid(
        value: NovaChavePixRequest?,
        annotationMetadata: AnnotationValue<ValidPixKey>,
        context: io.micronaut.validation.validator.constraints.ConstraintValidatorContext,
    ): Boolean {
        if (value?.tipoDeChave == null) {
            return false
        }
        return value.tipoDeChave.valida(value.chave)

    }

}
