package bst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class BinaryTree {

    /**
     * The basic element of a binary tree with a value and a (optional) left and right child node.
     */
    static class Node<T> {
        T val;
        Node left, right;

        Node(T val) {
            this.val = val;
            left = null;
            right = null;
        }
    }

    /**
     * Build a tree from `list representation`_ and return its root node.
     * list representation: https://en.wikipedia.org/wiki/Binary_tree#Arrays
     *
     * Note: This a port of the tree builder via list representation in https://github.com/joowani/binarytree
     * (has an MIT licence)
     *
     * @param values the values used to build the Binary Tree
     * @return the root of the binary tree
     */
    static <T> Node<T> build(T[] values) {

        List<Node<T>> nodes = Arrays.stream(values).map(Node::new).collect(Collectors.toList());

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

    /**
     * Gets the pretty-print string of a an entire binary tree.
     *
     * @param node Root node of the binary tree.
     * @return the pretty-print string representing the entire binary tree
     */
    static String prettyPrint(Node node) {
        return String.join("\n", buildTreeString(node).content);
    }

    /**
     * POJO to recursively generate the pretty-print string for the binary tree.
     */
    static class PrettyPrintBox {
        String[] content;
        int boxWidth;
        int rootStart;
        int rootEnd;

        PrettyPrintBox(String[] content, int boxWidth, int rootStart, int rootEnd) {
            this.content = content;
            this.boxWidth = boxWidth;
            this.rootStart = rootStart;
            this.rootEnd = rootEnd;
        }
    }

    /**
     * Recursively walk down the binary tree and build a pretty-print string.
     * In each recursive call, a "box" of characters visually representing the
     * current (sub)tree is constructed line by line. Each line is padded with
     * whitespaces to ensure all lines in the box have the same length. Then the
     * box, its width, and start-end positions of its root node value repr string
     * (required for drawing branches) are sent up to the parent call. The parent
     * call then combines its left and right sub-boxes to build a larger box etc.
     *
     * Note: This a slimmed down port of the pretty printing function in https://github.com/joowani/binarytree
     * (has an MIT licence)
     *
     * @param root Root node of the binary tree.
     * @return Box of characters visually representing the current subtree, width
     *         of the box, and start-end positions of the repr string of the new root
     *         node value.
     */
    private static PrettyPrintBox buildTreeString(Node root) {

        if (root == null || root.val == null) {
            return new PrettyPrintBox(new String[]{}, 0, 0, 0);
        }

        StringBuilder line1 = new StringBuilder();
        StringBuilder line2 = new StringBuilder();

        String nodeRepr = root.val.toString();
        int newRootWidth = nodeRepr.length(), gapSize = nodeRepr.length();

        // Get the left and right sub-boxes, their widths, and root repr positions
        PrettyPrintBox leftPrettyPrintBox = buildTreeString(root.left);
        PrettyPrintBox rightPrettyPrintBox = buildTreeString(root.right);

        // Draw the branch connecting the current root node to the left sub-content
        // Pad the line with whitespaces where necessary
        int newRootStart = 0;
        if (leftPrettyPrintBox.boxWidth > 0) {
            int lRoot = Math.floorDiv((leftPrettyPrintBox.rootStart + leftPrettyPrintBox.rootEnd), 2) + 1;
            line1.append(String.join("", Collections.nCopies(lRoot + 1, " ")));
            line1.append(String.join("", Collections.nCopies(leftPrettyPrintBox.boxWidth - lRoot, "_")));
            line2.append(String.join("", Collections.nCopies(lRoot, " ")));
            line2.append("/");
            line2.append(String.join("", Collections.nCopies(leftPrettyPrintBox.boxWidth - lRoot, " ")));
            newRootStart = leftPrettyPrintBox.boxWidth + 1;
            gapSize += 1;
        }

        // Draw the representation of the current root node
        line1.append(nodeRepr);
        line2.append(String.join("", Collections.nCopies(newRootWidth, " ")));

        // Draw the branch connecting the current root node to the right sub-content
        // Pad the line with whitespaces where necessary
        if (rightPrettyPrintBox.boxWidth > 0) {
            int rRoot = Math.floorDiv((rightPrettyPrintBox.rootStart + rightPrettyPrintBox.rootEnd), 2);
            line1.append(String.join("", Collections.nCopies(rRoot, "_")));
            line1.append(String.join("", Collections.nCopies(rightPrettyPrintBox.boxWidth - rRoot, " ")));
            line2.append(String.join("", Collections.nCopies(rRoot, " ")));
            line2.append("\\");
            line2.append(String.join("", Collections.nCopies(rightPrettyPrintBox.boxWidth - rRoot, " ")));
            gapSize += 1;
        }
        int newRootEnd = newRootStart + newRootWidth - 1;

        // Combine the left and right sub-boxes with the branches drawn above
        String gap = String.join("", Collections.nCopies(gapSize, " "));
        List<String> newContent =
                new ArrayList<>(Arrays.asList(String.join("", line1.toString()), String.join("", line2.toString())));
        for (int i = 0; i < Math.max(leftPrettyPrintBox.content.length, rightPrettyPrintBox.content.length); i++) {
            String lLine = i < leftPrettyPrintBox.content.length
                    ? leftPrettyPrintBox.content[i]
                    : String.join("", Collections.nCopies(leftPrettyPrintBox.boxWidth, " "));
            String rLine = i < rightPrettyPrintBox.content.length
                    ? rightPrettyPrintBox.content[i]
                    : String.join("", Collections.nCopies(rightPrettyPrintBox.boxWidth, " "));
            newContent.add(lLine + gap + rLine);
        }

        // Return the new content, its width and its root repr positions
        return new PrettyPrintBox(
                newContent.toArray(new String[]{}), newContent.get(0).length(), newRootStart, newRootEnd);
    }
}
