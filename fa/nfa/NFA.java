package fa.nfa;

import fa.State;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class NFA implements NFAInterface {

    Set<NFAState> states = new LinkedHashSet<>();
    Set<NFAState> finalStates = new LinkedHashSet<>();
    Set<Character> symbols = new LinkedHashSet<>();

    NFAState startState;

    @Override
    public boolean addState(String name) {
        if(states.contains(getStateByName(name, states))){
            return false;
        }
        states.add(new NFAState(name));
        return true;
    }

    @Override
    public boolean setFinal(String name) {
        NFAState state = getStateByName(name, states);
        if (state == null) {
            return false; // State does not exist
        }
        finalStates.add(state);
        return true;
    }

    @Override
    public boolean setStart(String name) {
        NFAState state = getStateByName(name, states);
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
        return false;
    }

    @Override
    public boolean isStart(String name) {
        return false;
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        return getStateByName(from.getName(), states).transitions.get(onSymb);
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
        return null;
    }

    @Override
    public int maxCopies(String s) {
        return 0;
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
        }

        fromStateObj.transitions.put(onSymb, toStatesObj);
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
