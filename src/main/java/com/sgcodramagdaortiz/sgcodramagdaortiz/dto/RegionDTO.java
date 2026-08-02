package com.sgcodramagdaortiz.sgcodramagdaortiz.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * ============================================================
 * REGIONDTO.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * DTO que representa una región de Colombia, tal como la
 * devuelve la API pública externa "API Colombia"
 * (https://api-colombia.com).
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegionDTO {

    private Integer id;

    private String name;

    public RegionDTO() {
    }

    public RegionDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}