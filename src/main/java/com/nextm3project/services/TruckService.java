package com.nextm3project.services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.nextm3project.models.TruckModel;
import com.nextm3project.repositories.TruckRepository;

@Service //Por ser uma camada de servico, deve inserir o esteriótipo @Service
public class TruckService {

	//Criando ponto de injeção de dependencias do ParkingSpotRepository
	
	final TruckRepository truckRepository;
	
	public TruckService(TruckRepository truckRepository) {						//Mesma funcao do @Autowired	
		this.truckRepository = truckRepository;
	}
	
	@Transactional												    			//Em uso de métodos construtivos ou destrutivos, é importante usar o @Transactional, principalmente quando tem relacionamento.
	public TruckModel save(TruckModel truckmodel) {				    			//Método criado para salvar os dados de Service para Model (truckService.save(truckModel)), presente no Controller.
		return truckRepository.save(truckmodel);
	}
			
	public boolean existsByLicensePlateTruck(String licensePlateTruck) {  		//Método criado para verificar se já existe a placa do caminhao inserido pelo cliente na classe Controller.
		return truckRepository.existsByLicensePlateTruck(licensePlateTruck);    //Para funcionar foram declarados em Repository (1).
	}
		
}

//@Autowired									    			    //Autowired: Serve para informar ao Spring que em determinados momentos 
//TruckRepository truckRepository;		        			        //ele vai ter que injetar dependencias de TruckRepository dentro de TruckService (basicamente um construtor).


