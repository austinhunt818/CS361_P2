package fa.nfa;

import fa.State;

import java.util.*;

public class NFAState extends State{

    Map<Character, Set<NFAState>> transitions = new HashMap<>();

    public NFAState() {
        super();
    }

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

    public Set<NFAState> toStates(char c) {
        return transitions.getOrDefault(c, Collections.emptySet());
    }    
}
