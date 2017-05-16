import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ubuntu-master on 16.05.17.
 */
public class Task3 {
    Set<String> words = new HashSet<>();

    public static void main(String[] args) {
        System.out.println(minDistance("polynomial", "exponential"));
        System.out.println(editDist("polynomial", "exponential", "polynomial".length(), "exponential".length()));
        Task3 t3 = new Task3();
        t3.setDataFromTableRow("words", "words", -1);
        t3.setDataFromTableRow("city", "city", -1);
        t3.setDataFromTableRow("country", "country", -1);
        t3.setDataFromTableRow("names", "name", -1);
        System.out.println(t3.words.size());
        System.out.println("key: ital results: " + t3.getClosest3Strings("ita"));
        System.out.println("key: germa results: " + t3.getClosest3Strings("ger"));
        System.out.println();
        System.out.println();
        System.out.println("key: ital results: " + t3.getSuggest3Strings("ital"));
        System.out.println("key: germa results: " + t3.getSuggest3Strings("ger"));

    }

    public void setDataFromTableRow(String tableName, String rowName, int n){
        try
        {
            // create our mysql database connection
            String myDriver = "org.gjt.mm.mysql.Driver";
            String myUrl = "jdbc:mysql://localhost:3306/test";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "1234");

            // our SQL SELECT query.
            // if you only need a few columns, specify them by name instead of using "*"
            String query = "SELECT * FROM " + tableName;

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            if(n < 0) {
                while (rs.next()){
                    if(rs.getString(rowName).indexOf(' ') > 0){
                        words.add(rs.getString(rowName).substring(0, rs.getString(rowName).indexOf(' ')).toLowerCase());
                    } else {
                        words.add(rs.getString(rowName).toLowerCase());
                    }
                }
            } else {
                int i = 0;
                while (i < n && rs.next()){
                    if(rs.getString(rowName).indexOf(' ') > 0){
                        words.add(rs.getString(rowName).substring(0, rs.getString(rowName).indexOf(' ')).toLowerCase());
                    } else {
                        words.add(rs.getString(rowName).toLowerCase());
                    }
                    i++;
                }
            }
            st.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }


    public static int minDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();

        // len1+1, len2+1, because finally return dp[len1][len2]
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        //iterate though, and check last char
        for (int i = 0; i < len1; i++) {
            char c1 = word1.charAt(i);
            for (int j = 0; j < len2; j++) {
                char c2 = word2.charAt(j);

                //if last two chars equal
                if (c1 == c2) {
                    //update dp value for +1 length
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    int replace = dp[i][j] + 1;
                    int insert = dp[i][j + 1] + 1;
                    int delete = dp[i + 1][j] + 1;

                    int min = replace > insert ? insert : replace;
                    min = delete > min ? min : delete;
                    dp[i + 1][j + 1] = min;
                }
            }
        }

        return dp[len1][len2];
    }

    static int min(int x,int y,int z)
    {
        if (x<y && x<z) return x;
        if (y<x && y<z) return y;
        else return z;
    }

    static int editDist(String str1 , String str2 , int m ,int n)
    {
        // If first string is empty, the only option is to
        // insert all characters of second string into first
        if (m == 0) return n;

        // If second string is empty, the only option is to
        // remove all characters of first string
        if (n == 0) return m;

        // If last characters of two strings are same, nothing
        // much to do. Ignore last characters and get count for
        // remaining strings.
        if (str1.charAt(m-1) == str2.charAt(n-1))
            return editDist(str1, str2, m-1, n-1);

        // If last characters are not same, consider all three
        // operations on last character of first string, recursively
        // compute minimum cost for all three operations and take
        // minimum of three values.
        return 1 + min ( editDist(str1,  str2, m, n-1),    // Insert
                editDist(str1,  str2, m-1, n),   // Remove
                editDist(str1,  str2, m-1, n-1) // Replace
        );
    }

    public String getClosestString(String str){
        int min = Integer.MAX_VALUE;
        String result  = "";
        for (String s : words) {
            int dist = minDistance(str, s);
            if(dist < min ){
                min = dist;
                result = s;
            }
        }
        return result;
    }

    public String getClosest3Strings(String str){
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;
        int min3 = Integer.MAX_VALUE;
        String result1  = "";
        String result2  = "";
        String result3  = "";
        for (String s : words) {
            int dist = minDistance(str, s);
            if(dist < min1 ){
                min3 = min2;
                result3 = result2;
                min2 = min1;
                result2 = result1;
                min1 = dist;
                result1 = s;
            } else if (dist < min2) {
                min3 = min2;
                result3 = result2;
                min2 = dist;
                result2 = s;
            } else if (dist < min3){
                min3 = dist;
                result3 = s;
            }
        }
        return result1 + " " + result2 + " " + result3;
    }

    public String getSuggest3Strings(String str){
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;
        int min3 = Integer.MAX_VALUE;
        String result1  = "";
        String result2  = "";
        String result3  = "";
        for (String s : words) {
            String shortS;
            if(s.length() > str.length()){
                shortS = s.substring(0, str.length());
            } else {
                shortS = s;
            }
            int dist = minDistance(str, shortS);
            if(dist < min1 ){
                min3 = min2;
                result3 = result2;
                min2 = min1;
                result2 = result1;
                min1 = dist;
                result1 = s;
            } else if (dist < min2) {
                min3 = min2;
                result3 = result2;
                min2 = dist;
                result2 = s;
            } else if (dist < min3){
                min3 = dist;
                result3 = s;
            }
        }
        return result1 + " " + result2 + " " + result3;
    }

}
