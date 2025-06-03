package projeto.ifood.pedido.estado;
import projeto.ifood.pedido.modelo.Pedido;
public class EstadoEmTransito implements EstadoPedido {
    @Override
    public void avancar(Pedido pedido) {
        System.out.println("Pedido em trânsito avançando para Entregue.");
        pedido.setEstado(new EstadoEntregue());
    }

    @Override
    public void cancelar(Pedido pedido) {
        System.out.println("Não é possível cancelar um pedido em trânsito.");
    }

    @Override
    public String getNome() {
        return "Em Trânsito";
    }
}
