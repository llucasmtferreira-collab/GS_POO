public class SensorChuva extends ComponenteTerritorial implements Sensor {

    public SensorChuva(String id, String nome, String regiao) {
        super(id, nome, regiao);
    }

    @Override
    public double lerValor() {
        return Math.random() * 90.0;
    }

    @Override
    public boolean verificarFuncionamento() {
        return getStatus().equals("ATIVO");
    }

    @Override
    public String getTipo() {
        return "CHUVA";
    }

    @Override
    public String getUnidade() {
        return "mm/h";
    }

    @Override
    public String verificarStatus() {
        return getNome() + ": " + getStatus() + " (mede a intensidade da chuva em mm/h)";
    }
}
