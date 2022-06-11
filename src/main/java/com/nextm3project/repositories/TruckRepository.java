package com.nextm3project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nextm3project.models.TruckModel;

//Criado como interface e fiz o extends (herança) do JpaRepository
//Dentro do <> JpaRepository é passado qual o model e o tipo de identificador (id), que foi definido como Integer.
//Foi utilizado o JpaRespository por ele já possuir métodos prontos para poder utilizar para transações com banco de dados

@Repository  //Usado para transações com o banco de dados
public interface TruckRepository extends JpaRepository<TruckModel, Integer> {
	
	// Declarando os métodos aqui no Repository para serem chamados dentro do Service (1).
	boolean existsByLicensePlateTruck(String licensePlateTruck); 
		
}