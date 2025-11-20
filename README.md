# Particle Swarm Optimization (PSO) in Java

This project provides a simple and educational implementation of **Particle Swarm Optimization (PSO)** in Java.  
The goal is to offer a clear and intuitive version of PSO suitable for learning, experimenting, and understanding the core mechanics of the algorithm.

---

## Overview

Particle Swarm Optimization is a population-based metaheuristic inspired by the behavior of flocking birds.  
Each particle represents a candidate solution and moves in the search space according to:

- its **current velocity**
- its **personal best position**
- the **global best position** found by the swarm

The algorithm iteratively updates velocities and positions until it converges or reaches a maximum number of iterations.

This implementation supports different objective functions, including:

- Sphere function
- Sum of partial sums (quadratic)
- Rosenbrock function
- Schwefel-like sine function

---

## Project Structure

### `Particle.java`
Represents a single particle with:
- position
- velocity
- personal best position
- personal best value

Includes different objective functions and the logic to evaluate and update personal best.

### `Main.java`
Implements the core PSO loop:
- swarm initialization
- velocity and position updates
- global best tracking
- console input for number of particles and problem dimension

---

## How the Algorithm Works

1. Initialize a population of particles randomly.
2. Evaluate each particle and store its personal best.
3. Determine the global best across all particles.
4. Iteratively update:
    - velocity
    - position
    - personal best
    - global best
5. Return the best solution found.

---

## Pseudocode

```
Initialize N particles with random positions and velocities
For each particle:
    Evaluate its objective value
    Store position as personal best (pbest)

Set global best (gbest) as the best pbest in the swarm

Repeat for MAX_ITERS iterations:
    For each particle:
        Generate random numbers r1 and r2 in [0, 1]

        Update velocity:
            v = w * v
                + c1 * r1 * (pbest - x)
                + c2 * r2 * (gbest - x)

        Clamp velocity to [-VMAX, VMAX]

        Update position:
            x = x + v

        Clamp position to allowed bounds

        Evaluate objective value
        If value improves pbest:
            update pbest

    Update gbest using all pbest values

Return gbest
```

---

## Running the Program

The program will ask for:
- number of particles
- dimension of the search space

Example run:

```
Enter the number of particles: 30
Enter the dimension of the problem: 10
Iter 0 -> gBest = ...
Iter 100 -> gBest = ...
...
Best solution value found: <value>
Best position: [...]
```

---

## Parameters Used

| Parameter | Meaning | Value |
|----------|----------|--------|
| `W` | Inertia weight | 0.5 |
| `C1` | Cognitive component | 1.5 |
| `C2` | Social component | 1.5 |
| `VMAX` | Max velocity | 0.5 |
| `LOWER/UPPER` | Search space bounds | [-30, 30] |
| `MAX_ITERS` | Max iterations | 5000 |

These can be tweaked to observe performance differences.

---

## Notes

- Velocity clamping helps prevent explosion of particles.
- The Rosenbrock function is challenging and commonly used for benchmarking.
- Initialization uses fixed random seed for reproducibility.
- The implementation is intentionally simple for educational purposes.

---