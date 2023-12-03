import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class servidorTCP {
    private List<preguntas> preguntas;
    private int indicePreguntaActual;
    private int puntos;
    private long tiempoTotal;

    public servidorTCP() {
        preguntas = new ArrayList<>();
        preguntas.add(new preguntas("¿Cuál es la capital de Francia?", "París", 100));
        preguntas.add(new preguntas("¿Cuánto es 2 + 2?", "4", 100));
        preguntas.add(new preguntas("¿En qué año se llevó a cabo la Revolución Rusa?", "1917", 100));
        preguntas.add(new preguntas("¿Cuál es la capital de Australia?","Canberra",100));
        preguntas.add(new preguntas("¿Quién fue la primera mujer en recibir el Premio Nobel de la Paz?", "Marie Curie",100));

        indicePreguntaActual = 0;
        puntos = 0;
        tiempoTotal = 0;
    }

    public static void main(String[] args) {
        servidorTCP servidor = new servidorTCP();
        servidor.iniciar();
    }

    public void iniciar() {
        try {
            ServerSocket servidorSocket = new ServerSocket(5000);
            System.out.println("Servidor listo para recibir conexiones.");

            while (true) {
                Socket socketCliente = servidorSocket.accept();
                InetAddress clienteAddress = socketCliente.getInetAddress();
                System.out.println("Nuevo cliente conectado desde: " + clienteAddress.getHostAddress());

                hiloCliente hilo = new hiloCliente(socketCliente, this);
                hilo.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized preguntas obtenerPreguntaActual() {
        if (indicePreguntaActual < preguntas.size()) {
            preguntas.get(indicePreguntaActual).iniciarTiempo(); // Iniciar tiempo al obtener la nueva pregunta
            return preguntas.get(indicePreguntaActual);
        }
        return null;
    }

    public synchronized void incrementarIndicePregunta() {
        indicePreguntaActual++;
    }

    public synchronized void sumarPuntos(int puntosGanados) {
        puntos += puntosGanados;
    }

    public synchronized int obtenerPuntos() {
        return puntos;
    }

    public synchronized void sumarTiempoTotal(long tiempo) {
        tiempoTotal += tiempo;
    }

    public synchronized void mostrarResultado(PrintWriter salida) {
        System.out.println("Juego terminado. Has respondido " + preguntas.size() + " preguntas en " +
                TimeUnit.MILLISECONDS.toSeconds(tiempoTotal) + " segundos y el total de puntos obtenidos es de " + puntos);

        // Mostrar información de cada pregunta
        for (preguntas pregunta : preguntas) {
            System.out.println("Pregunta: " + pregunta.getEnunciado());
            System.out.println("Tu respuesta: " + pregunta.getRespuestaUsuario());
            if (pregunta.esRespuestaCorrecta()) {
                System.out.println("Servidor: Respuesta correcta, obtienes " + pregunta.getPuntos() + " puntos");
            } else {
                System.out.println("Servidor: Respuesta incorrecta.");
            }
        }
    }

    public synchronized void clienteDesconectado() {
        System.out.println("Cliente desconectado.");
    }
}
