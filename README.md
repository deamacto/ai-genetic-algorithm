# ai-genetic-algorithm

### **Introduction**
`ai-genetic-algorithm` is a project in which GA is used to find most efficient factory in each category.

### **Methods**
Factory is a matrix with randomly generated setup of ints representing machines. Each factory has an efficiency calculated  using the formula
$$ \sum_{i,j} F_{ij} C_{ij} D_{ij} $$
where

F<sub>ij</sub> - flow amount between i and j machines

C<sub>ij</sub> - cost of flow between i and j machines

D<sub>ij</sub> - distance between i and j machines

The cost and flow values are read from the data files, distance is calculated using the Manhattan distance formula.

For the generation selection either the tournament or roulette algorithm is used. Some specimens are then mutated or crossed over, depending on hyperparameters.

### **Results**
Redults can be viewed in wyniki.pdf file
