package com.ista.exameninterciclo.Control;

import java.util.ArrayList;
import java.util.List;

import com.ista.exameninterciclo.Modelo.Producto;
import com.ista.exameninterciclo.Modelo.RespuestaGenerica;
import com.ista.exameninterciclo.Repositorio.Rproducto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class Cproducto {

@Autowired
private Rproducto rproducto;

@GetMapping("/lista-producto")
public ResponseEntity<RespuestaGenerica> getProducto(){
    List<Producto> lista = rproducto.findAll();
    RespuestaGenerica<Producto> respuesta = new RespuestaGenerica<>("Listado generado exitosamente",lista,0);
    return new ResponseEntity<RespuestaGenerica>(respuesta,HttpStatus.OK);
    
}
@PostMapping("/producto")
 public ResponseEntity<RespuestaGenerica> crearProducto(@RequestBody Producto producto){
     
     List<Producto> data = new ArrayList<Producto>();
     RespuestaGenerica<Producto> respuesta = new RespuestaGenerica<>();
     try{           
         Producto productoInsert = rproducto.save(producto);
         
         if(productoInsert!=null){
             data.add(productoInsert);
             respuesta.setMensaje("Producto ingresada exitosamente");
             respuesta.setData(data);
             respuesta.setEstado(0);
         }  
     }
     catch(Exception ex){
         respuesta.setMensaje("Hubo un problema al insertar el producto");
         respuesta.setData(data);
         respuesta.setEstado(1);
         System.out.println("No se pudo almacenar el objeto en la base de datos");
         
     }
     return new ResponseEntity<RespuestaGenerica>(respuesta,HttpStatus.CREATED);   

 }
 
 


 @PutMapping("/{codigo}")
 public Producto actualizarProducto(@RequestBody Producto newProducto,@PathVariable Long codigo){

     try{
         return rproducto.findById(codigo)
         .map(producto ->{
             producto.setDescripcion(newProducto.getDescripcion());
             return rproducto.save(producto);
         })
         .orElseGet(
             () ->{
                 return new Producto();
             }
         );
     }
     catch(Exception ex){
         System.out.println("No se pudo actualizar el objeto en la base de datos");
         
     }
     return new Producto();
 }
 

 @DeleteMapping("/{codigo}")
 public ResponseEntity<RespuestaGenerica> deleteProducto(@PathVariable Long codigo){
     rproducto.deleteById(codigo);
     List<Producto> data = new ArrayList<Producto>();
     RespuestaGenerica<Producto> respuesta = new RespuestaGenerica<Producto>("Producto eliminada exitosamente", data,0);
     return new ResponseEntity<RespuestaGenerica>(respuesta, HttpStatus.OK);
 }

 @GetMapping("/listar/{codigo}")
 public ResponseEntity<RespuestaGenerica>getProductocodigo(@PathVariable Long codigo){
     List<Producto>lista= rproducto.findAllByCodigo(codigo);
     RespuestaGenerica<Producto> respuesta=new RespuestaGenerica<>("Listado generado exitosamente", lista, 0);
     return new ResponseEntity<RespuestaGenerica>(respuesta,HttpStatus.OK);
          
      
 }



}
