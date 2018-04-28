import java.util.ArrayList;

public class BST<K, V> {
	
	private Node<K, V> root;
	private int size;
	
	public Person search(Node<K, V> root, int target) throws NullPointerException {
		if (target > root.getValue())
			return search(root.getRight(), target);
		else if (target == root.getValue())
			return root.getKey();
		else
			return search(root.getLeft(), target);
	}
	
	public void insert(Node<K, V> root, Person person, int insertV) {
		if (root == null) {
			root = new Node<K, V>();
			root.setKey(person);
			root.setValue(insertV);
			this.setRoot(root);
		}
		else
			if (insertV < root.getValue())
				if (root.getLeft() != null) {
					insert(root.getLeft(), person, insertV);
				} else {
					Node<K, V> insertNode = new Node<K, V>(person, insertV);
					root.setLeft(insertNode);
					insertNode.setLeft(true);
					insertNode.setRight(false);
					this.incrementSize();
					isLeaf(insertNode);
				}
			else
				if (root.getRight() != null) {
					insert(root.getRight(), person, insertV);
				} else {
					Node<K, V> insertNode = new Node<K, V>(person, insertV);
					root.setRight(insertNode);
					insertNode.setLeft(false);
					insertNode.setRight(true);
					this.incrementSize();
					isLeaf(insertNode);
				}
	}
	
	public void inOrderTraversal(Node<K, V> root) {
		if (root == null)
			return;
		inOrderTraversal(root.getLeft());
		System.out.println(root.getValue());
		inOrderTraversal(root.getRight());
	}
	
	public ArrayList<Person> getHalfSiblings(
			Node <Person, Integer> root, Person person, ArrayList<Person> halfSiblings) {
		if (root == null)
			return halfSiblings;
		getHalfSiblings(root.getLeft(), person, halfSiblings);
		if (
				(root.getKey().getFather() == person.getFather()) ^
				(root.getKey().getMother() == person.getMother())) {
			halfSiblings.add(root.getKey());
		}
		getHalfSiblings(root.getRight(), person, halfSiblings);
		return halfSiblings;
	}
	
	public ArrayList<Person> getFullSiblings(
			Node<Person, Integer> root, Person person, ArrayList<Person> fullSiblings) {
		if (root == null)
			return fullSiblings;
		getFullSiblings(root.getLeft(), person, fullSiblings);
		if (
				(root.getKey().getFather() == person.getFather()) &&
				(root.getKey().getMother() == person.getMother())  ) {
			if (root.getKey().getSSN() != person.getSSN())
				fullSiblings.add(root.getKey());
		}
		getFullSiblings(root.getRight(), person, fullSiblings);
		return fullSiblings;
	}
	
	public ArrayList<Person> getChildren(
			Node<Person, Integer> root, Person person, ArrayList<Person> children) {
		if (root == null)
			return children;
		getChildren(root.getLeft(), person, children);
		if (
				(root.getKey().getFather() == person.getSSN()) || 
				(root.getKey().getMother() == person.getSSN())) {
			children.add(root.getKey());
		}
		getChildren(root.getRight(), person, children);
		return children;
	}
	
	public ArrayList<Person> getMutualFriends(
			Node<Person, Integer> root, ArrayList<Person> mutualFriends, Person person) {
		if (root == null)
			return mutualFriends;
		getMutualFriends(root.getLeft(), mutualFriends, person);
		if (person.isFriendsWith(root.getKey()) && root.getKey().isFriendsWith(person))
			mutualFriends.add(root.getKey());
		getMutualFriends(root.getRight(), mutualFriends, person);
		return mutualFriends;
	}
	
	public ArrayList<Person> getInverseFriends(
			Node<Person, Integer> root, ArrayList<Person> inverseFriends, Person person) {
		if (root == null)
			return inverseFriends;
		getInverseFriends(root.getLeft(), inverseFriends, person);
		if (root.getKey().isFriendsWith(person))
			inverseFriends.add(root.getKey());
		getInverseFriends(root.getRight(), inverseFriends, person);
		return inverseFriends;
	}
	
	public void incrementSize() {
		this.setSize(this.getSize() + 1);
	}
	
	public void isLeaf(Node<K, V> node) {
		if (node.getLeft() != null || node.getRight() != null)
			node.setLeaf(false);
		else
			node.setLeaf(true);
	}
	
	public Node<K, V> getRoot() {
		return root;
	}

	public void setRoot(Node<K, V> root) {
		this.root = root;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	
	static final class Node<K, V> {
		
		private Person key;
		private int value;
		private Node<K, V> left;
		private Node<K, V> right;
		private boolean isLeaf;
		private boolean isRight;
		private boolean isLeft;
		
		public Node() {}
		
		public Node(Person key, int value) {
			this.setKey(key);
			this.setValue(value);
		}

		public Person getKey() {
			return key;
		}

		public void setKey(Person key) {
			this.key = key;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public Node<K, V> getLeft() {
			return left;
		}

		public void setLeft(Node<K, V> left) {
			this.left = left;
		}

		public Node<K, V> getRight() {
			return right;
		}

		public void setRight(Node<K, V> right) {
			this.right = right;
		}

		public boolean isLeaf() {
			return isLeaf;
		}

		public void setLeaf(boolean isLeaf) {
			this.isLeaf = isLeaf;
		}

		public boolean isRight() {
			return isRight;
		}

		public void setRight(boolean isRight) {
			this.isRight = isRight;
		}

		public boolean isLeft() {
			return isLeft;
		}

		public void setLeft(boolean isLeft) {
			this.isLeft = isLeft;
		}
		
	}
	
}