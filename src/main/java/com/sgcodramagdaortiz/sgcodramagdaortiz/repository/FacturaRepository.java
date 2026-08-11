/*
============================================================
FacturaRepository.java

Sistema de Gestión de Citas Odontológicas

Repositorio encargado del acceso a datos
de la entidad Factura.

Además de las operaciones CRUD heredadas
de JpaRepository, contiene consultas utilizadas
por el Dashboard.

============================================================
*/


package com.sgcodramagdaortiz.sgcodramagdaortiz.repository;



import java.util.List;

import java.util.Optional;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;



import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Factura;





@Repository
public interface FacturaRepository
        extends JpaRepository<Factura, Long> {




    /*
    ========================================================
    BUSCAR ÚLTIMA FACTURA REGISTRADA

    Utilizada para generar consecutivos:

    FAC-0001
    FAC-0002
    FAC-0003

    ========================================================
    */


    Optional<Factura>
    findTopByOrderByNumeroFacturaDesc();







    /*
    ========================================================
    FACTURACIÓN AGRUPADA POR MES

    Consulta utilizada para gráficos.

    Retorna:

    Mes
    Total facturado


    Ejemplo:

    1  | 500000

    2  | 820000


    ========================================================
    */


    @Query("""
            SELECT
                MONTH(f.fecha),
                SUM(f.total)
            FROM Factura f
            GROUP BY MONTH(f.fecha)
            ORDER BY MONTH(f.fecha)
            """)
    List<Object[]> obtenerFacturacionMensual();




}