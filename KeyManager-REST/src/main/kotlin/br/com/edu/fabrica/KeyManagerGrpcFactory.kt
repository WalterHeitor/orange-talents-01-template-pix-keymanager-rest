package br.com.edu.fabrica

import br.com.edu.KeyManagerCarregaGRPCServiceGrpc
import br.com.edu.KeyManagerListaGRPCServiceGrpc
import br.com.edu.KeyManagerRegistraGRPCServiceGrpc
import br.com.edu.KeyManagerRemoveGRPCServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton
@Factory
class KeyManagerGrpcFactory (@GrpcChannel("keyManager") val channel: ManagedChannel) {

    @Singleton
    fun registraChave() = KeyManagerRegistraGRPCServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun deletaChave() = KeyManagerRemoveGRPCServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun listaChave() = KeyManagerListaGRPCServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun carregaChave() = KeyManagerCarregaGRPCServiceGrpc.newBlockingStub(channel)
}