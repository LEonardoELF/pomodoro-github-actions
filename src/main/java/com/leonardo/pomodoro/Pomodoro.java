package com.leonardo.pomodoro;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Pomodoro {
    static int workTime=1;
    static int breakTime=1;
    static int circleCount=1;
    static int circleMultiplier=1;

    public static void main(String[] args) {

        openTomato();

        startProgressBar();
    }

    private static void openTomato() {
        boolean parametersAccepted=false;
        do {
            System.out.println("Введите параметры приложения(-h помощь)");
            String[] inData=new Scanner(System.in).nextLine().split("-");
            boolean showHelp=false;
            for (String inDatum : inData) {
                String[] comm = inDatum.toLowerCase().trim().split(" ");

                switch (comm[0]) {
                    case ("w") : {
                        try {
                            workTime = Integer.parseInt(comm[1]);
                        } catch (NumberFormatException ex) {
                        } catch (ArrayIndexOutOfBoundsException ex) {}
                        break;
                    } case ("b"): {
                        try {
                            breakTime = Integer.parseInt(comm[1]);
                        } catch (NumberFormatException ex) {
                        } catch (ArrayIndexOutOfBoundsException ex) {}
                        break;
                    } case ("c"): {
                        try {
                            circleCount = Integer.parseInt(comm[1]);
                        } catch (NumberFormatException ex) {
                        } catch (ArrayIndexOutOfBoundsException ex) {}
                        break;
                    } case ("h"), ("help"): {
                        showHelp=true;//
                        break;
                    } case ("m"): {
                        try {
                            circleMultiplier = Integer.parseInt(comm[1]);
                        } catch (NumberFormatException ex) {
                        } catch (ArrayIndexOutOfBoundsException ex) {}
                        break;
                    }
                }
            }
            if (showHelp) {
                helpMessage();
                continue;
            }
            System.out.println("Параметры для работы приложения:");
            System.out.println(new StringBuilder()
                    .append("\tвремя работы " + workTime +"\n\t")
                    .append("время перерыва " + breakTime +"\n\t")
                    .append("число повторов " + circleCount +"\n\t")
                    .append("множитель времени работы " + circleMultiplier +"\n\t"));


            if (workTime<1||breakTime<1||circleCount<1||circleMultiplier<1) {
                System.out.println("Внимание! Все параметры должны быть больше 0!");
                continue;
            }

            System.out.println("Продолжить? Y/N");
            String cont = "" + new Scanner(System.in).next().toLowerCase().charAt(0);
            switch (cont) {
                case("y"): parametersAccepted=true;
            }
        } while (!parametersAccepted);
    }

    private static void startProgressBar() {
        for (int i = 0; i < circleCount; i++) {
            try {
                makeProgressBar(true,i);
                makeProgressBar(false,i);
            } catch  (Exception e) {}

        }
    }

    private static void makeProgressBar(boolean isWork,int iter) throws InterruptedException {
        int totalTime=(isWork?workTime*(int)Math.pow(circleMultiplier, iter):breakTime)*60;

        for (int i = 1; i <= totalTime; i++) {
            System.out.print(new StringBuilder()
                .append(isWork?"Работа ":"Отдых  ")
                .append((1000*i/totalTime)*1.0/10)
                .append(" ".repeat(5-String.valueOf((1000*i/totalTime)*1.0/10).length()))
                .append("[")
                .append("#".repeat((int)(i*60/totalTime)))
                .append("-".repeat(60-(int)(i*60/totalTime)))
                .append("] (")
                    .append(+i/60 + "/" + totalTime/60 + " минут, итерация "+(iter+1)+")")
                .append("\r"));
            TimeUnit.SECONDS.sleep(totalTime/60);
        }
        System.out.println();
    }

    private static void helpMessage() {
        System.out.println(new StringBuilder().append("\tСправка").append("\n\n\t-b время перерыва\n\t")
                .append("-c число повторов\n\t").append("-h -help помощь\n\t")
                .append("-m множитель времени работы\n\t").append("-w время работы\n\t"));
    }
}
