import src.DatabaseOperation;
import src.Movi;

public class Main {
    public static void main(String[]args){
        Movi m=new Movi("race 3","action",120,4.5f);
        m.insertMovi();
        m.showMovies();

    }

}
