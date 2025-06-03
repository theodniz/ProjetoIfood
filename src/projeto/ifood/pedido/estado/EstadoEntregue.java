package projeto.ifood.pedido.estado;
import projeto.ifood.pedido.modelo.Pedido;

public class EstadoEntregue implements EstadoPedido {
    @Override
    public void avancar(Pedido pedido) {
        System.out.println("Pedido já foi entregue. Nenhuma ação adicional.");
    }

    @Override
    public void cancelar(Pedido pedido) {
        System.out.println("Não é possível cancelar um pedido que já foi entregue.");
    }

    @Override
    public String getNome() {
        return "Entregue";
    }
}
