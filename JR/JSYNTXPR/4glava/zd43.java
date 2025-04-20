import java.util.Scanner;

/*
Сумма кратных чисел
*/

public class zd43 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int start = scanner.nextInt();
        int end = scanner.nextInt();
        int multiple = scanner.nextInt();

        int sum = 0;
        for(;start<end;start++){
            if(start % multiple == 0){
                sum += start;
                continue;
            }
        }

        System.out.println(sum);
    }
}