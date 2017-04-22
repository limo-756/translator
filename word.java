import java.util.*;
import java.lang.*;
import java.io.*;
import java.sql.*;
class Word
{
    String word;
    String partOfSpeech;
    String meaning;
    String pronunciation;
    String translation;
    String translationPartOfSpeech;
    String query="Select partOfSpeech,meaning,pronunciation from Word where name=\"";
    String queryTranslate="Select word2 from Translate where word1=\"";
    String queryTranslationPartOfSpeech="Select partOfSpeech from Word where name=\"";
    String queryEnder="\" limit 1 ;";
    /**
     * This is a constructor to initialise the attributes of word like partOfSpeech,meaning,
     * pronunciation,translation(word corresponding to the word in another language both
     * have same meaning and usage in target language)
     * @param word                the word which we want to translate
     * @param database            the language in which word falls
     * @param translationDatabase the source language and target language database
     * @param targetDatabase      the target language database
     */
    Word(String word,String database,String translationDatabase,String targetDatabase)
    {
        this.word=word;
        try
        {
            Database d = new Database(database);
            ResultSet rs = d.executeQuery(query+word+queryEnder);
            if(!rs.isBeforeFirst())
            {
               DatabaseException rowEmpty=new DatabaseException(word);
               throw rowEmpty;
            }
            while(rs.next())
            {
                partOfSpeech = rs.getString("partOfSpeech");
                meaning = rs.getString("meaning");
                pronunciation = rs.getString("pronunciation");
            }
            d.closeDatabase();
            d = new Database(translationDatabase);
            rs=d.executeQuery(queryTranslate+word+queryEnder);
            if(!rs.isBeforeFirst())
            {
               DatabaseException rowEmpty=new DatabaseException(word);
               throw rowEmpty;
            }
            while(rs.next())
                translation = rs.getString("word2");
            d.closeDatabase();
            d = new Database(targetDatabase);
            rs=d.executeQuery(queryTranslationPartOfSpeech+translation+queryEnder);
           if(!rs.isBeforeFirst())
           {
               DatabaseException rowEmpty=new DatabaseException(translation);
               throw rowEmpty;
           }
            while(rs.next())
                translationPartOfSpeech = rs.getString("partOfSpeech");
            d.closeDatabase();
        }
       catch(DatabaseException e)
       {
           System.out.println(e.error);
       }
        catch(SQLException se)
        {
         //Handle errors for JDBC
         se.printStackTrace();
        }
    }
    String getTranslationPartOfSpeech()
    {
        return  translationPartOfSpeech;
    }
    String getTranslation()
    {
        return translation;
    }
    String getPartOfSpeech()
    {
        return partOfSpeech;
    }
}