class DatabaseException extends Exception
{
    /**
     * Word do not exist in database exception
     * @param word word which is not in database
     */
    String error;
    DatabaseException(String word)
    {
        error="Error : "+word+" do not exist Please make a entry for this to make translator better";
    }
}