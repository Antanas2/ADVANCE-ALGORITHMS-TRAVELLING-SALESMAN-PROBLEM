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
			Constants.cities[i] = new City((char)city_name);
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
				else {
					Constants.dis_matrix[i][j] = Constants.cities[i].distance(Constants.cities[j]);
					Constants.dis_matrix[j][i] = Constants.dis_matrix[i][j];
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
		path.convertStringToPath(Path.candidate_paths);
		
		System.out.println("The candidate paths are :");
		for (int i=0; i < Path.numberOfPossiblePaths(); i++) {
			for (int j=0; j < Constants.number_of_cities + 1; j++) {
				System.out.print(Path.paths[i][j].getName());
			}
			System.out.println();
		}
		System.out.println();
		
		/* Applying Brute-Force algorithm */
		BruteForce brute_force = new BruteForce();
		brute_force.bruteForce(Path.paths);
	}
}