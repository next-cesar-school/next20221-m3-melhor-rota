package com.nextm3project.bestRoute;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.nextm3project.models.TruckModel;

@Entity
@Table(name = "DISTANCE")
public class Distance implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private Integer id;
	
	@Column
	private int cost;
		
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}


	public static int distanceCalc(String status, String location) {
		int[][] m = new int[19][19];
		int contLineMat = 0;

		// inicio da leitura do grafo
		String pathArq = "C:\\temp\\ws-next-project\\next20221-m3-melhor-rota\\src\\main\\java\\com\\nextm3project\\bestRoute\\MODELAGEM_DESAFIO_NEXT.csv";

		try (BufferedReader br = new BufferedReader(new FileReader(pathArq))) {

			String line = br.readLine();
			line = br.readLine();

			while (line != null) {

				String[] vect = line.split(";");
				for (int contColMat = 0; contColMat < 19; contColMat++) {
					m[contLineMat][contColMat] = Integer.parseInt(vect[contColMat]);
				}
				contLineMat++;

				line = br.readLine();

			}

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}

		// Scanner sc = new Scanner(System.in);

		int n = m.length;
		int[][] path = new int[n][n];

		// Modifica a matriz para encontrar o start

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (m[i][j] == 100000)
					path[i][j] = -1;
				else
					path[i][j] = i;

		BestRoute.shortestpath(m, path);

		TruckModel truckModel = new TruckModel();
		String statusCaminhao = status; // cheio ou vazio
		String locationCaminhao = location; // truckModel.getLocation(); // location

		Map<Integer, String> matrixMap = new HashMap<Integer, String>();
		matrixMap.put(0, "INT1");
		matrixMap.put(1, "INT2");
		matrixMap.put(2, "INT3");
		matrixMap.put(3, "INT4");
		matrixMap.put(4, "INT5");
		matrixMap.put(5, "INT6");
		matrixMap.put(6, "INT7");
		matrixMap.put(7, "INT8");
		matrixMap.put(8, "INT9");
		matrixMap.put(9, "INT10");
		matrixMap.put(10, "INT11");
		matrixMap.put(11, "INT12");
		matrixMap.put(12, "INT13");
		matrixMap.put(13, "ESC1");
		matrixMap.put(14, "ESC2");
		matrixMap.put(15, "ESC3");
		matrixMap.put(16, "DESC1");
		matrixMap.put(17, "DESC2");
		matrixMap.put(18, "DESC3");

		Optional<Integer> indexMatriz = matrixMap.entrySet().stream()
				.filter(entry -> entry.getValue().equalsIgnoreCase(locationCaminhao)).map(entry -> entry.getKey())
				.findFirst();

		if (indexMatriz.isEmpty()) {
			// validação caso não tenha
		}

		int start = indexMatriz.get();

		truckModel.getStatus();

		if (statusCaminhao.equalsIgnoreCase("cheio")) {

			int desc1 = 16;
			int desc2 = 17;
			int desc3 = 18;

			String[] distRouteDesc1 = BestRoute.calculateDistanceAndPath(m, path, start, desc1);
			String[] distRouteDesc2 = BestRoute.calculateDistanceAndPath(m, path, start, desc2);
			String[] distRouteDesc3 = BestRoute.calculateDistanceAndPath(m, path, start, desc3);

			int BR = BestRoute.calculateBestDistance(start, distRouteDesc1, distRouteDesc2, distRouteDesc3);
			return BR;

		} else if (statusCaminhao.equalsIgnoreCase("vazio")) {
			int esc1 = 13;
			int esc2 = 14;
			int esc3 = 15;

			String[] distRouteEsc1 = BestRoute.calculateDistanceAndPath(m, path, start, esc1);
			String[] distRouteEsc2 = BestRoute.calculateDistanceAndPath(m, path, start, esc2);
			String[] distRouteEsc3 = BestRoute.calculateDistanceAndPath(m, path, start, esc3);

			int BR = BestRoute.calculateBestDistance(start, distRouteEsc1, distRouteEsc2, distRouteEsc3);
			return BR;
		} else {
			System.out.println("Status inválido. Opçoes para Status válidos: Cheio ou Vazio");
			return 0;
		}
	}
}
