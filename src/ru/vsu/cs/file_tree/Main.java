package ru.vsu.cs.file_tree;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SimpleTree tree = new SimpleTree();

        //Добавление директории System32 в дерево
        tree.directoryAsTree("C:\\Windows\\System32");

        //Поиск и вывод в консоль файлов с расширением dll в директории System32
        List<File> result1 = SimpleTree.findFileByExtension("dll", tree);
        for(File r: result1) {
            System.out.println(r.toString());
        }

        System.out.println();

        //Поиск и вывод в консоль файлов с названием "RBDSTAMIL99" в директории System32
        List<File> result2 = SimpleTree.findFileByName("RBDSTAMIL99", tree);
        for(File r: result2) {
            System.out.println(r.toString());
        }
    }
}

