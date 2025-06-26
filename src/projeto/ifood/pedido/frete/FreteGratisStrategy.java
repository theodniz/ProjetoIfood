package projeto.ifood.pedido.frete;

public class FreteGratisStrategy implements CalculoFreteStrategy {
    @Override
    public double calcular(double distanciaKm) {
        System.out.println("Promoção de Frete Grátis aplicada!");
        return 0.0;
    }
}
