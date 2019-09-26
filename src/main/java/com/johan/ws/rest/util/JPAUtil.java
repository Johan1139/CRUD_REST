package com.johan.ws.rest.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	//Alamacena el nombre de la unidad de persistencia
	private static final String PERSISTENCE_UNIT_NAME = "PERSISTENCE";
	
	//Contiene la conexión a la BD
	private static EntityManagerFactory factory;
	
	//Obtiene la conexión
	public static EntityManagerFactory getEntityManagerFactory(){
		if(factory==null){
			
			//Este metodo se encargar de pasarle todo al objeto factory 
			//toda la parte de la conexión y las propiedades de la BD
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		}
		
		//Se retorna para que hibernate pueda hacer todas las operaciones de la BD (CRUD)
		return factory;
	}
	
	//Cierra la conexión
	public static void shutdown(){
		if(factory!=null){
			factory.close();
		}
	}
}
