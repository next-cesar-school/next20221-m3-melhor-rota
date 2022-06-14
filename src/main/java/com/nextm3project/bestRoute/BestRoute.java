package com.nextm3project.bestRoute;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import com.nextm3project.models.TruckModel;


public class BestRoute 
{
	public static String routeCalc (String status , String location) {
		int[][] m = new int[19][19];
    	int contLineMat = 0;
		
    	// inicio da leitura do grafo
    	String pathArq = "D:\\eclipseWorkspace\\NextDesafio\\next20221-m3-melhor-rota\\src\\main\\java\\com\\nextm3project\\bestRoute\\MODELAGEM_DESAFIO_NEXT.csv";
				
		try (BufferedReader br = new BufferedReader(new FileReader(pathArq))) {
					
			String line = br.readLine();
			line = br.readLine();
			
			while (line != null) {
						
				String[] vect = line.split(";");
				for (int contColMat=0; contColMat<19; contColMat++) {
					m[contLineMat][contColMat] = Integer.parseInt(vect[contColMat]);
				}
				contLineMat++;
						
				line = br.readLine();
				
			}	
			
		}
		catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
    	
    	//Scanner sc = new Scanner(System.in);
                
    	int n = m.length;
    	int[][] path = new int[n][n];

        
        
        //Modifica a matriz para encontrar o start
        
        for (int i=0; i<n; i++)
            for (int j=0; j<n; j++)
                if (m[i][j] == 100000)
                    path[i][j] = -1;
    		else
                    path[i][j] = i;

    	shortestpath(m, path);
    	
    	TruckModel truckModel = new TruckModel();
    	String statusCaminhao = status; // cheio ou vazio
    	String locationCaminhao = location; //truckModel.getLocation(); // location 
    	
    	
    	String locationGraph[] = {"INT1", "INT2", "INT3", "INT4", "INT5", "INT6", "INT7", "INT8", "INT9", "INT10", "INT11", "INT12", "INT13", "ESC1", "ESC2", "ESC3", "DESC1", "DESC2", "DESC3"};
    	int indexMatriz = Arrays.asList(locationGraph).indexOf(locationCaminhao);
		int start = indexMatriz;
    	
    	truckModel.getStatus();       
        
        	
    	if (statusCaminhao == "cheio" || statusCaminhao == "Cheio" || statusCaminhao == "CHEIO"){
	    	
	    	int desc1 = 16;
	    	int desc2 = 17;
	    	int desc3 = 18;
	    	
	        String myPathDesc1 = desc1 + "";
	        String myPathDesc2 = desc2 + "";
	        String myPathDesc3 = desc3 + "";
	        String myPath = "";
	       
	
	        int distanceDesc1 = 0;
	        int distanceDesc2 = 0;
	        int distanceDesc3 = 0;
	        int distance = 0;	                  
	        
	        while (path[start][desc1] != start) {
	
	    		distanceDesc1 = m[path[start][desc1]][desc1] + distanceDesc1;   		
	            myPathDesc1 = path[start][desc1] + " -> " + myPathDesc1;
	           
	            desc1 = path[start][desc1];
	    	}
	    	distanceDesc1 = m[path[start][desc1]][desc1] + distanceDesc1;
	    	
	    	while (path[start][desc2] != start) {
	
	    		distanceDesc2 = m[path[start][desc2]][desc2] + distanceDesc2;   		
	            myPathDesc2 = path[start][desc2] + " -> " + myPathDesc2;
	           
	            desc2 = path[start][desc2];
	    	}
	    	distanceDesc2 = m[path[start][desc2]][desc2] + distanceDesc2;
	    	
	    	while (path[start][desc3] != start) {
	
	    		distanceDesc3 = m[path[start][desc3]][desc3] + distanceDesc3;   		
	            myPathDesc3 = path[start][desc3] + " -> " + myPathDesc3;
	           
	            desc3 = path[start][desc3];
	    	}
	    	distanceDesc3 = m[path[start][desc3]][desc3] + distanceDesc3;
	    	
	    	if (distanceDesc1 > distanceDesc2 && distanceDesc2 > distanceDesc3 ){
	    		distance = distanceDesc3;
	    		myPath = myPathDesc3;
	    	}else if (distanceDesc1 > distanceDesc2){
	    		distance = distanceDesc2;
	    		myPath = myPathDesc2;
	    	}else {
	    		
	    		distance = distanceDesc1;
	    		myPath = myPathDesc1; 
	    	}
	 
	    	myPath = start + " -> " + myPath;
	    	
	    	return myPath;
	    	
        }else if(statusCaminhao == "vazio" || statusCaminhao == "Vazio" || statusCaminhao == "VAZIO"){
        	int esc1 = 13;
	    	int esc2 = 14;
	    	int esc3 = 15;
	    	
	        String myPathEsc1 = esc1 + "";
	        String myPathEsc2 = esc2 + "";
	        String myPathEsc3 = esc3 + "";
	        String myPath = "";
	       
	
	        int distanceEsc1 = 0;
	        int distanceEsc2 = 0;
	        int distanceEsc3 = 0;
	        int distance = 0;	                  
	        
	        while (path[start][esc1] != start) {
	
	    		distanceEsc1 = m[path[start][esc1]][esc1] + distanceEsc1;   		
	            myPathEsc1 = path[start][esc1] + " -> " + myPathEsc1;
	           
	            esc1 = path[start][esc1];
	    	}
	    	distanceEsc1 = m[path[start][esc1]][esc1] + distanceEsc1;
	    	
	    	while (path[start][esc2] != start) {
	
	    		distanceEsc2 = m[path[start][esc2]][esc2] + distanceEsc2;   		
	            myPathEsc2 = path[start][esc2] + " -> " + myPathEsc2;
	           
	            esc2 = path[start][esc2];
	    	}
	    	distanceEsc2 = m[path[start][esc2]][esc2] + distanceEsc2;
	    	
	    	while (path[start][esc3] != start) {
	
	    		distanceEsc3 = m[path[start][esc3]][esc3] + distanceEsc3;   		
	            myPathEsc3 = path[start][esc3] + " -> " + myPathEsc3;
	           
	            esc3 = path[start][esc3];
	    	}
	    	distanceEsc3 = m[path[start][esc3]][esc3] + distanceEsc3;
	    	
	    	if (distanceEsc1 > distanceEsc2 && distanceEsc2 > distanceEsc3 ){
	    		distance = distanceEsc3;
	    		myPath = myPathEsc3;
	    	}else if (distanceEsc1 > distanceEsc2){
	    		distance = distanceEsc2;
	    		myPath = myPathEsc2;
	    	}else {
	    		
	    		distance = distanceEsc1;
	    		myPath = myPathEsc1; 
	    		
	    	}

	    	myPath = start + " -> " + myPath;
	    	
	    	return myPath;
        }
		return "";
	}
	
    public static int[][] shortestpath(int[][] adj, int[][] path) 
    {
        int n = adj.length;
        int[][] ans = new int[n][n];
        copy(ans, adj);
    	for (int k=0; k<n;k++) 
            for (int i=0; i<n; i++) 
        	for (int j=0; j<n;j++) 
                    if (ans[i][k]+ans[k][j] < ans[i][j]) {
                        ans[i][j] = ans[i][k]+ans[k][j];
          		        path[i][j] = path[k][j]; // k Ã© a conexÃ£o entre pontos
                    }
    	return ans;
    }
    public static void copy(int[][] a, int[][] b) 
    {
        for (int i=0;i<a.length;i++)
            for (int j=0;j<a[0].length;j++)
                a[i][j] = b[i][j];
    }
    public static void main(String[] args) {
        
    System.out.println("Melhor rota (cheio,INT4) = " + routeCalc("cheio", "INT4"));
    System.out.println("Melhor rota (vazio,INT4) = " + routeCalc("Vazio", "INT4"));
    }
    
}
