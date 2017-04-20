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
}