package projeto.ifood.pedido.estado;
import projeto.ifood.pedido.modelo.Pedido;

public class EstadoCancelado implements EstadoPedido {
    @Override
    public void avancar(Pedido pedido) {
        System.out.println("Pedido cancelado. Não pode avançar.");
    }

    @Override
    public void cancelar(Pedido pedido) {
        System.out.println("Pedido já está cancelado.");
    }

    @Override
    public String getNome() {
        return "Cancelado";
    }
}
