package prog1.kotprog.dontstarve.solution;

import prog1.kotprog.dontstarve.solution.character.BaseCharacter;
import prog1.kotprog.dontstarve.solution.character.Character;
import prog1.kotprog.dontstarve.solution.character.actions.Action;
import prog1.kotprog.dontstarve.solution.exceptions.NotImplementedException;
import prog1.kotprog.dontstarve.solution.exceptions.PlayerAlreadyJoinedException;
import prog1.kotprog.dontstarve.solution.level.BaseField;
import prog1.kotprog.dontstarve.solution.level.Field;
import prog1.kotprog.dontstarve.solution.level.Level;
import prog1.kotprog.dontstarve.solution.utility.Position;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * A játék működéséért felelős osztály.<br>
 * Az osztály a singleton tervezési mintát valósítja meg.
 */
public final class GameManager {

    private boolean isLevelLoaded = false;
    private Level level;
    private Field[][] fields;
    private ArrayList<Character> characters;

    private boolean hasPlayer;


    /**
     * Az osztályból létrehozott egyetlen példány (nem lehet final).
     */
    private static GameManager instance = new GameManager();

    /**
     * Random objektum, amit a játék során használni lehet.
     */
    private final Random random = new Random();

    /**
     * Az osztály privát konstruktora.
     */
    private GameManager() {
        characters = new ArrayList<>();
        hasPlayer = false;
    }

    /**
     * Az osztályból létrehozott példány elérésére szolgáló metódus.
     * @return az osztályból létrehozott példány
     */
    public static GameManager getInstance() {
        return instance;
    }

    /**
     * A létrehozott random objektum elérésére szolgáló metódus.
     * @return a létrehozott random objektum
     */
    public Random getRandom() {
        return random;
    }

    /**
     * Egy karakter becsatlakozása a játékba.<br>
     * A becsatlakozásnak számos feltétele van:
     * <ul>
     *     <li>A pálya már be lett töltve</li>
     *     <li>A játék még nem kezdődött el</li>
     *     <li>Csak egyetlen emberi játékos lehet, a többi karaktert a gép irányítja</li>
     *     <li>A névnek egyedinek kell lennie</li>
     * </ul>
     * @param name a csatlakozni kívánt karakter neve
     * @param player igaz, ha emberi játékosról van szó; hamis egyébként
     * @return a karakter pozíciója a pályán, vagy (Integer.MAX_VALUE, Integer.MAX_VALUE) ha nem sikerült hozzáadni
     */
    public Position joinCharacter(String name, boolean player) {

        if (!isGameStarted()) {
            if (getCharacter(name) == null) {
                if (player && !hasPlayer) {
                    characters.add(new Character(name, player));
                } else if (!player) {
                    characters.add(new Character(name, player));
                }
            }
        }

        return new Position(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Egy adott nevű karakter lekérésére szolgáló metódus.<br>
     * @param name A lekérdezni kívánt karakter neve
     * @return Az adott nevű karakter objektum, vagy null, ha már a karakter meghalt vagy nem is létezett
     */
    public BaseCharacter getCharacter(String name) {

        for (BaseCharacter character : characters) {
            if (character.getName().equals(name)) {
                return character;
            }
        }

        return null;
    }

    /**
     * Ezen metódus segítségével lekérdezhető, hogy hány karakter van még életben.
     * @return Az életben lévő karakterek száma
     */
    public int remainingCharacters() {
        int remaining = 0;

        for (Character character : characters) {
            if (character.getHp() > 0) {
                remaining++;
            }
        }

        return remaining;
    }

    /**
     * Ezen metódus segítségével történhet meg a pálya betöltése.<br>
     * A pálya betöltésének azelőtt kell megtörténnie, hogy akár 1 karakter is csatlakozott volna a játékhoz.<br>
     * A pálya egyetlen alkalommal tölthető be, később nem módosítható.
     * @param level a fájlból betöltött pálya
     */
    public void loadLevel(Level level) {

        if (!isLevelLoaded) {
            this.level = level;
            fields = new Field[level.getHeight()][level.getWidth()];

            for (int sor = 0; sor < level.getHeight(); sor++) {
                for (int oszlop = 0; oszlop < level.getWidth(); oszlop++) {
                    Field field = new Field();
                    field.setColor(level.getColor(sor, oszlop));
                    fields[sor][oszlop] = field;
                }
            }

            isLevelLoaded = true;
        }

    }

    public boolean isLevelLoaded() {
        return isLevelLoaded;
    }

    /**
     * A pálya egy adott pozícióján lévő mező lekérdezésére szolgáló metódus.
     * @param x a vízszintes (x) irányú koordináta
     * @param y a függőleges (y) irányú koordináta
     * @return az adott koordinátán lévő mező
     */
    public BaseField getField(int x, int y) {
        return fields[y][x];
    }

    /**
     * A játék megkezdésére szolgáló metódus.<br>
     * A játék csak akkor kezdhető el, ha legalább 2 karakter már a pályán van,
     * és közülük pontosan az egyik az emberi játékos által irányított karakter.
     * @return igaz, ha sikerült elkezdeni a játékot; hamis egyébként
     */
    public boolean startGame() {
        throw new NotImplementedException();
    }

    /**
     * Ez a metódus jelzi, hogy 1 időegység eltelt.<br>
     * A metódus először lekezeli a felhasználói inputot, majd a gépi ellenfelek cselekvését végzi el,
     * végül eltelik egy időegység.<br>
     * Csak akkor csinál bármit is, ha a játék már elkezdődött, de még nem fejeződött be.
     * @param action az emberi játékos által végrehajtani kívánt akció
     */
    public void tick(Action action) {
        throw new NotImplementedException();
    }

    /**
     * Ezen metódus segítségével lekérdezhető az aktuális időpillanat.<br>
     * A játék kezdetekor ez az érték 0 (tehát a legelső időpillanatban az idő 0),
     * majd minden eltelt időpillanat után 1-gyel növelődik.
     * @return az aktuális időpillanat
     */
    public int time() {
        throw new NotImplementedException();
    }

    /**
     * Ezen metódus segítségével lekérdezhetjük a játék győztesét.<br>
     * Amennyiben a játéknak még nincs vége (vagy esetleg nincs győztes), akkor null-t ad vissza.
     * @return a győztes karakter vagy null
     */
    public BaseCharacter getWinner() {
        throw new NotImplementedException();
    }

    /**
     * Ezen metódus segítségével lekérdezhetjük, hogy a játék elkezdődött-e már.
     * @return igaz, ha a játék már elkezdődött; hamis egyébként
     */
    public boolean isGameStarted() {
        throw new NotImplementedException();
    }

    /**
     * Ezen metódus segítségével lekérdezhetjük, hogy a játék befejeződött-e már.
     * @return igaz, ha a játék már befejeződött; hamis egyébként
     */
    public boolean isGameEnded() {
        throw new NotImplementedException();
    }

    /**
     * Ezen metódus segítségével beállítható, hogy a játékot tutorial módban szeretnénk-e elindítani.<br>
     * Alapértelmezetten (ha nem mondunk semmit) nem tutorial módban indul el a játék.<br>
     * Tutorial módban a gépi karakterek nem végeznek cselekvést, csak egy helyben állnak.<br>
     * A tutorial mód beállítása még a karakterek csatlakozása előtt történik.
     * @param tutorial igaz, amennyiben tutorial módot szeretnénk; hamis egyébként
     */
    public void setTutorial(boolean tutorial) {
        throw new NotImplementedException();
    }

    public Level getLevel() {
        return this.level;
    }

    public boolean hasPlayer() {
        return hasPlayer;
    }


}
