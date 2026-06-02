package ec.edu.uteq.distribuidas.common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CsvLogger {

    public static void guardarResultado(String archivo, String metodo, int envios, double latenciaPromedioMs) {
        File file = new File(archivo);
        boolean archivoNuevo = !file.exists();

        try {
            File carpeta = file.getParentFile();

            if (carpeta != null && !carpeta.exists()) {
                carpeta.mkdirs();
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
                if (archivoNuevo) {
                    writer.println("metodo,envios,latencia_promedio_ms");
                }

                writer.println(metodo + "," + envios + "," + latenciaPromedioMs);
            }

        } catch (IOException e) {
            System.out.println("Error al guardar CSV: " + e.getMessage());
        }
    }
}