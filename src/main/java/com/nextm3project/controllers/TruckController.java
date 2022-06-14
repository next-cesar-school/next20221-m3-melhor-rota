package com.nextm3project.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nextm3project.dtos.TruckDto;
import com.nextm3project.models.TruckModel;
import com.nextm3project.services.TruckService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)		//Permitindo que seja acessado de qualquer fonte.
@RequestMapping("/truck") 						// Criando o Mapping (URI) a nível de classe
public class TruckController {

	final TruckService truckService;
	
	public TruckController(TruckService truckService) {									// Mesma funcao do @Autowired.
		this.truckService = truckService;
	}
	
	//Criando o método POST
	
	@PostMapping
    public ResponseEntity<Object> saveTruck(@RequestBody @Valid TruckDto truckDto){		//Método para salvar so dados digitados pelo cliente.
        if(truckService.existsByLicensePlateTruck(truckDto.getLicensePlateTruck())){	//Verificação se já existe registro dos dados: placa do caminhao.
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Truck License Plate is already in use!");
        }        
        var truckModel = new TruckModel();												//Iniciando uma instância para salvar os dados em TruckModel
        BeanUtils.copyProperties(truckDto, truckModel);									//Convertendo os dados inseridos em Dto para Model
        truckModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));			//Setando a data de registro de forma automatica.
        return ResponseEntity.status(HttpStatus.CREATED).body(truckService.save(truckModel));
    }
	
	//Criando um método GET All para exibicao da listagem de todos os caminhoes cadastrados no banco de dados.
	@GetMapping
	public ResponseEntity<List<TruckModel>> getAllTruck(){
		return ResponseEntity.status(HttpStatus.OK).body(truckService.findAll());
	}
	
	//Criando um método GET ONE para exibicao do caminhao cadastrado no banco de dados buscando pela placa do caminhão.
	@GetMapping("/{licensePlateTruck}")
	public ResponseEntity<Object> getOneTruck(@PathVariable(value = "licensePlateTruck") String licensePlateTruck){
		Optional<TruckModel> truckModelOptional = truckService.findByLicensePlateTruck(licensePlateTruck);			//Método findByid serve buscar o id no banco de dados e vai retornar um Optional de TruckModel
		if (!truckModelOptional.isPresent()) {																		//Condicao para verificar se aquele id digitado existe ou não.
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Truck not found.");							//Se este Optional não estiver presente, será retornado uma mensagem not found.
		}
		return ResponseEntity.status(HttpStatus.OK).body(truckModelOptional.get());
	}
	
	//Criando um método PUT para atualizar algum caminhão do banco de dados, sendo acessado pela placa do caminhão.
	@PutMapping("/{licensePlateTruck}")
	public ResponseEntity<Object> updateTruck(@PathVariable(value = "licensePlateTruck") String licensePlateTruck, @RequestBody @Valid TruckDto truckDto){
		Optional<TruckModel> truckModelOptional = truckService.findByLicensePlateTruck(licensePlateTruck);			//Fazendo dessa forma, garante que o id e a data de registro não serão modificados.
		if (!truckModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Truck not found.");
		}
		//Aproveitando o registro que foi obtido na verificacao do if (truckModelOptional), assim não preciso instanciar do zero.
		var truckModel = truckModelOptional.get();
		truckModel.setStatus(truckDto.getStatus());										//Setando os dados que podem ser atualizados.
		truckModel.setLocation(truckDto.getLocation());									//Nao coloquei a placa do caminhao para atualizar.
		return ResponseEntity.status(HttpStatus.OK).body(truckService.save(truckModel));
	}
}

//@Autowired									//Autowired: Serve para informar ao Spring que em determinados momentos 
//TruckService truckService;					//ele vai ter que injetar dependencias de TruckService dentro de TruckController (basicamente um construtor).