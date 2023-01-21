package ru.vsu.cs.file_tree;

import java.awt.Color;
import java.io.File;
import java.util.*;


public interface Tree {

    interface TreeNode {

        File getValue();

        TreeNode getNode(int index);

        ArrayList<TreeNode> getChildren();

        String getName();

    }

    TreeNode getRoot();


}

