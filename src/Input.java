import java.util.Objects;
import java.util.Scanner;

public class Input {

    static Scanner scanner = new Scanner(System.in);

    // Behaviour:   Returns true if user input is yes. Returns false if user input is no. Returns null otherwise.
    public static Boolean getYesOrNo() {
        String input = scanner.nextLine();
        if (Objects.equals(input.toUpperCase(), "YES")) {
            return true;
        } else if(Objects.equals(input.toUpperCase(), "NO")) {
            return false;
        } else {
            return null;
        }
    }

    // Behaviour:   Returns Integer instance representation of user input. Returns null if user input is invalid.
    public static Integer getIntegerInput() {
        String input = scanner.nextLine();
        try{
            return Integer.parseInt(input);
        } catch (Exception e) {
            return null;
        }
    }

    // Behaviour:   Returns Character instance representation of user input. Returns null if user input is invalid.
    public static Character getCharacterInput() {
        String input = scanner.nextLine();
        return (input.length() == 1)? input.charAt(0) : null;
    }

    public static int LoopUntilInputIsInteger(String msgPrompt) {
        Integer integerInput = null;
        while(integerInput == null) {
            System.out.print(msgPrompt);
            integerInput = Input.getIntegerInput();
        }
        return integerInput;
    }

    public static char LoopUntilInputIsCharacter(String msgPrompt) {
        Character characterInput = null;
        while(characterInput == null) {
            System.out.print(msgPrompt);
            characterInput = Input.getCharacterInput();
        }
        return characterInput;
    }

    public static boolean LoopUntilYesOrNo(String msgPrompt) {
        Boolean answer = null;
        while(answer == null) {
            System.out.print(msgPrompt);
            answer = Input.getYesOrNo();
        }
        return answer;
    }
}
