package projeto.ifood.pedido.estado;
import projeto.ifood.pedido.modelo.Pedido;

public class EstadoEmPreparo implements EstadoPedido {
    @Override
    public void avancar(Pedido pedido) {
        System.out.println("Pedido em preparo avançando para Pronto para Entrega.");
        pedido.setEstado(new EstadoProntoParaEntrega());
    }

    @Override
    public void cancelar(Pedido pedido) {
        System.out.println("Não é possível cancelar um pedido que já está em preparo. Contate o suporte.");
        // Em um sistema real, poderia haver lógica mais complexa ou nenhuma mudança de estado.
    }

    @Override
    public String getNome() {
        return "Em Preparo";
    }
}
