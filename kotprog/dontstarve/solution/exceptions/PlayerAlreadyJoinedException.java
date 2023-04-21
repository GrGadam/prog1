package prog1.kotprog.dontstarve.solution.exceptions;

public class PlayerAlreadyJoinedException extends Exception {

    public PlayerAlreadyJoinedException() {
        super("Már csatlakozott egy player a játékhoz!");
    }

}
