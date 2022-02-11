package com.ista.exameninterciclo.Repositorio;

import java.util.List;

import com.ista.exameninterciclo.Modelo.Producto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Rproducto extends JpaRepository<Producto,Long>{
    //Producto findAllByCodigo(Long codigo);
    List<Producto> findAllByCodigo(Long codigo);
}
