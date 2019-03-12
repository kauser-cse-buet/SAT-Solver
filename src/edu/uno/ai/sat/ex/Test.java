package edu.uno.ai.sat.ex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import edu.uno.ai.sat.Main;
import edu.uno.ai.sat.Problem;
import edu.uno.ai.sat.Settings;
import edu.uno.ai.sat.Solver;
import edu.uno.ai.sat.solve.BruteForceSolver;
import edu.uno.ai.sat.solve.DPLL;
import edu.uno.ai.sat.solve.GSAT;
import edu.uno.ai.sat.solve.WalkSAT;

/**
 * An UNOFFICIAL way to test a solver from inside an IDE like Eclipse.
 * 
 * @author Stephen G. Ware
 */
public class Test {

	/**
	 * The solvers to compare. You can comment some out if you only want to
	 * test against certain ones.
	 */
	private static final Solver[] SOLVERS = new Solver[] {
		new MahmmedSolver(),
		new BruteForceSolver(),
		new DPLL(),
		new GSAT(),
		new WalkSAT(),
	};
	
	/**
	 * The benchmark problems to solve. You can comment some out if you only
	 * want to test certain problems.
	 */
	private static final String[] PROBLEMS = new String[] {
//		"benchmarks/true.sat",
//		"benchmarks/false.sat",
//		"benchmarks/positive_literal.sat",
//		"benchmarks/negative_literal.sat",
//		"benchmarks/disjunction.sat",
//		"benchmarks/conjunction.sat",
//		"benchmarks/contradiction.sat",
//		"benchmarks/cnf_1.sat",
//		"benchmarks/cnf_2.sat",
//		"benchmarks/cnf_3.sat",
//		"benchmarks/graph_coloring_easy_1.sat",
//		"benchmarks/graph_coloring_easy_2.sat",
//		"benchmarks/graph_coloring_easy_3.sat",
//		"benchmarks/graph_coloring_easy_4.sat",
//		"benchmarks/graph_coloring_easy_5.sat",
//		"benchmarks/graph_coloring_hard_1.sat",
//		"benchmarks/graph_coloring_hard_2.sat",
//		"benchmarks/graph_coloring_hard_3.sat",
//		"benchmarks/graph_coloring_hard_4.sat",
//		"benchmarks/graph_coloring_hard_5.sat",
//		"benchmarks/3sat_easy_1.sat",
//		"benchmarks/3sat_easy_2.sat",
//		"benchmarks/3sat_easy_3.sat",
//		"benchmarks/3sat_easy_4.sat",
//		"benchmarks/3sat_easy_5.sat",
//		"benchmarks/3sat_medium_1.sat",
//		"benchmarks/3sat_medium_2.sat",
//		"benchmarks/3sat_medium_3.sat",
//		"benchmarks/3sat_medium_4.sat",
//		"benchmarks/3sat_medium_5.sat",
//		"benchmarks/3sat_hard_1.sat",
//		"benchmarks/3sat_hard_2.sat",
//		"benchmarks/3sat_hard_3.sat",
//		"benchmarks/3sat_hard_4.sat",
//		"benchmarks/3sat_hard_5.sat",
//		"benchmarks/bb_cake_eat_cake_time_1.sat",
//		"benchmarks/bb_cake_have_eat_cake_time_2.sat",
//		"benchmarks/bb_blocks_easy_stack_time_1.sat",
//		"benchmarks/bb_blocks_easy_unstack_time_1.sat",
//		"benchmarks/bb_blocks_sussman_time_3.sat",
//		"benchmarks/bb_blocks_reverse_2_time_2.sat",
//		"benchmarks/bb_blocks_reverse_4_time_5.sat",
//		"benchmarks/bb_blocks_reverse_6_time_7.sat",
//		"benchmarks/bb_blocks_reverse_8_time_9.sat",
//		"benchmarks/bb_blocks_reverse_10_time_11.sat",
//		"benchmarks/bb_blocks_reverse_12_time_13.sat",
//		"benchmarks/bb_cargo_deliver_1_time_3.sat",
//		"benchmarks/bb_cargo_deliver_2_time_3.sat",
//		"benchmarks/bb_cargo_deliver_3_time_5.sat",
//		"benchmarks/bb_cargo_deliver_return_1_time_4.sat",
//		"benchmarks/bb_cargo_deliver_return_2_time_4.sat",
//		"benchmarks/bb_wumpus_easy_wumpus_time_3.sat",
		"benchmarks/bb_wumpus_medium_wumpus_time_7.sat"
	};

	/**
	 * Read the example problems and benchmark all solvers on those problems.
	 * The results will be displayed on the console and written to
	 * 'results.html.'
	 * 
	 * @param args ignored
	 * @throws Exception if an uncaught exception is thrown
	 */
	public static void main(String[] args) throws Exception {
		Problem[] problems = new Problem[PROBLEMS.length];
		for(int i=0; i<problems.length; i++) {
			System.out.println("Reading problem \"" + PROBLEMS[i] + "\"...");
			problems[i] = new Problem(new File(PROBLEMS[i]));
		}
		try(Writer output = new BufferedWriter(new FileWriter("results.html"))) {
			Main.benchmark(SOLVERS, problems, Settings.OPERATIONS_LIMIT, Settings.TIME_LIMIT, output);
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
}
