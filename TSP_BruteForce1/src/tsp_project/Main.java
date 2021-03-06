
/**
 * 
 */
package tsp_project;

/**
 * @author walid
 *
 */
public class Main {
	public static void main (String[] args) {
		/* Setting the array of cities by giving each city a random name */
		for(int i=0; i < Constants.number_of_cities; i++) {
			char city_name = (char)(i+65);
			Constants.cities[i] = new City(city_name+"");
		}
		
		/* Distances matrix initialization */
		for (int i=0; i < Constants.number_of_cities; i++) {
			for (int j=0; j < Constants.number_of_cities; j++) {
				Constants.dis_matrix[i][j] = -1;
			}
		}
		
		/* Setting the matrix of distances by giving random distances between cities */
		for (int i=0; i < Constants.number_of_cities; i++) {
			for (int j=0; j < Constants.number_of_cities; j++) {
				if (i == j) {
					continue;
				}
				else if (Constants.dis_matrix[i][j] != -1) {
					continue;
				}
				/* Symetric matrix of distances */
//				else {
//					Constants.dis_matrix[i][j] = Constants.cities[i].distance(Constants.cities[j]);
//					Constants.dis_matrix[j][xi] = Constants.dis_matrix[i][j];
//				}
				/* Non symmetric matrix of distances */
				else {
					Constants.dis_matrix[i][j] = Constants.cities[i].distance(Constants.cities[j]);
				}
			}
		}
		
		/* Printing the matrix of distances */
		System.out.println("Matrix representing the cities and the distances between them :");
		String str = "|\t";
		String str2 = "\t";
		for(int k=0; k < Constants.number_of_cities; k++) {
			str2 += " " + Constants.cities[k].getName() + "\t";
		}
		System.out.println(str2);
		
		for (int i=0; i < Constants.number_of_cities; i++) {
			for (int j=0; j < Constants.number_of_cities; j++) {
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
		
		/* Applying Branch And Bound algorithm */
		System.out.println();

		System.out.println("---- Branch And Bound  Algorithm ----");
		BranchAndBound branch_and_bound = new BranchAndBound();
		branch_and_bound.branchAndBound(Constants.paths);
		/* Applying Greedy  algorithm */

		System.out.println();

		System.out.println("---- Greedy Algorithm ----");
		GreedyAlgorithm greedy = new GreedyAlgorithm();
		greedy.GreedyAlgorithm(Constants.paths, 1);
		
		System.out.println();

		System.out.println("---- Minimum spanning tree Algorithm ----");

		
		MST t = new MST(); 
		t.primMST(Constants.dis_matrix,0); 

	}
}
