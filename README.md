# A simple topic-based Message Broker
Extremely simple, yet very fast Message Broker written in Scala with Akka actors. 
I have not benchmark is properly, but some quick tests showed it can process (aknowldge, queue and distribute) 100k messages in less than a second.
Allthough, this result is not remarkable since Akka actors themselves can handle a much bigger RPS.

## Diagrams
![image](https://github.com/FoxFurry/MessageBroker/assets/33430469/e3150bc5-da5c-47bf-a9e2-83d18df27879)

![image](https://github.com/FoxFurry/MessageBroker/assets/33430469/ae1fb05f-5790-442e-9db1-1d0b661d27c5)

![image](https://github.com/FoxFurry/MessageBroker/assets/33430469/dd5148b6-0085-4c30-8ba4-a24b52aa3792)
