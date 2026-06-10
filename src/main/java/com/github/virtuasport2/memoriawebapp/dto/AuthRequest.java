package com.github.virtuasport2.memoriawebapp.dto;

public class AuthRequest {

    private String email;
    private String password;
    private String username;
    

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}    
    
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
	@Override
	public String toString() {
		return "AuthRequest [email=" + email + ", password=" + password + ", username=" + username + "]";
	}

	
    
}
