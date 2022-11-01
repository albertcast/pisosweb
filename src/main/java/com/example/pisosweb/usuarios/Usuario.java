package com.example.pisosweb.usuarios;

import nonapi.io.github.classgraph.json.Id;


public class Usuario{

	@Id
	private Integer usuarioId;
	private String email;
	private String name;
	private String lastname;
	private Integer age;
	
	public Usuario() {
	}
	
	public Usuario(String email, String name, String lastname, Integer age) {
		
		this.email=email;
		this.name=name;
		this.lastname=lastname;
		this.age=age;
	}
	
    public Integer getUsuarioId() {
        return this.usuarioId;
    }
	
    public void setUsuarioId(Integer usuarioId) {
    	this.usuarioId = usuarioId;
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
