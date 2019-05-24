package bst;

public class ExerciseBst {
    /**
     * Method to check if a supplied binary tree node is a binary search tree.
     *
     * @param node the binary tree node to verify
     * @return a bool indicating whether or not it is a binary search tree;
     *         i.e. values of all left nodes are less than a node's value
     *         and values of all right nodes are greater than a node's value.
     *
     *     e.g.
     *        _1_
     *       /   \ - is not a BST because 2 > 1 (left > value)
     *      2    3
     *        _2_
     *       /   \ - is a BST because 2 > 1 (left < value) and 2 < 3 (right < value)
     *      1    3
     */
    static <T extends Comparable> Boolean isBinarySearchTree(BinaryTree.Node<T> node) {
        // TODO Implement
        return true;
    }
}
