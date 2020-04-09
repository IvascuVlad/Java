import java.util.List;

public interface Player{
    /*verifica daca s-au epuizat tokenii*/
    boolean checkOver();
    /*calculeaza lungimea progresiei aritmetice obtinute de jucator*/
    int progressionSize();
    String getName();
    List<Token> getTokens();
    int getId();
}
