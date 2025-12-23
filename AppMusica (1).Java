import java.util.ArrayList;
import java.util.List;

class Musica {
    private String titulo;
    private String artista;
    private int duracao; // em segundos

    // Construtor
    public Musica(String titulo, String artista, int duracao) {
        this.titulo = titulo;
        this.artista = artista;
        setDuracao(duracao); // usa o setter para validar
    }

    // Getter
    public String getTitulo() {
        return titulo;
    }

    // Setter com validação
    public void setDuracao(int duracao) {
        if (duracao > 0) {
            this.duracao = duracao;
        } else {
            System.out.println("Erro: A duração deve ser maior que 0.");
        }
    }

    // Método para exibir informações
    public void exibirInfo() {
        System.out.println("Título: " + titulo + ", Artista: " + artista + ", Duração: " + duracao + "s");
    }
}

// Subclasse MusicaPremium
class MusicaPremium extends Musica {
    private String qualidade; // "MP3" ou "FLAC"

    MusicaPremium mp = new MusicaPremium("xxx", "aaaa", 400, "FLAC");
    MusicaPremium mp2 = new MusicaPremium("xxx", "aaaaa", 400);
    mp.adicionarMusica();
    public void adicionarMusica(Musica m){
        System.out.println("Musica adicionada");
    }
    public void adicionarMusica(String titulo, String artista, int duracao){
        MusicaPremium mp3 = new MusicaPremium("xxx", "aaaa", 400, "FLAC");
        adicionarMusica(mp3);
    }
}

// Classe Playlist
class Playlist {
    private String nome;
    private String dono;

    // Construtor
    public Playlist(String nome, String dono) {
        this.nome = nome;
        this.dono = dono;
    }

    // Método adicionarMusica recebendo objeto
    public void adicionarMusica(Musica m) {
        System.out.println("Música adicionada à playlist \"" + nome + "\": " + m.getTitulo());
    }

    // Método sobrecarregado
    public void adicionarMusica(String titulo, String artista, int duracao) {
        Musica novaMusica = new Musica(titulo, artista, duracao);
        adicionarMusica(novaMusica);
    }
}

// Classe principal
public class App {
    public static void main(String[] args) {
        // 1. Criar uma música simples
        Musica m1 = new Musica("Houdini", "Eminem", 240);
        m1.exibirInfo();

        // 2. Criar músicas premium
        MusicaPremium mp1 = new MusicaPremium("Awake and ALive", "Skillet", 200, "FLAC");
        MusicaPremium mp2 = new MusicaPremium("Dont Stop me now", "Queen", 210); // padrão MP3

        mp1.exibirInfo();
        mp2.exibirInfo();

        // 3. Criar playlist e adicionar músicas
        Playlist playlist = new Playlist("Favoritas", "Beto");
        playlist.adicionarMusica(m1);
        playlist.adicionarMusica("Sway", "Michael Bublé", 300);

        // 4. Testar sobrescrita
        mp1.exibirInfo();

        // 5. Testar setter inválido
        m1.setDuracao(-50); // deve dar erro
    }
}