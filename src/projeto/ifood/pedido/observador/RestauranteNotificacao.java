package projeto.ifood.pedido.observador;
import projeto.ifood.pedido.modelo.Pedido;
public class RestauranteNotificacao implements ObservadorPedido {
    private String nomeRestaurante;

    public RestauranteNotificacao(String nomeRestaurante) {
        this.nomeRestaurante = nomeRestaurante;
    }

    @Override
    public void atualizar(Pedido pedido) {

        System.out.println("[RESTAURANTE: " + nomeRestaurante + "] Atenção! Pedido #" + pedido.getId() +
                " para o cliente " + pedido.getClienteNome() +
                " mudou para o status: " + pedido.getStatusNome());

        if ("Pendente".equals(pedido.getStatusNome())) {
            System.out.println("  -> Novo pedido recebido. Por favor, confirme ou cancele.");
        } else if ("Cancelado".equals(pedido.getStatusNome())) {
            System.out.println("  -> Atenção: Pedido #" + pedido.getId() + " foi cancelado pelo sistema/cliente.");
        }
    }
}
