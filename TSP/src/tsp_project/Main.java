/**
 * 
 */
package tsp_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author walid
 *
 */
public class Main {
	static int number_of_cities;
	private static Scanner sc;
	static int database = Constants.use_database;
	
	
	public static void main (String[] args) {
		if (database == 1) {
			List<Integer> rows = new ArrayList<Integer>();
			try {
				File f = new File(Constants.database);
				sc = new Scanner(f);
				/* Ignore first line which includes matrix size */
				number_of_cities = Integer.parseInt(sc.nextLine());
				
				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					String[] details = line.split("\\s+");
					for(String s: details) {
						rows.add(Integer.parseInt(s));
					}
				}
				
				/* Distances matrix initialization */
				for (int i=0; i < number_of_cities; i++) {
					for (int j=0; j < number_of_cities; j++) {
						Constants.dis_matrix[i][j] = -1;
					}
				}
				
				/* Setting the array of cities by giving each city a name */
				int nb = 0;
				for(int i=0; i < number_of_cities; i++) {
					String city_name = "C" + nb;
					nb++;
					Constants.cities[i] = new City(city_name);
				}
			
				for (int i=0; i < number_of_cities; i++) {
					for (int j=0; j < number_of_cities; j++) {
						Constants.dis_matrix[i][j] = rows.get(j + (i * number_of_cities));
					}
				}
			}
			catch (FileNotFoundException e) {
				System.out.println("The file was not found, random variables are gonna be used instead !");
				database = 0;
	            e.printStackTrace();
			}
//			System.out.println(number_of_cities);
//			for (int i=0; i < cities.size(); i++) {
//				System.out.println(cities.get(i).getName() + " " + cities.get(i).getX() + " " + cities.get(i).getY());
//			}
		}
		if (database != 1) {
			number_of_cities = Constants.number_of_cities;
			
			/* Distances matrix initialization */
			for (int i=0; i < number_of_cities; i++) {
				for (int j=0; j < number_of_cities; j++) {
					Constants.dis_matrix[i][j] = -1;
				}
			}
			
			/* Setting the array of cities by giving each city a name */
			int nb = 0;
			for(int i=0; i < number_of_cities; i++) {
				String city_name = "C" + nb;
				nb++;
				Constants.cities[i] = new City(city_name);
			}
			
			/* Setting the matrix of distances by giving random distances between cities */
			for (int i=0; i < number_of_cities; i++) {
				for (int j=0; j < number_of_cities; j++) {
					if (i == j) {
						continue;
					}
					else if (Constants.dis_matrix[i][j] != -1) {
						continue;
					}
					/* Symetric matrix of distances */
//					else {
//						Constants.dis_matrix[i][j] = Constants.cities[i].distance(Constants.cities[j]);
//						Constants.dis_matrix[j][i] = Constants.dis_matrix[i][j];
//					}
					/* Non symmetric matrix of distances */
					else {
						Constants.dis_matrix[i][j] = Constants.cities[i].distance(Constants.cities[j]);
					}
				}
			}
		}
		
		/* Printing the matrix of distances */
		System.out.println("Matrix representing the cities and the distances between them :");
		String str = "|\t";
		String str2 = "\t";
		for(int k=0; k < number_of_cities; k++) {
			str2 += " " + Constants.cities[k].getName() + "\t";
		}
		System.out.println(str2);
		
		for (int i=0; i < number_of_cities; i++) {
			for (int j=0; j < number_of_cities; j++) {
		        str += Constants.dis_matrix[i][j] + "\t";
			}
			System.out.print(Constants.cities[i].getName());
			System.out.println(str + "|");
	        str = "|\t";
		}
		System.out.println();
		
		/* Computing the candidate paths */
		Path path = new Path();
		path.cityPermutation(path.convertPathToString(Constants.cities));
		path.convertStringToPath(Constants.candidate_paths);
		 
		/* Brute Force Algo */
		double debut = System.currentTimeMillis();
		System.out.println("Applying the \"Brute Force\" algorithm :");
		BruteForce a = new BruteForce();
		a.bruteForce(Constants.paths);
		System.out.println();
		System.out.println("Execution time : " + (System.currentTimeMillis() - debut) + " ms");
		System.out.println();
		
		System.out.println("----------------------------------------------------------");
		System.out.println();
		
		/* Branch and Bound Algo */
		debut = System.currentTimeMillis();
		System.out.println("Applying the \"Branch and Bound\" algorithm :");
		BranchAndBound b = new BranchAndBound();
		b.initializeNodes();
		b.branchAndBound();
		System.out.println();
		System.out.println("Execution time : " + (System.currentTimeMillis() - debut) + " ms");
		System.out.println();
		
		System.out.println("----------------------------------------------------------");
		System.out.println();
		
		/* Add and Remove Edges Algo */
//		debut = System.currentTimeMillis();
//		System.out.println("Applying the \"Add and Remove Edges\" algorithm :");
//		AddAndRemoveEdges c = new AddAndRemoveEdges();
//		c.addAndRemoveEdges();
//		System.out.println();
//		System.out.println("Execution time : " + (System.currentTimeMillis() - debut) + " ms");
//		System.out.println();
		
//		System.out.println("----------------------------------------------------------");
		System.out.println();
		
		/* Minimum Spanning Tree Algo */
		debut = System.currentTimeMillis();
		System.out.println("Applying the \"Minimum Spanning Tree\" algorithm :");
		MinimunSpanningTree d = new MinimunSpanningTree(); 
		d.primMST(Constants.dis_matrix, Constants.starting_point);
		System.out.println();
		System.out.println("Execution time : " + (System.currentTimeMillis() - debut) + " ms");
		System.out.println();
		
		System.out.println("----------------------------------------------------------");
		System.out.println();
		
		/* Greedy Algo */
		debut = System.currentTimeMillis();
		System.out.println("Applying the \"Greedy\" algorithm :");
		Greedy e = new Greedy();
		e.GreedyAlgorithm(Constants.paths, Constants.starting_point);
		System.out.println();
		System.out.println("Execution time : " + (System.currentTimeMillis() - debut) + " ms");
		System.out.println();
		
		System.out.println("----------------------------------------------------------");
		System.out.println();
		
		/* Dynamic Algo */
		debut = System.currentTimeMillis();
		System.out.println("Applying the \"Dynamic\" Algorithm :");
    	Dynamic f = new Dynamic();
    	f.minCost(Constants.dis_matrix);
		System.out.println();
		System.out.println("Execution time : " + (System.currentTimeMillis() - debut) + " ms");
		System.out.println();
		
		System.out.println("----------------------------------------------------------");
		System.out.println();
		
		/* Randomized Algo */
		debut = System.currentTimeMillis();
		System.out.println("Applying the \"Randomized\" Algorithm :");
		Randomized g = new Randomized();
		g.initializeNodes();
		g.randomized();
		System.out.println();
		System.out.println("Execution time : " + (System.currentTimeMillis() - debut) + " ms");
	}
}
