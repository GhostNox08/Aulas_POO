import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.Locale;

/**
 * 1. Interface ProcessadorPagamento
 * Define o contrato para todos os métodos de pagamento.
 * Inclui o método default emitirRecibo (Opcional/Requisito 1).
 */
interface ProcessadorPagamento {
    boolean pagar(double valor);

    // Requisito 1: default void emitirRecibo (double valor)
    default void emitirRecibo(double valor) {
        // Usa Locale.US ou Locale.getDefault() para formatação, embora o console exija R$
        // Usando String.format("%.2f", valor) conforme o requisito.
        System.out.println("Recibo: pagamento de R$ " +
                String.format(Locale.forLanguageTag("pt-BR"), "%.2f", valor) + " confirmado.");
    }
}

// 2. Implementações: PagamentoPix
class PagamentoPix implements ProcessadorPagamento {
    private String chavePix; // Requisito 2: Construtor recebe chavePix (String).

    public PagamentoPix(String chavePix) {
        this.chavePix = chavePix;
    }

    @Override
    public boolean pagar(double valor) {
        // Requisito 2: pagar retorna true se valor > 0 e chavePix não está vazia; imprime.
        if (valor > 0 && chavePix != null && !chavePix.trim().isEmpty()) {
            System.out.println("Processando PIX para chave: " + this.chavePix + "...");
            System.out.println("Pagamento aprovado!"); // Simulação de confirmação
            return true;
        }
        System.out.println("Erro no PIX: Valor inválido ou chave PIX não informada.");
        return false;
    }
}

// 2. Implementações: PagamentoCartaoCredito
class PagamentoCartaoCredito implements ProcessadorPagamento {
    private String numero;
    private String nomeTitular;
    private String cvv;
    private static final double LIMITE_SIMULADO = 5000.00; // Requisito 2: limiteSimulado (ex.: 5.000)

    // Requisito 2: Construtor recebe numero, nomeTitular, cvv.
    public PagamentoCartaoCredito(String numero, String nomeTitular, String cvv) {
        this.numero = numero;
        this.nomeTitular = nomeTitular;
        this.cvv = cvv;
    }

    @Override
    public boolean pagar(double valor) {
        boolean camposValidos = (numero != null && !numero.trim().isEmpty()) &&
                               (nomeTitular != null && !nomeTitular.trim().isEmpty()) &&
                               (cvv != null && !cvv.trim().isEmpty());

        // Requisito 2: pagar retorna true se os campos não estão vazios e valor ≤ limiteSimulado
        if (camposValidos && valor > 0 && valor <= LIMITE_SIMULADO) {
            System.out.println("Simulação de autorização de Cartão de Crédito...");
            System.out.println("Autorização simulada: APROVADA (R$ " + String.format(Locale.forLanguageTag("pt-BR"), "%.2f", valor) + ")");
            return true;
        }

        System.out.print("Simulação de autorização de Cartão de Crédito... ");
        if (!camposValidos) {
            System.out.println("Erro: Dados do cartão incompletos/inválidos.");
        } else if (valor <= 0) {
            System.out.println("Erro: Valor do pagamento inválido.");
        } else if (valor > LIMITE_SIMULADO) {
            System.out.println("Autorização simulada: RECUSADA (Excedeu o limite de R$ " + String.format(Locale.forLanguageTag("pt-BR"), "%.2f", LIMITE_SIMULADO) + ")");
        } else {
             System.out.println("Autorização simulada: RECUSADA (Motivo Desconhecido).");
        }
        return false;
    }
}

// 2. Implementações: PagamentoBoleto
class PagamentoBoleto implements ProcessadorPagamento {
    // Não precisa de construtor especial.
    
    @Override
    public boolean pagar(double valor) {
        // Requisito 2: sempre retorna true se valor > 0.
        if (valor > 0) {
            // Requisito 2: Gera "linha digitável" simulada (pode ser um UUID)
            String linhaDigitavel = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 47);
            
            // Requisito 2: imprime instruções de pagamento.
            System.out.println("Boleto gerado para o valor de R$ " + String.format(Locale.forLanguageTag("pt-BR"), "%.2f", valor) + ".");
            System.out.println("Linha digitável: " + linhaDigitavel);
            System.out.println("Instruções: Pagar este código de barras em qualquer banco ou internet banking.");
            return true;
        }
        System.out.println("Erro no Boleto: Valor inválido.");
        return false;
    }
}

