package ru.vsu.cs.file_tree;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;

public class SimpleTree implements Tree {

    protected class SimpleTreeNode implements Tree.TreeNode {

        public File value;

        public String name;

        public ArrayList<TreeNode> children;

        public SimpleTreeNode(File value, String name) {
            this.value = value;
            this.name = name;
            this.children = new ArrayList<>();
        }
        @Override
        public String getName() {
            return name;
        }

        @Override
        public File getValue() {
            return value;
        }

        @Override
        public TreeNode getNode(int index) {
            return children.get(index);
        }

        public ArrayList<TreeNode> getChildren() {
            return children;
        }
    }

    protected SimpleTreeNode root = null;

    public SimpleTree() {

    }

    @Override
    public TreeNode getRoot() {
        return root;
    }

    public void directoryAsTree(String directory) {
        SimpleTreeNode root = addFilesToTree(new File(directory));
        this.root = root;
    }

    private SimpleTreeNode addFilesToTree(File currentDirectory) {
        String parentValue = currentDirectory.getName();
        File[] filesInDirectory = currentDirectory.listFiles();
        SimpleTreeNode parentNode = new SimpleTreeNode(currentDirectory, parentValue);
        if(filesInDirectory != null) {
            for (File file : filesInDirectory) {
                if (file.isDirectory()) {
                    parentNode.children.add(addFilesToTree(file));
                } else {
                    parentNode.children.add(new SimpleTreeNode(file, file.getName()));
                }
            }
        }
        return parentNode;
    }

    public static List<File> findFileByExtension(String fileExtension, Tree tree) {
        if (tree.getRoot() == null) {
            return null;
        }
        Queue<TreeNode> treeNodeQueue = new LinkedList<>();
        treeNodeQueue.add(tree.getRoot());
        String mask = maskToRegex("*." + fileExtension);
        List<File> resultFiles = new ArrayList<>();
        while (!treeNodeQueue.isEmpty()) {
            int queueSize = treeNodeQueue.size();
            while (queueSize > 0) {
                Tree.TreeNode currNode = treeNodeQueue.peek();
                treeNodeQueue.remove();
                if(currNode.getChildren().size() == 0) {
                    if (currNode.getName().matches(mask)) {
                        resultFiles.add(currNode.getValue());
                    }
                } else {
                    for (int i = 0; i < currNode.getChildren().size(); i++) {
                        treeNodeQueue.add(currNode.getNode(i));
                    }
                }
                queueSize--;
            }
        }
        return resultFiles;
    }

    public static List<File> findFileByName(String fileName, Tree tree) {
        if (tree.getRoot() == null) {
            return null;
        }
        Queue<TreeNode> treeNodeQueue = new LinkedList<>();
        treeNodeQueue.add(tree.getRoot());
        String mask = maskToRegex(fileName + ".*");
        List<File> resultFiles = new ArrayList<>();
        while (!treeNodeQueue.isEmpty()) {
            int queueSize = treeNodeQueue.size();
            while (queueSize > 0) {
                Tree.TreeNode currNode = treeNodeQueue.peek();
                treeNodeQueue.remove();
                if(currNode.getChildren().size() == 0) {
                    if (currNode.getName().matches(mask)) {
                        resultFiles.add(currNode.getValue());
                    }
                } else {
                    for (int i = 0; i < currNode.getChildren().size(); i++) {
                        treeNodeQueue.add(currNode.getNode(i));
                    }
                }
                queueSize--;
            }
        }
        return resultFiles;
    }

    private static String maskToRegex(String mask) {
        mask = mask.replace(".", "\\.");
        mask = mask.replace("-", "\\-");
        mask = mask.replace("(", "\\(");
        mask = mask.replace(")", "\\)");
        mask = mask.replace("_", "\\_");
        mask = mask.replace("*", ".*");
        mask = mask.replace("?", ".");
        return mask;
    }

}

