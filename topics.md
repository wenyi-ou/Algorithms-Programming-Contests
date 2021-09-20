# General

* How the algorithms work
* Complexity
* Which is better when
* How to apply (problems similar to assignments)
* Run the algorithm by hand on paper
* How the complexity corresponds to what the algorithm does

# Data structures 1

## Binary search

Requirement: Sorted
Complexity: O(log n)

## Union Find

Complexity: «Almost» constant
* Inverse Ackermann, definition not required

# Graphs

## Representation

* Adjacency matrix
* Adjacency list
** (optional) Adjacency hashmap/hashset

What operation works better with adjacency matrix than with adjacency list?

## Traversal

* DFS
* BFS

Complexity: O(E+V)

## Topological sort

Complexity: O(E+V)

## Minimum spanning tree

Why Kruskal/Prim work correctly

Complexity:
* Kruskal: O(E log E)
* Prim: Better than O(E + V²)
** O(E+V log V) but not required, as the correct heap is rarely used…

## Dijkstra algorithm

Complexity:
* Better than O(E+V²) 
** O(E+V log V) but not required

Limitations!

## Bellman-Ford

Complexity: O(VE)

## Floyd-Warshall

Complexity: O(V³)

## Longest path in DAG

Complexity
* Bellman-Ford: O(VE)
* Topological sort: O(V+E)

# Maximum flow

## Minimum cut & theorem

## Ford-Fulkerson algorithm

Complexity: O(EU), answer-dependent

Non-termination for irrational capacities

## Edmonds-Karp

Complexity: Better than O(E³)
* Actaully O(VE²), not required

## Dinis, all shortest paths

Complexity: Better
* Actually O(V²E), not required

## Push-relabel

Differences with residual flow

Complexity: Better than Dinic
* Actually O(V²E), easy to obtain O(V³) with FIFO order of operations

## Bipartite matching

Choice of max-flow algorithm

# Brute force and backtracking

## Combinatorics (counting the cases)

combinatorial primitives: 
* Number of functions $A \mapsto B$
* Number of permutations of size $n$
* Number of $k$-element subsets of size $n$

Feasible problem sizes

## Backtracking vs. pure Brute force

Recognising applicability of backtracking and methods for pruning

# Greedy algorithms

## Exact greedy algorithms

Problems similar to assignments

## 3/2-optimal scheduling by longest-first

Why it works
Complexity: O(N log N)

## Local search

Example: Facility location problem

# Dynamic programming

General approach

Memoization, top-down vs. bottom-up

Problems similar to assignments

## Knapsack via dynamic programming

Small-weight, small-value

# Number theory

## Long arithmetics

Representations: integers, rationals

Simple algorithms for operations

## Fast exponentiation

Complexity: n^d in log d multiplications

## Euclidean algorithm

Greatest common divisor

Least common multiple

Chinese remainder theorem

Systems of modular equation with non-coprime moduli

Modular inverse

## Sieve of Eratosphenes

Complexity: O(n log log n)

# Data structures: Trie/Segment tree

## Trie

Complexity:
* Insert: O(L)
* Look-up: O(L)

## Range minimum queries, naive

Complexity:

O(1)/O(n) or O(n)/O(1)

## Segment tree

Complexity:
* Build: O(n)
* (Range) Add: O(log n)
* (Range) Sum: O(log n)

## LCA

Complexity:
* Build: O(n)
* Query: O(log n)

# Geometry

## Basic operations

* Line/halfline/segment intersection
* Triangle area (oriented)
* Point on the left/right from vector, CCW/CW

## Polygon operations

* Point in polygon: raycasting
* Area of a polygon

Complexity: O(n)

## Convex hull

General: implementing angle comparisons with CCW

### Gift-wrapping

Complexity: O(nh) where h size of convex hull

### Graham's scan

Complexity: O(n log n)

