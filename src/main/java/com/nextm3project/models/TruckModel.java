package com.nextm3project.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity	//Tornando a classe uma entidade
@Table(name = "TRUCK")	//Criando a tabela e definindo o nome
public class TruckModel implements Serializable {  //Serializable + serialVersionUID serve para fazer o controle das versões no JVM
	private static final long serialVersionUID = 1L;  // ID da versão serial padrão para TruckModel
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) //Gerando o Id de forma automatica
	private Integer id; //UUID tipo do identificador apropriado univesal, para melhor distribuicao
	
	@Column(nullable = false, unique = true, length = 7)
	private String licensePlateTruck; //Placa do caminhao
	
	@Column(nullable = false, length = 5)
	private String status; //Status do caminhao
	
	@Column(nullable = false, length = 10)
	private String location; //localizacao (vértice)
	
	@Column(nullable = false)
	private LocalDateTime registrationDate;  //Data de registro

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLicensePlateTruck() {
		return licensePlateTruck;
	}

	public void setLicensePlateTruck(String licensePlateTruck) {
		this.licensePlateTruck = licensePlateTruck;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}
}
