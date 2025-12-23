import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;


public class MiniServiceDesk {


    private static int nextId = 1;

    public enum NivelPrioridade {
        BAIXA("Pode esperar um pouco"),
        MEDIA("Precisa de atenção em breve"),
        ALTA("Urgente, equipe avançada"),
        CRITICA("Interrupção de serviço, ação imediata");

        private final String descricao;

        NivelPrioridade(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    public static class Cliente {
        private String nome;
        private String email;

        public Cliente(String nome, String email) {
            this.nome = nome;
            this.email = email;
        }

       
        public String getNome() { return nome; }
        public String getEmail() { return email; }
    }

    
    public static class Chamado {
        private final int id;
        private String descricao;
        private NivelPrioridade prioridade;
        private Cliente cliente;
        private String resolucao;

        public Chamado(String descricao, NivelPrioridade prioridade, Cliente cliente) {
            this.id = nextId++;
            this.descricao = descricao;
            this.prioridade = prioridade;
            this.cliente = cliente;
            this.resolucao = "";
        }

        
        public void validar() throws IllegalArgumentException {
            if (cliente == null) {
                throw new IllegalArgumentException("Cliente não pode ser nulo.");
            }
            if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
                throw new IllegalArgumentException("Nome do cliente não pode ser vazio.");
            }
            if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Email do cliente não pode ser vazio.");
            }
            if (descricao == null || descricao.trim().isEmpty()) {
                throw new IllegalArgumentException("Descrição do chamado não pode ser vazia.");
            }
            if (prioridade == null) {
                throw new IllegalArgumentException("Prioridade do chamado não pode ser nula.");
            }
        }

     
        public int getId() { return id; }
        public String getDescricao() { return descricao; }
        public NivelPrioridade getPrioridade() { return prioridade; }
        public Cliente getCliente() { return cliente; }
        public String getResolucao() { return resolucao; }
        public void setResolucao(String resolucao) { this.resolucao = resolucao; }

        public String toFileString() {
            return String.format("ID: %d | Cliente: %s | Email: %s | Prioridade: %s | Resolução: %s",
                    id, cliente.getNome(), cliente.getEmail(), prioridade.name(), resolucao);
        }
    }

    public interface Atendimento {
        
        String resolverChamado(Chamado chamado);
    }


    public static class SuporteBasico implements Atendimento {
        @Override
        public String resolverChamado(Chamado chamado) {
            String resolucao = String.format("[Suporte Básico] Chamado #%d (%s) resolvido: Configuração de software / suporte por telefone.",
                    chamado.getId(), chamado.getPrioridade().name());
            chamado.setResolucao(resolucao);
            return resolucao;
        }
    }

    
    public static class SuporteAvancado implements Atendimento {
        @Override
        public String resolverChamado(Chamado chamado) {
            String resolucao = String.format("[Suporte Avançado] Chamado #%d (%s) resolvido: Troca de hardware / intervenção remota avançada.",
                    chamado.getId(), chamado.getPrioridade().name());
            chamado.setResolucao(resolucao);
            return resolucao;
        }
    }

    public static void gravarChamado(Chamado chamado) {
        
        try (FileWriter fw = new FileWriter("chamados.txt", true);
             PrintWriter pw = new PrintWriter(fw)) {
            
            pw.println(chamado.toFileString());
            System.out.println("\n--- PERSISTÊNCIA ---");
            System.out.println("SUCESSO: O chamado foi registrado no arquivo 'chamados.txt'.");
            System.out.println("Linha gravada: " + chamado.toFileString());
            System.out.println("--------------------\n");

        } catch (IOException e) {
            // Requisito 4: Try-catch para erros de gravação de arquivo
            System.err.println("\n*** ERRO DE PERSISTÊNCIA ***");
            System.err.println("Erro ao gravar o chamado no arquivo: " + e.getMessage());
            System.err.println("***************************\n");
        }
    }

    /**
     * Requisito 8: Implementação do fluxo de interação principal.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Requisito 8.1: Exibir as prioridades disponíveis
        System.out.println("==============================================");
        System.out.println("        MINI-SERVICE DESK - ABERTURA");
        System.out.println("==============================================");
        System.out.print("Prioridades disponíveis: ");
        for (NivelPrioridade p : NivelPrioridade.values()) {
            System.out.print(p.name() + " ");
        }
        System.out.println("\n");

     
        String nomeCliente = "";
        String emailCliente = "";
        String descricaoChamado = "";
        NivelPrioridade prioridadeEscolhida = null;
       

        try {
       
            System.out.print("Nome do cliente: ");
            nomeCliente = scanner.nextLine().trim();

            System.out.print("E-mail do cliente: ");
            emailCliente = scanner.nextLine().trim();

            System.out.print("Descrição do chamado: ");
            descricaoChamado = scanner.nextLine().trim();

            
            while (prioridadeEscolhida == null) {
                System.out.print("Prioridade (" + String.join(", ", java.util.Arrays.stream(NivelPrioridade.values()).map(Enum::name).toArray(String[]::new)) + "): ");
                String inputPrioridade = scanner.nextLine().toUpperCase().trim();
                try {
                    prioridadeEscolhida = NivelPrioridade.valueOf(inputPrioridade);
                } catch (IllegalArgumentException e) {
                  
                    System.err.println("\n*** ERRO DE ENTRADA ***");
                    System.err.println("Prioridade inválida. Por favor, escolha entre: " + 
                        String.join(", ", java.util.Arrays.stream(NivelPrioridade.values()).map(Enum::name).toArray(String[]::new)));
                    System.err.println("***********************\n");
                }
            }

            
            Cliente cliente = new Cliente(nomeCliente, emailCliente);
            Chamado chamado = new Chamado(descricaoChamado, prioridadeEscolhida, cliente);

            
            chamado.validar();
            
            
            Atendimento handler;
            if (prioridadeEscolhida == NivelPrioridade.BAIXA || prioridadeEscolhida == NivelPrioridade.MEDIA) {
                handler = new SuporteBasico();
            } else { 
                handler = new SuporteAvancado();
            }

            
            String resolucao = handler.resolverChamado(chamado);

            
            System.out.println("\n--- ATENDIMENTO CONCLUÍDO ---");
            System.out.println("Chamado encaminhado para: " + handler.getClass().getSimpleName());
            System.out.println("Resolução: " + resolucao);
            System.out.println("-----------------------------\n");

            
            gravarChamado(chamado);

        } catch (IllegalArgumentException e) {
           
            System.err.println("\n*** ERRO DE VALIDAÇÃO ***");
            System.err.println("Não foi possível abrir o chamado. Motivo: " + e.getMessage());
            System.err.println("*************************\n");
        } catch (Exception e) {
            
            System.err.println("\n*** ERRO INESPERADO ***");
            System.err.println("Ocorreu um erro: " + e.getMessage());
            System.err.println("***********************\n");
        } finally {
            
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}