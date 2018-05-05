import java.util.ArrayList;

/**
 * This class is used to represent a community if Person objects as well as a "friends list" of
 * sorts for each individual Person object.
 * @author juliansmithdeniro 
 * @param <K> Any type that will be used for the key values of each node.
 * @param <V> Any type that will be used for the comparable value of each node.
 * @version 1.0
 */
public class BST<K, V> {
	
	/**
	 * A Node which is the root of the BST.
	 */
	private Node<K, V> root;
	
	/**
	 * An int representing how many nodes are currently in the BST.
	 */
	private int size;
	
	/**
	 * Default constructor.
	 */
	public BST() {}
	
	/**
	 * Search function which returns the specified Person being held in the located Node..
	 * @param root Is a Node representing the root of the current serve space.
	 * @param target Is the target value being searched for; typically the SSN of a Person.
	 * @return A Person object.
	 * @throws NullPointerException
	 */
	public Person search(Node<K, V> root, int target) throws NullPointerException {
		if (target > root.getValue())
			return search(root.getRight(), target);
		else if (target == root.getValue())
			return root.getKey();
		else
			return search(root.getLeft(), target);
	}
	
	/**
	 * Insert function which inserts a Person object into the BST.
	 * @param root a Node representing the root of the current serve space.
	 * @param person The person wished to be inserted into the BST.
	 * @param insertV The value (SSN) of the person being inserted into the BST.
	 */
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
	
	/**
	 * An in order traversal of the BST which prints the value of each Node.
	 * @param root Is a Node representing the root of the current serve space.
	 */
	public void inOrderTraversal(Node<K, V> root) {
		if (root == null)
			return;
		inOrderTraversal(root.getLeft());
		System.out.println(root.getValue());
		inOrderTraversal(root.getRight());
	}
	
	/**
	 * Retrieves the half siblings of the specified Person.
	 * @param root Is a Node representing the root of the current serve space.
	 * @param person The Person whose half siblings you would like to find.
	 * @param halfSiblings An ArrayList<Person> which is used in the recursive function
	 * to store the half siblings found.
	 * @return An ArrayList<Person> of the half siblings of the Person.
	 */
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
	
	/**
	 * Retrieves the full siblings of the specified Person.
	 * @param root Is a Node representing the root of the current serve space.
	 * @param person The Person whose full siblings you would like to find.
	 * @param fullSiblings An ArrayList<Person> which is used in the recursive function
	 * to store the full siblings found.
	 * @return An ArrayList<Person> of the full siblings of the Person.
	 */
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
	
	/**
	 * Gets the children of the specified person.
	 * @param root Is a Node representing the root of the current serve space.
	 * @param person The Person object whose children you would like to find.
	 * @param children An ArrayList<Person> which is used in the recursive function
	 * to store the children found.
	 * @return An ArrayList<Person> representing the children of the specified Person.
	 */
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
	
	/**
	 * Retrieves the mutual friends of the specified Person.
	 * @param root Is a Node representing the root of the current serve space.
	 * @param mutualFriends An ArrayList<Person> which is used in the recursive function
	 * to store the mutual friends found.
	 * @param person The Person object whose mutual friends you would like to find.
	 * @return An ArrayList<Person> representing the mutual friends of the specified Person.
	 */
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
	
	/**
	 * Retrieves the "inverse friends" of the specified Person.
	 * @param root Is a Node representing the root of the current serve space.
	 * @param inverseFriends An ArrayList<Person> which is used in the recursive function
	 * to store the inverse friends found.
	 * @param person The Person object whose inverse friends you would like to find.
	 * @return An ArrayList<Person> representing the mutual friends of the specified Person.
	 */
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
	
	/**
	 * Increment's the size of the BST by one.
	 */
	public void incrementSize() {
		this.setSize(this.getSize() + 1);
	}
	
	/**
	 * Checks whether or not a Node is a leaf.
	 * @param node The Node for wich you want to know is a leaf or not.
	 */
	public void isLeaf(Node<K, V> node) {
		if (node.getLeft() != null || node.getRight() != null)
			node.setLeaf(false);
		else
			node.setLeaf(true);
	}
	
	/**
	 * Getter for the root of the BST.
	 * @return A Node object representing the root of the BST.
	 */
	public Node<K, V> getRoot() {
		return root;
	}

	/**
	 * Setter for the root of the BST.
	 * @param root A Node object you would like to set as the root of the BST.
	 */
	public void setRoot(Node<K, V> root) {
		this.root = root;
	}

