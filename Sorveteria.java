interface Sorvete {
    String getDescricao();
    double getPreco();
}

class SorveteSimples implements Sorvete {
    public String getDescricao() {
        return "Sorvete de baunilha";
    }
    public double getPreco() {
        return 5.00;
    }
}

class CoberturaChocolate implements Sorvete {
    private Sorvete sorvete;
    public CoberturaChocolate(Sorvete sorvete) {
        this.sorvete = sorvete;
    }
    public String getDescricao() {
        return sorvete.getDescricao() + " + cobertura de chocolate";
    }
    public double getPreco() {
        return sorvete.getPreco() + 1.50;
    }
}

class CoberturaCaramelo implements Sorvete {
    private Sorvete sorvete;
    public CoberturaCaramelo(Sorvete sorvete) {
        this.sorvete = sorvete;
    }
    public String getDescricao() {
        return sorvete.getDescricao() + " + cobertura de caramelo";
    }
    public double getPreco() {
        return sorvete.getPreco() + 1.20;
    }
}

class GranuladoColorido implements Sorvete {
    private Sorvete sorvete;
    public GranuladoColorido(Sorvete sorvete) {
        this.sorvete = sorvete;
    }
    public String getDescricao() {
        return sorvete.getDescricao() + " + granulado colorido";
    }
    public double getPreco() {
        return sorvete.getPreco() + 0.80;
    }
}

class ChantillyExtra implements Sorvete {
    private Sorvete sorvete;
    public ChantillyExtra(Sorvete sorvete) {
        this.sorvete = sorvete;
    }
    public String getDescricao() {
        return sorvete.getDescricao() + " + chantilly extra";
    }
    public double getPreco() {
        return sorvete.getPreco() + 1.00;
    }
}

public class Sorveteria {
    public static void main(String[] args) {
        Sorvete pedido1 = new SorveteSimples();
        pedido1 = new CoberturaChocolate(pedido1);
        pedido1 = new GranuladoColorido(pedido1);
        pedido1 = new ChantillyExtra(pedido1);
        System.out.printf("%s - R$%.2f%n", pedido1.getDescricao(), pedido1.getPreco());

        Sorvete pedido2 = new SorveteSimples();
        pedido2 = new ChantillyExtra(pedido2);
        pedido2 = new CoberturaCaramelo(pedido2);
        System.out.printf("%s - R$%.2f%n", pedido2.getDescricao(), pedido2.getPreco());
    }
}
