import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение: ");
        String input = scanner.nextLine();

        String[] parts = input.split(" ");
        int a, b;
        boolean isRomanNumeral = false;
        Map<Character, Integer> romanNumerals = new HashMap<>();
        romanNumerals.put('I', 1);
        romanNumerals.put('V', 5);
        romanNumerals.put('X', 10);

        if (parts.length !=3) {
            System.out.println("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
            return;
        }

        if (parts[0].matches("^[IVXivx]+$") && parts[2].matches("^[IVXivx]+$")) {
            a = romanToArabic(parts[0], romanNumerals);
            b = romanToArabic(parts[2], romanNumerals);
            isRomanNumeral = true;
        } else if ((parts[0].matches("^[IVXivx]+$") && !parts[2].matches("^[IVXivx]+$")) || (!parts[0].matches("^[IVXivx]+$") && parts[2].matches("^[IVXivx]+$"))) {
            throw new Exception ("Ошибка! т.к. используются одновременно разные системы счисления.");
        } else {
            a = Integer.parseInt(parts[0]);
            b = Integer.parseInt(parts[2]);
        }

        char operator = parts[1].charAt(0);

        if ((a < 1 || a > 10) || (b < 1 || b > 10)) {
            System.out.println("Я умею принимать Арабские числа от 1 до 10 или Римские от I до X!");
            return;
        }

        int sum;

        switch (operator) {
            case '+' -> sum = a + b;
            case '-' -> sum = a - b;
            case '*' -> sum = a * b;
            case '/' -> sum = a / b;
            default -> {
                System.out.println("Ошибка! Неверный оператор.");
                return;
            }
        }

        String result;
        if (isRomanNumeral) {
            result = arabicToRoman(sum);
        } else {
            result = Integer.toString(sum);
        }

        System.out.println(result);
    }

    public static int romanToArabic(String input, Map<Character, Integer> romanNumerals) {
        int result = 0;
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i > 0 && romanNumerals.get(chars[i]) > romanNumerals.get(chars[i-1])) {
                result += romanNumerals.get(chars[i]) - 2 * romanNumerals.get(chars[i-1]);
            } else {
                result += romanNumerals.get(chars[i]);
            }
        }
        return result;
    }

    public static String arabicToRoman(int input) {
        if (input < 1) {
            return "Ошибка! т.к. в римской системе нет отрицательных чисел.";
        }

        String[] romanNumerals = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = {100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < romanNumerals.length; i++) {
            while (input >= values[i]) {
                input -= values[i];
                result.append(romanNumerals[i]);
            }
        }
        return result.toString();
    }
}
