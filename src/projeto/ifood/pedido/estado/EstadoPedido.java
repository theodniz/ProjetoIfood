package projeto.ifood.pedido.estado;

import projeto.ifood.pedido.modelo.*;

public interface EstadoPedido {
    void avancar(Pedido pedido);
    void cancelar(Pedido pedido);
    String getNome(); // Para obter o nome do estado atual
}
