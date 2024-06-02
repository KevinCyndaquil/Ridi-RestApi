package ridi.modelos;

import org.junit.jupiter.api.Test;
import ridi.modelos.persistence.*;

import static org.junit.jupiter.api.Assertions.*;

public class DispositivoTest {
    @Test
    void testInstanciado() {
        Computadora computadora = new Computadora();
        assertEquals(
                Dispositivo.Instancias.COMPUTADORA,
                computadora.instanciado());

        Periferico periferico = new Periferico();
        assertEquals(
                Dispositivo.Instancias.PERIFERICO,
                periferico.instanciado());

        MemoriaRam memoriaRam = new MemoriaRam();
        assertEquals(
                Dispositivo.Instancias.MEMORIA_RAM,
                memoriaRam.instanciado());

        DiscoDuro discoDuro = new DiscoDuro();
        assertEquals(
                Dispositivo.Instancias.DISCO_DURO,
                discoDuro.instanciado());

        Procesador procesador = new Procesador();
        assertEquals(
                Dispositivo.Instancias.PROCESADOR,
                procesador.instanciado());
    }
}
