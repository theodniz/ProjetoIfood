package projeto.ifood.pedido.modelo;

import projeto.ifood.pedido.observador.ObservadorPedido;
import projeto.ifood.pedido.builder.PedidoBuilder;
import projeto.ifood.pedido.estado.EstadoConfirmado;
import projeto.ifood.pedido.estado.EstadoEntregue;
import projeto.ifood.pedido.estado.EstadoPendente;
import projeto.ifood.pedido.frete.FreteGratisStrategy;
import projeto.ifood.pedido.frete.FretePorKmStrategy;
import projeto.ifood.pedido.item.BordaRecheadaDecorator;
import projeto.ifood.pedido.item.ItemCusto;
import projeto.ifood.pedido.item.ItemPedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoCompletoTest {

    private PedidoBuilder pedidoBuilder;
    private ObservadorPedido mockObservadorCliente;
    private ObservadorPedido mockObservadorRestaurante;

    @BeforeEach
    void setUp() {
        // Prepara um builder base e mocks para cada teste
        pedidoBuilder = Pedido.builder("T001")
                .paraCliente("Cliente Teste")
                .noRestaurante("Restaurante Teste");

        mockObservadorCliente = mock(ObservadorPedido.class);
        mockObservadorRestaurante = mock(ObservadorPedido.class);
    }

    @Test
    @DisplayName("Deve construir um Pedido corretamente com o Builder")
    void testeBuilder() {
        // (DECORATOR) Cria itens, um deles decorado
        ItemCusto pizza = new BordaRecheadaDecorator(new ItemPedido("Pizza Teste", 50.00)); // 50 + 8 = 58
        ItemCusto refri = new ItemPedido("Refri Teste", 7.00);

        // (BUILDER) Constrói o pedido
        Pedido pedido = pedidoBuilder
                .comItem(pizza)
                .comItem(refri)
                .build();

        assertNotNull(pedido, "O pedido não deveria ser nulo.");
        assertEquals("T001", pedido.getId());
        assertEquals(2, pedido.getItens().size(), "O pedido deveria ter 2 itens.");
        assertEquals(65.00, pedido.getValorTotalItens(), "O valor total dos itens (com adicional) deve ser 65.00.");
    }

    @Test
    @DisplayName("Deve aplicar a Strategy de Frete Grátis corretamente")
    void testeStrategyFreteGratis() {
        Pedido pedido = pedidoBuilder.comItem(new ItemPedido("Item Caro", 60.00)).build();

        // (STRATEGY) Aplica a estratégia de frete grátis
        pedido.setEstrategiaDeFrete(new FreteGratisStrategy());

        assertEquals(0.0, pedido.calcularFrete(10.0), "O frete deveria ser 0.0 com a estratégia de frete grátis.");
    }

    @Test
    @DisplayName("Deve aplicar a Strategy de Frete por KM corretamente")
    void testeStrategyFretePorKm() {
        Pedido pedido = pedidoBuilder.comItem(new ItemPedido("Item Barato", 20.00)).build();

        // (STRATEGY) Aplica a estratégia de frete por km
        pedido.setEstrategiaDeFrete(new FretePorKmStrategy()); // Custo de 2.5 por km

        assertEquals(25.0, pedido.calcularFrete(10.0), "O frete deveria ser 25.0 para 10km.");
    }

    @Test
    @DisplayName("Deve transicionar de Pendente para Confirmado e Notificar Observadores")
    void testeStateEObserver() {
        Pedido pedido = pedidoBuilder.comItem(new ItemPedido("Item Teste", 30.00)).build();
        pedido.adicionarObservador(mockObservadorCliente);
        pedido.adicionarObservador(mockObservadorRestaurante);

        // (STATE) Garante que o estado inicial é Pendente
        assertEquals("Pendente", pedido.getStatusNome(), "O estado inicial deveria ser Pendente.");

        // Notificação inicial do estado pendente
        pedido.notificarObservadores();
        verify(mockObservadorCliente, times(1)).atualizar(pedido);
        verify(mockObservadorRestaurante, times(1)).atualizar(pedido);


        // (STATE) Avança o status
        pedido.avancarStatus();

        // (STATE) Verifica a transição de estado
        assertEquals("Confirmado pelo Restaurante", pedido.getStatusNome(), "O estado deveria ter mudado para Confirmado.");

        // (OBSERVER) Verifica se os observadores foram notificados da mudança
        // Total de 2 notificações: 1 do estado Pendente, 1 do estado Confirmado
        verify(mockObservadorCliente, times(2)).atualizar(pedido);
        verify(mockObservadorRestaurante, times(2)).atualizar(pedido);

        // Captura o argumento para verificar o estado no momento da notificação
        ArgumentCaptor<Pedido> pedidoCaptor = ArgumentCaptor.forClass(Pedido.class);
        verify(mockObservadorCliente, atLeast(1)).atualizar(pedidoCaptor.capture());
        assertEquals("Confirmado pelo Restaurante", pedidoCaptor.getValue().getStatusNome(), "A notificação deve conter o estado 'Confirmado'.");
    }

    @Test
    @DisplayName("Deve percorrer o fluxo de estados completo e notificar a cada passo")
    void testeFluxoCompletoStateObserver() {
        Pedido pedido = pedidoBuilder.comItem(new ItemPedido("Item Fluxo", 40.00)).build();
        pedido.adicionarObservador(mockObservadorCliente);

        pedido.notificarObservadores(); // 1. Notificação do estado Pendente

        pedido.avancarStatus(); // 2. Notificação do estado Confirmado
        pedido.avancarStatus(); // 3. Notificação do estado Em Preparo
        pedido.avancarStatus(); // 4. Notificação do estado Pronto para Entrega
        pedido.avancarStatus(); // 5. Notificação do estado Em Trânsito
        pedido.avancarStatus(); // 6. Notificação do estado Entregue

        assertEquals("Entregue", pedido.getStatusNome(), "O estado final do pedido deveria ser 'Entregue'.");

        // Verifica se o observador foi notificado em todas as 6 etapas do ciclo de vida
        verify(mockObservadorCliente, times(6)).atualizar(pedido);
    }
}

