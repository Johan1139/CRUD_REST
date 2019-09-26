package com.johan.ws.rest.service;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.johan.ws.rest.util.JPAUtil;
import com.johan.ws.rest.vo.Productos;

@Path("/productos")
public class Servicio {
	
	@POST
	@Path("/registrar")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Productos registrar(Productos miProducto){
		
		try {
			EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();
			
			if(miProducto.getNombre() != null && miProducto.getPrecio() != 0 && miProducto.getDescripcion() != null){

				entity.getTransaction().begin();
				entity.persist(miProducto);
				entity.getTransaction().commit();
				
				System.out.println("Registro Exitoso!");
				System.out.println();
				
			}else{
				System.out.println("No se pudo registrar el producto. Ingrese todos los datos");
				System.out.println();
			}
		}catch (Exception e){
			System.out.println("Error en el ingreso de datos " + e.getMessage());
			System.out.println();
		}
		return miProducto;
	}
	
	@GET
	@Path("/consultar/{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Productos consultar(@PathParam("id") int id){
		Productos miProducto = new Productos();
		try {
			EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();
			
			miProducto = entity.find(Productos.class, id);
			System.out.println(miProducto);
			
			String mensaje = "Id: " + miProducto.getId() + "\n"
					+ "Nombre: " + miProducto.getNombre() + "\n"
					+ "Descripcion: " + miProducto.getDescripcion() + "\n"
					+ "Precio: " + miProducto.getPrecio() + "\n";
			
			System.out.println(mensaje);
			System.out.println();
		
		}catch(Exception e) {
			System.out.println("Producto NO encontrado " +e.getMessage());
			System.out.println();
		}
		return miProducto;
	}
	
	@PUT
	@Path("/actualizar")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Productos actualizar(Productos miProducto){
		try {
			EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();
			
			System.out.println(miProducto);
			
			entity.getTransaction().begin();
			entity.merge(miProducto);
			entity.getTransaction().commit();
	
			System.out.println("Producto actualizado");
			
		}catch(Exception e) {
			System.out.println("Error en el ingreso de datos" + e.getMessage());
		}
		return miProducto;
	}
	
	@DELETE
	@Path("/eliminar/{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public void eliminar(@PathParam("id") int id){
		Productos miProducto = new Productos();
		
		try {		
			EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();
			miProducto = entity.find(Productos.class, id);
			
			if(miProducto != null){
				entity.getTransaction().begin();
				entity.remove(miProducto);
				entity.getTransaction().commit();
				
				System.out.println("Producto eliminado");	
			
			}else{
				System.out.println("Producto NO encontrado");
			}
			
		}catch (Exception e){
			System.out.println("Ocurrio un error al eliminar el producto " + e.getMessage());
		}
	}
}
