# Consumer and producer problem

Solution made for concurrency theory course in AGH UST. Solution has different consumers/producers implementations and different monitors on buffer, with possibility to use interchangeably. <br>

Consumer and producer (agent) implementation:
- fixed where agent uses specified portion of data each time
- random where agent uses random portion of data - randomness is restricted by setting random seed to generators in order to compare different versions.

Monitors that are wrapping buffer and ensure that only one agent operate on buffer at same time:
- lock monitor - implementation that uses 1 Condition and 3 Locks
- condition monitor - implementation that uses 4 Conditions
- a simple monitor - implementation that uses 1 Lock and 2 Conditions

We can test this implementation (or create own) and check:
- they speed (collecting statistics)
- do they produce a deadlock
- if they are starved more agents
