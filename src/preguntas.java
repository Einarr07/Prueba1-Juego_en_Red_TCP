public class preguntas {
    private String enunciado;
    private String respuestaCorrecta;
    private int puntos;
    private long tiempoInicio;
    private String respuestaUsuario;
    private boolean esRespuestaCorrecta; // Cambiado el nombre para evitar conflicto con el método getRespuestaCorrecta()

    public preguntas(String enunciado, String respuestaCorrecta, int puntos) {
        this.enunciado = enunciado;
        this.respuestaCorrecta = respuestaCorrecta;
        this.puntos = puntos;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public int getPuntos() {
        return puntos;
    }

    public void iniciarTiempo() {
        tiempoInicio = System.currentTimeMillis();
    }

    public long obtenerTiempo() {
        return System.currentTimeMillis() - tiempoInicio;
    }

    public void setRespuestaUsuario(String respuestaUsuario) {
        this.respuestaUsuario = respuestaUsuario;
    }

    public String getRespuestaUsuario() {
        return respuestaUsuario;
    }

    public void setEsRespuestaCorrecta(boolean esRespuestaCorrecta) {
        this.esRespuestaCorrecta = esRespuestaCorrecta;
    }

    public boolean esRespuestaCorrecta() {
        return esRespuestaCorrecta;
    }
}
