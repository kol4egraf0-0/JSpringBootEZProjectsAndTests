import java.util.Scanner;

/*
Тепло или холодно
*/

public class zd3 {
    public static void main(String[] args) {
        String cold = "на улице холодно";
        String warm = "на улице тепло";
        Scanner lol = new Scanner(System.in);
        int t = lol.nextInt();
        if(t<0){
            System.out.print(cold);
        }
        else{
            System.out.print(warm);
        }

    }
}