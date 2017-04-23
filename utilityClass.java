import java.util.*;
import java.lang.*;
import java.io.*;
class UtilityClass
{
    static int infiniteInt=2147483647;
    static int min(int a,int b)
    {
        return (a<b) ? a : b;
    }
    static int min(int a,int b,int c)
    {
        return min(a,min(b,c));
    }
    static int findMin(int length,int arr[])
    {
        int min=infiniteInt;
        for (int i=0;i < length ;i++ )
        {
            min=min(min,arr[i]);
        }
        return min;
    }
    /**
     * Implementation not complete.Passing has to be done.
     * @param  sentence  String from which you want to extract words
     * @param  delimeter char on which you want to break sentence
     * @return
     */
    static String[] breakTheSentence(String sentence,char delimeter)
    {
       char temp[] = new char[40];
       String words[] = new String[100];
       int numberOfWords=0;
       int k=0,i;
       for (i=0;i<sentence.length() ;i++ )
       {
           if(sentence.charAt(i)==delimeter)
           {
               words[numberOfWords++] = new String(temp,0,k);
               k=0;
           }
           else
           {
               temp[k++]=sentence.charAt(i);
           }
       }
       words[numberOfWords++]=new String(temp,0,k);
       String wordLen[]=new String[numberOfWords];
       for (i=0;i<numberOfWords ;i++ )
       {
            wordLen[i]=words[i];
       }
       return wordLen;
    }
}