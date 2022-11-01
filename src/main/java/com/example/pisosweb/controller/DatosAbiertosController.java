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

import com.example.pisosweb.document.Booking;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.json.*;

//https://datosabiertos.ayto-arganda.es/dataset/3edc135e-0713-4557-9866-d76e8c91f223/resource/243046b4-f1b4-49e7-84ce-c09509675b15/download/registrohora3trimestre2022.json
//https://datosabiertos.jcyl.es/web/jcyl/risp/es/sociedad-bienestar/datos-plantillas-cuerpos-policia-local/1285126997658.json
//https://dipcas.opendatasoft.com/api/v2/catalog/datasets/directorio-de-bibliotecas-valencianas0/exports/json
//https://www.zaragoza.es/contenidos/bici/carriles_WGS84.json

@RestController
@RequestMapping("/api/ejemplo")
public class DatosAbiertosController {

	private static String urlStatic = "https://datosabiertos.ayto-arganda.es/dataset/3edc135e-0713-4557-9866-d76e8c91f223/resource/243046b4-f1b4-49e7-84ce-c09509675b15/download/registrohora3trimestre2022.json";
	
	
	@Operation(description = "Introduce una url y devuelve el texto plano de la página", responses = {
			@ApiResponse(responseCode = "200", description = "Texto plano devuelto"),
			@ApiResponse(responseCode = "404", description = "La url aportada no contiene texto plano") })
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
	
	
//	@GetMapping(value="/staticURL", produces = MediaType.APPLICATION_JSON_VALUE)
//	public static String staticURL() {
//		StringBuilder json = new StringBuilder();
//	    try (InputStream input = new URL(urlStatic).openStream()) {
//	        InputStreamReader isr = new InputStreamReader(input);
//	        BufferedReader reader = new BufferedReader(isr);
//	        int c;
//	        while ((c = reader.read()) != -1) {
//	            json.append((char) c);
//	        }
//	        return json.toString();
//	    } catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    return json.toString();
//	}
	
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }
	
	@Operation(description = "Introduce un número de línea y recibe toda la información sobre la linea del autobus", responses = {
			@ApiResponse(responseCode = "200", description = "Linea encontrada"),
			@ApiResponse(responseCode = "404", description = "Línea no encontrada o no existe") })
	@GetMapping(value="/lineaParadasBus", produces =MediaType.APPLICATION_JSON_VALUE)
	public static String lineaParadasBus(int linea) throws IOException{
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
				if(Float.parseFloat(aux.get("codLineaStr").toString()) == linea){
					result = aux;
					fin = false;
				}
			}
			
			
			return result.toString();
			
		} finally {
		}
	}
	
	@Operation(description = "Introduce un número de línea y recibe las paradas que hace el autobus de dicha linea", responses = {
			@ApiResponse(responseCode = "200", description = "Paradas encontradas"),
			@ApiResponse(responseCode = "500", description = "Paradas no encontradas o línea no existente") })
	@GetMapping(value="/mostrarParadasBus")
	public static String mostrarParadasBus(int linea) throws IOException{
		try {
			String url = "https://datosabiertos.malaga.eu/recursos/transporte/EMT/EMTLineasYParadas/lineasyparadas.geojson";
			BufferedReader rd = new BufferedReader(new InputStreamReader(new URL(url).openStream(), Charset.forName("UTF-8")));
			
			String jsonText = readAll(rd);
			JSONArray jsonArray = new JSONArray(jsonText);

			List<JSONObject> jList = new ArrayList<>();
			for(int i = 0; i < jsonArray.length(); i++) {
				jList.add(jsonArray.getJSONObject(i));
			}
			
			JSONObject lineaAux = new JSONObject();
			boolean fin = false;
			Iterator<JSONObject> jIterator = jList.iterator();
			while(jIterator.hasNext() && !fin) {
				JSONObject aux = jIterator.next();
				if(Integer.parseInt(aux.get("codLineaStr").toString()) == linea){
					lineaAux = aux;
					fin = false;
				}
			}
			
			List<JSONObject> paradasList = new ArrayList<>();
			for(int i = 0; i < lineaAux.getJSONArray("paradas").length(); i++) {
				paradasList.add(lineaAux.getJSONArray("paradas").getJSONObject(i));
			}
			
			String resultado = "Paradas: \n";
			for (JSONObject jsonObject : paradasList) {
				resultado += "Nombre: " + jsonObject.getJSONObject("parada").getString("nombreParada");
				resultado += ", direccion: " + jsonObject.getJSONObject("parada").getString("direccion") + ",\n";
			}
			
			
			return resultado;
			
		} finally {
		}
	}
	
	@Operation(description = "Introduce latitud y longitud, devuelve las paradas de autobus más cercanas en un determinado radio", responses = {
			@ApiResponse(responseCode = "200", description = "Paradas encontradas") })
	@GetMapping(value="/mostrarParadasBusCercana")
	public static String mostrarParadasBusCercana(float latitud, float longitud, float rango) throws IOException{
		try {
			String url = "https://datosabiertos.malaga.eu/recursos/transporte/EMT/EMTLineasYParadas/lineasyparadas.geojson";
			BufferedReader rd = new BufferedReader(new InputStreamReader(new URL(url).openStream(), Charset.forName("UTF-8")));
			
			String jsonText = readAll(rd);
			JSONArray jsonArray = new JSONArray(jsonText);

			//Lista de lineas
			List<JSONObject> jList = new ArrayList<>();
			for(int i = 0; i < jsonArray.length(); i++) {
				jList.add(jsonArray.getJSONObject(i));
			}

			//Iterador de la lista de lineas
			Iterator<JSONObject> jIterator = jList.iterator();
			List<JSONObject> paradasList = new ArrayList<>();
			
			//Sobre cada linea
			while(jIterator.hasNext()) {
				JSONArray arrayParadas = jIterator.next().getJSONArray("paradas");
				
				//Sobre la lista de paradas
				for(int i = 0; i < arrayParadas.length(); i++) {
					//Aux = objeto interno de paradas
					JSONObject aux = arrayParadas.getJSONObject(i);
					//Buscar parada
					float auxLat = aux.getJSONObject("parada").getFloat("latitud"), auxLong = aux.getJSONObject("parada").getFloat("longitud");					
					if(Math.abs(latitud - auxLat) + Math.abs(longitud - auxLong) < rango) {
						paradasList.add(arrayParadas.getJSONObject(i));
					}
				}
			}
			
			
			
			String resultado = "Paradas: \n";
			for (JSONObject jsonObject : paradasList) {
				resultado += "Nombre: " + jsonObject.getJSONObject("parada").getString("nombreParada");
				resultado += ", direccion: " + jsonObject.getJSONObject("parada").getString("direccion");
				resultado += ", latitud|longitud: " + jsonObject.getJSONObject("parada").getFloat("latitud") + "|" + jsonObject.getJSONObject("parada").getFloat("longitud") + ",\n";
			}
			
			
			return resultado;
			
		} finally {
		}
	}
	
	
}
