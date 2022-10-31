package com.example.pisosweb.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.json.*;

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
	
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }
	
	@GetMapping(value="/jsonUrl", produces = MediaType.APPLICATION_JSON_VALUE)
	public static JSONArray readJsonArrayFromUrl(String url) throws IOException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONArray json = new JSONArray(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	  }
	
	@GetMapping(value="/paradasBus", produces = MediaType.APPLICATION_JSON_VALUE)
	public static JSONObject paradasBus(float linea) throws IOException{
		try {
			String url = "https://datosabiertos.malaga.eu/recursos/transporte/EMT/EMTLineasYParadas/lineasyparadas.geojson";
			BufferedReader rd = new BufferedReader(new InputStreamReader(new URL(url).openStream(), Charset.forName("UTF-8")));
			
			String jsonText = readAll(rd);
			JSONArray jsonArray = new JSONArray(jsonText);

			List<JSONObject> jList = new ArrayList<>();
			for(int i = 0; i < jsonArray.length(); i++) {
				jList.add(jsonArray.getJSONObject(i));
			}
			
			JSONObject result = new JSONObject();
			boolean fin = false;
			Iterator<JSONObject> jIterator = jList.iterator();
			while(jIterator.hasNext() && !fin) {
				JSONObject aux = jIterator.next();
				if(Float.parseFloat(aux.get("codLinea").toString()) == linea){
					result = aux;
				}
			}
			
			
			
			
			return jsonArray.getJSONObject(0);
		} finally {
			
		}
		
		
		
		
	}
	
	
}
