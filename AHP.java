/***********************************************************
 * Copyright 2020 Rafi Mochamad Fahreza
 * prokoding
 * Analytical Hierarchy Process
 ***********************************************************/

import java.util.*;

public class AHP{

	public double[] getWeight(double[][] x, int n_criteria){
		/*
		This function will get the average of each row of the given matrix
		and returning as 1 dimension matrix.
		*/
		double[] weight = new double[x.length];
		for(int i=0; i<x.length; i++){
			double sum = 0;
			for (double value : x[i]){
				sum+=value;
			}
			weight[i] = Math.round((sum/n_criteria)*100.0) / 100.0;
		}
		return weight;
	}
	
	public double[][] normalize(double[][] x){
		/*
		This is the important function, its the main method of Analytical Hierarchy Process
		The function will normilize the given matrix 
		*/
		double[] sum_col = new double[x[0].length];
		List<double[]> norm_mat = new ArrayList<>();
		
		for(int row=0; row<x[0].length; row++){
			for(int col=0; col<x[0].length; col++){
				sum_col[row] = Math.round((x[col][row] + sum_col[row])*100.0)/100.0;
			}
		}
		
		for(int i=0; i<x.length; i++){
			double[] norm_mat_col = new double[x[0].length];
			for(int j=0; j<x[0].length; j++){
				norm_mat_col[j]=Math.round((x[i][j] / sum_col[j])*100.0)/100.0;
			}
			norm_mat.add(norm_mat_col);
		}
		
		double[][] norm_matrix = new double[x.length][x[0].length];
		for(int i=0; i<x.length; i++){
			norm_matrix[i] = norm_mat.get(i);
		}
		return norm_matrix;
	}

	public double[][] matMul(double[][] x, double[][] y){
		/*
		This function will multiply each given matrix with
		standard multiply method, and returning the result of multiplied matrixes.
		*/
		double[][] result = new double[x.length][y[0].length];

		if(y.length != x[0].length){
			System.out.println("Multiply is not possible");
		}

		for(int row=0; row<x.length; row++){
			for(int col=0; col<y[0].length;col++){
				for(int k=0; k<y.length; k++){
					result[row][col] += x[row][k] * y[k][col];
				}
			}
		}
		return result;
	}
	
	public double[][] transpose(double[][] x){
		/*
		This function will transpose the given matrix
		and returning the transposed matirx
		*/
		double[][] transposed = new double[x[0].length][x.length];
		for(int i=0;  i<x[0].length;  i++){
			for(int j=0; j<x.length; j++){
				transposed[i][j] = x[j][i];
			}
		}
		return transposed;
	}

	public static void main(String[] args) {

		/* Example case 

		Layers Structure :
			0
			0 0 0 //Pairwise of the criterias
			0 0 0 //Pairwise of the alternatives
		*/
		AHP ahp = new AHP();

		double[][] layer1 = { {1.0, 0.3, 5.0}, {3.0, 1.0, 5.0}, {0.2, 0.2, 1.0} };
		double[] layer1_weight = ahp.getWeight(ahp.normalize(layer1), layer1[0].length);
		double[][] layer1_fix = new double[1][1];
		layer1_fix[0] = layer1_weight;

		double[][] layer2_1 = {{1.0, 0.2, 0.14}, {5.0, 1.0, 0.3}, {7.0, 3.0, 1.0}};
		double[] layer2_1_weight = ahp.getWeight(ahp.normalize(layer2_1), layer2_1[0].length);

		double[][] layer2 = new double[layer2_1.length][layer2_1[0].length];
		layer2[0] = layer2_1_weight;

		double[][] layer2_2 = {{1.0, 3.0, 0.2}, {0.3, 1.0, 0.3}, {5.0, 3.0, 1.0}};
		double[] layer2_2_weight = ahp.getWeight(ahp.normalize(layer2_2), layer2_2[0].length);
		layer2[1] = layer2_2_weight;

		double[][] layer2_3 = {{1.0, 0.3, 0.14}, {3.0, 1.0, 0.2}, {7.0, 5.0, 1.0}};
		double[] layer2_3_weight = ahp.getWeight(ahp.normalize(layer2_3), layer2_3[0].length);
		layer2[2] = layer2_3_weight;

		double[][] layer2_transpose = ahp.transpose(layer2);
		double[][] multiplied = ahp.matMul(layer1_fix,layer2);
		for(double[] tr:multiplied){
			System.out.println(Arrays.toString(tr));
		}
	}
}
