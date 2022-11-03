package com.example.pisosweb.document;

import com.mongodb.lang.NonNull;
import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Document(collection = "comments")
public class Comment implements Serializable {
    @Id
    @NonNull
    private String id;

    @NonNull
    private String text;

    @NonNull
    private String rating;

    @NonNull
    private String apartment;

    @NonNull
    private String user;

	public Comment(String text, String rating, User usr, Apartment ap) {
		this.text = text;
		this.rating = rating;
		user = usr.getUsuarioId();
		apartment = ap.getId();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getApartment() {
		return apartment;
	}

	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
