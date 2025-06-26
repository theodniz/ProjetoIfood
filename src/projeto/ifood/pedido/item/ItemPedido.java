package projeto.ifood.pedido.item;

public class ItemPedido implements ItemCusto {
    private String nome;
    private double valor;

    public ItemPedido(String nome, double valor) {
        this.nome = nome;
        this.valor = valor;
    }

    @Override
    public double getValor() {
        return this.valor;
    }

    @Override
    public String getDescricao() {
        return this.nome;
    }
}

