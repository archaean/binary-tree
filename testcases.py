import unittest

from binarytree import _build_tree, stringify
from parameterized import parameterized

from exercise_bst import is_search_tree


class TestTree:

    def __init__(self, name, root, search_tree):
        self.name = name
        self.root = root
        self.search_tree = search_tree

    def stringify(self):
        return stringify(self.root)

    def __repr__(self):
        return self.name

    def __str__(self):
        return 'The tree \"%s\" is%s sorted:' % \
            (self.name, ('' if self.search_tree else ' not')) + \
            stringify(self.root)


test_trees = [TestTree('Linear', _build_tree([1, 2, 3, 4, 5, 6, 7]), False),
              TestTree('Sorted', _build_tree([4, 2, 6, 1, 3, 5, 7]), True),
              TestTree('Sort-ish', _build_tree([4, 2, 6, 1, 5, 3, 7]), False)]


class TreesUnitTest(unittest.TestCase):

    @parameterized.expand([(tt.name, tt) for tt in test_trees])
    def test_if_search_tree(self, name, tt):
        msg = None
        self.assertEqual(is_search_tree(tt.root), tt.search_tree, msg=msg)


def main():
    unittest.main(verbosity=2)

if __name__ == "__main__":
    main()
