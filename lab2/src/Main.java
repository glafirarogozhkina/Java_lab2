import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Запрос названия входного файла
        System.out.println("Input name of input file:");
        String finname = scanner.nextLine();

        // Проверка и открытие файла
        File fin = new File(finname);
        if (!fin.exists()) {
            System.err.println("File " + finname + " not found!");
            System.exit(-1);
        }
        else
            System.out.println("File " + finname + " exists");

        // Запрос названия выходного файла
        System.out.println("Input name of output file:");
        String foutname = scanner.nextLine();
        // Проверка пути к файлу на корректность
        if (!foutname.contains(".")) {
            System.err.println("File path must contain an extension");
            System.exit(-2);
        }

        Map<Character, Integer> lettersDict = new HashMap<>(); // Словарь английских букв
        // Открываем входной файл для чтения
        try(FileReader reader = new FileReader(finname))
        {
            int code;
            // Посимвольно считываем файл
            while((code=reader.read())!=-1) {
                // Проверим, является ли символ английской буквой
                if (Character.isLetter(code)
                        && Character.UnicodeBlock.of(code).equals(Character.UnicodeBlock.BASIC_LATIN)) {

                    char letter = (char)code;

                    lettersDict.putIfAbsent(letter, 0); // Добавим запись, если буква встречается впервые
                    lettersDict.put(letter, lettersDict.get(letter) + 1); // Увеличиваем количество вхождений

                }
            }
        }
        catch(IOException ex){
            System.err.println(ex.getMessage());
        }

        // Создаем или открываем выходной файл на запись
        try (FileWriter writer = new FileWriter(foutname, false))
        {
            // Добавляем записи из словаря в файл
            for (Map.Entry<Character, Integer> item: lettersDict.entrySet()) {
                writer.append(String.valueOf(item.getKey()))
                        .append(": ")
                        .append(String.valueOf(item.getValue()))
                        .append("\n");
            }

            writer.flush(); // Выводим буфер в файл
        }
        catch(IOException ex){
            System.err.println(ex.getMessage());
        }
    }
}