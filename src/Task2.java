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
        String report = "String length, lsc\n";
        Long lscTime = Long.valueOf(0);

//        System.out.println("hello world");
        System.out.println(lcs("bdcaba", "abcbdab"));
//        System.out.println(longestCommonSubsequence(rs.nextString(), rs.nextString()));
//        System.out.println(lcs("bdcaba", "abcbdab"));

        for(int i = testJump; i < testJump * testNumber; i += testJump) {
            lscTime = Long.valueOf(0);
            for(int j = 0; j < sameDataSizeTestNumber; j++) {
                long startTime;
                long stopTime;
                RandomString rs = new RandomString(i, 'a', 'z');

                startTime = System.nanoTime();
                lcs(rs.nextString(), rs.nextString());
                stopTime = System.nanoTime();
                lscTime += (stopTime - startTime);

            }
            report += (i + "," + String.format("%.3f", ((double) lscTime / (double) sameDataSizeTestNumber)) + "\n");
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

    

    public static int lcs(String a, String b){
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
