package gb.jdk.server;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOControllerDefault implements IOController{
    /**
     * Записывает список строк в файл
     *
     * @param list Список строк
     * @param file Путь к файлу
     */
    @Override
    public void writeFile(List<String> list, File file) {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(file.getAbsolutePath()))){
            for (String line: list) {
                output.write(line);
                output.newLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден!");
        } catch (IOException e) {
            System.out.println("Ошибка доступа к файлу!");
        }
    }

    /**
     * Читает файл с диска
     *
     * @param file файл
     * @return Список строк
     */
    @Override
    public List<String> readFile(File file) {
        List<String> list = new ArrayList<>();
        String str;

        try (BufferedReader input = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
            while ((str = input.readLine()) != null) {
                list.add(str);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден!");
        } catch (IOException e) {
            System.out.println("Ошибка доступа к файлу!");
        }
        return list;
    }
}
