import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
	
	private static ArrayList<Person> getPersonObjects(String communityFile) {
		
		ArrayList<Person> people = new ArrayList<Person>();
		ArrayList<String> communityFileData = new ArrayList<String>();
		
		String line = null;
		
		try {
			
			FileReader communityFileReader = new FileReader(communityFile);
			BufferedReader communityBufferedReader = new BufferedReader(communityFileReader);
			
			while ((line = communityBufferedReader.readLine()) != null)
				communityFileData.add(line);
			
			communityBufferedReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Person person = new Person();
		String firstName, lastName;
		int ssn, father, mother;
		boolean done = false;
		for (int i = 0; i < communityFileData.size(); i++) {
			if (communityFileData.get(i).contains("FIRST NAME")) {
				firstName = communityFileData.get(i).substring(12);
				person.setFirstName(firstName);
				
			} else if (communityFileData.get(i).contains("LAST NAME")) {
				lastName = communityFileData.get(i).substring(11);
				person.setLastName(lastName);
				
			} else if (communityFileData.get(i).contains("SSN")) {
				ssn = Integer.parseInt(communityFileData.get(i).substring(5));
				person.setSSN(ssn);
				
			} else if (communityFileData.get(i).contains("FATHER")) {
				father = Integer.parseInt(communityFileData.get(i).substring(8));
				person.setFather(father);
				
			} else if (communityFileData.get(i).contains("MOTHER")) {
				mother = Integer.parseInt(communityFileData.get(i).substring(8));
				person.setMother(mother);
				
			} else if (communityFileData.get(i).contains("FRIENDS")) {
				BST<Person, Integer> friends = new BST<Person, Integer>();
				String friendSSN = communityFileData.get(i).substring(9);
				String[] friendSSNArray = friendSSN.split(",");
				int randomNum = ThreadLocalRandom.current().nextInt(0, friendSSNArray.length);
				Person root = new Person();
				root.setSSN(Integer.parseInt(friendSSNArray[randomNum]));
				friends.insert(friends.getRoot(), person, root.getSSN());
				for (int j = 0; j < friendSSNArray.length; j++) {
					if (Integer.parseInt(friendSSNArray[j]) != friends.getRoot().getValue()) {
						Person friend = new Person();
						friend.setSSN(Integer.parseInt(friendSSNArray[j]));
						friends.insert(friends.getRoot(), friend, friend.getSSN());
					}
				}
				person.setFriends(friends);
				done = true;
			}
			if (done) {
				people.add(person);
				person = new Person();
				done = false;
			}
		}
		return people;
	}
	
	private static ArrayList<String> getQueries(String queryFile) {

		ArrayList<String> queryFileData = new ArrayList<String>();
		
		String line = null;
		
		try {
			
			FileReader queryFileReader = new FileReader(queryFile);
			BufferedReader queryBufferedReader = new BufferedReader(queryFileReader);
			
			while ((line = queryBufferedReader.readLine()) != null)
				queryFileData.add(line);
			
			queryBufferedReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return queryFileData;
		
	}

	public static void main(String[] args) {
	
		BST<Person, Integer> community = new BST<Person, Integer>();
		ArrayList<Person> people;
		ArrayList<String> queries;
		
		String communityFile = args[0];
		String queryFile = args[1];
		
		people = getPersonObjects(communityFile);
		int mid = people.size() / 2;
		community.insert(community.getRoot(), people.get(mid), people.get(mid).getSSN());
		for (int k = 0; k < people.size(); k++) {
			if (people.get(k).getSSN() != community.getRoot().getValue())
				community.insert(community.getRoot(), people.get(k), people.get(k).getSSN());
		}
		
		queries = getQueries(queryFile);
		
		for (int i = 0 ; i < queries.size(); i++) {
			
			if (queries.get(i).contains("NAME-OF"))
				for (int j = 0; j < people.size(); j++) {
					if (queries.get(i).substring(8).equals(Integer.toString(people.get(j).getSSN()))) {
						Person person = new Person();
						try {
						person = community.search(community.getRoot(), people.get(j).getSSN());
						System.out.println(
								"NAME-OF " + people.get(j).getSSN() + ":" + 
						person.getFirstName() + " " + person.getLastName());
						} catch (NullPointerException e) {
							System.out.println("NAME-OF " + people.get(j).getSSN() + ":UNAVAILABLE");
						}
					}
				}
			if (queries.get(i).contains("FATHER-OF"))
				for (int j = 0; j < people.size(); j++) {
					if (queries.get(i).substring(10).equals(Integer.toString(people.get(j).getSSN()))) {
						Person person = new Person();
						Person father = new Person();
						person = community.search(community.getRoot(), people.get(j).getSSN());
						try {
							father = community.search(community.getRoot(), person.getFather());
						} catch (NullPointerException e) {}
						if (father.getFirstName() == null)
							System.out.println("FATHER-OF " + people.get(j).getSSN() + ":UNAVAILABLE");
						else
							System.out.println(
									"FATHER-OF " + people.get(j).getSSN() + ":" + 
							father.getFirstName() + " " + father.getLastName());
					}
				}
			if (queries.get(i).contains("MOTHER-OF"))
				for (int j = 0; j < people.size(); j++) {
					if (queries.get(i).substring(10).equals(Integer.toString(people.get(j).getSSN()))) {
						Person person = new Person();
						Person mother = new Person();
						try {
							person = community.search(community.getRoot(), people.get(j).getSSN());
							mother = community.search(community.getRoot(), person.getMother());
							System.out.println(
									"MOTHER-OF " + people.get(j).getSSN() + ":" + 
							mother.getFirstName() + " " + mother.getLastName());
						} catch (NullPointerException e) {
							System.out.println("MOTHER-OF " + people.get(j).getSSN() + ":UNAVAILABLE");
						}
					}
				}
			if (queries.get(i).contains("HALF-SIBLINGS-OF"))
				for (int j = 0; j < people.size() ; j++) {
					if (queries.get(i).substring(17).equals(Integer.toString(people.get(j).getSSN()))) {
						Person person;
						ArrayList<Person> halfSiblings = new ArrayList<Person>();
						person = community.search(community.getRoot(), people.get(j).getSSN());
						halfSiblings = community.getHalfSiblings(community.getRoot(), person, halfSiblings);
						System.out.print("HALF-SIBLINGS-OF " + people.get(j).getSSN() + ":");
						if (halfSiblings.size() != 0) {
							for (int m = 0; m < halfSiblings.size(); m++) {
								System.out.print(
										halfSiblings.get(m).getFirstName() + " " +
								halfSiblings.get(m).getLastName() + ",");
							}
							System.out.println("");
						} else {
							System.out.println("UNAVAILABLE");
						}
					}
				}
			if (queries.get(i).contains("FULL-SIBLINGS-OF"))
				for (int j = 0; j < people.size(); j++) {
					if (queries.get(i).substring(17).equals(Integer.toString(people.get(j).getSSN()))) {
						Person person;
						ArrayList<Person> fullSiblings = new ArrayList<Person>();
						person = community.search(community.getRoot(), people.get(j).getSSN());
						fullSiblings = community.getFullSiblings(community.getRoot(), person, fullSiblings);
						System.out.print("FULL-SIBLINGS-OF " + people.get(j).getSSN() + ":");
						if (fullSiblings.size() != 0) {
							for (int n = 0; n < fullSiblings.size(); n++) {
								System.out.print(
										fullSiblings.get(n).getFirstName() + " " +
								fullSiblings.get(n).getLastName() + ",");
							}
							System.out.println("");
						} else {
							System.out.println("UNAVAILABLE");
						}
					}
				}
			if (queries.get(i).contains("CHILDREN-OF"))
				for (int j = 0; j < people.size(); j++) {
					if (queries.get(i).substring(12).equals(Integer.toString(people.get(j).getSSN()))) {
						Person person;
						ArrayList<Person> children = new ArrayList<Person>();
						person = community.search(community.getRoot(), people.get(j).getSSN());
						children = community.getChildren(community.getRoot(), person, children);
						System.out.print("CHILDREN-OF " + people.get(j).getSSN() + ":");
						if (children.size() != 0) {
							for (int x = 0; x < children.size(); x++) {
								System.out.print(
										children.get(x).getFirstName() + " " +
								children.get(x).getLastName() + ",");
							}
							System.out.println("");
						} else {
							System.out.println("UNAVAILABLE");
						}
					}
				}
			if (queries.get(i).contains("MUTUAL-FRIENDS-OF"))
				for (int j = 0; j < people.size(); j++) {
					if (queries.get(i).substring(18).equals(Integer.toString(people.get(j).getSSN()))) {
						Person person = community.search(community.getRoot(),  people.get(j).getSSN());
						ArrayList<Person> mutualFriends = new ArrayList<Person>();
						mutualFriends = community.getMutualFriends(community.getRoot(), mutualFriends, person);
						System.out.print("MUTUAL-FRIENDS-OF " + people.get(j).getSSN() + ":");
						if (mutualFriends.size() != 0) {
							for (int y = 0; y < mutualFriends.size(); y++) {
								System.out.print(
										mutualFriends.get(y).getFirstName() + " " + 
								mutualFriends.get(y).getLastName() + ",");
							}
							System.out.println("");
						} else {
							System.out.println("UNAVAILABLE");
						}
					}
				}
			if (queries.get(i).contains("INVERSE-FRIENDS-OF"))
				for (int j = 0; j < people.size(); j++) {
					if (queries.get(i).substring(19).equals(Integer.toString(people.get(j).getSSN()))) {
						Person person = community.search(community.getRoot(), people.get(j).getSSN());
						ArrayList<Person> inverseFriends = new ArrayList<Person>();
						inverseFriends = community.getInverseFriends(community.getRoot(), inverseFriends, person);
						System.out.print("INVERSE-FRIENDS-OF " + people.get(j).getSSN() + ":");
						if (inverseFriends.size() != 0) {
							for (int z = 0; z < inverseFriends.size(); z++) {
								System.out.print(
										inverseFriends.get(z).getFirstName() + " " + 
								inverseFriends.get(z).getLastName() + ",");
							}
							System.out.println("");
						} else {
							System.out.println("UNAVAILABLE");
						}
					}
				}
			if (queries.get(i).contains("WHO-HAS-MOST-MUTUAL-FRIENDS")) {
				Person currentMax = people.get(0);
				ArrayList<Person> currentMaxList = new ArrayList<Person>();
				currentMaxList = community.getMutualFriends(
						community.getRoot(), currentMaxList, currentMax);
				ArrayList<Person> comparisonList = new ArrayList<Person>();
				for (int j = 1; j < people.size(); j++) {
					Person comparison = people.get(j);
					comparisonList = community.getMutualFriends(
							community.getRoot(), comparisonList, people.get(j));
					if (currentMaxList.size() < comparisonList.size())
						currentMax = comparison;

						currentMaxList = comparisonList;
				}
				System.out.print(
						"WHO-HAS-MOST-MUTUAL-FRIENDS:" + currentMax.getFirstName() + 
						" " + currentMax.getLastName());
			}
		}
	}
}
