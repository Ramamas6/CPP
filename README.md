# Coverage Path Planning

This project has for goal to create, test and compare different CPP algorithms. 

## Algorithms

2 algorithms are currently implemented

  - ### Glouton
    At each step, the algorithm goes to the last discovered but not visited case.
    
  - ### Shortest Path
    At each step, the algorithm goes to the closest case discovered but not visited.

## Source code

The project is divided in 3 main packages :

  - ### creation
    
    <details>
      <summary>Files for creating the algorithm test environment</summary>
      &nbsp;&nbsp;&nbsp;&nbsp;<ins>Challenge</ins> - main class of this package, handle the communication with the rest of the program<br>
      &nbsp;&nbsp;&nbsp;&nbsp;<ins>Room</ins> - field (cell grid)<br>
      &nbsp;&nbsp;&nbsp;&nbsp;<ins>Generation</ins> - algorithms to generate pseudo-random Rooms<br>
      &nbsp;&nbsp;&nbsp;&nbsp;<ins>Cell</ins> - field cells<br>
      &nbsp;&nbsp;&nbsp;&nbsp;<ins>State</ins> - enum of the states of the cells<br>
    </details>

  - ### gui
    <details>
      <summary>Files for the graphical interface</summary>
      &nbsp;&nbsp;&nbsp;&nbsp;<ins>Gui</ins> - main class of this package (main Panel), contains the others<br>
      &nbsp;&nbsp;&nbsp;&nbsp;<ins>GuiListener</ins> - listener implemented by Main to handle communication with Gui<br>
      &nbsp;&nbsp;&nbsp;&nbsp;<ins>GuiStats</ins> - statistics (top of the screen)<br>
      &nbsp;&nbsp;&nbsp;&nbsp;<ins>GuiChallenge</ins> - field configurations (left of the screen)<br>
      &nbsp;&nbsp;&nbsp;&nbsp;<ins>GuiSolving</ins> - solvers configurations (right of the screen)<br>
      &nbsp;&nbsp;&nbsp;&nbsp;<ins>GuiConfigs</ins> - global configurations (bottom of the screen)<br>
      
    </details>

  - ### sovlings
    The different solving algorithms currently implemented


