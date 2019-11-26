# Predator-Prey Multi-Agent System

## [Group 28] Second Project

##### Agents and Distributed Artificial Intelligence

###### Table of Contents

1. [Context](#context)
2. [Data Analytics Tasks](#predictive-data-analytics-tasks)
3. [Dependent Variables](#dependent-variables)
4. [Independent Variables](#independent-variables)

---

### 1. <a name="context">Context</a>

The first project of the _Agents and Distributed Artificial Intelligence_ aimed at developing a multi agent system composed of three interacting types of agents, namely __Preys__, __Predators__ and an additional third entity, unique to the simulation, called the __Observer__ agent. Predator and Prey agents interact with the Observer agent to retrieve information regarding the state of the world in which they exist. Furthermore, both Preys and Predators communicate with other agents of the same _species_.

The Observer agent never inittiates communication. Instead it communicates with the _animal agents_ upon the reception of a message. Such messages convey requests to validate moves (to ensure that the agents never collide), questions about the location of food resources and notifications related to the termination of an agent.

On the other hand,  the animal agents exhibit more complex behaviours which are dependent on their energy level. Lower energy levels imply an instinct of __feeding__ (on Preys or Plants according to the type of animal agent), while intermediate levels lead to a __random movement__ of the agent and higher values to a __mating__ state which can result on the growth of the population.

This environment is totally dynamic due to the constant movement of the different agents, the emergence of new plants and the eating behaviours. In addition, the agents exhibit an __energy expenditure level__ which is a measure of the amount by which the energy level decreases upon a move to a contiguous position. The female agents also look at the energy expenditure values of potential partners and elect the one with the lowest value. In fact, the energy expenditure acts as a measurement of the animal's adaptability.

### 2. <a name="predictive-data-analytics-tasks">Predictive Data Analytics Tasks</a>

The goal of the second project is to predict the final state of an environment given its initial setup. In particular, the interest behind this project is to solve a __classification problem__ which involves predicting which species are going to prevail (either the predators or the preys). More over, there's a __regression problem__ which is also worth considering: analyzing the average life expectancy of both species as well as the maximum and minimum offset between the number of preys and the number of predators. The previous will provide a better understanding of which dependent variables have an higher influence on the subsistence of the species.

### 3. <a name="dependent-variables">Dependent Variables</a>

- Regression Problem
    - Average life expectancy of the predators
    - Average life expectancy of the preys
    - Maximum offset between the number of preys and the number of predators
    - Minimum offset between the number of preys and the number of predators

- Classification Problem
    - Remaining species (the surviving species)

### 4. <a name ="independent-variables">Independent Variables</a>

- Initial number of plants
- Initial number of predators
- Initial number of preys
- Initial ratio of male and female predators
- Initial ratio of male and female preys
- Average energy expenditure values of the predators
- Average energy expenditure values of the preys
- Average energy values of the predators
- Average energy values of the preys
