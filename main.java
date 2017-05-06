import java.util.*;
import java.lang.*;
import java.io.*;
class Main extends Thread
{
    // String sentence;
    // Main(String sentence)
    // {
    //     this.sentence=sentence;
    //     this.run();
    // }
    static String databaseSource="Hindi";
    static String databaseTranslate="EnglishHindi";
    static String databaseTarget="English";
    static String ignoreFilePath="/media/anurag/New Volume/translator/project/Ignore";
    static int maxWordLimit=100;
    static String ignoreWordList[]=new String[maxWordLimit];
    /**
     * This function controls the execution order
     */
    public static void main(String[] args)
    {
        System.out.println("Enter : your sentence");
//         String sentence="mai";
//        String sentence = "";
         String sentence="mai bhut tej daud ta hu";
//          String sentence="kalma padayi karti hai";
//         String sentence="kalma bhut natkhat hai";
        //String sentence="mumbai bhut sundar mahanagar hai";
//  String sentence="kalma bhut tej daud hai";
//           String sentence="kalma hotel ke ander hai";
//          String sentence="mahanagar bhut acchae hai";
        Parser pass=new Parser();
        pass.pass1(sentence);
        FileRead fp = new FileRead(ignoreFilePath);
        int len=0;
        HashSet<String> set=new HashSet<String>();
        while(fp.hasNextLine())
        {
            ignoreWordList[len++]=fp.readNextLine();
            set.add(ignoreWordList[len-1]);
        }
        fp.fileClose();
        String inputWords[]=sentence.split(" ");
        Word word[]=new Word[inputWords.length];
        HashMap<String,String> map=new HashMap<String,String>();
        String rule=new String();
        for (int i=0;i<inputWords.length ;i++ )
        {
            if(set.contains(inputWords[i]))
                continue;
            word[i]=new Word(inputWords[i],databaseSource,databaseTranslate,databaseTarget);
            map.put(word[i].getTranslationPartOfSpeech(),word[i].getTranslation());
            rule=rule+" "+word[i].getPartOfSpeech();
        }
        rule=rule.trim();
        String ruleFilePath="/media/anurag/New Volume/translator/project/Hindi";
        Rules ruleObj = new Rules();
        ruleObj.getRules(ruleFilePath);
        ruleObj.ruleSelector(rule);
        String translatedSentence[]=ruleObj.ruleTransformer(map);
        for (int i=0;i<ruleObj.getLength() ;i++)
        {
            String s=pass.pass2(translatedSentence[i],map);
             System.out.println("orignal "+translatedSentence[i]);
            System.out.println("transformed "+s);
        }
        int choice=0;
        if(choice==1)
        {
            System.out.println("Please give us a correct translation");
            String newTranslatedSentence="i run very fast";
            inputWords=newTranslatedSentence.split(" ");
            Word translatedWords[]=new Word[inputWords.length];
            String translatedRule=new String();
            for (int i=0;i<inputWords.length ;i++ )
            {
                translatedWords[i]=new Word(inputWords[i],databaseTarget,databaseTranslate,databaseSource);
                translatedRule=translatedRule+" "+translatedWords[i].getPartOfSpeech();
            }
            ruleObj.updateRules(rule,translatedRule);
        }
        System.out.println("Thanking you for using our Translator");
    }
}