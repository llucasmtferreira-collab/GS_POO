import java.util.ArrayList;

public abstract class ComponenteTerritorial {
    private String id;
    private String nome;
    private String regiao;
    private String status;
    private ArrayList<Double> historicoLeituras;

    public ComponenteTerritorial(String id, String nome, String regiao) {
        this.id = id;
        this.nome = nome;
        this.regiao = regiao;
        this.status = "ATIVO";
        this.historicoLeituras = new ArrayList<Double>();
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getRegiao() {
        return regiao;
    }

    public String getStatus() {
        return status;
    }

    public void atualizarRegiao(String novaRegiao) {
        if (novaRegiao != null && !novaRegiao.trim().isEmpty()) {
            this.regiao = novaRegiao.trim();
        }
    }

    public void ativar() {
        this.status = "ATIVO";
    }

    public void desativar() {
        this.status = "INATIVO";
    }

    public void registrarLeitura(double valor) {
        if (Double.isNaN(valor) || Double.isInfinite(valor) || valor < 0) {
            System.out.println("Leitura invalida. O valor anterior foi mantido.");
            return;
        }

        historicoLeituras.add(valor);
        if (historicoLeituras.size() > 48) {
            historicoLeituras.remove(0);
        }
    }

    public ArrayList<Double> getHistoricoLeituras() {
        return new ArrayList<Double>(historicoLeituras);
    }

    public abstract String verificarStatus();
}
