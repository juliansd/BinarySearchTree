/**
 * This is a public class which is used to represent persons who are in the "community"
 * @author juliansmithdeniro
 * @version 1.0
 */
public class Person {
	
	/**
	 * A string representing the first name of the person.
	 */
	private String firstName;
	
	/**
	 * A string representing the last name of the person.
	 */
	private String lastName;
	
	/**
	 * An int representing the social security number of the person
	 */
	private int SSN;
	
	/**
	 * An int representing the social security number of the specified person's father.
	 */
	private int father;
	
	/**
	 * An int representing the social security number of the specified person's mother.
	 */
	private int mother;
	
	/**
	 * A BST which is used to store references to the specified person's friends.
	 * This is not undirected in the sense that just because the specified person
	 * declares someone their friend it does not mean that it is reciprocated.
	 */
	private BST<Person, Integer> friends;
	
	/**
	 * Default constructor
	 */
	public Person() {}
	
	/**
	 * This method checks whether or not the specified person is friends with someone.
	 * @param person Is a Person object representing another person that is in the community.
	 * @return A boolean value; true if they are friends, otherwise false.
	 */
	public boolean isFriendsWith(Person person) {
		BST<Person, Integer> friends = this.getFriends();
		try {
			person = friends.search(friends.getRoot(), person.getSSN());
			if (person != null)
				return true;
			else
				return false;
		} catch (NullPointerException e) {}
		return false;
		
	}
	
	/**
	 * Getter for firstName instance variable.
	 * @return A string representing the current objects first name.
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Setter for firstName instance variable.
	 * @param firstName A String representing the person's first name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Getter for lastName instance variable.
	 * @return A String representing the current object's last name.
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Setter for lastName instance variable.
	 * @param lastName A String representing the person's last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Getter for SSN instance variable.
	 * @return An int representing the person's SSN.
	 */
	public int getSSN() {
		return SSN;
	}
	
	/**
	 * Setter for SSN instance variable.
	 * @param sSN Is an int representing the person's SSN.
	 */
	public void setSSN(int sSN) {
		SSN = sSN;
	}
	
	/**
	 * Getter for the father instance variable.
	 * @return An int representing current person's, father's SSN. 
	 */
	public int getFather() {
		return father;
	}
	
	/**
	 * Setter for the instance variable.
	 * @param father An int which should reference the current person's, father's SSN.
	 */
	public void setFather(int father) {
		this.father = father;
	}
	
	/**
	 * Getter for the mother instance variable.
	 * @return An int representing the current person's, mother's SSN.
	 */
	public int getMother() {
		return mother;
	}
	
	/**
	 * Setter for the mother instance variable.
	 * @param mother An int which should reference the current person's, mother's SSN.
	 */
	public void setMother(int mother) {
		this.mother = mother;
	}
	
	/**
	 * Getter for the friends instance variable.
	 * @return A BST representing the current person's friends.
	 */
	public BST<Person, Integer> getFriends() {
		return friends;
	}
	
	/**
	 * Setter for the friends instance variable.
	 * @param friends A BST storing the SSN's of the current object's, friend's SSN's.
	 */
	public void setFriends(BST<Person, Integer> friends) {
		this.friends = friends;
	}
	
}
