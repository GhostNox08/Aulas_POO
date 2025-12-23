import java.util.ArrayList;

class MaterialBiblioteca {
    private String titulo;
    private String autor;
    private int anoPublicacao;

    public MaterialBiblioteca(String titulo, String autor, int anoPublicacao) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public String getInformacoes() {
        return "Título: " + titulo + ", Autor: " + autor + ", Ano: " + anoPublicacao;
    }
}

class Livro extends MaterialBiblioteca {
    private int numeroPaginas;

    public Livro(String titulo, String autor, int anoPublicacao, int numeroPaginas) {
        super(titulo, autor, anoPublicacao);
        this.numeroPaginas = numeroPaginas;
    }

    @Override
    public String getInformacoes() {
        return super.getInformacoes() + ", Páginas: " + numeroPaginas;
    }
}

class Revista extends MaterialBiblioteca {
    private int edicao;

    public Revista(String titulo, String autor, int anoPublicacao, int edicao) {
        super(titulo, autor, anoPublicacao);
        this.edicao = edicao;
    }

    @Override
    public String getInformacoes() {
        return super.getInformacoes() + ", Edição: " + edicao;
    }
}

class Ebook extends MaterialBiblioteca {
    private String formatoArquivo;

    public Ebook(String titulo, String autor, int anoPublicacao, String formatoArquivo) {
        super(titulo, autor, anoPublicacao);
        this.formatoArquivo = formatoArquivo;
    }

    @Override
    public String getInformacoes() {
        return super.getInformacoes() + ", Formato: " + formatoArquivo;
    }
}

class Audiolivro extends MaterialBiblioteca {
    private int duracaoMinutos;

    public Audiolivro(String titulo, String autor, int anoPublicacao, int duracaoMinutos) {
        super(titulo, autor, anoPublicacao);
        this.duracaoMinutos = duracaoMinutos;
    }

    @Override
    public String getInformacoes() {
        return super.getInformacoes() + ", Duração: " + duracaoMinutos + " minutos";
    }
}

class Biblioteca {
    private ArrayList<MaterialBiblioteca> materiais;

    public Biblioteca() {
        this.materiais = new ArrayList<>();
    }

    public void adicionarMaterial(MaterialBiblioteca material) {
        materiais.add(material);
        System.out.println("Material '" + material.getTitulo() + "' adicionado com sucesso.");
    }

    public void removerMaterial(String titulo) {
        MaterialBiblioteca materialParaRemover = null;
        for (MaterialBiblioteca material : materiais) {
            if (material.getTitulo().equalsIgnoreCase(titulo)) {
                materialParaRemover = material;
                break;
            }
        }
        
        if (materialParaRemover != null) {
            materiais.remove(materialParaRemover);
            System.out.println("Material '" + titulo + "' removido com sucesso.");
        } else {
            System.out.println("Material '" + titulo + "' não encontrado.");
        }
    }

    public void exibirInformacoesMaterial(String titulo) {
        for (MaterialBiblioteca material : materiais) {
            if (material.getTitulo().equalsIgnoreCase(titulo)) {
                System.out.println(material.getInformacoes());
                return;
            }
        }
        System.out.println("Material '" + titulo + "' não encontrado.");
    }

    public void listarTodosMateriais() {
        if (materiais.isEmpty()) {
            System.out.println("A biblioteca está vazia.");
        } else {
            System.out.println("--- Lista de Todos os Materiais ---");
            for (MaterialBiblioteca material : materiais) {
                System.out.println(material.getInformacoes());
            }
            System.out.println("------------------------------------");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Biblioteca minhaBiblioteca = new Biblioteca();

        // Adicionar materiais de diferentes tipos
        Livro livro1 = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", 1954, 1178);
        Revista revista1 = new Revista("National Geographic", "Diversos", 2023, 150);
        Ebook ebook1 = new Ebook("Java para Iniciantes", "João Silva", 2022, "PDF");
        Audiolivro audiolivro1 = new Audiolivro("A Culpa é das Estrelas", "John Green", 2012, 540);

        minhaBiblioteca.adicionarMaterial(livro1);
        minhaBiblioteca.adicionarMaterial(revista1);
        minhaBiblioteca.adicionarMaterial(ebook1);
        minhaBiblioteca.adicionarMaterial(audiolivro1);
        
        System.out.println("\n--- Demonstrando Funcionalidades ---");

        // Listar todos os materiais
        minhaBiblioteca.listarTodosMateriais();
        
        // Exibir informações de um material específico
        System.out.println("\nBuscando informações de 'Java para Iniciantes':");
        minhaBiblioteca.exibirInformacoesMaterial("Java para Iniciantes");

        // Tentar buscar um material que não existe
        System.out.println("\nBuscando informações de 'Código Limpo':");
        minhaBiblioteca.exibirInformacoesMaterial("Código Limpo");
        
        // Remover um material
        System.out.println("\nRemovendo 'O Senhor dos Anéis'...");
        minhaBiblioteca.removerMaterial("O Senhor dos Anéis");
        
        // Listar todos os materiais novamente para ver a remoção
        System.out.println("\nLista após remoção:");
        minhaBiblioteca.listarTodosMateriais();
    }
}
