// Jeremiah Roise.
// CSC1060
// november 17, 2023
// this is a simple game of adding any combination of two dice rolls to reach a score of 30.
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
/**
 * main
 */
public class Main {

    public static void main(String[] args) {
        // initialize the windowing system.
        windowCompositor comp = new windowCompositor();
        TextWindow test = new TextWindow(95, comp);
        TextWindow test2 = new TextWindow(95, comp);


        // initialize the Die Object.
        Die die = new Die();

        // initialize two players.
        player p1 = new player(test);
        player p2 = new player(test2);

        // print the rules.
        comp.clear();
        typeWritePrint("\nThe goal of this game is to accumulate a player score of exactly 30.\n");
        typeWritePrint("The first player to score exactly 30 is the Winner!\n");

        typeWritePrint("You will roll a pair of dice, then you must choose the score of one of the dice or the total of the two dice.\n");
        typeWritePrint("This value is added to your player score.\n");
        typeWritePrint("If your score goes over 30, your score is reset to zero.\n");
        typeWritePrint("Player turn changes after each roll of the dice.\n");
        typeWritePrint("You win when you accumulate a score of exactly 30.\n");

        // give the players some time to read the rules.
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // have players set up their characters.
        p1.playerPrint("Hello, what is the name of the first player?");
        p1.setupPlayer();

        //typeWritePrint("Hello, what is the name of the second player?\n");
        p2.playerPrint("Hello, what is the name of the second player?");
        p2.setupPlayer();

        // main game loop.
        for (int i = 0; i < 20; i++) {

            // give player 1 a turn.
            if(p1.takeTurn(die) == 1){
                p1.playerPrint("YOU WON!");
                comp.render();
                break;
            }

            // give player 2 a turn.
            if(p2.takeTurn(die) == 1){
                p2.playerPrint("YOU WON!");
                comp.render();
                break;
            }
        }
    }

    // type input string with delays between each letter.
    static void typeWritePrint(int millisecondToDelay, String string){
        Random r = new Random();
        int fastletters = 0;

        for (char letter : string.toCharArray()) {

            System.out.print(letter);
            try {
                // delay every n letters.
                if (fastletters == 0) {
                    fastletters = r.nextInt(3,10);
                    TimeUnit.MILLISECONDS.sleep(r.nextInt(70, 150));
                }
                // delay between each letter.
                TimeUnit.MILLISECONDS.sleep(millisecondToDelay + r.nextInt(0,11));
            } catch (Exception e) {
                System.out.println("well thats strange the application failed to delay");
                return;
            }
            if(letter == ' '){
                fastletters--;
            }
        }
    }
    // just sets a default delay time for typeWritePrint.
    static void typeWritePrint(String string){
        typeWritePrint(25, string);
    }
}


/**
 * TextWindow
 * create this class and pass in the width of the window and a compositor to use,
 * it will automatically add itself to the compositor and signal the compositor to render when requested.
 */
class TextWindow {
    private int width = 40;
    private int height = 20;
    private String title = "";
    private windowCompositor comp;
    public String[] buffer = new String[height];

    private int currentLine = 0;

    TextWindow(int width, windowCompositor compositor){
        this.width = width;
        this.comp = compositor;
        this.comp.addWindow(this);
    }

    private String padTextToLength(String textToPad, int padToLength){
        String pad = "";
        int padLength = padToLength - textToPad.length();
        for (int i = 0; i < padLength; i++) {
            pad += " ";
        }
        return textToPad + pad;
    }


    // take a string and split to windows length.
    private List<String> splitStringtoLength(String text,int length){
        List<String> lines = new ArrayList<String>();

        int lastindex = 0;
        // get the length of the string with non visible chars removed.
        int cachedStringLength = text.length();

        // lets just put an arbitrary limit here just in case.
        for (int i = 0; i < 500; i++) {
            if (lastindex + length < cachedStringLength) {
                lines.add(text.substring(lastindex, lastindex + length));
                lastindex += length;
            }
            else {
                lines.add(text.substring(lastindex,text.length()));
                break;
            }
        }
        return lines;
    }

    // add a line of text to this window.
    TextWindow println(String text){
        for (String line : splitStringtoLength(text, width)) {
            buffer[currentLine] = padTextToLength(line, width);
            currentLine++;
        }
        return this;
    }

    // add a line of text with color to this window,
    // the color string should be an ansi color code like "\u001B[31m" for red
    TextWindow printlnWithColor(String text, String ansiColorString){
        for (String line : splitStringtoLength(text, width)) {
            buffer[currentLine] = ansiColorString + padTextToLength(line, width) + "\u001B[0m";
            currentLine++;
        }
        return this;
    }

