package edu.uno.ai.sat.ex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;

import edu.uno.ai.sat.Assignment;
import edu.uno.ai.sat.Clause;
import edu.uno.ai.sat.Literal;
import edu.uno.ai.sat.Solver;
import edu.uno.ai.sat.Value;
import edu.uno.ai.sat.Variable;
import edu.uno.ai.util.MinPriorityQueue;

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
	
	@Override
	public boolean solve(Assignment assignment) {
		// If the problem has no variables, it is trivially true or false.
		if(assignment.problem.variables.size() == 0)
			return assignment.getValue() == Value.TRUE;
		else {
			if(assignment.getValue() == Value.FALSE) {
				return false;
			}
			
			if(assignment.getValue() == Value.TRUE) {
				return true;
			}
						
			// unitPropagation
			for (Clause clause : assignment.problem.clauses) {

				if(assignment.getValue(clause) == Value.UNKNOWN && assignment.countUnknownLiterals(clause) == 1) {
					
					Literal literal = null;
					
					for (Literal l : clause.literals) {
						if(assignment.getValue(l) == Value.UNKNOWN) {
							literal = l;
						}	
					}
					Variable variable = literal.variable;
					
					Value value = Value.FALSE;
					if(literal.valence) {
						value = Value.TRUE;
					}
					
					if(!tryValue(assignment, variable, value)) {
						return false;
					}
					else {
						return true;
					}
				}
			}
			
			// Detect pure variables. propagate setting pure variable.  
			for (Variable variable : assignment.problem.variables) {
				if(assignment.getValue(variable) == Value.UNKNOWN) {
					double valenceDiffRatio = getValenceDiffRatio(assignment, variable);
					
					if(valenceDiffRatio == 1.0) {
						// positive pure variable
						return tryValue(assignment, variable, Value.TRUE);
					}
					
					if(valenceDiffRatio == -1.0) {
						// negative pure variable
						return tryValue(assignment, variable, Value.FALSE);
					}
				}
			}
			

			Variable variable = chooseUnassignedVariable(assignment);
			
			
			double valenceDiffRatio = getValenceDiffRatio(assignment, variable);
			
			if(valenceDiffRatio >= 0) {
				
				return tryValue(assignment, variable, Value.TRUE) || tryValue(assignment, variable, Value.FALSE); 
			}
			
			if(valenceDiffRatio < 0) {
				return tryValue(assignment, variable, Value.FALSE) || tryValue(assignment, variable, Value.TRUE);
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
	
	private final Variable chooseUnassignedVariable(Assignment assignment) {
		MinPriorityQueue<Variable> unknownPQ = new MinPriorityQueue<Variable>();
		
		// Loop through all the variables in the problem and find ones whose
		// current value is 'unknown.'
		for(Variable variable : assignment.problem.variables)
			if(assignment.getValue(variable) == Value.UNKNOWN) {
				double valencediffRatio = getValenceDiffRatio(assignment, variable);
				if(valencediffRatio == Double.POSITIVE_INFINITY) {
					//totalCount = 0, means all of its clauses have value. so its value is useless.
					continue;
				}
				else {
					double absValencediffRatio = Math.abs(valencediffRatio); // scale in the range [0, 1]. 1 is the pure variable.
					
					// we will prioritize more value closer to 1. Higher priority value closer to 1. lower priority value closer to 0.
					// as we are using min priority queue we will reverse the the absValencediffRatio, by subtract from 1. 
					// so 1 - absValencediffRatio will scale [0, 1] to [1, 0]. 
					unknownPQ.push(variable, 1 - absValencediffRatio);
				}
			}
		
		if(unknownPQ.size() > 0)
			return unknownPQ.peek();
		
		else
			return null;
	}
	
	private final double getValenceDiffRatio(Assignment assignment, Variable variable) {
		double posCount = 0.0;
		double negCount = 0.0;
		
		
		for (Literal literal : variable.literals) {
			if(assignment.getValue(literal.clause) == Value.UNKNOWN) {
				if(literal.valence) {
					posCount += 1.0;
				}
				else {
					negCount += 1.0;
				}
			}
		}
		
		double totalCount = posCount + negCount;
		
		if(totalCount != 0) {
			// between the range [-1.0 , 1.0]
			return (posCount - negCount) / totalCount; 
		}
		else {
			
			//totalCount = 0, means all of its clauses have value. so its value is useless.  
			return Double.POSITIVE_INFINITY;
		}
	}
}
