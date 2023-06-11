/*
15/12/2022
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Please Enter File Name> ");
            String fileName = input.nextLine();
            System.out.println("Waiting for dictionary initialization...");
            Dictionary d = new Dictionary(new File(""+fileName));

            String[] selectChoice = {"Add", "Delete", "Find Similar", "Find","exit()" };
            System.out.println("Please Select Operation:");
            for (int i = 0; i < selectChoice.length; i++) {
                System.out.println((i + 1) + ". " + selectChoice[i]);
            }
            String choice = input.nextLine();
            while (!choice.equals("exit()")) {
                if (choice.toLowerCase().equals("add") || choice.equals("1")) {
                    try {
                        System.out.print("Please Enter Added Word> ");
                        choice = input.nextLine();
                        d.addWord(choice);
                        System.out.print("Save updated dictionary? [Y/N]> ");
                        String choice2 = input.nextLine();
                        if (choice2.toLowerCase().equals("y")){
                            System.out.print("Enter new dictionary file name> ");
                            choice = input.nextLine();
                            FileWriter myWriter = new FileWriter(
                                    "/Users/talalharbi/IdeaProjects/ProjectFinal/src/"+choice
                            );
                            d.dict.iterativePreorder(myWriter);

                        }
                    } catch (WordAlreadyExistsException e) {
                        System.out.println(choice + " Already exists in the dictionary");
                        continue;
                    }catch (IOException e){
                        System.out.println("Writer Error");
                    }
                } else if (choice.toLowerCase().equals("delete") | choice.equals("2")) {
                    try {
                        System.out.print("Please Enter A Word To Be Deleted> ");
                        choice = input.nextLine();
                        d.deleteWord(choice);
                    } catch (WordNotFoundException e) {
                        System.out.println("Word Not Found. Can not be deleted");
                        continue;
                    }
                } else if (choice.toLowerCase().equals("find similar") || choice.equals("3")) {
                    System.out.print("What word to found similar?> ");
                    choice = input.nextLine();
                    System.out.print("Similar words are>> ");
                    for (int i = 0; i < d.findSimilar(choice).length; i++) {
                        System.out.print(d.findSimilar(choice)[i] + " ");
                    }
                    System.out.println();
                } else if (choice.toLowerCase().equals("find") || choice.equals("4")) {
                    System.out.print("Enter a word to check if it exists in the dictionary>> ");
                    choice = input.nextLine();
                    if (d.findWord(choice)) {
                        System.out.println("word does exist");
                    } else {
                        System.out.println("Does not exits");
                    }
                }
                for (int i = 0; i < selectChoice.length; i++) {
                    System.out.println((i + 1) + ". " + selectChoice[i]);
                }
                choice = input.nextLine();

            }
        }catch (WordAlreadyExistsException e){
            System.out.println("error");}
}
}