    // completely emptys the window.
    // the compositor will only display an empty frame if rendered after calling this.
    void clearWindow(){
        for (int i = 0; i < buffer.length; i++) {
                    buffer[i] = null;
        }
        currentLine = 0;
        for (int i = 0; i < buffer.length; i++) {
                    buffer[i] = null;
        }
        currentLine = 0;
        for (int i = 0; i < buffer.length; i++) {
                    buffer[i] = null;
            }
            currentLine = 0;
    }

    // call the compositors render function.
    void render(){
        comp.render();
    }

    // get window width without frame.
    int getWidth(){
        return width;
    }

    // get the window title.
    String getTitle(){
        return title;
    }

    // set the window title.
    void setTitle(String title){
        this.title = title;
    }
}

/**
 * windowCompositor
 * this is the central entity for handling TextWindows,
 * currently won't properly handle case where leftmost window is taller than the rightmost window.
 * simply call render on this class to redisplay the internal buffer.
 * render function also scrolls the terminal and resets the cursor to give the impression of a fresh screen,
 * but scrolling up will show the terminal history.
 */
class windowCompositor {
    private List<TextWindow> windows = new ArrayList<TextWindow>();
    private int screenSize = 0;
    private String[] scbuffer = new String[20];
    private int bufferIndex = 0;


    // this function is normally called by a TextWindow that wants to register with the this compositor.
    void addWindow(TextWindow window){
        windows.add(window);
    }

    // this function is used when we only care about visible characters.
    private int getVisibleCharCount(String text) {
        int chars = 0;
        boolean record = false;
        for (char letter : text.toCharArray()) {

            if (letter == '\u001B') {
               record = true;
            }
            if (record == true) {
                chars++;
            }
            if (record == true && letter == 'm')
            {
                record = false;
            }
        }
        return text.length() - chars;
    }

    // clear the console.
    public void clear(){
        final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
    }

    // make a string of length filled with fill string
    // the returned string will be of length (length * fill.length())
    private String StringFill(int length, String fill){
        String tmp = "";
        for (int i = 0; i < length; i++) {
            tmp += fill;
        }
        return tmp;
    }

    // updates the buffer with the contents of a new window and frame
    private void updateScreenWithWindow(TextWindow textWindow, int leftOffset){

        // this super messy block of code is to get the title centered in the window frame.
            String windowTitle = textWindow.getTitle();
            windowTitle = StringFill((textWindow.getWidth()/2) - (getVisibleCharCount(windowTitle)/2), "═") + windowTitle;
            scbuffer[bufferIndex] += "╔" + windowTitle + StringFill(textWindow.getWidth() - getVisibleCharCount(windowTitle), "═") + "╗";
            bufferIndex++;

            String pad = "";

            for (String line : textWindow.buffer) {
                if (line != null) {
                    pad = StringFill(leftOffset - getVisibleCharCount(scbuffer[bufferIndex]), " ");
                    scbuffer[bufferIndex] += pad + "║" + line + "║";
                    bufferIndex++;
                }
            }

            pad = StringFill(leftOffset - getVisibleCharCount(scbuffer[bufferIndex]), " ");

            scbuffer[bufferIndex] += pad + "╚" + StringFill(textWindow.getWidth(), "═") + "╝";
            //screenSize  = scbuffer[bufferIndex].length();

            bufferIndex = 0;
    }



    // update and display buffer contents.
    public void render(){
        // clear buffer.
        for (int i = 0; i < 20; i++) {
            scbuffer[i] = "";
        }
        // clear console.
        clear();

        // rerender text from windows.
        for (TextWindow textWindow : windows) {
            if (textWindow != null) {
                updateScreenWithWindow(textWindow, screenSize);
                screenSize += textWindow.getWidth()+2;
            }
        }
        screenSize = 0;
        
        // print of buffer.
        for (String line : scbuffer) {
            if (line != null) {
                System.out.println(line); 
            }
        }
    }
}


/**
 * die
 * handles little things like generating the correct range of random numbers,
 * as well as generating the unicode characters for dice.
 * also handy for testing because players take a Die object to use for their turn.
 */
class Die {
    private Random generator = new Random();
    private String lastRollString = "This is not a valid String if you are seeing this something is very wrong!";
    private int lastRollValue = 0;

    // make sure a Die object is always initialized.
    Die() {
        Roll();
    }

