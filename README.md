# Game-of-Three-reactive
Game of three implemented with WebFlux, Reactive Mongo, EventSourcing and CQRS.


This repository takes the [Game of Three](https://github.com/razorcd/Game-of-Three) implementation in vanilla Java and integrates it with Spring WebFlux, ReactiveMongodb following Event Sourcing and CQRS.

#### At the moment this app is still work in progress!


## Architecture


```
                 _________                _____________           _______________
                |         |              |             |         |               |
  (actions)     | Actions | (subscribe)  |             |         |               |
--------------->|  Event  |------------->|             |         |               |
                |  Source |              |             |         |               |
                |_________|              |   Command   |         | Game Of Three |
                                         | Interpreter |<------->|    Engine     | 
                 _________               |             |         |               |
                |         |              |             |         |               |
<---------------| Actions |  (publish)   |             |         |               |
  (log events)  |  Logs   |<-------------|             |         |               |
                |_________|              |_____________|         |_______________|
``` 