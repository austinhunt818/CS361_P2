package fa.nfa;

import fa.State;

import java.util.*;

/**
 * @author Austin Hunt
 * @author Carson Magee
 *
 * An NFAState that manages its own transitions
 */
public class NFAState extends State{

    Map<Character, Set<NFAState>> transitions = new HashMap<>();

    public NFAState(String name) {
        super(name);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    /**
     * Adds a transition to a specified state on the specified symbol
     * @param toState the state to transition to
     * @param symbol the symbol to transition on
     */
    public void addTransition(NFAState toState, char symbol){
        if(transitions.containsKey(symbol)){
            transitions.get(symbol).add(toState);
        }
        else {
            Set<NFAState> transitionStates = new LinkedHashSet<>();
            transitionStates.add(toState);
            transitions.put(symbol, transitionStates);
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
