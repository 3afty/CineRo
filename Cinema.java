package cinema;

import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        String[][] cinemaArray = constructCinema();
        menu(cinemaArray);
    }

    private static int soldTickets;
    private static double percentage;
    private static int currentIncome;
    private static int totalIncome;
    public static int rows;
    public static int columns;

    public static double getPercentage() {
        return percentage;
    }

    public static int getSoldTickets() {
        return soldTickets;
    }

    public static int getCurrentIncome() {
        return currentIncome;
    }

    public static int getTotalIncome() {
        return totalIncome;
    }

    public static void menu(String[][] cinemaArray) {

        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
        Scanner scanner = new Scanner(System.in);
        int menuValue = scanner.nextInt();
        switch (menuValue) {
            case 1:
                showCinema(cinemaArray);
                break;
            case 2:
                buyATicket(cinemaArray);
                break;
            case 3:
                showStatistics(cinemaArray);
                break;
            case 0:
                return;
            default:
                System.out.println("Chose 1,2 or 0");
                menu(cinemaArray);
                break;
        }
    }


    public static String[][] constructCinema() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        columns = scanner.nextInt();
        String[][] cinemaArray = new String[rows + 1][columns + 1];
        for (int i = 0; i < cinemaArray.length; i++) {
            for (int j = 0; j < cinemaArray[i].length; j++) {
                if (j == 0 && i == 0) {
                    cinemaArray[i][j] = " ";
                } else if (i != 0 && j == 0) {
                    String row = String.valueOf(i);
                    cinemaArray[i][j] = row;
                } else if (i == 0 && j != 0) {
                    String column = String.valueOf(j);
                    cinemaArray[i][j] = column;
                } else {
                    cinemaArray[i][j] = "S";
                }
            }
        }
        calculateTotalPrice();
        return cinemaArray;
    }

    private static int calculateTotalPrice() {
        int totalSeats = rows * columns;
        if (totalSeats < 60) {
            totalIncome = totalSeats * 10;
            return totalIncome;
        } else {
            int firstHalf = 0;
            int secondHalf = 0;
            int price1;
            int price2;
            firstHalf = (rows / 2) * columns;
            secondHalf = totalSeats - firstHalf;
            price1 = firstHalf * 10;
            price2 = secondHalf * 8;
            totalIncome = price1 + price2;
        }
        return totalIncome;
    }

    public static double calculatePercentage() {
        int totalSeats = rows * columns;
        percentage = ((double)soldTickets / (double)totalSeats) * 100;
        return percentage;
    }

    public static void showCinema(String[][] cinemaArray) {
        System.out.println("Cinema:");
        for (int i = 0; i < cinemaArray.length; i++) {
            for (int j = 0; j < cinemaArray[i].length; j++) {
                System.out.print(cinemaArray[i][j] + " ");
            }
            System.out.println();
        }
        menu(cinemaArray);
    }

    public static void buyATicket(String[][] cinemaArray) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a row number:");
        int selectedr = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        int selectedc = scanner.nextInt();
        try {
            if (cinemaArray[selectedr][selectedc].contains("B")) {
                System.out.println("That ticket has already been purchased!");
                buyATicket(cinemaArray);
            }
            for (int i = 0; i < cinemaArray.length; i++) {
                for (int j = 0; j < cinemaArray[i].length; j++) {
                    if (j == selectedc && i == selectedr) {
                        cinemaArray[i][j] = "B";
                    }
                }
            }
            soldTickets++;
            calculatePrice(cinemaArray, selectedr, selectedc);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Wrong input!");
        }

        menu(cinemaArray);
    }

    private static void calculatePrice(String[][] seatArray, int chosenRow, int chosenSeat) {
        int price = 0;
        int seats = seatArray.length * seatArray[0].length;
        if (seats <= 60) {
            price = 10;
        } else if (chosenRow < seatArray.length / 2) {
            price = 10;
        } else if (chosenRow >= seatArray.length / 2) {
            price = 8;
        }
        currentIncome += price;
        System.out.println("Ticket price: $" + price);
    }


    public static void showStatistics(String[][] cinemaArray) {
        calculatePercentage();
        String s = String.format("Number of purchased tickets: %d \nPercentage: %.2f%%  \nCurrent income: $%d" +
                "\nTotal income: $%d\n", getSoldTickets(), getPercentage(), getCurrentIncome(), getTotalIncome());
        System.out.println(s);
        menu(cinemaArray);
    }
}