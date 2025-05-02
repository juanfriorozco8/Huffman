import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputText = "";

        System.out.println("¿Cómo deseas ingresar el texto?");
        System.out.println("1. Escribir el texto manualmente");
        System.out.println("2. Leer texto desde el archivo'");
        System.out.print("Elige una opción: ");
        String option = scanner.nextLine();

        if (option.equals("1")) {
            System.out.print("Escribe el texto aquí: ");
            inputText = scanner.nextLine();
        } else if (option.equals("2")) {
            try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                inputText = sb.toString().trim();
            } catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
                return;
            }
        } else {
            System.out.println("Opción no válida.");
            return;
        }

        HuffmanTree tree = new HuffmanTree();
        tree.build(inputText);
        String compressed = tree.encode(inputText);
        String decompressed = tree.decode(compressed);

        System.out.println("\n--- RESULTADOS ---");
        System.out.println("Texto original: " + inputText.length() + " caracteres");
        System.out.println("Texto comprimido: " + compressed.length() + " bits");
        System.out.println("Texto descomprimido: " + decompressed.length() + " caracteres");

        if (inputText.equals(decompressed)) {
            System.out.println("La descompresión fue exitosa.");
        } else {
            System.out.println("Error en la descompresión.");
        }

        System.out.println("\nFrecuencia de caracteres:");
        for (Map.Entry<Character, Integer> entry : tree.getFrequencies().entrySet()) {
            String charDisplay = (entry.getKey() == '\n') ? "\\n" : String.valueOf(entry.getKey());
            System.out.println("'" + charDisplay + "' : " + entry.getValue());
        }
    }
}
