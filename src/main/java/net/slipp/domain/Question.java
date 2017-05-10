package net.slipp.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Question {

	@Id
	@GeneratedValue
	private Long id;

//	private String writer;
	private String title;
	private String contents;
	private LocalDateTime createDate; //java8

	@ManyToOne
	@JoinColumn(foreignKey=@ForeignKey(name="fk_question_writer"))
	private User writer;
	
	public Question() {
	}

	public Question(User writer, String title, String contents) {
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createDate = LocalDateTime.now();
	}

//	public void setWriter(String writer) {
//		this.writer = writer;
//	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getCreateDate() {
		if (this.createDate == null) {
			return "";
		}
		return this.createDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	public void update(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}
	
	@Override
	public String toString() {
		return "Question [id=" + id + ", writer=" + writer + ", title=" + title + ", contents=" + contents + "]";
	}
}
