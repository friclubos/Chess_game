Chess Game in Java

This is a simple chess game that I worked on during my first year of studying informatics. I gradually modified the project and added new features.

The game is designed for two players playing on the same computer. It has a graphical interface, so players can select pieces and move them using the mouse.

Features

Chessboard with the standard starting positions.

Movement of chess pieces according to their rules.

Turns alternate between the white and black players.

Check, checkmate and stalemate detection.

Castling.

En passant capture.

Pawn promotion to another piece.

A 10-minute time limit for each player.

A message showing the result when the game ends.

Option to start a new game.

Piece images and highlighting of the selected square.

Technologies Used

Java

Java Swing – graphical user interface.

AWT – working with the window, colors and mouse events.

How to Run

Download or clone the project from GitHub.

Open the project in an IDE, such as IntelliJ IDEA or Eclipse.

Make sure the chess piece images are stored in the images folder.

Run SachovaHra.java.

You can also run the game from the command line if all Java files are in the same folder:

javac *.java
java SachovaHra

How to Play

Click on one of your chess pieces.

Click on the square where you want to move it.

After a valid move, the other player takes their turn.

The game ends when there is checkmate, stalemate or a player's time runs out.

Project Structure

SachovaHra.java – graphical interface, game controls and timer.

Sachovnica.java – chessboard, piece positions and checking valid moves.

Figurka.java – common base class for all chess pieces.

Kral.java – king movement and castling.

Dama.java – queen movement.

Veza.java – rook movement.

Strelec.java – bishop movement.

Jazdec.java – knight movement.

Pesec.java – pawn movement and special rules.

Farba.java – piece colors.

Tah.java – stores information about a move.

TypFigurky.java – chess piece types.
