import java.util.*;

interface Video {
    void play();
}

class RealVideo implements Video {
    private String nomeArquivo;
    public RealVideo(String nomeArquivo) {
        System.out.println("Carregando vídeo do disco: " + nomeArquivo);
        this.nomeArquivo = nomeArquivo;
    }
    public void play() {
        System.out.println("Reproduzindo vídeo: " + nomeArquivo);
    }
}

class ProxyVideo implements Video {
    private String nomeArquivo;
    private RealVideo videoReal;

    public ProxyVideo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public void play() {
        if (videoReal == null) {
            videoReal = new RealVideo(nomeArquivo);
        }
        videoReal.play();
    }
}

public class Main {
    public static void main(String[] args) {
        List<Video> listaVideos = new ArrayList<>();
        listaVideos.add(new ProxyVideo("video1.mp4"));
        listaVideos.add(new ProxyVideo("video2.mp4"));
        listaVideos.add(new ProxyVideo("video3.mp4"));

        System.out.println("[App] Lista de vídeos carregada.");

        System.out.println("[App] Usuário escolheu assistir: video1.mp4");
        listaVideos.get(0).play();

        System.out.println("[App] Usuário escolheu assistir novamente: video1.mp4");
        listaVideos.get(0).play();

        System.out.println("[App] Usuário escolheu assistir: video3.mp4");
        listaVideos.get(2).play();
    }
}
