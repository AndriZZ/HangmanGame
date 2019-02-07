package server;

public enum GameMessages{

    letterNotExist("Letter does not exist"),
	youLost("You lost!!!"),
    gameOver("Game over!"),
    letterAlreadyGuessed("Letter has already been guessed."),
    emptyMessage("Click on a letter first!"),
    tryAgain("Try another one!"),
    youWin("You win!!!"),
    gameWon("Game won!"),
    correctLetter ("Letter correct!"),
    keepGuessing("Keep guessing");

    private String message;

    GameMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}