package com.example.pisosweb.document;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Apartment { 
    @Id
    private String id;
    private String title;
    private String place;
    private String description;
    private Date date;
    private String owner;
    private int capacity;

    public Apartment() {}

    public Apartment(String title, String place, String description, Date date, String owner, int capacity) {
        this.title = title;
        this.place = place;
        this.description = description;
        this.date = date;
        this.owner = owner;
        this.capacity = capacity;
    }


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return this.place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	


}
