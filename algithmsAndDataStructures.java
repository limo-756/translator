import java.util.*;
import java.lang.*;
import java.io.*;
class algo
{
    static int insertionCost=1;
    static int deletionCost=1;
    static int matchCost=-2;
    /**
     * This function is used for computing the similarity between source rules part of speech
     *  and given string part of speech.
     * @param  A [This is first string]
     * @param  B [This is secound string]
     * @return   [returns the minimal amount of insertion and deletion one has to convert one string
     *            to another]
     */
    static int editDistance(int A[],int B[])
    {
        int m=A.length;
        int n=B.length;
        int C[][] = new int[m+1][n+1];
        for (int i=0;i<n+1 ;i++ )
            C[0][i]=0;
        for (int i=0;i<m+1 ;i++ )
            C[i][0]=0;
        for (int i=1;i<m+1 ;i++ )
        {
            int match,deletion,insertion;
            for (int j=1;j<n+1;j++)
            {
                if (A[i-1]==B[j-1])
                    match=matchCost+C[i-1][j-1];
                else
                    match=C[i-1][j-1];
                deletion=C[i][j-1]+deletionCost;
                insertion=C[i-1][j]+insertionCost;
                C[i][j]=UtilityClass.min(match,insertion,deletion);
            }
        }
        return C[m][n];
    }
}