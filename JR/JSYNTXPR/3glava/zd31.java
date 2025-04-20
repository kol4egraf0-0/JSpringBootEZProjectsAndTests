import java.util.Scanner;

/*
Призывная кампания
*/

public class zd31 {
    public static void main(String[] args) {
        String militaryCommissar = ", явитесь в военкомат";
        Scanner lol = new Scanner(System.in);
        String name = lol.nextLine();
        int age = lol.nextInt();
        if(age>17 && age<29){
            System.out.print(name+militaryCommissar);
        }
    }
}             
