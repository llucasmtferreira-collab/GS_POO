import java.util.ArrayList;
import java.util.Scanner;

public class SistemaMonitoramento {
    private static Scanner scanner = new Scanner(System.in);
    private static DadosTerritorio dados;
    private static ArrayList<ComponenteTerritorial> componentes = new ArrayList<ComponenteTerritorial>();
    private static EstrategiaPrevisao mediaMovel = new PrevisaoMediaMovel(5);
    private static EstrategiaPrevisao comparacao = new PrevisaoComparacao(3);

    public static void main(String[] args) {
        System.out.println("=== TERRITORY PREDICTIVE INSIGHTS ===");
        System.out.println("Monitoramento de chuva e risco de alagamento");
        System.out.println("Os dados deste programa sao simulados.");
        System.out.println();

        String codigo = cadastrarCodigo();
        dados = new DadosTerritorio("Regiao Piloto", 10000, codigo);

        componentes.add(new SensorChuva("S01", "Sensor de chuva", dados.getRegiao()));
        componentes.add(new SensorNivelRio("S02", "Sensor de nivel do rio", dados.getRegiao()));
        componentes.add(new SensorUmidade("S03", "Sensor de umidade", dados.getRegiao()));

        int opcao;
        do {
            mostrarMenu();
            opcao = lerInteiro("Escolha uma opcao: ");

            switch (opcao) {
                case 1:
                    verificarSensores();
                    break;
                case 2:
                    rodarPrevisoes();
                    break;
                case 3:
                    gerenciarDados();
                    break;
                case 4:
                    int quantidade = lerInteiro("Quantas leituras simular (1 a 6)? ");
                    if (quantidade >= 1 && quantidade <= 6) {
                        simularLeituras(quantidade);
                    } else {
                        System.out.println("Quantidade invalida. Use um valor de 1 a 6.");
                    }
                    break;
                case 5:
                    exibirStatusCompleto();
                    break;
                case 6:
                    System.out.println("Simulacao de 24 horas: 1 rodada representa 1 hora ficticia.");
                    simularLeituras(24);
                    break;
                case 0:
                    System.out.println("Programa encerrado.");
                    break;
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static String cadastrarCodigo() {
        while (true) {
            System.out.print("Crie um codigo de acesso de 4 digitos para os limiares: ");
            String codigo = scanner.nextLine().trim();

            boolean valido = codigo.length() == 4;
            for (int i = 0; i < codigo.length(); i++) {
                if (codigo.charAt(i) < '0' || codigo.charAt(i) > '9') {
                    valido = false;
                }
            }

            if (valido) {
                return codigo;
            }
            System.out.println("Codigo invalido. Digite exatamente 4 numeros.");
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n========== MENU PRINCIPAL ==========");
        System.out.println("1 - Verificar e ativar/desativar sensores");
        System.out.println("2 - Rodar previsoes e verificar alertas");
        System.out.println("3 - Gerenciar dados do territorio");
        System.out.println("4 - Simular proximas leituras");
        System.out.println("5 - Exibir status completo");
        System.out.println("6 - Simular 24 horas (bonus)");
        System.out.println("0 - Sair");
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Digite um numero inteiro.");
            }
        }
    }

    private static double lerDecimal(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim().replace(',', '.');
            try {
                double valor = Double.parseDouble(entrada);
                if (!Double.isNaN(valor) && !Double.isInfinite(valor)) {
                    return valor;
                }
            } catch (NumberFormatException e) {
                // A mensagem abaixo tambem atende entradas que nao sao numeros.
            }
            System.out.println("Entrada invalida. Digite um numero valido.");
        }
    }

    private static void verificarSensores() {
        System.out.println("\n--- VERIFICACAO DOS SENSORES ---");
        for (int i = 0; i < componentes.size(); i++) {
            ComponenteTerritorial componente = componentes.get(i);
            String funcionamento = "Nao e sensor";
            if (componente instanceof Sensor) {
                Sensor sensor = (Sensor) componente;
                funcionamento = sensor.verificarFuncionamento() ? "Funcionando" : "Desativado";
            }
            System.out.println((i + 1) + " - " + componente.verificarStatus()
                    + " | " + funcionamento + " | Regiao: " + componente.getRegiao());
        }

        System.out.println("1 - Ativar um sensor");
        System.out.println("2 - Desativar um sensor");
        System.out.println("0 - Voltar");
        int opcao = lerInteiro("Opcao: ");
        if (opcao == 0) {
            return;
        }
        if (opcao != 1 && opcao != 2) {
            System.out.println("Opcao invalida.");
            return;
        }

        int numero = lerInteiro("Numero do sensor (1 a " + componentes.size() + "): ");
        if (numero < 1 || numero > componentes.size()) {
            System.out.println("Sensor inexistente.");
            return;
        }

        ComponenteTerritorial escolhido = componentes.get(numero - 1);
        if (opcao == 1) {
            escolhido.ativar();
        } else {
            escolhido.desativar();
        }
        System.out.println(escolhido.verificarStatus());
    }

    private static void gerenciarDados() {
        System.out.println("\n--- DADOS DO TERRITORIO ---");
        mostrarDados();
        System.out.println("1 - Alterar regiao");
        System.out.println("2 - Alterar populacao");
        System.out.println("3 - Alterar limite de chuva");
        System.out.println("4 - Alterar limite do nivel do rio");
        System.out.println("5 - Alterar limite de umidade");
        System.out.println("0 - Voltar");

        int opcao = lerInteiro("Opcao: ");
        switch (opcao) {
            case 1:
                System.out.print("Nova regiao: ");
                String novaRegiao = scanner.nextLine();
                if (dados.setRegiao(novaRegiao)) {
                    for (ComponenteTerritorial componente : componentes) {
                        componente.atualizarRegiao(dados.getRegiao());
                    }
                    System.out.println("Regiao atualizada com sucesso.");
                }
                break;
            case 2:
                int populacao = lerInteiro("Nova populacao: ");
                if (dados.setPopulacao(populacao)) {
                    System.out.println("Populacao atualizada com sucesso.");
                }
                break;
            case 3:
                double chuva = lerDecimal("Novo limite de chuva (1 a 150 mm/h): ");
                System.out.print("Codigo de acesso: ");
                if (dados.setLimiteChuva(chuva, scanner.nextLine().trim())) {
                    System.out.println("Limite de chuva atualizado com sucesso.");
                }
                break;
            case 4:
                double nivel = lerDecimal("Novo limite do rio (0,1 a 10 m): ");
                System.out.print("Codigo de acesso: ");
                if (dados.setLimiteNivelRio(nivel, scanner.nextLine().trim())) {
                    System.out.println("Limite do rio atualizado com sucesso.");
                }
                break;
            case 5:
                double umidade = lerDecimal("Novo limite de umidade (1 a 100%): ");
                System.out.print("Codigo de acesso: ");
                if (dados.setLimiteUmidade(umidade, scanner.nextLine().trim())) {
                    System.out.println("Limite de umidade atualizado com sucesso.");
                }
                break;
            case 0:
                break;
            default:
                System.out.println("Opcao invalida.");
        }
    }

    private static void mostrarDados() {
        System.out.println("Regiao: " + dados.getRegiao());
        System.out.println("Populacao (exemplo): " + dados.getPopulacao());
        System.out.printf("Limite de chuva: %.2f mm/h%n", dados.getLimiteChuva());
        System.out.printf("Limite de nivel do rio: %.2f m%n", dados.getLimiteNivelRio());
        System.out.printf("Limite de umidade: %.2f%%%n", dados.getLimiteUmidade());
    }

    private static void simularLeituras(int quantidade) {
        for (int hora = 1; hora <= quantidade; hora++) {
            System.out.println("\n--- RODADA " + hora + " DE " + quantidade + " ---");
            for (ComponenteTerritorial componente : componentes) {
                if (!(componente instanceof Sensor)) {
                    continue; // so sensores fazem leituras
                }
                Sensor sensor = (Sensor) componente;
                if (!sensor.verificarFuncionamento()) {
                    System.out.println(componente.getNome() + ": sem leitura (desativado).");
                    continue;
                }

                double valor = sensor.lerValor();
                componente.registrarLeitura(valor);
                System.out.printf("%s: %.2f %s%n", componente.getNome(), valor, sensor.getUnidade());

                double limite = obterLimite(sensor);
                if (valor >= limite) {
                    System.out.printf("LEITURA NO/ACIMA DO LIMITE: %s = %.2f %s (limite: %.2f %s).%n",
                            componente.getNome(), valor, sensor.getUnidade(), limite, sensor.getUnidade());
                }
            }
            System.out.println("Previsoes e alertas automaticos:");
            rodarPrevisoes();
        }
    }

    private static void rodarPrevisoes() {
        for (ComponenteTerritorial componente : componentes) {
            if (!(componente instanceof Sensor)) {
                continue; // so sensores geram previsoes
            }
            Sensor sensor = (Sensor) componente;
            System.out.println("\nSensor: " + componente.getNome());

            if (!sensor.verificarFuncionamento()) {
                System.out.println("Sensor desativado: previsoes suspensas.");
                continue;
            }

            ArrayList<Double> leituras = componente.getHistoricoLeituras();
            avaliarPrevisao(sensor, mediaMovel, leituras);
            avaliarPrevisao(sensor, comparacao, leituras);
        }
    }

    private static void avaliarPrevisao(Sensor sensor, EstrategiaPrevisao estrategia,
                                        ArrayList<Double> leituras) {
        if (leituras.size() < estrategia.getMinimoLeituras()) {
            System.out.println(estrategia.getNome() + ": sem previsao (" + leituras.size()
                    + " leituras; minimo de " + estrategia.getMinimoLeituras() + ").");
            return;
        }

        double valorPrevisto = estrategia.prever(leituras);
        if (sensor.getTipo().equals("UMIDADE") && valorPrevisto > 100.0) {
            valorPrevisto = 100.0;
        }

        double limite = obterLimite(sensor);
        double limiteCritico = obterLimiteCritico(sensor, limite);
        String nivel = classificarAlerta(valorPrevisto, limite, limiteCritico);
        String motivo;

        if (nivel.equals("CRITICO")) {
            motivo = String.format("previsao chegou ao patamar critico (%.2f %s)",
                    limiteCritico, sensor.getUnidade());
        } else if (nivel.equals("ALERTA")) {
            motivo = "previsao atingiu ou ultrapassou o limite";
        } else if (nivel.equals("ATENCAO")) {
            motivo = "previsao chegou a pelo menos 80% do limite";
        } else {
            motivo = "previsao abaixo de 80% do limite";
        }

        System.out.printf("[%s] %s | tipo: %s | previsto: %.2f %s | limite: %.2f %s | motivo: %s.%n",
                nivel, estrategia.getNome(), sensor.getTipo(), valorPrevisto,
                sensor.getUnidade(), limite, sensor.getUnidade(), motivo);

        if (!nivel.equals("NORMAL")) {
            System.out.println("   -> " + descreverRisco(sensor) + " " + sugerirAcao(nivel));
        }
    }

    private static double obterLimite(Sensor sensor) {
        if (sensor.getTipo().equals("CHUVA")) {
            return dados.getLimiteChuva();
        }
        if (sensor.getTipo().equals("NIVEL_RIO")) {
            return dados.getLimiteNivelRio();
        }
        return dados.getLimiteUmidade();
    }

    private static double obterLimiteCritico(Sensor sensor, double limite) {
        if (sensor.getTipo().equals("UMIDADE")) {
            // A umidade nao passa de 100%, entao 150% do limite nunca seria atingido.
            // Para ela, o nivel critico comeca na metade do caminho entre o limite e 100%.
            return limite + (100.0 - limite) / 2;
        }
        return limite * 1.5;
    }

    private static String classificarAlerta(double previsao, double limite, double limiteCritico) {
        if (previsao >= limiteCritico) {
            return "CRITICO";
        }
        if (previsao >= limite) {
            return "ALERTA";
        }
        if (previsao >= limite * 0.8) {
            return "ATENCAO";
        }
        return "NORMAL";
    }

    private static String descreverRisco(Sensor sensor) {
        if (sensor.getTipo().equals("CHUVA")) {
            return "Sinal de chuva forte e risco de alagamento em pontos vulneraveis.";
        }
        if (sensor.getTipo().equals("NIVEL_RIO")) {
            return "Sinal de nivel do rio elevado e risco de transbordamento.";
        }
        return "Sinal de umidade alta; sozinha, nao confirma alagamento.";
    }

    private static String sugerirAcao(String nivel) {
        if (nivel.equals("CRITICO")) {
            return "Sugestao: pedir avaliacao tecnica e preparar a comunicacao com os moradores"
                    + " (a decisao e das pessoas responsaveis).";
        }
        if (nivel.equals("ALERTA")) {
            return "Sugestao: verificar os pontos vulneraveis do territorio.";
        }
        return "Sugestao: acompanhar a situacao de perto.";
    }

    private static void exibirStatusCompleto() {
        System.out.println("\n========== STATUS COMPLETO ==========");
        mostrarDados();

        for (ComponenteTerritorial componente : componentes) {
            ArrayList<Double> historico = componente.getHistoricoLeituras();
            System.out.println("\nID: " + componente.getId());
            System.out.println("Nome: " + componente.getNome());
            System.out.println("Regiao: " + componente.getRegiao());
            System.out.println("Status: " + componente.getStatus());
            if (componente instanceof Sensor) {
                Sensor sensor = (Sensor) componente;
                System.out.println("Tipo: " + sensor.getTipo());
                System.out.println("Leituras armazenadas: " + historico.size());
                if (!historico.isEmpty()) {
                    double ultima = historico.get(historico.size() - 1);
                    System.out.printf("Ultima leitura: %.2f %s%n", ultima, sensor.getUnidade());
                }
            }
        }
        System.out.println("\n--- PREVISOES E ALERTAS ATUAIS ---");
        rodarPrevisoes();
    }
}
