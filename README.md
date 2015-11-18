# Poker
This project is an implementation of Poker Hold'em game in Java.

## How to use ?
Import project in your favorite IDE.

**Be sure that maven is managed. The project has a dependency.**

To launch your just have to run `Main.main()`.

## For the second part...
...you can extend `Player` and `Poker` to implement distribution. `Poker` represents to server and `Player` the client.

## Diagram
The structure is quite complex. Here is the intellij generated diagram :

![Diagram](https://bytebucket.org/ptango/poker/raw/8d15076f46cc2f1f49fd896264b8d31f0f892d81/diagrams/diagram.png)

[Full size](https://bytebucket.org/ptango/poker/raw/8d15076f46cc2f1f49fd896264b8d31f0f892d81/diagrams/diagram.png)
[Full size + detail](https://bytebucket.org/ptango/poker/raw/33d053bd1731268be127299079cbfea301e3fa9c/diagrams/diagram.svg)


## Improvements

- Add configuration for initialValue (money), players, etc.
- Add tests
- Improve logic and coherence

## My work each week

- **S45-46-47** : A lot of bug fixes, improvements and tests in order that poker works.
- **S44** : Implement of shutdown. Refactor project into packages. Extract business methods from CyclicIterator. Create abstract methods for poker and player. This methods will be redefined in the second part of the project.
- **S43** : Start to implement poker game itself. Add method valueOf to Chip and the corresponding tests. Add a method to random factory to generate a random integer in a range. Start of implementation for Player and Table. Define ChipStack and it tests. Add a tool to add functional features like dropWhite, take, etc. 
- **S42** : Creation of test for Button, and fixes of Button. Tests for Card. Thoughts about structure for the second part of the project.
- **S41** : Improvement of CardPacket. Creation of the Button and Hand interface. Add implementation and tests of Hand interface with HandConcrete. I've created the enum Chip and a method to get all chip values.
- **S40** : Create structure from the class diagram. Add a way to generate a card packet (generate all cards and a way to permute cards). Add a function to get the first card of the packet. Start tests of the CardPacket.

