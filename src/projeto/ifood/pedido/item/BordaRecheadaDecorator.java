package projeto.ifood.pedido.item;

public class BordaRecheadaDecorator extends ItemAdicionalDecorator {
    private static final double CUSTO_BORDA = 8.00;

    public BordaRecheadaDecorator(ItemCusto item) {
        super(item);
    }

    @Override
    public double getValor() {
        return super.getValor() + CUSTO_BORDA;
    }

    @Override
    public String getDescricao() {
        return super.getDescricao() + ", com borda recheada";
    }
}
