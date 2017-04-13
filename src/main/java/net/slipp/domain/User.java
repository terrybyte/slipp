package net.slipp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable=false, length=50, unique=true)
	private String userID;
	
	private String password;
	private String name;
	private String email;

	public User() {
		super();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public boolean isMatchId(Long newId){
		if (newId == null) {
			return false;
		}
		
		return newId.equals(id);
	}
	
	public String getUserID() {
		return userID;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public boolean isMatchPass(String newPass) {
		if (newPass == null) {
			return false;
		}
		
		return newPass.equals(password);
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", userID=" + userID + ", password=" + password + ", name=" + name + ", email="
				+ email + "]";
	}

	public void update(User updateUser) {
		this.name = updateUser.name;
		this.password = updateUser.password;
		this.email = updateUser.email;
	}

}
