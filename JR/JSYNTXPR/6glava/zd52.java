import java.util.Scanner;

/*
Максимальное из N чисел
*/

public class zd52 {
    public static int[] array;

    public static void main(String[] args) throws Exception {
        //напишите тут ваш код
        Scanner lol = new Scanner(System.in);
        int N = lol.nextInt();
        array = new int[N];
        for(int i=0; i<N; i++){
            array[i] = lol.nextInt();
        }
        int max=array[0];
        for(int i=1; i<array.length; i++){
             if(max<array[i]){
                max = array[i];
            }
        }
        System.out.print(max);
    }
}
