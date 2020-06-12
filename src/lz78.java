import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class lz78 {

    ArrayList<String> dictionary=new ArrayList<>();
    ArrayList<Tag>tags=new ArrayList<>();
    lz78(){
        dictionary.add("null");
    }
    void add(Tag t){tags.add(t);}
    boolean check(String substr){
            for(int i=0;i<dictionary.size();i++){
                if(substr.equals(dictionary.get(i))){
                    return true;
                }
            }


            return false;
        }

        int  get_index(String str){
        for(int i=0;i< dictionary.size();i++){
            if(dictionary.get(i).equals(str)){
                return i;
            }
        }
    return 0;
    }
  void  print (){
      for(int i=0;i<dictionary.size();i++){
          System.out.println(dictionary.get(i));
      }
      for(int i=0;i<tags.size();i++){
          System.out.println("< "+tags.get(i).indix_dictionary+","+tags.get(i).next_symbol+" >");
      }
    }


    public void compress(String text) throws FileNotFoundException {
        for (int i = 0; i < text.length(); i++) {
            if(i+1==text.length()){
                Tag t=new Tag(get_index(text.substring(i, i + 1)),'n');
                tags.add(t);
                break;

            }
            boolean b = check(text.substring(i, i + 1));
            if (b == false) {
                Tag n = new Tag(0, text.charAt(i));
                add(n);
                dictionary.add(text.substring(i, i + 1));
            } else {
                for (int j = i + 1; j < text.length(); j++) {
                    b = check(text.substring(i, j + 1));
                    if (b == false) {
                        Tag n = new Tag(get_index(text.substring(i, j)), text.charAt(j));
                        add(n);
                        dictionary.add(text.substring(i, j + 1));
                        i=i+(j-i);
                        break;
                    }
                }

            }

        }
        PrintWriter write = new PrintWriter("tags.txt");
        for(int i=0;i<tags.size();i++){
           write.println("< "+tags.get(i).indix_dictionary+","+tags.get(i).next_symbol+" >");
        }
        write.close();
    }
        public void clear(){dictionary.clear();}
    public String decompress(){
            String text="";
            dictionary.add("null");
            for(int i=0;i<tags.size()-1;i++) {
                if (tags.get(i).indix_dictionary == 0) {
                    dictionary.add(String.valueOf(tags.get(i).next_symbol));
                    text += tags.get(i).next_symbol;
                } else {
                    String subtext="";
                            subtext+= dictionary.get(tags.get(i).indix_dictionary);
                    subtext+=tags.get(i).next_symbol;
                    dictionary.add(subtext);
                    text+=subtext;
                }
            }
            text+=dictionary.get(tags.get(tags.size()-1).indix_dictionary);
            System.out.println( text);
        return text;
    }
    public static void main(String[] args) throws FileNotFoundException {
                        lz78 obj=new lz78();
        File f=new File("reading.txt");
         Scanner in = new Scanner(f);
        String data="";
        while(in.hasNextLine())
        {
           data+=in.nextLine();
       }
       System.out.println(data);

                        obj.compress(data);
                        obj.print();
                        obj.clear();
                     String d= obj.decompress();

}
}
