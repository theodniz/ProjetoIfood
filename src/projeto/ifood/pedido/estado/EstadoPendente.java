package projeto.ifood.pedido.estado;
import projeto.ifood.pedido.modelo.Pedido;

public class EstadoPendente implements EstadoPedido {
    @Override
    public void avancar(Pedido pedido) {
        System.out.println("Pedido pendente avançando para Confirmado.");
        pedido.setEstado(new EstadoConfirmado());
    }

    @Override
    public void cancelar(Pedido pedido) {
        System.out.println("Pedido pendente sendo cancelado.");
        pedido.setEstado(new EstadoCancelado());
    }

    @Override
    public String getNome() {
        return "Pendente";
    }
}
