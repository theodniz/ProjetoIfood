package projeto.ifood.app;

import projeto.ifood.pedido.frete.*;
import projeto.ifood.pedido.item.*;
import projeto.ifood.pedido.modelo.Pedido;
import projeto.ifood.pedido.observador.ClienteNotificacao;
import projeto.ifood.pedido.observador.RestauranteNotificacao;

public class DemoIfood {
    public static void main(String[] args) {
        System.out.println("--- Iniciando Simulação iFood com 5 Padrões de Projeto ---");

        // --- Cenário 1: Pedido de Pizza com Borda Recheada ---
        System.out.println("\n--- Cenário 1: Pedido de Pizza ---");

        // 1. (Decorator) Criando os itens e adicionando opcionais
        System.out.println("1. Montando itens com Decorator...");
        ItemCusto pizzaCalabresa = new ItemPedido("Pizza de Calabresa Grande", 45.00);
        pizzaCalabresa = new BordaRecheadaDecorator(pizzaCalabresa); // Decorando com borda!

        ItemCusto refrigerante = new ItemPedido("Refrigerante 2L", 8.00);

        // 2. (Builder) Montando o pedido de forma fluente e legível
        System.out.println("\n2. Construindo o pedido com Builder...");
        Pedido pedidoPizza = Pedido.builder("P001")
                .paraCliente("Theo")
                .noRestaurante("Pizzaria Dev")
                .entregarEm("Rua da Programação, 101")
                .comItem(pizzaCalabresa)
                .comItem(refrigerante)
                .build();

        // 3. (Strategy) Definindo a política de frete dinamicamente
        System.out.println("\n3. Calculando o frete com Strategy...");
        double distanciaEmKm = 3.5;
        CalculoFreteStrategy estrategiaFrete;

        if (pedidoPizza.getValorTotalItens() > 50.00) {
            estrategiaFrete = new FreteGratisStrategy();
        } else {
            estrategiaFrete = new FretePorKmStrategy();
        }
        pedidoPizza.setEstrategiaDeFrete(estrategiaFrete);

        // Calculando valores finais
        double valorFrete = pedidoPizza.calcularFrete(distanciaEmKm);
        double valorTotal = pedidoPizza.getValorTotalItens() + valorFrete;

        System.out.println("\n--- Resumo do Pedido " + pedidoPizza.getId() + " ---");
        System.out.println("Itens do Pedido:");
        pedidoPizza.getItens().forEach(item -> System.out.println(" - " + item.getDescricao() + ": R$" + String.format("%.2f", item.getValor())));
        System.out.println("Valor dos Itens: R$" + String.format("%.2f", pedidoPizza.getValorTotalItens()));
        System.out.println("Valor do Frete: R$" + String.format("%.2f", valorFrete));
        System.out.println("VALOR TOTAL DO PEDIDO: R$" + String.format("%.2f", valorTotal));
        System.out.println("---------------------------------");


        // 4. (Observer) Configurando quem será notificado sobre as mudanças
        System.out.println("\n4. Configurando notificações com Observer...");
        ClienteNotificacao obsCliente = new ClienteNotificacao("Theo");
        RestauranteNotificacao obsRestaurante = new RestauranteNotificacao("Pizzaria Dev");
        pedidoPizza.adicionarObservador(obsCliente);
        pedidoPizza.adicionarObservador(obsRestaurante);

        // 5. (State) Gerenciando o ciclo de vida do pedido
        System.out.println("\n5. Acompanhando o ciclo de vida com State...");
        // O estado inicial "Pendente" é notificado agora:
        pedidoPizza.notificarObservadores();

        pedidoPizza.avancarStatus(); // -> Confirmado
        pedidoPizza.avancarStatus(); // -> Em Preparo
        pedidoPizza.avancarStatus(); // -> Pronto para Entrega
        pedidoPizza.avancarStatus(); // -> Em Trânsito
        pedidoPizza.avancarStatus(); // -> Entregue

        System.out.println("\n--- Fim da Simulação ---");
    }
}
