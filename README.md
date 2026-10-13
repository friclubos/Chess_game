# Chess Game

A chess game developed in Java as a first-year informatics project and later extended with additional features.

The game allows two players to play chess on the same computer through a graphical user interface.

## Features

- Graphical chessboard with selectable pieces
- Legal piece movements
- Alternating turns for white and black
- Check, checkmate and stalemate detection
- Castling
- En passant capture
- Pawn promotion
- 10-minute timer for each player
- Game-over notification
- Option to start a new game

## Technologies

- Java
- Java Swing
- Java AWT

## Getting Started

### Requirements

- Java Development Kit (JDK)
- A Java IDE or terminal

### Running the Application

1. Clone or download the repository.
2. Open the project in your preferred Java IDE.
3. Make sure the chess piece images are available in the required `images` directory.
4. Run the `SachovaHra.java` file.

Alternatively, if all Java source files are in the same directory, compile and run the application from the terminal:

```bash
javac *.java
java SachovaHra
```

## Project Structure

The project is organized into classes responsible for the graphical interface, chessboard, individual pieces and game logic.

- `SachovaHra.java` – graphical interface and game controls
- `Sachovnica.java` – chessboard and movement logic
- `Figurka.java` – base class for chess pieces
- `Kral.java` – king
- `Dama.java` – queen
- `Veza.java` – rook
- `Strelec.java` – bishop
- `Jazdec.java` – knight
- `Pesec.java` – pawn
- `Farba.java` – piece colors
- `Tah.java` – move representation
- `TypFigurky.java` – chess piece types

