import java.util.Scanner;

public class ExercicioEnum {
	enum Prioridade {
		BAIXA,
		MEDIA,
		ALTA;
	}

	public static void main(String[] args) {
		System.out.println("As prioridades possC-veis sC#o:");
		for (Prioridade p : Prioridade.values()) {
			System.out.println(p);
		}

		Scanner scanner = new Scanner(System.in);
		System.out.print("Digite o nome de uma prioridade (BAIXA, MEDIA, ALTA): ");
		String input = scanner.nextLine().toUpperCase();

		try {
			Prioridade prioridadeEscolhida = Prioridade.valueOf(input);
			switch(prioridadeEscolhida) {
			case BAIXA:
				System.out.println("A prioridade BAIXA foi escolhida. Tarefa pode ser feita mais tarde");
				break;
			case MEDIA:
				System.out.println("A prioridade MEDIA foi escolhida. Tarefa deve ser feita jaja");
				break;
			case ALTA:
				System.out.println("A prioridade ALTA foi escolhida. Tarefa deve ser feita agora");
				break;
			}
		} catch(IllegalArgumentException e) {
			System.out.println("Prioridade invC!lida. Por favor, digite BAIXA, MEDIA ou ALTA.");
		}

		scanner.close();
	}
}