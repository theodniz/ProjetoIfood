package projeto.ifood.pedido.modelo;

import projeto.ifood.pedido.estado.EstadoPedido;
import projeto.ifood.pedido.estado.EstadoPendente;
import projeto.ifood.pedido.observador.ObservadorPedido;
import projeto.ifood.pedido.builder.PedidoBuilder;
import projeto.ifood.pedido.item.ItemCusto;
import projeto.ifood.pedido.frete.CalculoFreteStrategy;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    // Atributos principais
    private String id;
    private String clienteNome;
    private String restauranteNome;
    private String enderecoEntrega;
    private List<ItemCusto> itens = new ArrayList<>();

    // Atributos dos padrões
    private EstadoPedido estadoAtual;
    private List<ObservadorPedido> observadores = new ArrayList<>();
    private CalculoFreteStrategy estrategiaDeFrete;

    // Construtor PRIVADO que só o Builder pode chamar
    public Pedido(PedidoBuilder builder) {
        this.id = builder.getId();
        this.clienteNome = builder.getClienteNome();
        this.restauranteNome = builder.getRestauranteNome();
        this.enderecoEntrega = builder.getEnderecoEntrega();
        this.itens.addAll(builder.getItens());
        this.estadoAtual = new EstadoPendente(); // Define o estado inicial
    }

    // Ponto de entrada estático para o Builder
    public static PedidoBuilder builder(String id) {
        return new PedidoBuilder(id);
    }

    // Métodos do Padrão State
    public void setEstado(EstadoPedido novoEstado) {
        this.estadoAtual = novoEstado;
        System.out.println("\n[LOG] Pedido " + id + " mudou para o estado: " + estadoAtual.getNome());
        notificarObservadores();
    }

    public void avancarStatus() {
        estadoAtual.avancar(this);
    }

    public void cancelarPedido() {
        estadoAtual.cancelar(this);
    }

    // Métodos do Padrão Observer
    public void adicionarObservador(ObservadorPedido observador) {
        this.observadores.add(observador);
    }

    public void removerObservador(ObservadorPedido observador) {
        this.observadores.remove(observador);
    }

    public void notificarObservadores() {
        for (ObservadorPedido observador : observadores) {
            observador.atualizar(this);
        }
    }

    // Métodos do Padrão Strategy
    public void setEstrategiaDeFrete(CalculoFreteStrategy estrategiaDeFrete) {
        this.estrategiaDeFrete = estrategiaDeFrete;
    }

    public double calcularFrete(double distanciaKm) {
        if (estrategiaDeFrete == null) {
            throw new IllegalStateException("Estratégia de frete não foi definida.");
        }
        return estrategiaDeFrete.calcular(distanciaKm);
    }

    // Getters para expor informações de forma segura
    public String getId() { return id; }
    public String getClienteNome() { return clienteNome; }
    public String getRestauranteNome() { return restauranteNome; }
    public String getStatusNome() { return estadoAtual.getNome(); }
    public double getValorTotalItens() {
        return itens.stream().mapToDouble(ItemCusto::getValor).sum();
    }
    public List<ItemCusto> getItens() {
        return new ArrayList<>(itens); // Retorna uma cópia para proteger a lista original
    }
}

