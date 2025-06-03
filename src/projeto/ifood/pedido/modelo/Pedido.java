package projeto.ifood.pedido.modelo;

import projeto.ifood.pedido.estado.*;
import projeto.ifood.pedido.observador.ObservadorPedido;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private String id;
    private EstadoPedido estadoAtual;
    private List<ObservadorPedido> observadores = new ArrayList<>();
    private String clienteNome; // Adicionado para identificação
    private String restauranteNome; // Adicionado para identificação

    public Pedido(String id, String clienteNome, String restauranteNome) {
        this.id = id;
        this.clienteNome = clienteNome;
        this.restauranteNome = restauranteNome;
        // Estado inicial do pedido é Pendente
        this.estadoAtual = new EstadoPendente();
        System.out.println("Pedido " + id + " criado para " + clienteNome + " no restaurante " + restauranteNome + ". Status inicial: " + estadoAtual.getNome());
    }

    // Métodos do Padrão State
    public void setEstado(EstadoPedido novoEstado) {
        this.estadoAtual = novoEstado;
        System.out.println("Pedido " + id + " mudou para o estado: " + estadoAtual.getNome());
        notificarObservadores(); // Notifica os observadores sobre a mudança de estado
    }

    public void avancarStatus() {
        estadoAtual.avancar(this);
    }

    public void cancelarPedido() {
        estadoAtual.cancelar(this);
    }

    public EstadoPedido getEstadoAtual() {
        return estadoAtual;
    }

    public String getStatusNome() {
        return estadoAtual.getNome();
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

    // Getters para informações do pedido
    public String getId() {
        return id;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public String getRestauranteNome() {
        return restauranteNome;
    }
}
