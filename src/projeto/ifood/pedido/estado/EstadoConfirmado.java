package projeto.ifood.pedido.estado;
import projeto.ifood.pedido.modelo.Pedido;

public class EstadoConfirmado implements EstadoPedido {
    @Override
    public void avancar(Pedido pedido) {
        System.out.println("Pedido confirmado avançando para Em Preparo.");
        pedido.setEstado(new EstadoEmPreparo());
    }

    @Override
    public void cancelar(Pedido pedido) {
        System.out.println("Pedido confirmado sendo cancelado.");
        pedido.setEstado(new EstadoCancelado());
    }

    @Override
    public String getNome() {
        return "Confirmado pelo Restaurante";
    }
}
