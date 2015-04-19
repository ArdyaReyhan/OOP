package BridgeJava;
import java.util.*;
public class BridgeJava {
    public static void main (String[] args) {
        Scanner keyboard = new Scanner(System.in);
        Deck Pack = new Deck();
        Board Field = new Board();
        Player1 P1 = new Player1();
        Player2 CPU2 = new Player2();
        Player3 CPU3 = new Player3();
        Player4 CPU4 = new Player4();
        P1.Cards = CardHandle(P1.Cards);
        CPU2.Cards = CardHandle(CPU2.Cards);
        CPU3.Cards = CardHandle(CPU3.Cards);
        CPU4.Cards = CardHandle(CPU4.Cards);
        System.out.println ("Enter any key to start the game!");
        String input = keyboard.nextLine();
        Shuffle(Pack.Cards);
        System.out.println("Draw Phase");
        for (int i = 1; i <= 4; i++) {
            P1.Cards[i] = getCard(Pack.Current, Pack.Cards);
            P1.Current++;
            Pack.Current--;
        }
        for (int i = 1; i <= 4; i++) {
            CPU2.Cards[i] = getCard(Pack.Current, Pack.Cards);
            CPU2.Current++;
            Pack.Current--;
        }
        for (int i = 1; i <= 4; i++) {
            CPU3.Cards[i] = getCard(Pack.Current, Pack.Cards);
            CPU3.Current++;
            Pack.Current--;
        }
        for (int i = 1; i <= 4; i++) {
            CPU4.Cards[i] = getCard(Pack.Current, Pack.Cards);
            CPU4.Current++;
            Pack.Current--;
        }
        Field.Current = getCard(Pack.Current, Pack.Cards);
        int FP = (int)(Math.random() * 4 + 1);
        for (;;FP++) {
            ShowStatus(Pack.Current, FP, Field.Current);
            if (FP == 1) Field.Current = PlayTurn(P1.Cards, FP, P1.Current, Pack.Current, Field.Current, Pack.Cards, keyboard);
            if (FP == 2) Field.Current = PlayTurn(CPU2.Cards, FP, CPU2.Current, Pack.Current, Field.Current, Pack.Cards, keyboard);
            if (FP == 3) Field.Current = PlayTurn(CPU3.Cards, FP, CPU3.Current, Pack.Current, Field.Current, Pack.Cards, keyboard);
            if (FP == 4) {
                Field.Current = PlayTurn(CPU4.Cards, FP, CPU4.Current, Pack.Current, Field.Current, Pack.Cards, keyboard);
                FP = 0;
            }
            if (P1.Current == 0) {
                System.out.println("Player 1 Menang");
                break;
            }
            if (CPU2.Current == 0) {
                System.out.println("Player 2 Menang");
                break;
            }
            if (CPU3.Current == 0) {
                System.out.println("Player 3 Menang");
                break;
            }
            if (CPU4.Current == 0) {
                System.out.println("Player 4 Menang");
                break;
            }
        }
    }
    
    private static int[] CardHandle(int[] Card){
        for (int i = 0; i < 52; i++) {
            Card[i] = 53;
        }
        return Card;
    }
    private static void Shuffle(int[] Cards) {
        for (int i = 0; i < 52; i++) {
            Cards[i] = (int)(Math.random() * 52 + 0);
            System.out.println(i);
            for (int j = 0; j < i; j++) {
                if (Cards[j] == Cards[i]) i--;
            }
        }
    }
    
    private static int CardID(int Card) {
        if (Card > 12 && Card < 26) Card = Card - 12;
        if (Card > 25 && Card < 39) Card = Card - 25;
        if (Card > 38 && Card < 52) Card = Card - 38;
        return Card;
    }
    
    private static void Show(int Card) {
        if (Card < 13) System.out.print ((Card + 1) + "D");
        if (Card > 12 && Card < 26) System.out.print ((Card - 12) + "H");
        if (Card > 25 && Card < 39) System.out.print ((Card - 25) + "S");
        if (Card > 38 && Card < 52) System.out.print ((Card - 38) + "C");
    }
    
    private static int getCard (int Current, int[] Cards) {
        int Card;
        if (Current > 0) {
            Card = Cards[Current-1];
            Current--; //Might be an error
            return Card;
        }
        else return 0;
    }
    
    private static void ShowStatus(int Current, int FP, int Field) {
        System.out.print ("\n\nOn Board : ");
        Show(Field);
        System.out.println ("\nCards left : " + Current);
        System.out.println ("Now Playing : Player " + FP);
        
    }
    
    private static int PlayTurn(int[] Hand, int FP, int Current, int CDeck, int Field, int[] Cards, Scanner keyboard) {
        int tempField = CardID(Field);
        int tempHand, Choice;
        boolean Apakah;
        String Input;
        if (FP == 1) {
            Showhand(Hand, Current);
            int temp = 0;
            System.out.print ("\nChoose a card to beat : ");
            for (int j = 0; j < CDeck; j++) {
                for (int i = 0; i < Current; i++) {
                    tempHand = CardID(Hand[i]);
                    if (tempHand > tempField ) temp++;
                }
                if (temp == 0) {
                    System.out.println ("You have to draw! Enter any key to draw");
                    Input = keyboard.nextLine();
                    Hand[Current + 1] = getCard(CDeck, Cards);
                    System.out.println ("You get ");
                    Show(Hand[Current + 1]);
                    tempHand = CardID(Hand[Current + 1]);
                    Apakah = CheckMatch(tempHand, tempField);
                    if (tempHand > tempField && Apakah) break;
                    else Current++;
                }
            }
            Showhand(Hand, Current);
            for (;;) {
                System.out.print ("\nChoose a card to beat : ");
                Choice = keyboard.nextInt();
                Choice = CardID(Hand[Choice]);
                Apakah = CheckMatch(Hand[Choice], tempField);
                if (Choice > tempField && Apakah) tempField = Choice;
                else continue;
                break;
            }
        }
        else {
            int temp = 0;
            for (int j = 0; j < CDeck; j++) {
                for (int i = 0; i < Current; i++) {
                    tempHand = CardID(Hand[i]);
                    if (tempHand > tempField ) temp++;
                }
                if (temp == 0) {
                    Hand[Current + 1] = getCard(CDeck, Cards);
                    tempHand = CardID(Hand[Current + 1]);
                    if (tempHand > tempField) break;
                    else Current++;
                }
            }
            for (int i = 0; i < Current; i++) {
                Choice = (int)(Math.random() * Current + 0);
                Apakah = CheckMatch(Hand[Choice], tempField);
                if (Hand[Choice] > tempField && Apakah) tempField = Hand[Choice];
                else continue;
                break;
            }
        }
        return tempField;
    }
    
    private static void Showhand(int[] Hand, int Current) {
        System.out.println ("\nIn-Hand Card(s) : ");
            for (int i = 0; i < Current; i++) {
                System.out.print ("(" + (i + 1) + ")-");
                Show(Hand[i]);
            }
    }
    
    private static boolean CheckMatch(int Card1, int Card2) {
        boolean Apakah;
        if (Card1 < 13 && Card2 < 13) Apakah = true;
        if (Card1 > 12 && Card1 < 26 && Card2 > 12 && Card2 < 26) Apakah = true;
        if (Card1 > 25 && Card1 < 39 && Card2 > 25 && Card2 < 39) Apakah = true;
        if (Card1 > 38 && Card1 < 52 && Card2 > 38 && Card2 < 52) Apakah = true;
        else Apakah = false;
        return Apakah;
    }
}
