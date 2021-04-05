package br.com.edu.controller.consulta

import br.com.edu.ListaChavePixResponse
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

class ChavePixResponse(chavePixResponse: ListaChavePixResponse.ChavePix) {
    val id = chavePixResponse.pixId
    val chave = chavePixResponse.chave
    val tipo = chavePixResponse.tipo
    val tipoDeConta = chavePixResponse.tipoDeconta
    val criadaEm = chavePixResponse.criadaEm.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()),ZoneOffset.UTC)
    }
}
