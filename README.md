# Numbers-Game-reactive
Numbers game implemented with WebFlux, Reactive Mongo, Event Sourcing (commands) and CQRS.


This repository takes the [Numbers-Game](https://github.com/razorcd/numbers-game) implementation in vanilla Java and integrates it with Spring WebFlux, ReactiveMongodb following Event Sourcing as the commands and CQRS.

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
