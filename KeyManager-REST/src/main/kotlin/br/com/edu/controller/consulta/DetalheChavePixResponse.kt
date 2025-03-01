package br.com.edu.controller.consulta

import br.com.edu.CarregaChavePixResponse
import br.com.edu.TipoDeConta
import io.micronaut.core.annotation.Introspected
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
@Introspected
class DetalheChavePixResponse(chaveResponse: CarregaChavePixResponse) {
    val pixId = chaveResponse.pixId
    val tipo = chaveResponse.chave.tipo
    val chave = chaveResponse.chave.chave

    val criadaEm: LocalDateTime = chaveResponse.chave.criadaEm.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
    }

    val tipoConta: String = when (chaveResponse.chave.conta.tipo) {
        TipoDeConta.CONTA_CORRENTE -> "CONTA_CORRENTE"
        TipoDeConta.CONTA_POUPANCA -> "CONTA_POUPANCA"
        else -> "NAO_RECONHECIDA"
    }
    val conta: Map<String, String> = mapOf(Pair("tipo", tipoConta),
    Pair("instituicao", chaveResponse.chave.conta.instituicao),
    Pair("nomeDoTitular", chaveResponse.chave.conta.nomeDoTitular),
    Pair("cpfDoTitular", chaveResponse.chave.conta.cpfDoTitular),
    Pair("agencia", chaveResponse.chave.conta.agencia),
    Pair("numeroDaConta", chaveResponse.chave.conta.numeroDaConta))
}