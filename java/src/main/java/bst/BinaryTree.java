package bst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class BinaryTree {

    static class Node {
        Integer val;
        Node left, right;

        Node(Integer val) {
            this.val = val;
            left = null;
            right = null;
        }
    }

    static Node build(Integer[] values) {

        List<Node> nodes = Arrays.stream(values).map(Node::new).collect(Collectors.toList());

        for (int idx = 1; idx < nodes.size(); idx++) {
            Node node = nodes.get(idx);
            if (node.val != null) {
                int parent_index = Math.floorDiv(idx - 1, 2);
                Node parent = nodes.get(parent_index);
                if (parent.val == null) {
                    throw new RuntimeException("Parent node missing at index " + parent_index);
                }
                if (idx % 2 != 0) {
                    parent.left = node;
                } else {
                    parent.right = node;
                }
            }
        }

        return nodes.get(0);
    }

    static class Box {
        String[] box;
        int boxWidth;
        int rootStart;
        int rootEnd;

        Box(String[] box, int boxWidth, int rootStart, int rootEnd) {
            this.box = box;
            this.boxWidth = boxWidth;
            this.rootStart = rootStart;
            this.rootEnd = rootEnd;
        }
    }

    static String prettyPrint(Node node) {
        return String.join("\n", buildTreeString(node).box);
    }

    static Box buildTreeString(Node root) {

        if (root == null || root.val == null) {
            return new Box(new String[]{}, 0, 0, 0);
        }

        StringBuilder line1 = new StringBuilder();
        StringBuilder line2 = new StringBuilder();

        String nodeRepr = root.val.toString();
        int newRootWidth = nodeRepr.length(), gapSize = nodeRepr.length();

        // Get the left and right sub-boxes, their widths, and root repr positions
        Box leftBox = buildTreeString(root.left);
        Box rightBox = buildTreeString(root.right);

        // Draw the branch connecting the current root node to the left sub-box
        // Pad the line with whitespaces where necessary
        int newRootStart = 0;
        if (leftBox.boxWidth > 0) {
            int lRoot = Math.floorDiv((leftBox.rootStart + leftBox.rootEnd), 2) + 1;
            line1.append(String.join("", Collections.nCopies(lRoot + 1, " ")));
            line1.append(String.join("", Collections.nCopies(leftBox.boxWidth - lRoot, "_")));
            line2.append(String.join("", Collections.nCopies(lRoot, " ")));
            line2.append("/");
            line2.append(String.join("", Collections.nCopies(leftBox.boxWidth - lRoot, " ")));
            newRootStart = leftBox.boxWidth + 1;
            gapSize += 1;
        }

        // Draw the representation of the current root node
        line1.append(nodeRepr);
        line2.append(String.join("", Collections.nCopies(newRootWidth, " ")));

        // Draw the branch connecting the current root node to the right sub-box
        // Pad the line with whitespaces where necessary
        if (rightBox.boxWidth > 0) {
            int rRoot = Math.floorDiv((rightBox.rootStart + rightBox.rootEnd), 2);
            line1.append(String.join("", Collections.nCopies(rRoot, "_")));
            line1.append(String.join("", Collections.nCopies(rightBox.boxWidth - rRoot, " ")));
            line2.append(String.join("", Collections.nCopies(rRoot, " ")));
            line2.append("\\");
            line2.append(String.join("", Collections.nCopies(rightBox.boxWidth - rRoot, " ")));
            gapSize += 1;
        }
        int newRootEnd = newRootStart + newRootWidth - 1;

        // Combine the left and right sub-boxes with the branches drawn above
        String gap = String.join("", Collections.nCopies(gapSize, " "));
        List<String> newBox = new ArrayList<String>(Arrays.asList(String.join("", line1.toString()), String.join("", line2.toString())));
        for (int i = 0; i < Math.max(leftBox.box.length, rightBox.box.length); i++) {
            String lLine = i < leftBox.box.length ? leftBox.box[i] : String.join("", Collections.nCopies(leftBox.boxWidth, " "));
            String rLine = i < rightBox.box.length ? rightBox.box[i] : String.join("", Collections.nCopies(rightBox.boxWidth, " "));
            newBox.add(lLine + gap + rLine);
        }

        // Return the new box, its width and its root repr positions
        return new Box(newBox.toArray(new String[]{}), newBox.get(0).length(), newRootStart, newRootEnd);
    }
}
