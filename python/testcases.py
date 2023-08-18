import unittest as unittest

from binarytree import build
from parameterized import parameterized

from exercise_bst import is_search_tree


class TestTree:

    def __init__(self, name, root):
        self.name = name
        self.root = root

    def __repr__(self):
        return self.name

    def __str__(self):
        return 'The tree \"%s\" is%s sorted:' % \
            (self.name, ('' if self.root.is_bst else ' not')) + \
            self.root.__str__()


test_trees = [
    TestTree('Linear', build([1, 2, 3, 4, 5, 6, 7])),
    TestTree('Sorted', build([4, 2, 6, 1, 3, 5, 7])),
    TestTree('Sort-ish', build([4, 2, 6, 1, 5, 3, 7]))
]


class TreesUnitTest(unittest.TestCase):

    @parameterized.expand([(tt,) for tt in test_trees])
    def test_if_search_tree(self, tt):
        self.assertEqual(is_search_tree(tt.root), tt.root.is_bst, msg=tt)


def main():
    unittest.main(verbosity=2)

if __name__ == "__main__":
    main()
