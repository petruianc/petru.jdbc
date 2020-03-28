package jdbc.petru.model;

public class User {
	
	private int id;
	private String lastName;
	private String firstName;
	private String email;
	private String password;
	
	public User(int id, String lastName, String firstName, String email, String password) {
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.password = password;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return  lastName ;
	}
	
	
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) return false;
	    if (!(obj instanceof User))
	        return false;
	    if (obj == this)
	        return true;
	    return this.getPassword() == ((User) obj).getPassword();
	}

	
}
