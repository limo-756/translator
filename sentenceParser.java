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
    String pass2(String bugySentence,HashMap<String,String> map)
    {
        fileOpen(specificSyntaxFilePath);
        bugyArray.clear();
        String word[]=bugySentence.split(" ");
        this.map=map;
        for (int i=0;i<word.length ;i++ )
        {
            bugyArray.add(word[i]);
        }
        decoding();
        String result=null;
        for (int i=0;i<bugyArray.size();i++)
        {
            if(i==0)
                result=""+bugyArray.get(i);
            else
                result=result+" "+bugyArray.get(i);
        }
        return result;
    }
    boolean commentLine(String word)
    {
        return word.equals("//");
    }
    boolean ifCondition(String word)
    {
        return word.equals("if");
    }
    boolean elseFinder(String word)
    {
        return word.equals("else");
    }
    boolean end(String word)
    {
        return word.equals("end");
    }
    /**
     * it checks the whole condition
     * @param  line it contains the condition to be evaluated
     * @return      it returns boolean values
     */
    boolean evaluateCondition(String line)
    {
        boolean status=false;
        String word[]=line.split(" ");
        if(condition(word[1]))
            status=true;
        for (int i=2;i<word.length ;i=i+2 )
        {
            if(word[i].equals("and"))
                status=status && condition(word[i+1]);
            else
                status=status || condition(word[i+1]);
        }
        return status;
    }
    /**
     * it evalute the single condition
     * @param  operands condition
     * @return      boolean values
     */
    boolean condition(String operands)
    {
        boolean returnValue=false;
        boolean negation=false;
        if(operands.indexOf("!")!=-1)
        {
            negation=true;
            int start=operands.indexOf("!")+1;
            operands=operands.substring(start);
        }
        if(operands.indexOf("'")==-1)
        {
            if(operands.indexOf("=")!=-1)
            {
                String word[]=UtilityClass.breakTheSentence(operands,'=');
                returnValue=(property[propMap.get(word[0])]==value[valueMap.get(word[1])]) ? true : false;
            }
            else
            {
                if(map.containsKey(operands))
                    returnValue=true;
            }
        }
        else
        {
            operands=getLiteral(operands);
            String word[]=UtilityClass.breakTheSentence(operands,'-');
            boolean temp=true;
            for (int i=0;i<word.length ;i++ )
            {
                if(!bugyArray.contains(word[i]))
                {
                    temp = false;
                    break;
                }
            }
            if(temp==true)
                returnValue = true;
            else
                returnValue = false;
        }
        if(negation==true)
            return !(returnValue);
        else
            return returnValue;
    }
    String getLiteral(String str)
    {
        int start=str.indexOf("'")+1;
        int end=str.lastIndexOf("'");
        return str.substring(start,end);
    }
    boolean checkLiteral(String word)
    {
        if(word.indexOf("'")!=-1)
            return true;
        else
            return false;
    }
    /**
     * it executes the statement
     * @param line statement
     * '.' add word after
     * '=' set property
     * '$' append word
     */
    void execute(String line)
    {
        if(line.indexOf("=")!=-1)
        {
            String word[]=UtilityClass.breakTheSentence(line,'=');
            property[propMap.get(word[0])]=value[valueMap.get(word[1])];
        }
        else if(line.indexOf(".")!=-1)
        {
            String word[]=UtilityClass.breakTheSentence(line,'.');
            if(checkLiteral(word[1]))
                word[1]=getLiteral(word[1]);
            if(word[0].indexOf("'")==-1)
            {
                if(bugyArray.contains(map.get(word[0])))
                {
                    int temp=bugyArray.indexOf(map.get(word[0]));
                    bugyArray.add(temp+1,word[1]);
                }
            }
            else
            {
                word[0]=getLiteral(word[0]);
                if(bugyArray.contains(word[0]))
                {
                    int temp=bugyArray.indexOf(word[0]);
                    bugyArray.add(temp+1,word[1]);
                }
            }
        }
        else if(line.indexOf("$")!=-1)
        {
            String word[]=UtilityClass.breakTheSentence(line,'$');
            if(checkLiteral(word[1]))
                word[1]=getLiteral(word[1]);
            if(word[0].indexOf("'")==-1)
            {
                if(bugyArray.contains(map.get(word[0])))
                {
                    int temp=bugyArray.indexOf(map.get(word[0]));
                    bugyArray.set(temp,map.get(word[0])+word[1]);
                }
            }
            else
            {
                word[0]=getLiteral(word[0]);
                if(bugyArray.contains(word[0]))
                {
                    int temp=bugyArray.indexOf(word[0]);
                    bugyArray.set(temp,word[0]+word[1]);
                }
            }
        }
    }
    /**
     * it decodes every line and call other function to
     * evaluate and execute line
     */
    void decoding()
    {
        boolean normalBlock=true;
        boolean ifBlock=false;
        boolean elseBlock=false;
        boolean condition=false;
        for (int i=0;i<len ;i++ )
        {
            String words[]=lineArray[i].split(" ");
            if(!commentLine(words[0]))
            {
                if(ifCondition(words[0]))
                {
                    normalBlock=false;
                    ifBlock=true;
                    elseBlock=false;
                    condition=evaluateCondition(lineArray[i]);
                }
                else if(elseFinder(words[0]))
                {
                    normalBlock=false;
                    ifBlock=false;
                    elseBlock=true;
                }
                else if(end(words[0]))
                {
                    normalBlock=true;
                    ifBlock=false;
                    elseBlock=false;
                    condition=false;
                }
                else if(normalBlock || (ifBlock && condition) || (elseBlock && !(condition)))
                    execute(lineArray[i]);
            }
        }
    }

}