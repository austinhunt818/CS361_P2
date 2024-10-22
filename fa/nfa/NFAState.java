package fa.nfa;

import fa.State;

public class NFAState extends State{
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

    @Override
    public String toString() {
        return super.toString();
    }

    public long toStates(char c) {
        return 0;
    }
}
