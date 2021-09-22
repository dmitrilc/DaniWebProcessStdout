package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Entry {

    private static final Runtime runtime = Runtime.getRuntime();

    public static void main(String[] args) {
        String pyContent = """
                    for i in range(1, 6):
                        print(f'Found secret {i}')
                    """; //15
        Path path = Path.of(".", "test.py"); //16

        Path script = createPyScript(path, pyContent); //17
        execPyScript(script); //18

        String ipconfig = "ipconfig /all";
        String systeminfo = "systeminfo";
        execSysCommands(ipconfig, systeminfo);
    }

    private static Path createPyScript(Path path, String content){ //1
        //Creates the script if does not exists, else do nothing.
        try {
            Files.writeString(path, content, StandardOpenOption.CREATE); //2
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path; //3
    }

    private static void execPyScript(Path script){ //4
        try {
            Process process = runtime.exec("python " + script); //5

            try (BufferedReader reader = process.inputReader()){ //6
                reader.lines() //7
                        .forEach(System.out::println); //8
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void execSysCommands(String... commands){ //9
        try {
            for (String command : commands){ //10
                Process process = runtime.exec(command); //11

                try (BufferedReader reader = process.inputReader()){ //12
                    reader.lines() //13
                            .forEach(System.out::println); //14
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}