	/**
	 * Getter for the size of the BST.
	 * @return An int representing how many Node objects are in the BST
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Setter for the size of the BST.
	 * @param size An int which will be set as the new size for the current BST.
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	/**
	 * A nested, static, final, class which represents the Node objects being stored in the BST.
	 * @author juliansmithdeniro
	 *
	 * @param <K> Any type that will be used for the key values of each node.
	 * @param <V> Any type that will be used for the comparable value of each node.
	 * @version 1.0
	 */
	static final class Node<K, V> {
		
		/**
		 * A Person object which is the key value of the Node.
		 */
		private Person key;
		
		/**
		 * An int which stores the comparable value of the Node.
		 */
		private int value;
		
		/**
		 * A Node which is the left child of the current Node.
		 */
		private Node<K, V> left;
		
		/**
		 * A Node which is the right child of the current Node.
		 */
		private Node<K, V> right;
		
		/**
		 * A boolean representing whether or not the current Node is a leaf (has no children).
		 */
		private boolean isLeaf;
		
		/**
		 * A boolean representing whether or not the current Node is a right child.
		 */
		private boolean isRight;
		
		/**
		 * A boolean representing whether or not the current Node is a left child.
		 */
		private boolean isLeft;
		
		/**
		 * Default constructor.
		 */
		public Node() {}
		
		/**
		 * Node constructor which takes both a key and value for the Node.
		 * @param key A Person object to be stored in the BST.
		 * @param value An int which represents the Person object's SSN instance variable.
		 */
		public Node(Person key, int value) {
			this.setKey(key);
			this.setValue(value);
		}

		/**
		 * Getter for the key instance variable.
		 * @return a Person object representing the person stored in the BST community.
		 */
		public Person getKey() {
			return key;
		}

		/**
		 * Setter for the Key of the Node.
		 * @param key A Person object.
		 */
		public void setKey(Person key) {
			this.key = key;
		}

		/**
		 * Getter for the value instance variable.
		 * @return An int which is the value of the current Node.
		 */
		public int getValue() {
			return value;
		}

		/**
		 * Setter for the value instance variable.
		 * @param value The value that you would like the current Node to have.
		 */
		public void setValue(int value) {
			this.value = value;
		}

		/**
		 * Getter for the left child of the current Node.
		 * @return A Node representing the left child of the current Node.
		 */
		public Node<K, V> getLeft() {
			return left;
		}

		/**
		 * Setter for the left child of the current Node.
		 * @param left A node that you would like to set the left child as.
		 */
		public void setLeft(Node<K, V> left) {
			this.left = left;
		}

		/**
		 * Getter for the right child of the current Node.
		 * @return  A Node representing the right child of the current Node.
		 */
		public Node<K, V> getRight() {
			return right;
		}

		/**
		 * Setter for the left child of the current Node.
		 * @param right A node that you would like to set the right child as.
		 */
		public void setRight(Node<K, V> right) {
			this.right = right;
		}

		/**
		 * Similar function to previous isLeaf except this runs at O(1).
		 * Should be used on Node's whose isLeaf instance variable has been set before.
		 * @return A boolean representing whether or not the Node is a leaf.
		 */
		public boolean isLeaf() {
			return isLeaf;
		}

		/**
		 * Setter for the isLeaf instance variable.
		 * @param isLeaf A boolean which tells whether or not the Node is a leaf.
		 */
		public void setLeaf(boolean isLeaf) {
			this.isLeaf = isLeaf;
		}

		/**
		 * Tells us whether or not the Node is a right child.
		 * @return A boolean which tells whether or not the Node is a right child. 
		 */
		public boolean isRight() {
			return isRight;
		}

		/**
		 * Setter for the isLeaf instance variable.
		 * @param isRight A boolean which tells whether or not the Node is a right child. 
		 */
		public void setRight(boolean isRight) {
			this.isRight = isRight;
		}

		/**
		 * Tells us whether or not the Node is a left child.
		 * @return A boolean which tells whether or not the Node is a left child. 
		 */
		public boolean isLeft() {
			return isLeft;
		}

		/**
		 * Tells us whether or not the Node is a right child.
		 * @param isLeft A boolean which tells whether or not the Node is a left child.
		 */
		public void setLeft(boolean isLeft) {
			this.isLeft = isLeft;
		}
		
	}
	
}