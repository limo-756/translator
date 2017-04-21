import java.util.*;
import java.lang.*;
import java.io.*;
/**
 * It transform the hindi sentence to respective english sentece
 * based on the similarity between the rules specified in file
 * and part of speech string of source sentence
 */
class Rules
{
    String ruleFilePath;
    String lineEnding="\n";
    String sourceRules[];
    String targetRules[];
    String matchingSourceRules[];
    String matchingTargetRules[];
    String mappingFilePath="/media/anurag/New Volume/translator/project/Mapping";
    final int maxRules=100;
    int length=0;
    int matchLength=0;
    int getLength()
    {
        return matchLength;
    }
    void getRules(String ruleFilePath)
    {
        this.ruleFilePath=ruleFilePath;
        FileRead fp=new FileRead(ruleFilePath);
        sourceRules=new String[maxRules];
        targetRules=new String[maxRules];
        length=0;
        while (fp.hasNextLine())
        {
            sourceRules[length]=fp.readNextLine();
            targetRules[length]=fp.readNextLine();
            length++;
        }
        fp.fileClose();
    }
    /**
     * it selects the rule
     * @param rule contaion part of speech of words in source sentence
     */
    void ruleSelector(String rule)
    {
        int arr[]= new int[maxRules];
        HashMap<String,Integer> mapPartOfSpeech=new HashMap<String,Integer>();
        FileRead fp=new FileRead(mappingFilePath);
        int index=0;
        while (fp.hasNextLine())
        {
            String partOfSpeech=fp.readNextLine();
            mapPartOfSpeech.put(partOfSpeech,index++);
        }
        fp.fileClose();
        String ruleWords[]=rule.split(" ");
        int ruleArray[]=new int[ruleWords.length];
        int ruleLen=0;
        for (ruleLen=0;ruleLen<ruleWords.length ;ruleLen++ )
        {
            if(mapPartOfSpeech.containsKey(ruleWords[ruleLen]))
                ruleArray[ruleLen]=mapPartOfSpeech.get(ruleWords[ruleLen]);
        }
        for (int i=0;i < length ;i++ )
        {
            ruleWords=sourceRules[i].split(" ");
            int ruleSourceArray[]=new int[ruleWords.length];
            int sourceRuleLen=0;
            for (sourceRuleLen=0;sourceRuleLen<ruleWords.length ;sourceRuleLen++ )
                ruleSourceArray[sourceRuleLen]=mapPartOfSpeech.get(ruleWords[sourceRuleLen]);
            arr[i]=algo.editDistance(ruleArray,ruleSourceArray);
        }
        int min=UtilityClass.findMin(length,arr);
        matchingSourceRules = new String[maxRules];
        matchingTargetRules = new String[maxRules];
        matchLength=0;
        for (int i=0;i < length ; i++ )
        {
            if (min==arr[i])
            {
                matchingTargetRules[matchLength]=targetRules[i];
                matchingSourceRules[matchLength++]=sourceRules[i];
            }
        }
    }
}