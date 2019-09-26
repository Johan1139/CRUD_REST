package com.johan.ws.rest.service;

import javax.persistence.EntityManager;
import javax.swing.JOptionPane;

import com.johan.ws.rest.util.JPAUtil;
import com.johan.ws.rest.vo.Productos;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class Cliente {

	public static void main(String[] args) {
	
		String  url = "";
		int acciones = 0;
		int id=0;
		Productos miProducto;
		
		EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();
		
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		
		Client client = Client.create(clientConfig);
		WebResource webResource = null;
		ClientResponse response = null;
		
		do {
			
			acciones = Integer.parseInt(JOptionPane.showInputDialog("Menú Productos\n"
			+"1.Registrar\n"   +"2.Consultar\n"   +"3.Actualizar\n"  +"4.Eliminar\n"  +"5.Salir"));
	
			switch(acciones) {
			
			case 1:
				
				miProducto = new Productos();
				url = "http://localhost:8080/CRUD_REST/servicio/productos/registrar";
				System.out.println(url);
				
				miProducto.setId(null);
				miProducto.setNombre(JOptionPane.showInputDialog("Digite el nombre del producto:").trim());
				miProducto.setDescripcion(JOptionPane.showInputDialog("Digite la descripcion del producto:").trim());
				miProducto.setPrecio(Double.parseDouble(JOptionPane.showInputDialog("Digite el precio del producto:").trim()));

				webResource = client.resource(url);
				response = webResource.type("application/json").post(ClientResponse.class , miProducto);
				miProducto = response.getEntity(Productos.class);
						
				if(miProducto.getNombre() != null && miProducto.getPrecio() != 0 && miProducto.getDescripcion() != null){
					JOptionPane.showConfirmDialog(null, "Registro Exitoso!");
				}else {
					JOptionPane.showMessageDialog(null, "No se pudo registrar el producto. Ingrese todos los datos","ERROR",JOptionPane.WARNING_MESSAGE);
				}
				
				System.out.println(miProducto);
				
				break;
				
			case 2:
				
				miProducto = new Productos();
				url = "http://localhost:8080/CRUD_REST/servicio/productos/consultar/";
				
				id = Integer.parseInt(JOptionPane.showInputDialog("Digite el id del producto a buscar:").trim());
			
				webResource = client.resource(url + id);
				System.out.println(url);
				
				response = webResource.type("application/json").get(ClientResponse.class);
				miProducto = response.getEntity(Productos.class);
				
				JOptionPane.showMessageDialog(null, miProducto);
				break;
			
			case 3:
				
				miProducto = new Productos();
				url = "http://localhost:8080/CRUD_REST/servicio/productos/actualizar";
				
				id = Integer.parseInt(JOptionPane.showInputDialog("Digite el id del producto a actualizar:").trim());
				
				miProducto = entity.find(Productos.class, id);
				
				if(miProducto != null){
					JOptionPane.showMessageDialog(null, miProducto);
					
					miProducto.setNombre(JOptionPane.showInputDialog("Digite el nombre del producto:").trim());
					miProducto.setDescripcion(JOptionPane.showInputDialog("Digite la descripcion del producto:").trim());
					miProducto.setPrecio(Double.parseDouble(JOptionPane.showInputDialog("Digite el precio del producto:").trim()));
				} else {
					JOptionPane.showMessageDialog(null, "Producto NO encontrado", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				
				webResource = client.resource(url);
				System.out.println(url);
				
				response = webResource.type("application/json").put(ClientResponse.class, miProducto);
				miProducto = response.getEntity(Productos.class);
				
				break;
				
			case 4:
				miProducto = new Productos();
				url = "http://localhost:8080/CRUD_REST/servicio/productos/eliminar/";
				
				id = Integer.parseInt(JOptionPane.showInputDialog("Digite el id del producto a eliminar:").trim());
				
				int confirmacion = 0;
				
				if(miProducto != null){
					confirmacion = Integer.parseInt(JOptionPane.showInputDialog(
							"¿Está seguro que desea eliminar el producto? \n 1.Si \n 2.No"));
					if(confirmacion == 1){
						webResource = client.resource(url + id);
						System.out.println(url);
						response = webResource.type("application/json").delete(ClientResponse.class, id);
					}
				}
				
				break;
				
			case 5:
				entity.close(); JPAUtil.shutdown();
				break;
			
			}

		}while(acciones != 5);
	}
}