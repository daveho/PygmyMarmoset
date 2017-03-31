package edu.ycp.cs.pygmymarmoset.app.model;

public class LoginCredentials {
	@Desc(size=30)
	private String username;
	@Desc(size=30)
	private String password;
	private String goal;
	
	public LoginCredentials() {
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}
	
	public boolean hasGoal() {
		return goal != null && !goal.equals("");
	}
}
