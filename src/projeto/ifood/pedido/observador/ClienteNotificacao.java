package projeto.ifood.pedido.observador;
import projeto.ifood.pedido.modelo.Pedido;

public class ClienteNotificacao implements ObservadorPedido {
    private String nomeCliente;

    public ClienteNotificacao(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    @Override
    public void atualizar(Pedido pedido) {
        System.out.println("[CLIENTE: " + nomeCliente + "] Olá, " + pedido.getClienteNome() +
                "! Status do seu pedido #" + pedido.getId() +
                " no restaurante " + pedido.getRestauranteNome() +
                " atualizado para: " + pedido.getStatusNome());
    }
}
