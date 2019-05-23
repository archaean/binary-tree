package bst;

import java.util.Arrays;
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
}
