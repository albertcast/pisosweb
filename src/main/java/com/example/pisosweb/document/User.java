package com.example.pisosweb.document;

import java.util.ArrayList;
import java.util.List;

import nonapi.io.github.classgraph.json.Id;


public class User{

	@Id
	private String id;
	private String email;
	private String name;
	private String lastname;
	private Integer age;
	
	public User() {
	}
	
	public User(String email, String name, String lastname, Integer age) {
		
		this.email=email;
		this.name=name;
		this.lastname=lastname;
		this.age=age;
	}
	
    public String getUsuarioId() {
        return this.id;
    }
	
    public void setUsuarioId(String id) {
    	this.id = id;
    }
    
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getNombre() {
        return this.name;
    }

    public void setNombre(String name) {
        this.name = name;
    }
    
    public String getApellidos() {
        return this.lastname;
    }

    public void setApellidos(String lastname) {
        this.lastname = lastname;
    }
    
    public Integer getEdad() {
        return this.age;
    }

    public void setEdad(Integer age) {
        this.age = age;
    }

	
    
    
}
