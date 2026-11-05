import java.util.ArrayList;

public class PrevisaoMediaMovel extends EstrategiaPrevisao {
    private int tamanhoJanela;

    public PrevisaoMediaMovel(int tamanhoJanela) {
        super("Media movel");
        if (tamanhoJanela < 1) {
            throw new IllegalArgumentException("A janela precisa ter pelo menos 1 leitura.");
        }
        this.tamanhoJanela = tamanhoJanela;
    }

    @Override
    public int getMinimoLeituras() {
        return tamanhoJanela;
    }

    @Override
    public double prever(ArrayList<Double> leituras) {
        if (leituras == null || leituras.size() < tamanhoJanela) {
            throw new IllegalArgumentException("Leituras insuficientes para a media movel.");
        }

        double soma = 0;
        for (int i = leituras.size() - tamanhoJanela; i < leituras.size(); i++) {
            soma += leituras.get(i);
        }
        return soma / tamanhoJanela;
    }
}
