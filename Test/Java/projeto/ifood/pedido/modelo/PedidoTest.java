package projeto.ifood.pedido.modelo;

import projeto.ifood.pedido.estado.*;
import projeto.ifood.pedido.observador.ObservadorPedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.reset;
// etc.
import static org.junit.jupiter.api.Assertions.*;


class PedidoTest {
    private Pedido pedido;
    private ObservadorPedido mockObservadorCliente;
    private ObservadorPedido mockObservadorRestaurante;

    @BeforeEach
    void setUp() {
        pedido = new Pedido("T001", "Cliente Teste", "Restaurante Teste");
        mockObservadorCliente = mock(ObservadorPedido.class); // Cria um mock
        mockObservadorRestaurante = mock(ObservadorPedido.class);

        pedido.adicionarObservador(mockObservadorCliente);
        pedido.adicionarObservador(mockObservadorRestaurante);
    }

    @Test
    void pedidoDeveIniciarNoEstadoPendente() {
        assertTrue(pedido.getEstadoAtual() instanceof EstadoPendente, "Pedido deveria iniciar como Pendente");
        assertEquals("Pendente", pedido.getStatusNome());
    }

    @Test
    void avancarDePendenteDeveIrParaConfirmadoENotificar() {
        pedido.avancarStatus(); // Pendente -> Confirmado
        assertTrue(pedido.getEstadoAtual() instanceof EstadoConfirmado, "Pedido deveria estar Confirmado");
        assertEquals("Confirmado pelo Restaurante", pedido.getStatusNome());
        verify(mockObservadorCliente, times(1)).atualizar(pedido); // Verifica se o cliente foi notificado
        verify(mockObservadorRestaurante, times(1)).atualizar(pedido); // Verifica se o restaurante foi notificado
    }

    @Test
    void avancarDeConfirmadoDeveIrParaEmPreparoENotificar() {
        pedido.setEstado(new EstadoConfirmado()); 
        reset(mockObservadorCliente, mockObservadorRestaurante);

        pedido.avancarStatus(); // Confirmado -> Em Preparo
        assertTrue(pedido.getEstadoAtual() instanceof EstadoEmPreparo, "Pedido deveria estar Em Preparo");
        assertEquals("Em Preparo", pedido.getStatusNome());
        verify(mockObservadorCliente, times(1)).atualizar(pedido);
        verify(mockObservadorRestaurante, times(1)).atualizar(pedido);
    }

    @Test
    void cancelarPedidoPendenteDeveIrParaCanceladoENotificar() {
        assertTrue(pedido.getEstadoAtual() instanceof EstadoPendente);
        reset(mockObservadorCliente, mockObservadorRestaurante);

        pedido.cancelarPedido(); // Pendente -> Cancelado
        assertTrue(pedido.getEstadoAtual() instanceof EstadoCancelado, "Pedido deveria estar Cancelado");
        assertEquals("Cancelado", pedido.getStatusNome());
        verify(mockObservadorCliente, times(1)).atualizar(pedido);
        verify(mockObservadorRestaurante, times(1)).atualizar(pedido);
    }


}
