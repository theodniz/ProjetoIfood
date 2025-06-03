package projeto.ifood.pedido.app;
import projeto.ifood.pedido.modelo.Pedido;
import projeto.ifood.pedido.observador.ClienteNotificacao;
import projeto.ifood.pedido.observador.RestauranteNotificacao;

public class DemoIfood {
    public static void main(String[] args) {
        System.out.println("--- Iniciando Simulação iFood ---");

        // Criando observadores
        ClienteNotificacao clienteTheo = new ClienteNotificacao("Theo");
        RestauranteNotificacao restauranteJavaLanches = new RestauranteNotificacao("Java Lanches");

        // Cliente faz um pedido
        System.out.println("\n--- Cliente Theo faz um pedido no Java Lanches ---");
        Pedido pedido123 = new Pedido("123", "Theo", "Java Lanches");

        // Adicionando observadores ao pedido
        pedido123.adicionarObservador(clienteTheo);
        pedido123.adicionarObservador(restauranteJavaLanches);

        // Restaurante visualiza o pedido pendente e o confirma (o estado inicial já notifica)
        // (A notificação do estado "Pendente" ocorre na criação do Pedido, se setEstado for chamado no construtor ou se notificarmos após adicionar observadores)
        // Para garantir que a primeira notificação "Pendente" seja enviada aos observadores já registrados:
        pedido123.notificarObservadores(); // Notifica o estado Pendente inicial aos observadores registrados

        System.out.println("\n--- Restaurante confirma o pedido ---");
        pedido123.avancarStatus(); // Pendente -> Confirmado

        System.out.println("\n--- Restaurante inicia o preparo ---");
        pedido123.avancarStatus(); // Confirmado -> Em Preparo

        System.out.println("\n--- Pedido fica pronto para entrega ---");
        pedido123.avancarStatus(); // Em Preparo -> Pronto para Entrega

        System.out.println("\n--- Entregador retira o pedido ---");
        pedido123.avancarStatus(); // Pronto para Entrega -> Em Trânsito

        System.out.println("\n--- Pedido é entregue ---");
        pedido123.avancarStatus(); // Em Trânsito -> Entregue

        System.out.println("\n--- Tentando avançar status de pedido entregue ---");
        pedido123.avancarStatus(); // Não deve mudar

        System.out.println("\n\n--- Simulação de um pedido cancelado ---");
        Pedido pedido456 = new Pedido("456", "Ana", "Pizza Byte");
        ClienteNotificacao clienteAna = new ClienteNotificacao("Ana");
        RestauranteNotificacao restaurantePizzaByte = new RestauranteNotificacao("Pizza Byte");

        pedido456.adicionarObservador(clienteAna);
        pedido456.adicionarObservador(restaurantePizzaByte);
        pedido456.notificarObservadores(); // Notifica estado Pendente

        System.out.println("\n--- Cliente Ana cancela o pedido enquanto Pendente ---");
        pedido456.cancelarPedido(); // Pendente -> Cancelado

        System.out.println("\n--- Tentando avançar status de pedido cancelado ---");
        pedido456.avancarStatus(); // Não deve mudar

        System.out.println("\n--- Fim da Simulação iFood ---");
    }
}
