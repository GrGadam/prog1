package prog1.kotprog.dontstarve.solution.character.actions;

import prog1.kotprog.dontstarve.solution.character.Character;

public class ActionManager {

    private Action action;
    private Character character;

    public ActionManager(Action action, Character character) {
        this.action = action;
        this.character = character;
    }

    private void start() {
        switch (action.getType()) {
            case ATTACK -> attack();
        }
    }

    private void attack() {

    }

}
