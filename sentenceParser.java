import java.util.*;
import java.lang.*;
import java.io.*;
/**
 *
 * it parses hindi sentence and sets the properties of the sentence
 * it also parse translated english sentence and enforce the rules on that sentence
 */
class Parser
{
    String specificSyntaxFilePath="/media/anurag/New Volume/translator/project/Syntax";
    String propertyFilePath="/media/anurag/New Volume/translator/project/property";
    String valueFilePath="/media/anurag/New Volume/translator/project/value";
    String getFilePath="/media/anurag/New Volume/translator/project/get";
    HashMap<String,Integer> propMap=new HashMap<String,Integer>();
    HashMap<String,Integer> valueMap=new HashMap<String,Integer>();
    ArrayList bugyArray=new ArrayList();
    HashMap<String,String> map;
    int maxLineLimit=200;
    int len=0;
    String lineArray[]=new String[maxLineLimit];
    String property[]=new String[100];
    String value[]=new String[100];
    /**
     * It reads the property and values from provided files and provides
     * the index to indivisual property using hashmap function
     */
    Parser()
    {
        FileRead fp=new FileRead(propertyFilePath);
        int index=1;
        while (fp.hasNextLine())
        {
            String temp=fp.readNextLine();
            propMap.put(temp,index++);
        }
        fp.fileClose();
        fp=new FileRead(valueFilePath);
        index=1;
        while (fp.hasNextLine())
        {
            String temp=fp.readNextLine();
            value[index]=temp;
            valueMap.put(temp,index++);
        }
        fp.fileClose();
    }
    void fileOpen(String file)
    {
        FileRead fp = new FileRead(file);
        len=0;
        while(fp.hasNextLine())
            lineArray[len++]=fp.readNextLine();
        fp.fileClose();
    }
    void pass1(String bugySentence)
    {
        fileOpen(getFilePath);
        String word[]=bugySentence.split(" ");
        for (int i=0;i<word.length ;i++ )
        {
            bugyArray.add(word[i]);
        }
        decoding();
    }
}