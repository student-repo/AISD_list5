import java.io.*;

/**
 * Created by ubuntu-master on 16.05.17.
 */
public class Task2 {

    public static void main(String[] args) throws IOException {
        int randomStringLength = 50;
        int sameDataSizeTestNumber = 10;
        int testJump = 10;
        int testNumber = 100;
//        RandomString rs = new RandomString(randomStringLength, 'a', 'z');
        String report = "String length, lsc, recursion lsc\n";
        Long lscTime = Long.valueOf(0);
        Long recursionLscTime = Long.valueOf(0);

//        System.out.println("hello world");
//        System.out.println(lcs("bdcaba", "abcbdab"));
//        System.out.println(longestCommonSubsequence(rs.nextString(), rs.nextString()));
//        System.out.println(getLongestCommonSubsequence("bdcaba", "abcbdab"));

        for(int i = testJump; i < testJump * testNumber; i += testJump) {
            lscTime = Long.valueOf(0);
            recursionLscTime = Long.valueOf(0);
            for(int j = 0; j < sameDataSizeTestNumber; j++) {
                long startTime;
                long stopTime;
                RandomString rs = new RandomString(i, 'a', 'z');

                startTime = System.nanoTime();
                longestCommonSubsequence(rs.nextString(), rs.nextString());
                stopTime = System.nanoTime();
                lscTime += (stopTime - startTime);

                startTime = System.nanoTime();
                getLongestCommonSubsequence(rs.nextString(), rs.nextString());
                stopTime = System.nanoTime();
                recursionLscTime += (stopTime - startTime);

            }
            report += (i + "," + String.format("%.3f", (double) lscTime / (double) sameDataSizeTestNumber)) + ", "  + String.format("%.3f", ((double) recursionLscTime / (double) sameDataSizeTestNumber)) + "\n";
        }

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("./src/task2_output.csv"), "utf-8"))) {
            writer.write(report);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Runtime runTime = Runtime.getRuntime();
        Process process = runTime.exec("libreoffice --calc ./src/task2_output.csv &");






    }

    public static int lcs(String a, String b, int i, int j) {
        int x;
        if(a.charAt(i) == b.charAt(j)) {
            x = lcs(a, b, i - 1, j - 1) + 1;
        } else {
            x = Math.max(lcs(a, b, i -1, j), lcs(a, b, i, j - 1));
        }
        return x;
    }

    public static int longestCommonSubsequence(String A, String B) {
        if (A == null || B == null || A.length() == 0 || B.length() == 0) {
            return 0;
        }
        int[][] check = new int[A.length()  + 1][B.length() + 1];
        for (int i = 1; i <= A.length(); i++) {
            for (int j = 1; j <= B.length(); j++) {
                if (A.charAt(i - 1) == B.charAt(j - 1)) {
                    check[i][j] = check[i - 1][j - 1] + 1;
                } else {
                    check[i][j] = Math.max(check[i][j], check[i - 1][j]);
                    check[i][j] = Math.max(check[i][j], check[i][j - 1]);
                }
            }
        }
        return check[A.length()][B.length()];
    }


//    public static String lcs(String a, String b){
//        int aLen = a.length();
//        int bLen = b.length();
//        if(aLen == 0 || bLen == 0){
//            return "";
//        }else if(a.charAt(aLen-1) == b.charAt(bLen-1)){
//            return lcs(a.substring(0,aLen-1),b.substring(0,bLen-1))
//                    + a.charAt(aLen-1);
//        }else{
//            String x = lcs(a, b.substring(0,bLen-1));
//            String y = lcs(a.substring(0,aLen-1), b);
//            return (x.length() > y.length()) ? x : y;
//        }
//    }
//
//
//    public static int LCS(String A, String B) {
//        if (A.length() == 0 || B.length() == 0) {
//            return 0;
//        }
//        int lenA = A.length();
//        int lenB = B.length();
//        // check if last characters are same
//        if (A.charAt(lenA - 1) == B.charAt(lenB - 1)) {
//            // Add 1 to the result and remove the last character from both
//            // the strings and make recursive call to the modified strings.
//            return 1 + LCS(A.substring(0, lenA - 1), B.substring(0, lenB - 1));
//        } else {
//            // Remove the last character of String 1 and make a recursive
//            // call and remove the last character from String 2 and make a
//            // recursive and then return the max from returns of both recursive
//            // calls
//            return Math.max(
//                    LCS(A.substring(0, lenA - 1), B.substring(0, lenB)),
//                    LCS(A.substring(0, lenA), B.substring(0, lenB - 1)));
//        }
//    }

    public static int getLongestCommonSubsequence(String a, String b){
        int m = a.length();
        int n = b.length();
        int[][] dp = new int[m+1][n+1];

        for(int i=0; i<=m; i++){
            for(int j=0; j<=n; j++){
                if(i==0 || j==0){
                    dp[i][j]=0;
                }else if(a.charAt(i-1)==b.charAt(j-1)){
                    dp[i][j] = 1 + dp[i-1][j-1];
                }else{
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

        return dp[m][n];
    }
}
