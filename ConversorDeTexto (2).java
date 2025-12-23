package util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConversorDeTextoTest {

    // ---------- ParaMaiusculas ----------
    @Test
    void testParaMaiusculasComTextoNormal() {
        String resultado = ConversorDeTexto.paraMaiusculas("teste");
        assertEquals("TESTE", resultado);
    }

    @Test
    void testParaMaiusculasComTextoJaMaiusculo() {
        String resultado = ConversorDeTexto.paraMaiusculas("TESTE");
        assertEquals("TESTE", resultado);
    }

    @Test
    void testParaMaiusculasComNull() {
        String resultado = ConversorDeTexto.paraMaiusculas(null);
        assertNull(resultado);
    }

    // ---------- ParaMinusculas ----------
    @Test
    void testParaMinusculasComTextoNormal() {
        String resultado = ConversorDeTexto.paraMinusculas("TESTE");
        assertEquals("teste", resultado);
    }

    @Test
    void testParaMinusculasComTextoJaMinusculo() {
        String resultado = ConversorDeTexto.paraMinusculas("teste");
        assertEquals("teste", resultado);
    }

    @Test
    void testParaMinusculasComNull() {
        String resultado = ConversorDeTexto.paraMinusculas(null);
        assertNull(resultado);
    }

    // ---------- Inverter ----------
    @Test
    void testInverterComTextoNormal() {
        String resultado = ConversorDeTexto.inverter("abcd");
        assertEquals("dcba", resultado);
    }

    @Test
    void testInverterComTextoPalindromo() {
        String resultado = ConversorDeTexto.inverter("arara");
        assertEquals("arara", resultado);
    }

    @Test
    void testInverterComNull() {
        String resultado = ConversorDeTexto.inverter(null);
        assertNull(resultado);
    }

    // ---------- ContarPalavras ----------
    @Test
    void testContarPalavrasComTextoNormal() {
        int resultado = ConversorDeTexto.contarPalavras("Isto é um teste");
        assertEquals(4, resultado);
    }

    @Test
    void testContarPalavrasComEspacosExtras() {
        int resultado = ConversorDeTexto.contarPalavras("   Isto   é   um   teste   ");
        assertEquals(4, resultado);
    }

    @Test
    void testContarPalavrasComStringVazia() {
        int resultado = ConversorDeTexto.contarPalavras("");
        assertEquals(0, resultado);
    }

    @Test
    void testContarPalavrasComNull() {
        int resultado = ConversorDeTexto.contarPalavras(null);
        assertEquals(0, resultado);
    }

    // ---------- RemoverEspacosExtras ----------
    @Test
    void testRemoverEspacosExtrasComTextoNormal() {
        String resultado = ConversorDeTexto.removerEspacosExtras("  Olá   mundo  !");
        assertEquals("Olá mundo !", resultado);
    }

    @Test
    void testRemoverEspacosExtrasSemEspacos() {
        String resultado = ConversorDeTexto.removerEspacosExtras("TextoSimples");
        assertEquals("TextoSimples", resultado);
    }

    @Test
    void testRemoverEspacosExtrasComNull() {
        String resultado = ConversorDeTexto.removerEspacosExtras(null);
        assertNull(resultado);
    }
}
