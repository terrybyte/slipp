package net.slipp.domain;

public class Repo {

	private String name;

	public Repo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Repo(String name) {
		super();
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Repo [name=" + name + "]";
	}

}
