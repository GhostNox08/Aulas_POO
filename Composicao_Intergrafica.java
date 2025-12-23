import java.util.ArrayList;
import java.util.List;

// Interface comum
interface ComponenteGUI {
    void renderizar(String identacao);
    default void renderizar() { renderizar(""); }
}

// Componentes simples
class Botao implements ComponenteGUI {
    private String rotulo;

    public Botao(String rotulo) {
        this.rotulo = rotulo;
    }

    @Override
    public void renderizar(String identacao) {
        System.out.println(identacao + "Botão: " + rotulo);
    }
}

class Texto implements ComponenteGUI {
    private String conteudo;

    public Texto(String conteudo) {
        this.conteudo = conteudo;
    }

    @Override
    public void renderizar(String identacao) {
        System.out.println(identacao + "Texto: " + conteudo);
    }
}

// Componente composto
class Painel implements ComponenteGUI {
    private List<ComponenteGUI> filhos = new ArrayList<>();

    public void adicionar(ComponenteGUI componente) {
        filhos.add(componente);
    }

    @Override
    public void renderizar(String identacao) {
        System.out.println(identacao + "Painel");
        for (ComponenteGUI filho : filhos) {
            filho.renderizar(identacao + "  ");
        }
    }
}

// Classe principal para teste
public class Main {
    public static void main(String[] args) {
        // Cria painel principal
        Painel painelPrincipal = new Painel();
        painelPrincipal.adicionar(new Botao("Salvar"));
        painelPrincipal.adicionar(new Texto("Bem-vindo!"));

        // Cria painel interno
        Painel painelInterno = new Painel();
        painelInterno.adicionar(new Botao("Cancelar"));
        painelInterno.adicionar(new Texto("Mensagem interna"));

        painelPrincipal.adicionar(painelInterno);

        // Renderiza tudo
        painelPrincipal.renderizar();
    }
}
