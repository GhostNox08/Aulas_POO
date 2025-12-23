import java.util.ArrayList;
import java.util.List;

/**
 * Representa um livro no sistema da biblioteca.
 * Contém informações sobre título, autor e ano de publicação.
 */
class Livro {
    private String titulo;
    private String autor;
    private int anoPublicacao;

    // Construtor
    public Livro(String titulo, String autor, int anoPublicacao) {
        this.titulo = titulo;
        this.autor = autor;
        setAnoPublicacao(anoPublicacao); // Usa o setter para validar o ano
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    // Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    // O setter do ano de publicação deve garantir que o valor seja maior que zero.
    public void setAnoPublicacao(int anoPublicacao) {
        if (anoPublicacao > 0) {
            this.anoPublicacao = anoPublicacao;
        } else {
            System.out.println("Erro: O ano de publicação deve ser um valor positivo.");
            this.anoPublicacao = 0; // Atribui um valor padrão em caso de erro
        }
    }

    /**
     * Exibe as informações completas do livro.
     */
    public void mostrarInfo() {
        System.out.printf("Título: %s, Autor: %s, Ano: %d%n",
                this.titulo, this.autor, this.anoPublicacao);
    }
}

/**
 * Representa uma biblioteca que armazena uma coleção de livros.
 * Tem uma capacidade máxima e métodos para gerenciar os livros.
 */
class Biblioteca {
    private List<Livro> livros;
    private int capacidade;

    // Construtor
    public Biblioteca(int capacidade) {
        setCapacidade(capacidade); // Usa o setter para validar a capacidade
        this.livros = new ArrayList<>();
    }

    // Getter e Setter para capacidade
    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        if (capacidade > 0) {
            this.capacidade = capacidade;
        } else {
            System.out.println("Erro: A capacidade da biblioteca deve ser um valor positivo.");
            this.capacidade = 1; // Atribui um valor padrão em caso de erro
        }
    }

    /**
     * Adiciona um livro à biblioteca se houver espaço.
     *
     * @param livro O objeto Livro a ser adicionado.
     */
    public void adicionarLivro(Livro livro) {
        if (livros.size() < capacidade) {
            livros.add(livro);
            System.out.printf("Livro '%s' adicionado com sucesso.%n", livro.getTitulo());
        } else {
            System.out.println("A biblioteca está cheia. Não é possível adicionar mais livros.");
        }
    }

    /**
     * Remove um livro da biblioteca se ele existir na coleção.
     *
     * @param livro O objeto Livro a ser removido.
     */
    public void removerLivro(Livro livro) {
        if (livros.contains(livro)) {
            livros.remove(livro);
            System.out.printf("Livro '%s' removido com sucesso.%n", livro.getTitulo());
        } else {
            System.out.println("O livro não foi encontrado na biblioteca.");
        }
    }

    /**
     * Exibe as informações de todos os livros na biblioteca.
     */
    public void mostrarLivros() {
        System.out.println("--- Livros na Biblioteca ---");
        if (livros.isEmpty()) {
            System.out.println("A biblioteca está vazia.");
        } else {
            for (Livro livro : livros) {
                livro.mostrarInfo();
            }
        }
        System.out.println("----------------------------");
    }
}

// Classe principal para testar o sistema
public class AppBiblioteca {
    public static void main(String[] args) {
        // Criando uma instância da Biblioteca com capacidade máxima de 3 livros
        Biblioteca minhaBiblioteca = new Biblioteca(3);

        // Criando instâncias de Livro
        Livro livro1 = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", 1954);
        Livro livro2 = new Livro("1984", "George Orwell", 1949);
        Livro livro3 = new Livro("Game of Thrones", "George R.R. Martin", 1996);
        Livro livro4 = new Livro("A Revolução dos Bichos", "George Orwell", 1945);

        // Tentando adicionar os livros na biblioteca
        minhaBiblioteca.adicionarLivro(livro1);
        minhaBiblioteca.adicionarLivro(livro2);
        minhaBiblioteca.adicionarLivro(livro3);

        System.out.println("\n--- Exibindo todos os livros cadastrados ---");
        minhaBiblioteca.mostrarLivros();

        // Tentando adicionar um quarto livro, que deve falhar
        System.out.println("\n--- Tentando adicionar um livro extra ---");
        minhaBiblioteca.adicionarLivro(livro4);

        // Removendo um livro
        System.out.println("\n--- Removendo um livro ---");
        minhaBiblioteca.removerLivro(livro2);

        // Exibindo a lista atualizada de livros
        System.out.println("\n--- Exibindo a lista de livros após a remoção ---");
        minhaBiblioteca.mostrarLivros();
    }
}