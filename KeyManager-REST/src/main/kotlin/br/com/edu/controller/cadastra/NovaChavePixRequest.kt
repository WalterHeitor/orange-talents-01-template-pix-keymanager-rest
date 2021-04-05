package br.com.edu.controller.cadastra

import br.com.edu.RegistraChavePixRequest
import br.com.edu.TipoDeChave
import br.com.edu.TipoDeConta
import br.com.edu.vadidate.ValidPixKey
import io.micronaut.core.annotation.Introspected
import io.micronaut.validation.validator.constraints.EmailValidator
import java.util.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator
@ValidPixKey
@Introspected
class NovaChavePixRequest(
    @field:NotNull val tipoDeConta: TipoDeContaRequest?,
    @field:Size(max = 77) val chave: String?,
    @field:NotNull val tipoDeChave: TipoDeChaveRequest?,
) {
    fun paraModeloGrpc(clienteId: UUID): RegistraChavePixRequest? {
        return RegistraChavePixRequest
            .newBuilder()
            .setClienteId(clienteId.toString())
            .setTipoDeConta(tipoDeConta?.atributoGrpc ?: TipoDeConta.UNKNOWN_TIPO_CONTA)
            .setTipoDeChave(tipoDeChave?.atributoGrpc ?: TipoDeChave.UNKNOWN_TIPO_CHAVE)

//            .setTipoDeConta(TipoDeConta.valueOf(tipoDeConta!!.name))
//            .setTipoDeChave(TipoDeChave.valueOf(tipoDeChave!!.name))

            .setChave(chave ?: "")


            .build()

//            .setChave(chave :? "")

    }

}

enum class TipoDeChaveRequest(val atributoGrpc: TipoDeChave) {
    CPF(TipoDeChave.CPF) {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank()) {
                return false
            }
            return CPFValidator().run {
                initialize(null)
                isValid(chave, null)
            }
        }
    },
    CELULAR(TipoDeChave.CELULAR) {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank()) {
                return false
            }
            return chave.matches("~\\+[1-9][0-9]\\d{1,14}\$".toRegex())
        }

    },
    EMAIL(TipoDeChave.EMAIL) {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank()) {
                return false
            }
            return EmailValidator().run {
                initialize(null)
                isValid(chave, null)
            }
        }
    },
    ALEATORIA(TipoDeChave.ALEATORIA) {
        override fun valida(chave: String?) = chave.isNullOrBlank() // nao deve ser preenchida

    };

    abstract fun valida(chave: String?): Boolean
}

enum class TipoDeContaRequest(val atributoGrpc: TipoDeConta) {
    CONTA_CORRENTE(TipoDeConta.CONTA_CORRENTE),
    CONTA_POUPANCA(TipoDeConta.CONTA_POUPANCA)
}