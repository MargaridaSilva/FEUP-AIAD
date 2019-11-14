# AIAD

## Intellij Setup Tutorial

1. Clone project

2. Open Intellij, Import Project

3. If no configuration available, add application configuration. Update working directory, classpath and set main class to serviceConsumerProviderVis.Repast3ServiceConsumerProviderLauncher.

4. Build and run project


## Predator-Prey Multi-Agent System


### Scenario
In this work we aim to develop a system which would simulate some wildlife behaviours. In particular, Predator-Pray huntings.

### Agents
As an initial approach, we will be working with two sorts of agents: Predator and Prey. The behaviour of such is inspired by the study case of the Cheetah and Gazelle, respectively. 

As future work, we intend to add different sorts of predator agents, in hopes of introducing the concept of a food chain. 


### Multi-agent System
The state of each agent will be determined by four indicators:

- Geographical Coordinates
- Energy levels
- Will to reproduce
- Energy Expenditure


Each of the agents will thrive to obtain energy enough in the environment, possibly acquiring enough to even reproduce. Naturally, the main difference is the energy source. In the case of Preys, they feed on Plants, and in case of Predators, they feed on Preys.


### Environment
The environment is composed of a map of several plants, randomly positioned at the beginning. Afterwards, the plants grow at some established rate.
This environment will have the following properties:

- __Multi-agent__, hence there are more than one agents which will be competing/cooperating, depending on the circumstances
- __Partially Observable__, hence each agent will only have access to its neighboorhood
- __Deterministic__, hence all actions in this context have well-established consequences (so as to enforce the survival values)
- __Sequential__, hence the agents have to spare resources, i.e., previous actions might lead to their death
- __Dynamic__, since the agents' positions and even the amount of plants available varies with time
- __Discrete__, hence there is a limited number of actions each agent can take


### Interactions

The __Preys interact with the environment by feeding on various types of Plants__, which differentiate themselves by the different levels of energy they offer to the animal they feed. As __Predators feed on the Preys__, they don't have to rely on the surrounding environment for food. 

The Predators have a certain energy expenditure, as do the Preys. The Predators have to ensure that they have enough energy to catch their Preys (based on their current energy and their energy expenditure). The preys can also run away if they have enough energy, but again, the energy expenditure will affect their ability to escape.

__Both Predators and Preys can reproduce__ with individuals of their kinds, which is a kind of interaction that also implies energy consumption. The individuals from new generations have an energy expenditure that results from the combination of the energy consumption of their parents.

Occasionaly, some of the male predators might be interested in the same potential partner. In such a case, they can __compete__, leading to the death of the weakest predator. The weakest predator is the one which can fight for a shorter period, taking into account the energy it has at the beginning of the fight and also its energy consumption rate. A similar interaction happens among the Preys, and especially in periods of food scarcity, but this time the Preys compete for food and not for a partner. 


### Individual and Global Goals

Each of the agents has the objective of guaranteeing their survival and, consequently, the subsistence of their species, which implies keeping their energy needs satisfied in order to ensure reproduction. To achieve those goals they have to use certain strategies so as to ensure efficient management of the food resources.

### Strategies

Both species can exhibit __cooperative instincts__. On one hand, the Predators can cooperate to kill a Prey when they find their energy unsufficient to reach that goal. This way, the food is shared between the Predators involved in the hunt and the energy distributed among them. On the other hand, the Prey can share food, which is a useful instinct to ensure that the species survives a food shortage situation.


### Variables


| Examples of Dependent Variables                       | Examples of Independent Variables                       |
| ----------------------------------------- | ------------------------------------------- |
| Preys' subsistence                        | Number of plants                            |
| Predators' subsistence                    | Growth rate of the plants                   |
| Energy consumption of the new generations | Initial number of preys                     |
|                                           | Initial number of predators                 |
|                                           | Initial energy expenditure of the preys     |
|                                           | Initial energy expenditure of the predators |
