package projeto.ifood.pedido.frete;

public class FretePorKmStrategy implements CalculoFreteStrategy {
    private static final double VALOR_POR_KM = 2.5;

    @Override
    public double calcular(double distanciaKm) {
        return distanciaKm * VALOR_POR_KM;
    }
}
