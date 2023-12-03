import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class hiloCliente extends Thread {
    private Socket socketCliente;
    private servidorTCP servidor;

    public hiloCliente(Socket socketCliente, servidorTCP servidor) {
        this.socketCliente = socketCliente;
        this.servidor = servidor;
    }

    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true);

            while (true) {
                preguntas preguntaActual = servidor.obtenerPreguntaActual();
                if (preguntaActual == null) {
                    break;
                }

                salida.println(preguntaActual.getEnunciado());

                long tiempoInicio = System.currentTimeMillis(); // Inicio del tiempo de respuesta

                String respuestaCliente = entrada.readLine();

                long tiempoFin = System.currentTimeMillis(); // Fin del tiempo de respuesta

                boolean respuestaCorrecta = preguntaActual.getRespuestaCorrecta().equalsIgnoreCase(respuestaCliente);
                preguntaActual.setRespuestaUsuario(respuestaCliente);
                preguntaActual.setEsRespuestaCorrecta(respuestaCorrecta);

                if (respuestaCorrecta) {
                    servidor.sumarPuntos(preguntaActual.getPuntos());
                }

                servidor.incrementarIndicePregunta();
                servidor.sumarTiempoTotal(tiempoFin - tiempoInicio);

                // Mostrar el tiempo de respuesta al cliente
                salida.println("Tiempo de respuesta: " + TimeUnit.MILLISECONDS.toSeconds(tiempoFin - tiempoInicio) + " segundos");
            }

            servidor.mostrarResultado(salida);  // Enviar información detallada al cliente
            servidor.clienteDesconectado();

            // Cerrar recursos
            entrada.close();
            salida.close();
            socketCliente.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
