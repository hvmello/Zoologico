package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.example.demo.validator.CheckDateFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.util.Date;


@Entity
public class Animal {

    @Id
    @ApiModelProperty(notes = "Identificador único do animal", required = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    @ApiModelProperty(notes = "Nome/Apelido do animal", required = true)
    private String nomeAnimal;

    @Column(nullable = false)
    @ApiModelProperty(notes = "Espécie do animal", required = true)
    private String especie;

    @Column(nullable = false)
    @ApiModelProperty(notes = "Ala onde o animal está localizado", required = true)
    private String alaZoologico;

    @Column(nullable = false)
    @ApiModelProperty(notes = "Data do nascimento ou da chegada do animal", required = true)
    private Date dataNascimentoChegada;


    public long getId() {
        return id;
    }

    @NotEmpty(message = "O nome deverá ser informado!")
    @Length(min = 5, max = 200, message = "O nome deverá ter de 5 a 200 caracteres")
    public String getNomeAnimal() {
        return nomeAnimal;
    }

    @NotEmpty(message = "A espécie deverá ser informada!")
    @Length(min = 5, max = 200, message = "A espécie deverá ter de 3 a 200 caracteres")
    public String getEspecie() {
        return especie;
    }

    @NotEmpty(message = "O Ala deverá ser informada!")
    @Length(min = 1, max = 10, message = "Alas disponíveis: A, B, C, D, E, F. Favor preencher com esses valores")
    public String getAlaZoologico() {
        return alaZoologico;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    public Date getDataNascimentoChegada() {
        return dataNascimentoChegada;
    }

    public void setId(long id) {
        this.id = id;
    }


    public void setNomeAnimal(String nomeAnimal) {
        this.nomeAnimal = nomeAnimal;
    }


    public void setEspecie(String especie) {
        this.especie = especie;
    }


    public void setAlaZoologico(String alaZoologico) {
        this.alaZoologico = alaZoologico;
    }


    public void setDataNascimentoChegada(Date dataNascimentoChegada) {
        this.dataNascimentoChegada = dataNascimentoChegada;
    }
}