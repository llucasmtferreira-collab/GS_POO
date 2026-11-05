public class SensorUmidade extends ComponenteTerritorial implements Sensor {

    public SensorUmidade(String id, String nome, String regiao) {
        super(id, nome, regiao);
    }

    @Override
    public double lerValor() {
        return 30.0 + Math.random() * 70.0;
    }

    @Override
    public boolean verificarFuncionamento() {
        return getStatus().equals("ATIVO");
    }

    @Override
    public String getTipo() {
        return "UMIDADE";
    }

    @Override
    public String getUnidade() {
        return "%";
    }

    @Override
    public String verificarStatus() {
        return getNome() + ": " + getStatus() + " (mede a umidade relativa do ar em %)";
    }
}
