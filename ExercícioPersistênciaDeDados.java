import java.sql.SQLException;        // Exceções de banco
import java.util.InputMismatchException; // Para tratar entradas inválidas
import java.util.List;               // Interface de lista
import java.util.Locale;             // Para garantir o padrão americano (ponto como separador decimal)
import java.util.Scanner;            // Para leitura de entrada
import java.text.DecimalFormat;      // Para formatar a saída de preços

// A classe Main agora deve estar no mesmo arquivo (ou visível) que FileTools e GerenciarBD.
// Assumindo que você colocou todo o código em um único arquivo Main.java para simplificar.
// (Se estiver em arquivos separados, mantenha assim, mas para o contexto da resposta, vou unir a parte do Main).

public class Main {
    private static final String DB_FILE = "loja.db";
    private static final String LOG_FILE = "loja.log";
    // Define o formato de preço com 2 casas decimais.
    private static final DecimalFormat DF = new DecimalFormat("#0.00");
    private static final Scanner SC = new Scanner(System.in);

    // Método auxiliar para exibir o menu
    private static void exibirMenu() {
        System.out.println("\n===== GERENCIAMENTO DE PRODUTOS =====");
        System.out.println("1. Inserir Novo Produto");
        System.out.println("2. Listar Todos os Produtos");
        System.out.println("3. Listar Produtos com Estoque Baixo (<= N)");
        System.out.println("4. Atualizar Quantidade de Produto");
        System.out.println("5. Atualizar Preço de Produto");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    // Método auxiliar para ler um inteiro com tratamento de erro
    private static int lerInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int valor = SC.nextInt();
                SC.nextLine(); // Consumir a quebra de linha
                return valor;
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Por favor, digite um número inteiro.");
                SC.nextLine(); // Limpar o buffer
            }
        }
    }

    // Método auxiliar para ler um double com tratamento de erro
    private static double lerDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double valor = SC.nextDouble();
                SC.nextLine(); // Consumir a quebra de linha
                return valor;
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Por favor, digite um número decimal (use ponto).");
                SC.nextLine(); // Limpar o buffer
            }
        }
    }

    // 1. Inserir Produto
    private static void inserirProduto(GerenciarBD dao) {
        System.out.println("\n--- INSERIR NOVO PRODUTO ---");
        System.out.print("Nome do Produto: ");
        String nome = SC.nextLine();
        
        double preco = lerDouble("Preço (ex: 12.50): ");
        int quantidade = lerInt("Quantidade em Estoque: ");

        try {
            long id = dao.inserirProduto(nome, preco, quantidade);
            String msg = "Produto '" + nome + "' inserido com ID: " + id + ", Preço: R$" + DF.format(preco);
            System.out.println(msg);
            FileTools.appendLog(LOG_FILE, "SUCESSO", msg);
        } catch (SQLException e) {
            String msg = "Falha ao inserir produto: " + nome;
            System.err.println(msg);
            FileTools.appendLog(LOG_FILE, "ERRO", msg, e);
        }
    }

    // 2. Listar Todos
    private static void listarTodos(GerenciarBD dao) {
        System.out.println("\n--- TODOS OS PRODUTOS ---");
        try {
            List<GerenciarBD.ProdutoDTO> produtos = dao.listarTodos();
            if (produtos.isEmpty()) {
                System.out.println("Nenhum produto cadastrado.");
                FileTools.appendLog(LOG_FILE, "INFO", "Tentativa de listar produtos: 0 encontrados.");
                return;
            }
            System.out.printf("%-5s | %-30s | %-10s | %-10s%n", "ID", "NOME", "PRECO", "QTD");
            System.out.println("------|--------------------------------|------------|------------");
            for (GerenciarBD.ProdutoDTO p : produtos) {
                System.out.printf("%-5d | %-30s | R$ %-7s | %-10d%n", 
                                  p.id, p.nome, DF.format(p.preco), p.quantidade);
            }
            FileTools.appendLog(LOG_FILE, "SUCESSO", "Listagem de todos os produtos realizada.");
        } catch (SQLException e) {
            String msg = "Falha ao listar todos os produtos.";
            System.err.println(msg);
            FileTools.appendLog(LOG_FILE, "ERRO", msg, e);
        }
    }

    // 3. Listar Estoque Baixo
    private static void listarEstoqueBaixo(GerenciarBD dao) {
        System.out.println("\n--- PRODUTOS COM ESTOQUE BAIXO ---");
        int limite = lerInt("Mostrar produtos com quantidade menor ou igual a: ");
        
        try {
            List<GerenciarBD.ProdutoDTO> produtos = dao.listarPorEstoqueAte(limite);
            if (produtos.isEmpty()) {
                System.out.println("Nenhum produto encontrado com estoque <= " + limite + ".");
                FileTools.appendLog(LOG_FILE, "INFO", "Tentativa de listar produtos com estoque <= " + limite + ": 0 encontrados.");
                return;
            }
            
            System.out.println("Produtos com estoque <= " + limite + ":");
            System.out.printf("%-5s | %-30s | %-10s | %-10s%n", "ID", "NOME", "PRECO", "QTD");
            System.out.println("------|--------------------------------|------------|------------");
            for (GerenciarBD.ProdutoDTO p : produtos) {
                System.out.printf("%-5d | %-30s | R$ %-7s | %-10d%n", 
                                  p.id, p.nome, DF.format(p.preco), p.quantidade);
            }
            FileTools.appendLog(LOG_FILE, "SUCESSO", "Listagem de produtos com estoque <= " + limite + " realizada.");
        } catch (SQLException e) {
            String msg = "Falha ao listar produtos com estoque baixo.";
            System.err.println(msg);
            FileTools.appendLog(LOG_FILE, "ERRO", msg, e);
        }
    }

    // 4. Atualizar Quantidade
    private static void atualizarQuantidade(GerenciarBD dao) {
        System.out.println("\n--- ATUALIZAR QUANTIDADE ---");
        int id = lerInt("ID do produto a ser atualizado: ");
        int novaQuantidade = lerInt("Nova quantidade em estoque: ");

        try {
            int linhasAfetadas = dao.atualizarQuantidade(id, novaQuantidade);
            if (linhasAfetadas > 0) {
                String msg = "Quantidade do Produto ID " + id + " atualizada para " + novaQuantidade + ".";
                System.out.println(msg);
                FileTools.appendLog(LOG_FILE, "SUCESSO", msg);
            } else {
                String msg = "Produto com ID " + id + " não encontrado. Nenhuma atualização realizada.";
                System.err.println(msg);
                FileTools.appendLog(LOG_FILE, "AVISO", msg);
            }
        } catch (SQLException e) {
            String msg = "Falha ao atualizar quantidade do produto ID " + id + ".";
            System.err.println(msg);
            FileTools.appendLog(LOG_FILE, "ERRO", msg, e);
        }
    }
    
    // 5. Atualizar Preço
    private static void atualizarPreco(GerenciarBD dao) {
        System.out.println("\n--- ATUALIZAR PREÇO ---");
        int id = lerInt("ID do produto a ter o preço atualizado: ");
        double novoPreco = lerDouble("Novo preço (ex: 29.99): ");

        try {
            int linhasAfetadas = dao.atualizarPreco(id, novoPreco);
            if (linhasAfetadas > 0) {
                String msg = "Preço do Produto ID " + id + " atualizado para R$" + DF.format(novoPreco) + ".";
                System.out.println(msg);
                FileTools.appendLog(LOG_FILE, "SUCESSO", msg);
            } else {
                String msg = "Produto com ID " + id + " não encontrado. Nenhuma atualização realizada.";
                System.err.println(msg);
                FileTools.appendLog(LOG_FILE, "AVISO", msg);
            }
        } catch (SQLException e) {
            String msg = "Falha ao atualizar preço do produto ID " + id + ".";
            System.err.println(msg);
            FileTools.appendLog(LOG_FILE, "ERRO", msg, e);
        }
    }

    public static void main(String[] args) {
        // Garante que o separador decimal seja o ponto, conforme o banco de dados.
        Locale.setDefault(Locale.US); 
        GerenciarBD dao = new GerenciarBD(DB_FILE);

        // 1. Configuração inicial (já existente no seu código)
        try {
            dao.criarTabelaSeNaoExistir();
            FileTools.appendLog(LOG_FILE, "SUCESSO", "Tabela 'produtos' verificada/criada com sucesso.");
        } catch (SQLException e) {
            FileTools.appendLog(LOG_FILE, "CRÍTICO", "Erro FATAL ao criar/verificar tabela.");
            System.err.println("Não foi possível inicializar o banco de dados. Verifique o log.");
            return;
        }

        // 2. Loop principal do menu
        while (true) {
            exibirMenu();
            
            int opcao = -1; // Inicializa com valor inválido
            try {
                // Tenta ler a opção, usando um método que trata InputMismatchException
                opcao = lerInt(""); 
            } catch (InputMismatchException e) {
                // O erro de entrada já é tratado dentro de lerInt, mas se algo inesperado acontecer:
                System.err.println("Opção inválida. Tente novamente.");
                continue;
            }

            switch (opcao) {
                case 1:
                    inserirProduto(dao);
                    break;
                case 2:
                    listarTodos(dao);
                    break;
                case 3:
                    listarEstoqueBaixo(dao);
                    break;
                case 4:
                    atualizarQuantidade(dao);
                    break;
                case 5:
                    atualizarPreco(dao);
                    break;
                case 0:
                    System.out.println("\nSaindo do sistema. Verifique 'loja.log' para o histórico de operações.");
                    FileTools.appendLog(LOG_FILE, "INFO", "Aplicação encerrada pelo usuário.");
                    SC.close();
                    return; // Sai do método main e encerra a aplicação
                default:
                    System.err.println("Opção '" + opcao + "' não reconhecida. Escolha um número do menu.");
                    FileTools.appendLog(LOG_FILE, "AVISO", "Tentativa de opção de menu inválida: " + opcao);
            }
        }
    }
}