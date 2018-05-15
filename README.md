# MazeSolver
Maze solver for csc 380: algorithms

An instance of a runner object seeks to reach the end of the maze. Three different objects use three different algorithms to solve four different mazes of increasing size, fifty times each for a total of two-hundred runs. The objective is to evaluate the complexity of each algorithm’s ability to solve the maze based on amount necessary steps vs. the actual solution path step-sum.

The start of each maze is the top left, and the bottom right is the end. The runner marks each space it visits with blue and once it finds the final route, it marks that route with green.

**Junction Origination**<br/>
Whenever the runner encounters a dead-end, it moves back to the previous junction. The light blue squares represent junctions that the runner has jumped back to.

![Junction Origination Small](https://github.com/tha7556/MazeSolver/blob/master/junction%20origination%20small.png)

**Wall Follower**<br/>
The runner follows a wall (either right or left) until it makes it to the end of the maze. The image below shows the runner following the left wall.

![Wall Follower Small](https://github.com/tha7556/MazeSolver/blob/master/left%20wall%20small.png)

**Manhattan Distance**<br/>
This runner is the fastest of the three runners. It makes its decisions at each junction by taking the distance from each available point to the end point, and choosing the one with the smallest distance. Similarly to Junction Origination, if it makes a mistake it backtracks, however it doesn't mark the junctions like junction originationi does. 

![Manhattan Distance Small](https://github.com/tha7556/MazeSolver/blob/master/robert%20small.png)
