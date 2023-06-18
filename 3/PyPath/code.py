# 这是一段示例代码，用于展示二叉树的中序遍历
import random

class Node:
	def __init__(self, key):
		self.left = None
		self.right = None
		self.val = key

def insert(root, node):
	if root is None:
		root = node
	else:
		if root.val < node.val:
			if root.right is None:
				root.right = node
			else:
				insert(root.right, node)
		else:
			if root.left is None:
				root.left = node
			else:
				insert(root.left, node)

def inorder(root):
	if root:
		inorder(root.left)
		print(root.val)
		inorder(root.right)

root = Node(random.randint(1, 100))

for _ in range(10):
	insert(root, Node(random.randint(1, 100)))

print("Inorder traversal of the tree:")
inorder(root)