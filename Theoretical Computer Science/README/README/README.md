First File FSA:

Implementing an FSA validator. Program outputs the result.txt containing an error description (see validation result) or a report, indicating if FSA is complete (or incomplete) and warning (see warning messages) if any.
Validation result
Errors:
E1: A state 's' is not in the set of states
E2: Some states are disjoint
E3: A transition 'a' is not represented in the alphabet
E4: Initial state is not defined
E5: Input file is malformed
Report:
FSA is complete/incomplete
Warnings:
W1: Accepting state is not defined
W2: Some states are not reachable from the initial state
W3: FSA is nondeterministic

Input file format
states=[s1,s2,...]	  // s1 , s2, ... ∈ latin letters, words and numbers
alpha=[a1,a2, ...]	  // a1 , a2, ... ∈ latin letters, words, numbers and character '_’(underscore)
init.st=[s]	  // s ∈ states
fin.st=[s1,s2,...]	  // s1, s2 ∈ states
trans=[s1>a>s2,... ]
  // s1,s2,...∈ states; a ∈ alpha

Second Task File DFSA:

Implementing a DFSA to RegExp Translator. Given an DFSA description in the input.txt (see input file format) program should output to console an error description (see validation errors) OR a regular expression that corresponds to the given DFSA. 

Input
states=[s1,s2,...] // s1 , s2, ... ∈ latin letters, words and numbers

alpha=[a1,a2,...] // a1 , a2, ... ∈ latin letters, words, numbers and character '_'(underscore)

initial=[s] // s ∈ states

accepting=[s1,s2,...] // s1, s2 ∈ states

trans=[s1>a>s2,...] // s1,s2,...∈ states; a ∈ alpha