    // roll the Die.
    void Roll() {
        lastRollValue = generator.nextInt(1, 7);

        // generate proper character for rolled number.
        // I started with a switch statement but this was so much more compact. (plus it's branchless!).
        lastRollString = String.valueOf((char)((lastRollValue - 1) + '⚀'));
    }

    // only getters/setters here.
    int getLastRollValue() {
        return lastRollValue;
    }

    String getLastRollString() {
        return lastRollString;
    }
}

/**
 * player
 * holds personalized info for different players.
 */
class player {

    //Printer printer;
    private String playerColor = "\u001B[0m";
    private String name = "";
    private int score = 0;

    // the window that we will print out to.
    private TextWindow window;
    
    // initialize user input system.
    private Scanner input = new Scanner(System.in);

    player(TextWindow window){
        this.window = window;
    }

    // print information with this players color and window.
    void playerPrint(String string){
        window.printlnWithColor(string, playerColor);
    }

    // make a string of length filled with fill string
    // the returned string will be of length (length * fill.length())
    private String StringFill(int length, String fill){
        String tmp = "";
        for (int i = 0; i < length; i++) {
            tmp += fill;
        }
        return tmp;
    }

    // this function should be called when you want your player to setup their character.
    void setupPlayer(){
        window.render();
        while (true) {
            System.out.print("name: ");
            try {
                name = input.nextLine();
                break;
            } catch (Exception e) {
                // we don't have to do anything here.
            }
            
            System.out.println("please enter a valid input.");
        }
        window.clearWindow();

        // set the windows initial title.
        window.setTitle(name);

        int colorCode = -1;
        while (true) {
            window.println("please choose a color!");
            window.printlnWithColor("(1: Red)", "\u001B[31m");
            window.printlnWithColor("(2: Green)", "\u001B[32m");
            window.printlnWithColor("(3: Blue)", "\u001B[34m");
            window.printlnWithColor("(4: yellow)", "\u001B[33m");
            window.render();
            try {
                colorCode = input.nextInt();
                if (colorCode > 0 && colorCode < 5) {
                    break;
                }
            } catch(Exception e) {
                input.nextLine();
            }
            System.out.println("please enter a valid input.");
        }

        switch (colorCode) {

            // set player color to red.
            case 1:
                playerColor = "\u001B[31m";
                break;

            // set player color to green.
            case 2:
                playerColor = "\u001B[32m";
                break;

            // set player color to blue.
            case 3:
                playerColor = "\u001B[34m";
                break;
        }
        window.clearWindow();
        playerPrint("Color Set!");
        window.render();
    }

    // call when it's time for a players turn.
    // returns 1 if this player has won, otherwise returns 0.
    int takeTurn(Die die) {
        window.clearWindow();
        window.setTitle("/" + playerColor + name + "\u001B[0m\\");
        playerPrint("Player " + name + " it is your turn!");
        playerPrint("Your score: " + score);

        // roll the die a first time.
        die.Roll();
        int roll1 = die.getLastRollValue();

        // print user info.
        String roll = ("die 1: " + roll1 + " " + die.getLastRollString());

        // roll the die for a second time.
        die.Roll();
        int roll2 = die.getLastRollValue();

        // print user info.
        roll += ("   die 2: " + roll2 + " " + die.getLastRollString());
        int sidefill = (window.getWidth() - roll.length())/2;
        
        roll = StringFill(sidefill, " ") + roll + StringFill(sidefill, " ");

        playerPrint(roll);

        int option = -1;
        while (true) {
            playerPrint("Do you wish to (1) Keep die 1, (2) Keep die 2, (3) Keep the total? (Respond with 1 or 2 or 3)");
            window.render();
            try {
                option = input.nextInt();
                if (option > 0 && option < 4) {
                    break;
                }
            } catch(Exception e) {
                input.nextLine();
            }
            System.out.println("please enter a valid input.");
        }

        switch (option) {

            // keep die 1
            case 1:
                score += roll1;
                break;

            // keep die 2
            case 2:
                score += roll2;
                break;

            // keep both die
            case 3:
                score += (roll1 + roll2);
                break;
        }



        // reset score if greater than 30.
        if (score > 30) {
            score = 0;
        }
        // if score is equal to 30 then you won!
        if (score == 30) {
            return 1;
        }
        window.setTitle(name);
        window.clearWindow();
        window.render();
        return 0;
    }

    // only getters/setters down here.
    void setName(String name) {
        this.name = name;
    }

    String getName() {
        return this.name;
    }
}
