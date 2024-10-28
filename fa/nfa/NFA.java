package fa.nfa;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Stack;

/**
 * @author Austin Hunt
 * @author Carson Magee
 *
 * An NFA that is able to add states and transitions, and can check the
 * e-closure of a state, max amount of copies, and if a string is accepted
 * by the machine.
 */
public class NFA implements NFAInterface {

    Set<NFAState> states = new LinkedHashSet<>();
    Set<NFAState> finalStates = new LinkedHashSet<>();
    Set<Character> symbols = new LinkedHashSet<>();

    NFAState startState;

    @Override
    public boolean addState(String name) {
        if(states.contains(getState(name))){
            return false;
        }
        states.add(new NFAState(name));
        return true;
    }

    @Override
    public boolean setFinal(String name) {
        NFAState state = getState(name);
        if (state == null) {
            return false; // State does not exist
        }
        finalStates.add(state);
        return true;
    }

    @Override
    public boolean setStart(String name) {
        NFAState state = getState(name);
        if (state == null) {
            return false; // State does not exist
        }
        startState = state;
        return true;
    }

    @Override
    public void addSigma(char symbol) {
        symbols.add(symbol);
    }

    @Override
    public boolean accepts(String s) {
        Set<NFAState> currentStates = eClosure(startState);
        for (char c : s.toCharArray()) {
            currentStates = traceNFA(currentStates, c);
        }
        for (NFAState state : currentStates) {
            for(NFAState finalState : finalStates){
                if(state.equals(finalState)) return true;
            }
        }
        return false;
    }

    @Override
    public Set<Character> getSigma() {
        return symbols;
    }

    @Override
    public NFAState getState(String name) {
        return getStateByName(name, states);
    }

    @Override
    public boolean isFinal(String name) {
        return finalStates.contains(getStateByName(name, finalStates));
    }

    @Override
    public boolean isStart(String name) {
        return startState.getName().equals(name);
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        return from.transitions.get(onSymb);
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
        Set<NFAState> closure = new HashSet<>();
        Stack<NFAState> stack = new Stack<>();
        stack.push(s);
        while (!stack.isEmpty()) {
            NFAState currentState = stack.pop();
            closure.add(currentState);
            Set<NFAState> epsilonTransitions = currentState.transitions.get('e');
            if (epsilonTransitions != null) {
                for (NFAState transState : epsilonTransitions) {
                    if (!closure.contains(transState)) {
                        stack.push(getStateByName(transState.getName(), states));
                    }
                }
            }
        }
        return closure;
    }

    @Override
    public int maxCopies(String s) {
        int max = 1;
        Set<NFAState> currentStates = eClosure(startState);
        for (char c : s.toCharArray()) {
            if(!symbols.contains(c)) {
                Set<NFAState> newStates = new LinkedHashSet<>();
                for(NFAState state : currentStates){
                    newStates.addAll(eClosure(state));
                }
                currentStates = newStates;
            }
            else currentStates = traceNFA(currentStates, c);
            max = Math.max(max, currentStates.size());
        }
        return max;
    }

    /**
     * Does one step of a trace through the machine
     * @param currentStates the current states at this point in the trace
     * @param c the symbol to take the next transition on
     * @return the next set of states after taking the transitions from each current state on c
     */
    private Set<NFAState> traceNFA(Set<NFAState> currentStates, char c) {
        Set<NFAState> nextStates = new HashSet<>();
        for (NFAState state : currentStates) {
            Set<NFAState> transitions = getToState(state, c);
            if (transitions != null) {
                for (NFAState transState : transitions) {
                    nextStates.addAll(eClosure(transState));
                }
            }
        }
        currentStates = nextStates;
        return currentStates;
    }

    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {
        NFAState fromStateObj = getState(fromState);
        Set<NFAState> toStatesObj = new HashSet<>();

        if(fromStateObj == null || (onSymb != 'e' && !symbols.contains(onSymb))){
            return false;
        }
        for(String stateName : toStates){
            if(getState(stateName) == null)  return false;
            toStatesObj.add(new NFAState(stateName));
            fromStateObj.addTransition(getState(stateName), onSymb);
        }

        return true;
    }

    @Override
    public boolean isDFA() {
        return false;
    }

    /**
     * A private getter for a specific state from the specified set to help with getting DFAStates
     * @author Austin Hunt
     * @param target the name of the target state
     * @param stateSet the set of states to get target from
     * @return the state associated with the name, or null of it isn't in the set
     */
    private NFAState getStateByName(String target, Set<NFAState> stateSet) {
        for (NFAState curr : stateSet) {
            if (curr.getName().equals(target)) {
                return curr;
            }
        }
        return null;
    }
}
