# Territory Predictive Insights — CitizenTech

**Disciplina:** Programação Orientada a Objetos (POO) — Global Solution 2026.2  
**Instituição:** FIAP  
**Professor:** Ygor Anjos  
**Aluno:** Lucas Mota - RM566670  
**Modalidade:** Trabalho individual

## 1. Proposta do projeto

O Territory Predictive Insights é um programa em Java para simular o monitoramento de chuva e risco de alagamento em uma região. Ele registra medições de três sensores, calcula estimativas simples a partir das leituras recentes e apresenta alertas no console.

O recorte escolhido foi **clima local: chuva intensa e risco de alagamento em pontos vulneráveis**. O programa não utiliza medições reais nem dados pessoais. A região e a população iniciais são valores fictícios de demonstração.

### Público beneficiado

O projeto é voltado para moradores de áreas com histórico de alagamentos e para equipes de associações comunitárias ou órgãos municipais. Os alertas podem ajudar a decidir quando acompanhar a situação mais de perto, verificar locais vulneráveis, preparar a comunicação com os moradores ou solicitar uma avaliação técnica. O sistema não toma essas decisões automaticamente.

## 2. Funcionalidades

O menu do sistema possui as seguintes opções:

| Opção | Funcionalidade |
|---|---|
| 1 | Verificar se os sensores estão funcionando e ativar/desativar um sensor. |
| 2 | Executar as duas estratégias de previsão e exibir alertas. |
| 3 | Consultar e alterar região, população e limites de alerta. |
| 4 | Simular de 1 a 6 novas rodadas de leituras, com previsões automáticas. |
| 5 | Consultar os dados, o estado dos sensores, a última medição e os alertas atuais. |
| 6 | Simular 24 horas fictícias, com leituras e alertas a cada hora (bônus). |
| 0 | Encerrar o programa. |

O histórico guarda **até 48 leituras por sensor** em um `ArrayList<Double>`. Os dados permanecem apenas na memória enquanto o programa está aberto.

## 3. Sensores e valores de exemplo

| Sensor | Unidade | Intervalo de leitura simulada | Limite inicial |
|---|---|---|---|
| Chuva (`SensorChuva`) | mm/h | 0 a 90 | 65 mm/h |
| Nível do rio (`SensorNivelRio`) | m | 0,5 a 5,0 | 4,0 m |
| Umidade (`SensorUmidade`) | % | 30 a 100 | 90% |

As leituras são geradas usando `Math.random()`. Os limites são valores **ilustrativos para testar o programa**, e não parâmetros oficiais para avaliar risco real de alagamento. Todos os sensores começam ativos.

### Estratégias de previsão

O sistema utiliza duas classes que herdam de `EstrategiaPrevisao` e sobrescrevem o método `prever()`:

- **Média móvel (`PrevisaoMediaMovel`):** soma as últimas 5 leituras do sensor e divide por 5. Precisa de pelo menos 5 medições.
- **Comparação (`PrevisaoComparacao`):** calcula a média das últimas 3 leituras, compara com a média das 3 anteriores e projeta a diferença: `previsão = média_atual + (média_atual - média_anterior)`. Precisa de pelo menos 6 medições.

O resultado é uma estimativa didática para a próxima leitura, não uma previsão meteorológica validada. Quando não há leituras suficientes, o programa informa a quantidade necessária e não apresenta uma previsão inventada. Previsões negativas são ajustadas para zero; no caso da umidade, valores superiores a 100% são limitados a 100%.

### Níveis de alerta

Para cada estratégia, o programa compara o valor previsto com o limite cadastrado para o sensor:

| Nível | Regra |
|---|---|
| NORMAL | Previsão abaixo de 80% do limite. |
| ATENÇÃO | Previsão a partir de 80% e abaixo de 100% do limite. |
| ALERTA | Previsão a partir de 100% e abaixo de 150% do limite. |
| CRÍTICO | Previsão a partir de 150% do limite. Para a umidade, que não passa de 100%, a partir da metade do caminho entre o limite e 100% (com limite de 90%, a partir de 95%). |

Cada resultado informa o sensor, o tipo de previsão, o valor previsto, a unidade, o limite e o motivo da classificação. Se uma leitura simulada alcançar ou ultrapassar seu limite, o programa também mostra uma mensagem sobre a **medição atual**, separada do resultado previsto.

No console, os níveis aparecem sem acento (`ATENCAO`, `ALERTA`, `CRITICO`) para evitar problemas de codificação em alguns terminais. Para cada resultado diferente de NORMAL, o programa também imprime uma linha com o significado do sinal e uma sugestão de acompanhamento; a decisão continua sendo das pessoas responsáveis.

## 4. Conceitos de POO utilizados

- **Classe abstrata:** `ComponenteTerritorial` define `id`, `nome`, `regiao`, `status`, histórico, métodos `ativar()` e `desativar()` e o método abstrato `verificarStatus()`, que cada sensor implementa com a sua própria descrição.
- **Interface:** `Sensor` declara `lerValor()`, `verificarFuncionamento()`, `getTipo()` e `getUnidade()`. Os três tipos de sensores implementam esses métodos.
- **Encapsulamento:** `DadosTerritorio` possui apenas atributos `private`, getters e setters com validação. Tentativas inválidas mostram uma mensagem e mantêm o valor anterior.
- **Proteção dos limiares:** ao iniciar, o usuário define um código numérico de 4 dígitos. O código é solicitado para modificar limites de chuva, nível do rio e umidade. Não existe getter para recuperar o código.
- **Herança:** `PrevisaoMediaMovel` e `PrevisaoComparacao` herdam de `EstrategiaPrevisao`. Cada uma tem seu próprio parâmetro e utiliza `super()` no construtor. Os sensores também herdam de `ComponenteTerritorial`.
- **Polimorfismo:** as duas estratégias possuem implementações diferentes de `prever()`, e os três sensores podem ser tratados pela interface `Sensor` (o programa confere com `instanceof` antes de converter um componente para `Sensor`).

