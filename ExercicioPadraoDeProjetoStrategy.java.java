interface FreteStrategy {
    double calcularFrete(double peso, double valor);
}


class SedexFrete implements FreteStrategy {
    @Override
    public double calcularFrete(double peso, double valor) {
        return peso * 1.45;
    }
}


class PacFrete implements FreteStrategy {
    @Override
    public double calcularFrete(double peso, double valor) {
        return peso * 1.10;
    }
}


class FreteGratis implements FreteStrategy {
    @Override
    public double calcularFrete(double peso, double valor) {
        if (valor > 200) {
            return 0.0;
        } else {
            return peso * 1.45;
        }
    }
}


class CalculadoraFrete {
    private FreteStrategy estrategia;

    public void setEstrategia(FreteStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public double calcular(double peso, double valor) {
        if (estrategia == null) {
            throw new IllegalStateException("Estratégia de frete não definida!");
        }
        return estrategia.calcularFrete(peso, valor);
    }
}

public class Main {
    public static void main(String[] args) {
        CalculadoraFrete calculadora = new CalculadoraFrete();

        calculadora.setEstrategia(new SedexFrete());
        System.out.println("Frete SEDEX: R$ " + calculadora.calcular(10, 0));

        calculadora.setEstrategia(new PacFrete());
        System.out.println("Frete PAC: R$ " + calculadora.calcular(10, 0));

        calculadora.setEstrategia(new FreteGratis());
        System.out.println("Frete Grátis (valor > 200): R$ " + calculadora.calcular(10, 250));
    }
}
