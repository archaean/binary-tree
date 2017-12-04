def is_search_tree(binary_tree):
    '''
    This method takes in a binary tree where a Node has the form:

    class Node:
        def __init__(self, value):
            self.value = value
            self.left = None
            self.right = None

    and outputs a Boolean indicating whether or not it is a binary search tree.
    Specifically, self.value > self.left* and self.value < self.right*

    e.g.
       _1_
      /   \ - is not a BST because 2 > 1 (left > value)
     2    3

       _2_
      /   \ - is a BST because 2 > 1 (left < value) and 2 < 3 (right < value)
     1    3

    :param binary_tree
    :return: Boolean
    '''

    # TODO Implement

    return True
