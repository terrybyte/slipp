package net.slipp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User extends AbstractEntity {

	@Column(nullable = false, length = 50, unique = true)
	@JsonProperty
	private String userID;

	@JsonIgnore
	private String password;

	@JsonProperty
	private String name;
	
	@JsonProperty
	private String email;

	public User() {
		super();
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

	public boolean isMatchId(Long newId) {
		if (newId == null) {
			return false;
		}

		return newId.equals(getId());
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
		return "User [" + super.toString() + ", userID=" + userID + ", password=" + password + ", name=" + name + ", email="
				+ email + "]";
	}

	public void update(User updateUser) {
		this.name = updateUser.name;
		this.password = updateUser.password;
		this.email = updateUser.email;
	}

}
