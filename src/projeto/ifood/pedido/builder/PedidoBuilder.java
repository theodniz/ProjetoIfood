package projeto.ifood.pedido.builder;

import projeto.ifood.pedido.item.ItemCusto;
import projeto.ifood.pedido.modelo.Pedido;

import java.util.ArrayList;
import java.util.List;

public class PedidoBuilder {
    // Atributos que serão passados para o Pedido
    private String id;
    private String clienteNome = "Não informado";
    private String restauranteNome = "Não informado";
    private String enderecoEntrega = "Não informado";
    private List<ItemCusto> itens = new ArrayList<>();

    // Construtor do Builder exige um ID
    public PedidoBuilder(String id) {
        this.id = id;
    }

    // Métodos para configurar o pedido
    public PedidoBuilder paraCliente(String clienteNome) {
        this.clienteNome = clienteNome;
        return this;
    }

    public PedidoBuilder noRestaurante(String restauranteNome) {
        this.restauranteNome = restauranteNome;
        return this;
    }

    public PedidoBuilder entregarEm(String endereco) {
        this.enderecoEntrega = endereco;
        return this;
    }

    public PedidoBuilder comItem(ItemCusto item) {
        this.itens.add(item);
        return this;
    }

    // Método final que constrói o Pedido
    public Pedido build() {
        if (itens.isEmpty()) {
            throw new IllegalStateException("Um pedido deve ter pelo menos um item.");
        }
        return new Pedido(this);
    }

    // Getters que a classe Pedido usará para se construir
    public String getId() { return id; }
    public String getClienteNome() { return clienteNome; }
    public String getRestauranteNome() { return restauranteNome; }
    public String getEnderecoEntrega() { return enderecoEntrega; }
    public List<ItemCusto> getItens() { return itens; }
}
