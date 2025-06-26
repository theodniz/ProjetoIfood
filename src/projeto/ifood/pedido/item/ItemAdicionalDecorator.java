package projeto.ifood.pedido.item;

public abstract class ItemAdicionalDecorator implements ItemCusto {
    protected ItemCusto item;

    public ItemAdicionalDecorator(ItemCusto item) {
        this.item = item;
    }

    @Override
    public double getValor() {
        return item.getValor();
    }

    @Override
    public String getDescricao() {
        return item.getDescricao();
    }
}

