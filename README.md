# Project 2

* Authors: Austin Hunt, Carson Magee
* Class: CS361 Section 001
* Semester: Fall 2024

## Overview

This program implements and simulates a Non-deterministic Finite Automata in Java.

## Reflections

### Austin
This project was very interesting and I feel that I have a deeper understanding of NFAs after completing it. Much of it was similar to the previous DFA project. The differences
were in the NFA class's accept, eclosure, and maxCopies methods. For this project, Carson did a first pass implementation of these, while I set up most of the other stuff and
debugged them. While debugging, I ran into an issue in multiple tests where any kind of trace would seemingly stop early. After some debugging, I found that some of the state
objects that were being returned and passed did not have transitions. Eventually, I realized that the issue was that the states that were given as the transition states of a
given origin state were not actually instances of the other states of the machine, but just represented them by name. This was due to how the tester creates the NFA

### Carson


## Compiling and Using

To compile the project, navigate to the top level directory and run the following commands:

```javac fa/*.java```

```javac fa/nfa/*.java```

The program has no main method, so to use it you would need to implement a test suite. An NFA can be created using a series of method calls found in fa.nfa.NFA.java.

## Sources used

* LinkedHashSet docs - https://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashSet.html