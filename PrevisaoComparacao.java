import java.util.ArrayList;

public class PrevisaoComparacao extends EstrategiaPrevisao {
    private int tamanhoPeriodo;

    public PrevisaoComparacao(int tamanhoPeriodo) {
        super("Comparacao de periodos");
        if (tamanhoPeriodo < 1) {
            throw new IllegalArgumentException("O periodo precisa ter pelo menos 1 leitura.");
        }
        this.tamanhoPeriodo = tamanhoPeriodo;
    }

    @Override
    public int getMinimoLeituras() {
        return tamanhoPeriodo * 2;
    }

    @Override
    public double prever(ArrayList<Double> leituras) {
        if (leituras == null || leituras.size() < getMinimoLeituras()) {
            throw new IllegalArgumentException("Leituras insuficientes para a comparacao.");
        }

        int quantidade = leituras.size();
        double somaAtual = 0;
        double somaAnterior = 0;

        for (int i = quantidade - tamanhoPeriodo; i < quantidade; i++) {
            somaAtual += leituras.get(i);
        }
        for (int i = quantidade - 2 * tamanhoPeriodo; i < quantidade - tamanhoPeriodo; i++) {
            somaAnterior += leituras.get(i);
        }

        double mediaAtual = somaAtual / tamanhoPeriodo;
        double mediaAnterior = somaAnterior / tamanhoPeriodo;
        double previsao = mediaAtual + (mediaAtual - mediaAnterior);

        if (previsao < 0) {
            return 0;
        }
        return previsao;
    }
}
