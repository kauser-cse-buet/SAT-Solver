package edu.uno.ai.sat.ex;

import java.util.ArrayList;
import java.util.Random;

import edu.uno.ai.sat.Assignment;
import edu.uno.ai.sat.Clause;
import edu.uno.ai.sat.Solver;
import edu.uno.ai.sat.Value;
import edu.uno.ai.sat.Variable;

/**
 * 
 * @author Your Name
 */
public class MahmmedSolver extends Solver {

	private final Random random = new Random(0);
	
	/**
	 * Constructs a new random SAT solver. You should change the string below
	 * from "random" to your ID. You should also change the name of this class.
	 * In Eclipse, you can do that easily by right-clicking on this file
	 * (RandomAgent.java) in the Package Explorer and choosing Refactor > Rename.
	 */
	public MahmmedSolver() {
		super("mahmmed");
	}

	/*
	@Override
	public boolean solve(Assignment assignment) {
		// If the problem has no variables, it is trivially true or false.
		if(assignment.problem.variables.size() == 0)
			return assignment.getValue() == Value.TRUE;
		else {
			// Keep trying until the assignment is satisfying.
			while(assignment.getValue() != Value.TRUE) {
				// Choose a variable whose value will be set.
				Variable variable = chooseVariable(assignment);
				// Choose 'true' or 'false' at random.
				Value value;
				if(random.nextBoolean())
					value = Value.TRUE;
				else
					value = Value.FALSE;
				// Assign the chosen value to the chosen variable.
				assignment.setValue(variable, value);
			}
			// Return success. (Note, if the problem cannot be solved, this
			// solver will run until it reaches the operations or time limit.)
			return true;
		}
	}
	*/
	
	@Override
	public boolean solve(Assignment assignment) {
		// If the problem has no variables, it is trivially true or false.
		if(assignment.problem.variables.size() == 0)
			return assignment.getValue() == Value.TRUE;
		else {
			
			

			if(assignment.getValue() == Value.TRUE) {
				return true;
			}
			
			
			if(assignment.getValue() == Value.FALSE) {
				return false;
			}
			
			Variable variable = chooseUnassignedVariable(assignment);
			
			if(tryValue(assignment, variable, Value.FALSE)) {
				return true;
			}
			
			if(tryValue(assignment, variable, Value.TRUE)) {
				return true;
			}
			
			return false;
			
		}
	}
	
	private boolean tryValue(Assignment assignment, Variable variable, Value value) {
        Value actualValue = assignment.getValue(variable);
        assignment.setValue(variable, value);
        if (solve(assignment)) {
            return true;
        }
        else {
	        assignment.setValue(variable, actualValue);
	        return false;
        }
	}

	/**
	 * Randomly choose a variable from the problem whose value will be set. If
	 * any variables have the value 'unknown,' choose one of those first;
	 * otherwise choose any variable.
	 * 
	 * @param assignment the assignment being worked on
	 * @return a variable, chosen randomly
	 */
	private final Variable chooseVariable(Assignment assignment) {
		// This list will hold all variables whose current value is 'unknown.'
		ArrayList<Variable> unknown = new ArrayList<>();
		// Loop through all the variables in the problem and find ones whose
		// current value is 'unknown.'
		for(Variable variable : assignment.problem.variables)
			if(assignment.getValue(variable) == Value.UNKNOWN)
				unknown.add(variable);
		// If any variables are 'unknown,' choose one of them randomly.
		if(unknown.size() > 0)
			return unknown.get(random.nextInt(unknown.size()));
		// Otherwise, choose any variable from the problem at random.
		else
			return assignment.problem.variables.get(random.nextInt(assignment.problem.variables.size()));
	}
	
	private final Variable chooseUnassignedVariable(Assignment assignment) {
		// This list will hold all variables whose current value is 'unknown.'
		ArrayList<Variable> unknown = new ArrayList<>();
		// Loop through all the variables in the problem and find ones whose
		// current value is 'unknown.'
		for(Variable variable : assignment.problem.variables)
			if(assignment.getValue(variable) == Value.UNKNOWN)
				unknown.add(variable);
		// If any variables are 'unknown,' choose one of them randomly.
		if(unknown.size() > 0)
//			return unknown.get(random.nextInt(unknown.size()));
			return unknown.get(0);
		// Otherwise, choose any variable from the problem at random.
		else
			return null;
	}
	
	
}
