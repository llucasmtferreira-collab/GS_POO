public class SensorNivelRio extends ComponenteTerritorial implements Sensor {

    public SensorNivelRio(String id, String nome, String regiao) {
        super(id, nome, regiao);
    }

    @Override
    public double lerValor() {
        return 0.5 + Math.random() * 4.5;
    }

    @Override
    public boolean verificarFuncionamento() {
        return getStatus().equals("ATIVO");
    }

    @Override
    public String getTipo() {
        return "NIVEL_RIO";
    }

    @Override
    public String getUnidade() {
        return "m";
    }

    @Override
    public String verificarStatus() {
        return getNome() + ": " + getStatus() + " (mede o nivel do rio em metros)";
    }
}
