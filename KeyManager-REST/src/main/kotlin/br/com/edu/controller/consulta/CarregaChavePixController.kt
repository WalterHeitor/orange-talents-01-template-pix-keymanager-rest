package br.com.edu.controller.consulta

import br.com.edu.CarregaChavePixRequest
import br.com.edu.KeyManagerCarregaGRPCServiceGrpc
import br.com.edu.KeyManagerListaGRPCServiceGrpc
import br.com.edu.ListaChavePixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.util.*
import javax.inject.Inject


@Validated
@Controller("/api/v1/clientes/{clienteId}")
class CarregaChavePixController (
    @Inject val carregaChavePixClient: KeyManagerCarregaGRPCServiceGrpc.KeyManagerCarregaGRPCServiceBlockingStub,
    @Inject val listaChavePixClient: KeyManagerListaGRPCServiceGrpc.KeyManagerListaGRPCServiceBlockingStub
        ) {
    private val LOGGER = LoggerFactory.getLogger(this::class.java)
    @Get("/pix/{pixId}")
    fun carrega(clienteId: UUID, pixId: String) : HttpResponse<Any>{
        LOGGER.info("[$clienteId] carregando chave pix por Id ${pixId}")
        val chaveResponse = carregaChavePixClient.carrega(CarregaChavePixRequest.newBuilder()
            .setPixId(CarregaChavePixRequest.FiltroPorPixId
                .newBuilder()
                .setClienteId(clienteId.toString())
                .setPixId(pixId)
                .build())
            .build())
        return HttpResponse.ok(DetalheChavePixResponse(chaveResponse))
    }
    @Get("/pix/")
    fun lista(clienteId: UUID) : HttpResponse<Any>{
        LOGGER.info("listando Chave Pix com clienteId $clienteId")
        val pix = listaChavePixClient.lista(ListaChavePixRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .build())
        val chaves =  pix.chavesList.map { ChavePixResponse(it) }
        return HttpResponse.ok(chaves)
    }
}