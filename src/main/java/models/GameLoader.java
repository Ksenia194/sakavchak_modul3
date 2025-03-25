package models;


import java.io.File;


public class GameLoader {
    public static void main(String[] args) throws Exception {
        File file = new File("src/main/resources/quest.json");
        if (!file.exists()) {
            throw new RuntimeException("Файл quests.json не знайдено!");
        }
        if (file.length() == 0) {
            throw new RuntimeException("Файл quests.json порожній!");
        }

    }
}
