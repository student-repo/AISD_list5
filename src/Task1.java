import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Task1 {

    private int valueRange = 10000;
    private int dataSize = 10000;

    public int getValueRange() {
        return valueRange;
    }

    public int getDataSize() {
        return dataSize;
    }

    public int getTestJump() {
        return testJump;
    }

    public int getTestNumber() {
        return testNumber;
    }

    private int testJump = 100;
    private int testNumber = 100;
    private int sameDataSizeTestNumber = 10;

    public static void main(String[] args) throws IOException {
        String report = "Data size, BST os-select, randomised select, select\n";
        Task1 t1 = new Task1();
        Long osTime = Long.valueOf(0);
        Long selTime = Long.valueOf(0);
        Long randSelTime = Long.valueOf(0);

        BST bst = new BST();
        Select select = new Select();
        RandomizedSelect randSelect = new RandomizedSelect();


        for(int i = t1.getTestJump(); i <= t1.getTestJump() * t1.getTestNumber(); i = i + t1.testJump){
             osTime = Long.valueOf(0);
             selTime = Long.valueOf(0);
             randSelTime = Long.valueOf(0);
            for(int j = 0; j < t1.sameDataSizeTestNumber; j ++){
                long startTime;
                long stopTime;
                int randomNum = ThreadLocalRandom.current().nextInt(0, i);
                ArrayList<Integer> curArr= t1.getRandomArray(i);
                int[] arr = curArr.stream().mapToInt(h -> h).toArray();

                bst = t1.builtBST(curArr);
                startTime = System.nanoTime();
                bst.os_select(randomNum);
                stopTime = System.nanoTime();
                osTime += (stopTime - startTime);

                startTime = System.nanoTime();
                select.select(arr, 0, i - 1, randomNum);
                stopTime = System.nanoTime();
                selTime += (stopTime - startTime);

                startTime = System.nanoTime();
                randSelect.randomizedSelect(arr, 0, i - 1, randomNum);
                stopTime = System.nanoTime();
                randSelTime += (stopTime - startTime);
            }
            report += (i + "," + String.format("%.3f", (double) osTime / (double) t1.sameDataSizeTestNumber)) + ", "  + String.format("%.3f", ((double) randSelTime / (double) t1.sameDataSizeTestNumber)) + ", " + String.format("%.3f", ((double) selTime/ (double) t1.sameDataSizeTestNumber)) + "\n";
        }

        report += "\n" + "\n" + "\n" + "\n";
        report += "Data size, BST os-select, randomised select, select\n";
        for(int i = t1.getTestJump(); i <= t1.getTestJump() * t1.getTestNumber(); i = i + t1.testJump){
            osTime = Long.valueOf(0);
            selTime = Long.valueOf(0);
            randSelTime = Long.valueOf(0);
            for(int j = 0; j < t1.sameDataSizeTestNumber; j ++){
                long startTime;
                long stopTime;
                int randomNum = ThreadLocalRandom.current().nextInt(0, i);
                ArrayList<Integer> curArr= t1.getRandomArray(i);
                int[] arr = curArr.stream().mapToInt(h -> h).toArray();

                startTime = System.nanoTime();
                bst = t1.builtBST(curArr);
                bst.os_select(randomNum);
                stopTime = System.nanoTime();
                osTime += (stopTime - startTime);

                startTime = System.nanoTime();
                select.select(arr, 0, i - 1, randomNum);
                stopTime = System.nanoTime();
                selTime += (stopTime - startTime);

                startTime = System.nanoTime();
                randSelect.randomizedSelect(arr, 0, i - 1, randomNum);
                stopTime = System.nanoTime();
                randSelTime += (stopTime - startTime);
            }
            report += (i + "," + String.format("%.3f", (double) osTime / (double) t1.sameDataSizeTestNumber)) + ", "  + String.format("%.3f", ((double) randSelTime / (double) t1.sameDataSizeTestNumber)) + ", " + String.format("%.3f", ((double) selTime/ (double) t1.sameDataSizeTestNumber)) + "\n";
        }

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("./src/task1_output.csv"), "utf-8"))) {
            writer.write(report);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Runtime runTime = Runtime.getRuntime();
        Process process = runTime.exec("libreoffice --calc ./src/task1_output.csv &");



    }
    public ArrayList<Integer> getRandomArray(int n){
        ArrayList<Integer> arr = new ArrayList<>();
        for(int i = 0; i < n; i ++) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, valueRange + 1);
            arr.add(randomNum);
        }
        return arr;
    }

    public BST builtBST(ArrayList<Integer> arr){
        BST b = new BST();
        arr.forEach(b::insert);
        return b;
    }


}
