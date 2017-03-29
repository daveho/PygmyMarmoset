package edu.ycp.cs.pygmymarmoset.app.model;

public class User {
	@PrimaryKey
	private int id;

	@Desc(size=40)
	private String username;
	
	@Desc(size=60)
	private String firstName;
	
	@Desc(size=60)
	private String lastName;
	
	@Desc(size=60, fixed=true)
	private String passwordHash;
	
	private boolean superUser;
	
	public User() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public boolean isSuperUser() {
		return superUser;
	}

	public void setSuperUser(boolean superUser) {
		this.superUser = superUser;
	}
}
