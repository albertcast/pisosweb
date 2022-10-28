package com.example.pisosweb.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//https://datosabiertos.ayto-arganda.es/dataset/3edc135e-0713-4557-9866-d76e8c91f223/resource/243046b4-f1b4-49e7-84ce-c09509675b15/download/registrohora3trimestre2022.json
//https://datosabiertos.jcyl.es/web/jcyl/risp/es/sociedad-bienestar/datos-plantillas-cuerpos-policia-local/1285126997658.json
//https://dipcas.opendatasoft.com/api/v2/catalog/datasets/directorio-de-bibliotecas-valencianas0/exports/json
//https://www.zaragoza.es/contenidos/bici/carriles_WGS84.json

@RestController
@RequestMapping("/api/ejemplo")
public class DatosAbiertosController {

	private static String urlStatic = "https://datosabiertos.ayto-arganda.es/dataset/3edc135e-0713-4557-9866-d76e8c91f223/resource/243046b4-f1b4-49e7-84ce-c09509675b15/download/registrohora3trimestre2022.json";
	
	@GetMapping(value="/showByUrl", produces = MediaType.APPLICATION_JSON_VALUE)
	public static String showByUrl(URL url) throws IOException {
	    	InputStream input = url.openStream();
			StringBuilder json = new StringBuilder();
	        InputStreamReader isr = new InputStreamReader(input);
	        BufferedReader reader = new BufferedReader(isr);
	        int c;
	        while ((c = reader.read()) != -1) {
	            json.append((char) c);
	        }
	        return json.toString();
	    
	}
	
	
	
	
	@GetMapping(value="/staticURL", produces = MediaType.APPLICATION_JSON_VALUE)
	public static String staticURL() {
		StringBuilder json = new StringBuilder();
	    try (InputStream input = new URL(urlStatic).openStream()) {
	        InputStreamReader isr = new InputStreamReader(input);
	        BufferedReader reader = new BufferedReader(isr);
	        int c;
	        while ((c = reader.read()) != -1) {
	            json.append((char) c);
	        }
	        return json.toString();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return json.toString();
	}
	
	
	
	
}
