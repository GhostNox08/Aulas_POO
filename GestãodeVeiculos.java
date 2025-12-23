import java.util.ArrayList;
import java.util.List;

abstract class Veiculo {

    private String marca;
    private String modelo;
    private int ano;
    private double preco;
    private boolean ligado;
    private double velocidade;

    public Veiculo(String marca, String modelo, int ano, double preco) {
        if (ano <= 0) {
            System.out.println("Ano inválido! Definindo ano como 2000.");
            ano = 2000;
        }

        if (preco < 0) {
            System.out.println("Preço inválido! Definindo preço como 0.");
            preco = 0;
        }

        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.preco = preco;
        this.ligado = false;
        this.velocidade = 0.0;
    }

    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public int getAno() { return ano; }
    public double getPreco() { return preco; }

    public double getVelocidade() { return velocidade; }
    public boolean isLigado() { return ligado; }

    public void ligar() {
        if (!ligado) {
            ligado = true;
            System.out.println(modelo + " foi ligado.");
        }
    }

    public void desligar() {
        if (ligado && velocidade == 0) {
            ligado = false;
            System.out.println(modelo + " foi desligado.");
        } else {
            System.out.println("Não pode desligar em movimento!");
        }
    }

    public void acelerar(double valor) {
        if (ligado && valor > 0) {
            velocidade += valor;
            System.out.println(modelo + " acelerou. Velocidade: " + velocidade);
        }
    }

    public void frear(double valor) {
        if (ligado && valor > 0) {
            velocidade -= valor;
            if (velocidade < 0) velocidade = 0;
            System.out.println(modelo + " freou. Velocidade: " + velocidade);
        }
    }

    public String info() {
        return "Marca: " + marca +
               " | Modelo: " + modelo +
               " | Ano: " + ano +
               " | Preço: R$" + preco +
               " | Ligado: " + ligado +
               " | Velocidade: " + velocidade;
    }

    public abstract double calcularIpva();
}

class Carro extends Veiculo {
    private int portas;
    private boolean temAirbag;

    public Carro(String marca, String modelo, int ano, double preco, int portas, boolean temAirbag) {
        super(marca, modelo, ano, preco);
        this.portas = portas;
        this.temAirbag = temAirbag;
    }

    @Override
    public String info() {
        return super.info() + " | Portas: " + portas + " | Airbag: " + temAirbag;
    }

    @Override
    public double calcularIpva() {
        return (getAno() >= 2010) ? getPreco() * 0.03 : getPreco() * 0.01;
    }
}

class Moto extends Veiculo {
    private int cilindradas;

    public Moto(String marca, String modelo, int ano, double preco, int cilindradas) {
        super(marca, modelo, ano, preco);
        this.cilindradas = cilindradas;
    }

    @Override
    public String info() {
        return super.info() + " | Cilindradas: " + cilindradas;
    }

    @Override
    public double calcularIpva() {
        return (cilindradas >= 300) ? getPreco() * 0.02 : 0;
    }
}

class Caminhao extends Veiculo {
    private double capacidadeTon;
    private int eixos;

    public Caminhao(String marca, String modelo, int ano, double preco, double capacidadeTon, int eixos) {
        super(marca, modelo, ano, preco);
        this.capacidadeTon = capacidadeTon;
        this.eixos = eixos;
    }

    @Override
    public String info() {
        return super.info() + " | Capacidade: " + capacidadeTon + " ton | Eixos: " + eixos;
    }

    @Override
    public double calcularIpva() {
        double ipva = getPreco() * (0.01 * eixos);
        return (ipva > getPreco() * 0.04) ? getPreco() * 0.04 : ipva;
    }
}

class Concessionaria {
    private List<Veiculo> estoque;

    public Concessionaria() {
        estoque = new ArrayList<>();
    }

    public void adicionar(Veiculo v) {
        estoque.add(v);
    }

    public void removerPorModelo(String modelo) {
        estoque.removeIf(v -> v.getModelo().equalsIgnoreCase(modelo));
    }

    public List<Veiculo> buscarPorMarca(String marca) {
        List<Veiculo> resultado = new ArrayList<>();
        for (Veiculo v : estoque) {
            if (v.getMarca().equalsIgnoreCase(marca)) {
                resultado.add(v);
            }
        }
        return resultado;
    }

    public void listar() {
        for (Veiculo v : estoque) {
            System.out.println(v.info());
        }
    }

    public double valorTotalEstoque() {
        double total = 0;
        for (Veiculo v : estoque) {
            total += v.getPreco();
        }
        return total;
    }
}

public class Main {
    public static void main(String[] args) {
        Concessionaria cons = new Concessionaria();
        Carro carro = new Carro("Toyota", "Corolla", 2015, 60000, 4, true);
        Moto moto = new Moto("Honda", "CB500", 2020, 30000, 500);
        Caminhao caminhao = new Caminhao("Volvo", "FH", 2018, 200000, 20, 4);

        cons.adicionar(carro);
        cons.adicionar(moto);
        cons.adicionar(caminhao);

        System.out.println("\n📋 Lista de Veículos:");
        cons.listar();

        System.out.println("\n💰 IPVA dos veículos:");
        System.out.println(carro.getModelo() + ": R$" + carro.calcularIpva());
        System.out.println(moto.getModelo() + ": R$" + moto.calcularIpva());
        System.out.println(caminhao.getModelo() + ": R$" + caminhao.calcularIpva());

        System.out.println("\n🔧 Testando funcionalidades no carro:");
        carro.ligar();
        carro.acelerar(50);
        carro.frear(20);
        carro.desligar();
        carro.frear(30);
        carro.desligar();
    }
}
