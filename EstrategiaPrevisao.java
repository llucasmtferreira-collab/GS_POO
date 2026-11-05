import java.util.ArrayList;

public abstract class EstrategiaPrevisao {
    private String nome;

    public EstrategiaPrevisao(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public abstract int getMinimoLeituras();

    public abstract double prever(ArrayList<Double> leituras);
}
