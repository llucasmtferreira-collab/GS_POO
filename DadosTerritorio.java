public class DadosTerritorio {
    private String regiao;
    private int populacao;
    private String codigoAcesso;
    private double limiteChuva;
    private double limiteNivelRio;
    private double limiteUmidade;

    public DadosTerritorio(String regiao, int populacao, String codigoAcesso) {
        if (regiao == null || regiao.trim().isEmpty()) {
            throw new IllegalArgumentException("A regiao nao pode ficar vazia.");
        }
        if (populacao < 0) {
            throw new IllegalArgumentException("A populacao nao pode ser negativa.");
        }
        if (!codigoValido(codigoAcesso)) {
            throw new IllegalArgumentException("O codigo deve conter 4 digitos numericos.");
        }

        this.regiao = regiao.trim();
        this.populacao = populacao;
        this.codigoAcesso = codigoAcesso;
        this.limiteChuva = 65.0;
        this.limiteNivelRio = 4.0;
        this.limiteUmidade = 90.0;
    }

    private boolean codigoValido(String codigo) {
        if (codigo == null || codigo.length() != 4) {
            return false;
        }
        for (int i = 0; i < codigo.length(); i++) {
            if (codigo.charAt(i) < '0' || codigo.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    private boolean acessoPermitido(String codigoInformado) {
        if (!codigoAcesso.equals(codigoInformado)) {
            System.out.println("Acesso negado: codigo incorreto. Nenhum limite foi alterado.");
            return false;
        }
        return true;
    }

    private boolean limiteValido(double valor, double minimo, double maximo) {
        return !Double.isNaN(valor) && !Double.isInfinite(valor)
                && valor >= minimo && valor <= maximo;
    }

    public String getRegiao() {
        return regiao;
    }

    public int getPopulacao() {
        return populacao;
    }

    public double getLimiteChuva() {
        return limiteChuva;
    }

    public double getLimiteNivelRio() {
        return limiteNivelRio;
    }

    public double getLimiteUmidade() {
        return limiteUmidade;
    }

    public boolean setRegiao(String novaRegiao) {
        if (novaRegiao == null || novaRegiao.trim().isEmpty()) {
            System.out.println("Regiao invalida: informe um nome. Valor anterior mantido.");
            return false;
        }
        this.regiao = novaRegiao.trim();
        return true;
    }

    public boolean setPopulacao(int novaPopulacao) {
        if (novaPopulacao < 0) {
            System.out.println("Populacao invalida: nao pode ser negativa. Valor anterior mantido.");
            return false;
        }
        this.populacao = novaPopulacao;
        return true;
    }

    public boolean setLimiteChuva(double novoLimite, String codigo) {
        if (!acessoPermitido(codigo)) {
            return false;
        }
        if (!limiteValido(novoLimite, 1.0, 150.0)) {
            System.out.println("Limite de chuva invalido: use de 1 a 150 mm/h. Valor anterior mantido.");
            return false;
        }
        this.limiteChuva = novoLimite;
        return true;
    }

    public boolean setLimiteNivelRio(double novoLimite, String codigo) {
        if (!acessoPermitido(codigo)) {
            return false;
        }
        if (!limiteValido(novoLimite, 0.1, 10.0)) {
            System.out.println("Limite do rio invalido: use de 0,1 a 10 m. Valor anterior mantido.");
            return false;
        }
        this.limiteNivelRio = novoLimite;
        return true;
    }

    public boolean setLimiteUmidade(double novoLimite, String codigo) {
        if (!acessoPermitido(codigo)) {
            return false;
        }
        if (!limiteValido(novoLimite, 1.0, 100.0)) {
            System.out.println("Limite de umidade invalido: use de 1 a 100%. Valor anterior mantido.");
            return false;
        }
        this.limiteUmidade = novoLimite;
        return true;
    }
}
