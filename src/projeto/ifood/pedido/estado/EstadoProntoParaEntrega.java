package projeto.ifood.pedido.estado;
import projeto.ifood.pedido.modelo.Pedido;

public class EstadoProntoParaEntrega implements EstadoPedido {
    @Override
    public void avancar(Pedido pedido) {
        System.out.println("Pedido pronto para entrega avançando para Em Trânsito.");
        pedido.setEstado(new EstadoEmTransito());
    }

    @Override
    public void cancelar(Pedido pedido) {
        System.out.println("Não é possível cancelar um pedido pronto para entrega.");
    }

    @Override
    public String getNome() {
        return "Pronto para Entrega";
    }
}
