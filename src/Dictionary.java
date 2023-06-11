/*
15/12/2022
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Dictionary {
    AVLTree<String> dict = new AVLTree<>();

    public Dictionary(){

    }
    public Dictionary(String s) throws WordAlreadyExistsException {
        try {
            dict.insert(s);
        }
        catch (IllegalArgumentException e){
            throw new WordAlreadyExistsException();
        }
    }
    public Dictionary(File f) throws WordAlreadyExistsException {
        try {
            File myObj = f;
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                dict.insertInitial(data);
                dict.balance();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            System.exit(0);
        }catch (IllegalArgumentException e){
            throw new WordAlreadyExistsException();
        }

    }
    public void addWord(String s) throws WordAlreadyExistsException {
        try {


            dict.insert(s);
        } catch (IllegalArgumentException ex) {
            throw new WordAlreadyExistsException();
        }
    }

    boolean findWord(String s){
        boolean found = dict.search(s);
        return found;
    }


    public void deleteWord(String s) throws WordNotFoundException{
        try{
            dict.deleteByCopying(s);
        }catch(NoSuchElementException ex){
            throw new WordNotFoundException();
        }catch(UnsupportedOperationException e){
            throw new WordNotFoundException();
        }
    }




    public String[] findSimilar (String s) {
        return dict.iterativePreorder(s);
<<<<<<< HEAD
=======
}
>>>>>>> 35aa03436fdf1e1ca26f8fac0dfabbd577ac69d7
}
}