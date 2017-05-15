import java.util.Arrays;

public class Select {
    public void swap(int[] list, int i, int j) {
        int temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }
    public int partition(int[] list, int left, int right, int val) {
        int i;
        for (i = left; i < right; i++) {
            if (list[i] == val) {
                break;
            }
        }
        swap(list, i, right);
        int pivotValue = list[right];
        int storeIndex = left;
        for (i = left; i <= right; i++) {
            if (list[i] < pivotValue) {
                swap(list, storeIndex, i);
                storeIndex++;
            }
        }
        swap(list, right, storeIndex);
        return storeIndex;
    }
    int findMedian(int arr[], int l, int len) {
        Arrays.sort(arr, l, l + len);
        return arr[l + len / 2];
    }
    int select(int arr[], int l, int r, int k) {
        if (k > 0 && k <= r - l + 1) {
            int n = r - l + 1, i;
            int median[] = new int[(n + 4) / 5];
            for (i = 0; i < n / 5; i++) {
                median[i] = findMedian(arr, l + i * 5, 5);
            }
            if (i * 5 < n) {
                median[i] = findMedian(arr, l + i * 5, n % 5);
                i++;
            }
            int medOfMed = (i == 1) ? median[i - 1]: select(median, 0, i - 1, i / 2);

            int pos = partition(arr, l, r, medOfMed);
            if (pos - l == k - 1) {
                return arr[pos];
            }
            if (pos - l > k - 1) {
                return select(arr, l, pos - 1, k);
            }
            return select(arr, pos + 1, r, k - pos + l - 1);
        }
        return Integer.MAX_VALUE;
    }

    public static void main(String args[])
    {
        Select ob = new Select();
        int arr[] = {12, 3, 5, 7, 4, 19, 26};
        int n = arr.length,k = 3;
        System.out.println("K'th smallest element is "+
                ob.select(arr, 0, n-1, k));
    }
}