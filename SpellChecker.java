
//import java.lang.reflect.Array;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class SpellChecker {
    private final HashMap<Character,ArrayList<String>> dict = new HashMap<>();

    public SpellChecker() {
        try

        {
            File myFile = new File("enable1.txt");
            Scanner myReader = new Scanner(myFile);
            ArrayList<String> dictString = new ArrayList<String>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (!dict.containsKey(data.toCharArray()[0])){
                    dict.put(data.toCharArray()[0], new ArrayList<String>());
                }
                dict.get(data.toCharArray()[0]).add(data);
            }

            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();} }

    public void printDict(char letter) {

        System.out.println(dict.get(letter));

    }
    public boolean Search(String word){
        ArrayList list = dict.get(word.toCharArray()[0]);
        if (!list.contains(word)){
            return false;
            }
        return true;
        }









    }








