
public class Person {
	
	private String firstName;
	private String lastName;
	private int SSN;
	private int father;
	private int mother;
	private BST<Person, Integer> friends;
	
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
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public int getSSN() {
		return SSN;
	}
	
	public void setSSN(int sSN) {
		SSN = sSN;
	}
	
	public int getFather() {
		return father;
	}
	
	public void setFather(int father) {
		this.father = father;
	}
	
	public int getMother() {
		return mother;
	}
	
	public void setMother(int mother) {
		this.mother = mother;
	}
	
	public BST<Person, Integer> getFriends() {
		return friends;
	}
	
	public void setFriends(BST<Person, Integer> friends) {
		this.friends = friends;
	}
	
}
