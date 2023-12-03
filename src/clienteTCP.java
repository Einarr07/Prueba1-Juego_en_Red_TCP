import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class clienteTCP {
    public static void main(String[] args) {
        try {
            Socket socketCliente = new Socket("localhost", 5000);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true);

            BufferedReader entradaUsuario = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                // Leer la pregunta del servidor
                String pregunta = entrada.readLine();
                if (pregunta == null) {
                    break; // El servidor ha terminado
                }
                System.out.println("Pregunta: " + pregunta);

                // Enviar respuesta al servidor
                System.out.print("Tu respuesta: ");
                String respuesta = entradaUsuario.readLine();
                salida.println(respuesta);

                // Mostrar la respuesta del servidor
                String respuestaServidor = entrada.readLine();
                System.out.println("Servidor: " + respuestaServidor);
            }

            // Cerrar recursos
            entrada.close();
            salida.close();
            socketCliente.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
