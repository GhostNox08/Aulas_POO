import java.util.Scanner; // Importa a classe Scanner para entrada do usuário
import java.util.Random;  // Importa a classe Random para gerar números aleatórios

public class Jokenpo {
    public static void main(String[] args) {
        // Declaração de variáveis para armazenar as escolhas do jogador e do computador
        int humano;
        int computador;
        
        // Contadores para armazenar o número de vitórias do humano, do computador e empates
        int vhumano = 0;
        int vcomputador = 0;
        int empates = 0;

        // Objeto Scanner para ler a entrada do usuário
        Scanner scanner = new Scanner(System.in);

        // Objeto Random para gerar a escolha aleatória do computador
        Random rand = new Random();

        // Loop infinito para repetir o jogo até o usuário decidir sair
        while (true) {
            // Exibição do menu de opções
            System.out.println("******Menu******");
            System.out.println("1 - papel");
            System.out.println("2 - pedra");
            System.out.println("3 - tesoura");
            System.out.println("0 - sair");
            System.out.println("****************");
            
            // Solicita a escolha do usuário
            System.out.print("opção:");
            humano = scanner.nextInt(); // Lê a escolha do usuário
            System.out.println("Humano escolheu: " + humano);

            // Gera a escolha do computador (um número aleatório entre 1 e 3)
            computador = rand.nextInt(3) + 1;
            System.out.println("Computador escolheu: " + computador);
            System.out.println("****************");

            // Se o usuário escolher 0, o jogo é encerrado
            if (humano == 0) {
                System.out.println("Saindo....");
                break; // Sai do loop
            }

            // Verifica se houve empate
            if (humano == computador) {
                empates += 1; // Incrementa o número de empates
                System.out.println("Empatou!");
            }
            // Verifica as condições de vitória do humano
            else if (humano == 1 && computador == 2) { // Papel vence Pedra
                vhumano += 1;
                System.out.println("Humano ganhou!");
            } else if (humano == 2 && computador == 3) { // Pedra vence Tesoura
                vhumano += 1;
                System.out.println("Humano ganhou!");
            } else if (humano == 3 && computador == 1) { // Tesoura vence Papel
                vhumano += 1;
                System.out.println("Humano ganhou!");
            } 
            // Se nenhuma das condições anteriores for verdadeira, o computador venceu
            else {
                vcomputador += 1;
                System.out.println("Computador ganhou!");
            }
        }

        // Exibição do placar final após o término do jogo
        System.out.println("****Placar****");
        System.out.println("Vitórias humano: " + vhumano);
        System.out.println("Vitórias computador: " + vcomputador);
        System.out.println("Empates: " + empates);
    }
}