## 5. Arquivos do projeto

```text
GS_POO/
├── ComponenteTerritorial.java
├── Sensor.java
├── DadosTerritorio.java
├── EstrategiaPrevisao.java
├── PrevisaoMediaMovel.java
├── PrevisaoComparacao.java
├── SensorChuva.java
├── SensorNivelRio.java
├── SensorUmidade.java
├── SistemaMonitoramento.java
├── README.md
└── .gitignore
```

## 6. Como executar

**Requisito:** JDK 8 ou superior instalado. Não são necessárias bibliotecas externas.

1. Clone o repositório (ou use **Code → Download ZIP** no GitHub e extraia o arquivo) e abra um terminal dentro da pasta onde estão os arquivos `.java`.
2. Compile todos os arquivos Java:

   ```bash
   javac -encoding UTF-8 *.java
   ```

3. Inicie o programa:

   ```bash
   java SistemaMonitoramento
   ```

4. Cadastre um código numérico de 4 dígitos. Esse código será exigido ao alterar os limites de alerta.
5. Utilize a opção **4** para gerar leituras. Após a quinta rodada, a média móvel fica disponível; após a sexta, as duas previsões ficam disponíveis. A opção **6** executa a simulação adicional de 24 horas.

Os arquivos `.class` não fazem parte do repositório: eles são gerados pela compilação do passo 2.

**Execução no Eclipse:** crie um projeto Java, coloque os dez arquivos `.java` dentro da pasta `src` (sem declaração de `package`) e execute a classe `SistemaMonitoramento`.

## 7. Decisões do projeto

O recorte de chuva foi escolhido por permitir relacionar três medições diferentes com o risco de alagamento. O programa utiliza apenas o console porque o enunciado não exige interface gráfica. As classes foram separadas por responsabilidade para facilitar a leitura e a manutenção do código.

Os sensores usam valores aleatórios apenas para simulação. Os limiares podem ser ajustados pelo menu, mediante código de acesso. A média móvel mostra a tendência recente das medições, enquanto a comparação considera a mudança entre dois períodos. Foi incluída a simulação de 24 horas como a funcionalidade opcional de bônus descrita no enunciado.

## 8. Reflexão crítica

Um sistema de monitoramento não deve emitir previsões quando faltam informações suficientes para sustentar o cálculo. Por exemplo, com menos de cinco leituras não é possível calcular a média móvel configurada neste projeto, e a comparação entre períodos precisa de seis. Nesses casos, o programa informa que não há dados suficientes, em vez de mostrar um número sem base. Mesmo quando existem várias leituras, isso não significa que o resultado seja confiável para uma situação real, principalmente porque os dados deste projeto são simulados.

Também existem informações que os sensores não conseguem representar. Uma medição de chuva ou de nível do rio não descreve todos os pontos de um bairro. Duas ruas próximas podem ter riscos muito diferentes por causa do relevo, da drenagem, de bueiros entupidos ou da distância até o curso d'água. A umidade, por si só, também não permite afirmar que ocorrerá um alagamento. Além disso, um sensor pode ficar desligado ou apresentar uma leitura incorreta. Por isso, o projeto suspende as previsões de um sensor desativado e apresenta resultados separados para cada medição.

Os alertas devem servir como apoio para quem conhece a realidade da comunidade. Quando o sistema mostra **ATENÇÃO**, **ALERTA** ou **CRÍTICO**, ele indica que uma estimativa se aproximou ou ultrapassou um limite configurado. Isso pode ajudar as equipes a observar a situação e buscar outras fontes de informação. A decisão de agir, orientar moradores ou mobilizar serviços públicos precisa considerar avaliações humanas e informações oficiais. Um alerta automático não deve ser tratado como uma ordem nem como garantia de que um evento vai acontecer. O objetivo do sistema é ajudar a perceber sinais de risco, sem substituir a responsabilidade das pessoas pelas decisões.

## 9. Testes

O código foi compilado e executado em ambiente com **OpenJDK 21**. Foram verificados os cálculos das duas previsões, os três tipos de sensores, a ativação e desativação, a atualização da região, a rejeição de população negativa, o bloqueio de alterações de limite por código incorreto, os limites fora de faixa, a proteção do histórico, os quatro níveis de classificação e o fluxo interativo, incluindo a simulação de 24 horas.

Como as leituras são aleatórias, os números e alertas exibidos mudam a cada execução.

## 10. Uso de IA

Utilizei inteligência artificial como apoio na elaboração do código Java, na organização do README e na revisão do projeto. O funcionamento do sistema foi verificado por meio de testes de compilação, previsões, sensores, validações e opções do menu. As leituras utilizadas são simuladas.

## 11. Divisão de tarefas

O trabalho é **individual**, realizado sob responsabilidade de **Lucas Mota (RM 566670)**. Por esse motivo, não existe divisão de tarefas entre integrantes. As etapas do projeto incluem modelagem, programação Java, testes, documentação e publicação do repositório para a entrega.
