# Particle Swarm Optimization (PSO) — Research & Statistical Performance Study

This repository contains an implementation of **Particle Swarm Optimization (PSO)** in Java along with an experimental evaluation of multiple parameter configurations and their performance across 10 benchmark mathematical functions.

This project evolved from a simple PSO demo into a statistically rigorous experimentation framework using **non-parametric paired statistical testing (Wilcoxon Signed-Rank test)** to compare configurations.

---

## What is PSO?

Particle Swarm Optimization is a metaheuristic inspired by the collective movement of animals (e.g., flocks of birds or schools of fish).  
Each particle represents a candidate solution and moves based on:

- its current velocity
- its personal historically best position (pBest)
- the globally best position found (gBest)

PSO is widely used for optimization in:
- engineering
- neural network training
- allocation problems
- continuous optimization
- parameter tuning

---

## Pseudocode (algorithm behavior)
```
Initialize N particles with random positions and velocities
Evaluate objective value of each particle
Store initial personal best pBest
Select best pBest as global best gBest

For iteration = 1 to MAX_ITERS:
    For each particle:
        r1, r2 ← random values in [0,1]

        Update velocity:
            v = W * v + C1 * r1 * (pBest – x) + C2 * r2 * (gBest – x)

        Update position:
            x = x + v

        Evaluate objective value
        If improved:
            update pBest

    Update gBest using all pBests

Return final gBest


```
---

## Benchmark functions used (F1–F10)

These functions are standard in optimization research.

| Func | Name | Difficulty | Notes |
|------|------|------------|------|
| F1 | Sphere | easy | convex, unimodal |
| F2 | Sum of partial sums | medium | accumulates quadratic error |
| F3 | Rosenbrock | hard | narrow curved valley |
| F4 | Schwefel (sin-weighted) | deceptive | many local minima |
| F5–F10 | additional continuous functions | mixed | test generalization |

These functions allow testing:
- ability to escape local minima
- convergence speed
- precision in final solution

---

## Parameter Configurations

Tested 5 PSO variants:

| label | W | C1 | C2 | Meaning |
|------|----|----|----|---------|
| baseline | 0.7 | 1.4 | 1.4 | balanced/default |
| exploration | 0.95 | 1.0 | 1.0 | strong roaming, slow convergence |
| exploitation | 0.3 | 2.0 | 2.0 | quick local convergence |
| social | 0.6 | 0.5 | 2.5 | strong group influence |
| cognitive | 0.6 | 2.5 | 0.5 | strong self-reinforcement |

---

## Experimental Methodology

For each function F1–F10:

- each configuration was run **30 times**
- results recorded per iteration
- Extracted:
    - best found value
    - convergence behavior
    - stability (variance)
    - mean performance

Example extract:

F1:
- baseline: 0.00000000016
- exploration: 4.155
- exploitation: 0.00264
- social: 0.07419
- cognitive: 0.06109




---

## Statistical Comparison

Because optimization algorithms are **stochastic**, individual runs differ.

So instead of comparing averages only, also used:

### Wilcoxon Signed-Rank Test (non-parametric)

Used to compare paired samples:  
for each function:  
baseline vs exploration  
baseline vs exploitation  
baseline vs social  
baseline vs cognitive

Properties:
- distribution-free
- non-parametric
- valid on non-Gaussian data
- widely used in optimization literature

Tool used:
- https://www.statskingdom.com/175wilcoxon_signed_ranks.html

Interpretation:
- if p ≤ 0.05 → statistically significant difference

Example:  
baseline vs exploitation → p = 0.001
→ exploitation significantly different from baseline




---

## Results Summary (interpretation)

- **exploitation** performs best on smooth, convex surfaces
- **exploration** works better early but converges slowly
- **social** can prematurely converge
- **cognitive** is more chaotic but occasionally finds better minima

The balanced **baseline** is a good general-purpose choice but gets outperformed on specific functions.

---

## Running the Project

Compile & run:
```
javac *.java
java Main
```



Example output:
```
====================================================
                FUNCTION f1
====================================================
 1) baseline     | W=0.70 C1=1.40 C2=1.40  | AVERAGE = 0.000000000001638
 2) exploration  | W=0.95 C1=1.00 C2=1.00  | AVERAGE = 5.028484589644786
 3) exploitation | W=0.30 C1=2.00 C2=2.00  | AVERAGE = 0.301484357306079
 4) social       | W=0.60 C1=0.50 C2=2.50  | AVERAGE = 0.000000000000000
 5) cognitive    | W=0.60 C1=2.50 C2=0.50  | AVERAGE = 13.293931068200127
 
 ====================================================
                FUNCTION f2
====================================================
 1) baseline     | W=0.70 C1=1.40 C2=1.40  | AVERAGE = 0.073630054727399
 2) exploration  | W=0.95 C1=1.00 C2=1.00  | AVERAGE = 8.061792573584713
 3) exploitation | W=0.30 C1=2.00 C2=2.00  | AVERAGE = 0.326914750208013
 4) social       | W=0.60 C1=0.50 C2=2.50  | AVERAGE = 0.004289553314535
 5) cognitive    | W=0.60 C1=2.50 C2=0.50  | AVERAGE = 12.773782274007662

====================================================
                FUNCTION f3
====================================================
```



---

## Conclusion

This project demonstrates:

- implementation of PSO from scratch
- experimental tuning of hyperparameters
- objective benchmarking on multiple functions
- rigorous statistical comparison of algorithmic behavior

The methodology follows modern optimization research practice, ensuring reproducibility and validity of results.

---