// 1. Modelo dos Itens
class Item {
    private String descricao;
    private double preco;

    // Requisito 1: construtor
    public Item(String descricao, double preco) {
        this.descricao = descricao;
        this.preco = preco;
    }

    // Requisito 1: getters
    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }
}

/**
 * 3. Classe Carrinho
 * O Carrinho não conhece as classes concretas (PagamentoPix, PagamentoCartaoCredito, etc.),
 * apenas a interface ProcessadorPagamento (Regra 45/46).
 */
class Carrinho {
    private List<Item> itens = new ArrayList<>();

    public void adicionar(Item item) {
        itens.add(item);
    }

    // Requisito 3: Método double total() soma os preços.
    public double total() {
        double soma = 0.0;
        for (Item item : itens) {
            soma += item.getPreco();
        }
        return soma;
    }

    public void exibirItens() {
        System.out.println("Carrinho:");
        for (Item item : itens) {
            System.out.println("- " + item.getDescricao() + ": R$ " + String.format(Locale.forLanguageTag("pt-BR"), "%.2f", item.getPreco()));
        }
        System.out.println("Total: R$ " + String.format(Locale.forLanguageTag("pt-BR"), "%.2f", total()));
    }

    // Requisito 3: Método finalizarCompra (ProcessadorPagamento proc)
    public void finalizarCompra(ProcessadorPagamento proc) {
        double valor = total(); // Calcula valor = total().
        
        // Chama proc.pagar(valor).
        if (proc.pagar(valor)) {
            // Se true, chama proc.emitirRecibo (valor);
            proc.emitirRecibo(valor);
        } else {
            // caso contrário, imprime "Pagamento recusado".
            System.out.println("Pagamento recusado.");
        }
    }
}

// 4. Classe App (console)
public class CaixaRap {
    public static void main(String[] args) {
        // Configura Locale para garantir a formatação correta de moeda no console (ex: vírgula)
        Locale.setDefault(Locale.forLanguageTag("pt-BR"));

        // Monta um carrinho com 3-4 itens de exemplo. (Requisito 4/Guia 5)
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Item("Café", 8.50));
        carrinho.adicionar(new Item("Pão de queijo", 6.00));
        carrinho.adicionar(new Item("Suco de Laranja", 9.90));
        carrinho.adicionar(new Item("Bolo de Fubá", 15.00)); // Quarto item

        carrinho.exibirItens();

        // Pede ao usuário escolher o meio de pagamento (Requisito 4)
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nSelecione o meio de pagamento [1-Pix, 2-Cartão, 3-Boleto]:");

        int escolha = 0;
        if (scanner.hasNextInt()) {
            escolha = scanner.nextInt();
        } else {
            System.out.println("Escolha inválida. Usando Pix por padrão.");
            escolha = 1;
        }

        ProcessadorPagamento processador; // Declaração da interface

        // Cria a implementação correspondente (Injeção por construtor/Polimorfismo)
        switch (escolha) {
            case 1:
                // Exemplo: chavePix (String)
                processador = new PagamentoPix("aluno@exemplo.com");
                break;
            case 2:
                // Exemplo: numero, nomeTitular, cvv. (Simulando dados válidos para teste)
                // Para testar a recusa por limite, mude o valor ou use o total do carrinho (> 5000)
                processador = new PagamentoCartaoCredito("1111222233334444", "ALUNO PROTOTIPO", "123");
                break;
            case 3:
                processador = new PagamentoBoleto();
                break;
            default:
                System.out.println("Opção inválida. Finalizando sem pagamento.");
                return;
        }
        
        System.out.println("\n--- Iniciando Pagamento ---");
        // Passa a implementação para finalizarCompra (Chama carrinho.finalizarCompra(processador))
        carrinho.finalizarCompra(processador);
        System.out.println("---------------------------\n");

        scanner.close();
    }
}
