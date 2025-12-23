import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

// A classe precisa implementar Serializable para poder ser gravada em arquivo
abstract class Restaurante implements Serializable {
    
    private static final long serialVersionUID = 1L;

    protected String nome;
    protected Map<String, Integer> avaliacoes; 
    
    private static int totalAvaliacoes = 0;

    public Restaurante(String nome) {
        this.nome = nome;
        this.avaliacoes = new HashMap<>();
    }

    public void adicionarAvaliacao(String data, int nota) {
        if (nota >= 1 && nota <= 5) {
            this.avaliacoes.put(data, nota);
            totalAvaliacoes++;
            System.out.println("-> Avaliação adicionada para " + this.nome + ".");
        } else {
            System.out.println("-> Erro: Nota inválida. Use um valor entre 1 e 5.");
        }
    }

    public abstract double calcularMedia();

    public String getNome() {
        return nome;
    }

    public Map<String, Integer> getAvaliacoes() {
        return avaliacoes;
    }

    public static int getTotalAvaliacoes() {
        return totalAvaliacoes;
    }
}

class FastFood extends Restaurante {
    
    private static final long serialVersionUID = 2L;

    private String tipo;

    public FastFood(String nome, String tipo) {
        super(nome);
        this.tipo = tipo;
    }

    @Override
    public double calcularMedia() {
        if (avaliacoes.isEmpty()) {
            return 0.0;
        }

        double somaNotas = 0;
        for (int nota : avaliacoes.values()) {
            somaNotas += nota;
        }
        return somaNotas / avaliacoes.size();
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "FastFood [Nome: " + nome + ", Tipo: " + tipo + "]";
    }
}

class FineDining extends Restaurante {
    
    private static final long serialVersionUID = 3L;

    private int estrelasMichelin;

    public FineDining(String nome, int estrelasMichelin) {
        super(nome);
        this.estrelasMichelin = estrelasMichelin;
    }

    @Override
    public double calcularMedia() {
        if (avaliacoes.isEmpty()) {
            return 0.0;
        }

        double somaNotas = 0;
        for (int nota : avaliacoes.values()) {
            somaNotas += nota;
        }

        double mediaBase = somaNotas / avaliacoes.size();
        double bonusEstrelas = estrelasMichelin * 0.5;
        
        return mediaBase + bonusEstrelas;
    }

    public int getEstrelasMichelin() {
        return estrelasMichelin;
    }

    @Override
    public String toString() {
        return "FineDining [Nome: " + nome + ", Estrelas Michelin: " + estrelasMichelin + "]";
    }
}

class GerenciarArquivo {

    public static void gravar(Restaurante r, String caminho) {
        try (FileOutputStream fileOut = new FileOutputStream(caminho);
             ObjectOutputStream objOut = new ObjectOutputStream(fileOut)) {
            
            objOut.writeObject(r);
            System.out.println("\n--- Arquivo ---");
            System.out.println("Objeto " + r.getNome() + " gravado com sucesso em: " + caminho);
            
        } catch (IOException i) {
            System.err.println("\n--- Erro ao Gravar ---");
            i.printStackTrace();
        }
    }

    public static Restaurante ler(String caminho) {
        Restaurante r = null;
        try (FileInputStream fileIn = new FileInputStream(caminho);
             ObjectInputStream objIn = new ObjectInputStream(fileIn)) {
            
            r = (Restaurante) objIn.readObject();
            System.out.println("Objeto lido com sucesso de: " + caminho);
            
        } catch (FileNotFoundException f) {
            System.err.println("\n--- Erro ao Ler ---");
            System.err.println("Arquivo não encontrado em: " + caminho);
        } catch (IOException i) {
            System.err.println("\n--- Erro ao Ler ---");
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.err.println("\n--- Erro ao Ler ---");
            System.err.println("Classe Restaurante não encontrada.");
            c.printStackTrace();
        }
        return r;
    }
}

public class Main {
    public static void main(String[] args) {
        
        DecimalFormat df = new DecimalFormat("#.##");

        System.out.println("--- 1. CRIAÇÃO DE RESTAURANTES ---");
        FastFood burgerKing = new FastFood("Burger King", "Hamburguer");
        FineDining olympe = new FineDining("Olympe", 2);

        System.out.println("\n--- 2. ADIÇÃO DE AVALIAÇÕES ---");
        burgerKing.adicionarAvaliacao("2025-09-01", 4);
        burgerKing.adicionarAvaliacao("2025-09-10", 3);
        burgerKing.adicionarAvaliacao("2025-09-20", 5);

        olympe.adicionarAvaliacao("2025-09-05", 5);
        olympe.adicionarAvaliacao("2025-09-15", 5);
        olympe.adicionarAvaliacao("2025-09-25", 4);

        System.out.println("\n--- 3. CÁLCULO DAS MÉDIAS ---");
        double mediaFastFood = burgerKing.calcularMedia();
        System.out.println(burgerKing.toString());
        System.out.println("Avaliações: " + burgerKing.getAvaliacoes());
        System.out.println("Média Simples (FastFood): " + df.format(mediaFastFood));

        System.out.println("------------------------------------");

        double mediaFineDining = olympe.calcularMedia();
        System.out.println(olympe.toString());
        System.out.println("Avaliações: " + olympe.getAvaliacoes());
        System.out.println("Média com Bônus (FineDining): " + df.format(mediaFineDining));

        System.out.println("\n--- 5. TOTAL DE AVALIAÇÕES ---");
        System.out.println("Total de Avaliações no Sistema: " + Restaurante.getTotalAvaliacoes());
        
        String caminhoArquivo = "restaurante_salvo.ser";
        GerenciarArquivo.gravar(olympe, caminhoArquivo);

        System.out.println("\n--- 4. RECUPERAÇÃO DE DADOS (ARQUIVO) ---");
        Restaurante restauranteRecuperado = GerenciarArquivo.ler(caminhoArquivo);

        if (restauranteRecuperado != null) {
            System.out.println("\n*** DADOS RECUPERADOS ***");
            System.out.println("Nome: " + restauranteRecuperado.getNome());
            System.out.println("Tipo de Objeto: " + restauranteRecuperado.getClass().getSimpleName());

            double mediaRecuperada = restauranteRecuperado.calcularMedia();
            System.out.println("Média Calculada (Objeto Recuperado): " + df.format(mediaRecuperada));
            System.out.println("Avaliações: " + restauranteRecuperado.getAvaliacoes());
        }
    }
}
