package br.com.edu.controller.remove

import br.com.edu.KeyManagerRemoveGRPCServiceGrpc
import br.com.edu.RemoveChavePixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.util.*
import javax.inject.Inject

@Validated
@Controller("/api/v1/clientes/{clienteId}")
class RemoveChavePixController(@Inject private  val removeChavePixClient:
                               KeyManagerRemoveGRPCServiceGrpc.KeyManagerRemoveGRPCServiceBlockingStub) {
    private val LOGGER = LoggerFactory.getLogger(this::class.java)
    @Delete("/pix/{pixId}")
    fun remove(clienteId: UUID, pixId: String) : HttpResponse<Any>{
        LOGGER.info("[$clienteId] removendo chave pix $pixId")
        removeChavePixClient.remove(RemoveChavePixRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .setPixId(pixId)
            .build())
        return HttpResponse.ok()
    }
}