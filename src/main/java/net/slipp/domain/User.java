package net.slipp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User {

	@Id
	@GeneratedValue
	@JsonProperty
	private Long id;

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

	public boolean isMatchId(Long newId) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
