package racingcar;

import camp.nextstep.edu.missionutils.Console;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        String carNamesInput = Console.readLine();
        validateCarNames(carNamesInput);
        List<String> carNames = List.of(carNamesInput.split(","));

        System.out.println("시도할 회수는 몇회인가요?");
        int attempts = Integer.parseInt(Console.readLine());

        List<Car> cars = createCars(carNames);

        System.out.println();
        System.out.println("실행 결과");
        startRacing(attempts, cars);

        List<String> winners = determineWinners(cars);
        printWinners(winners);

        Console.close();
    }

    private static List<Car> createCars(List<String> carNames) {
        List<Car> cars = new ArrayList<>();
        for (String name : carNames) {
            cars.add(new Car(name));
        }
        return cars;
    }

    private static void validateCarNames(String carNamesInput) {
        String[] carNames = carNamesInput.split(",");
        for (String name : carNames) {
            if (name.length() > 5) {
                throw new IllegalArgumentException("자동차 이름은 5자 이하여야 합니다.");
            }
        }
    }

    private static void startRacing(int attempts, List<Car> cars) {
        for (int i = 0; i < attempts; i++) {
            for (Car car : cars) {
                car.move();
            }
            printCurrentPositions(cars);
        }
    }

    private static void printCurrentPositions(List<Car> cars) {
        for (Car car : cars) {
            System.out.println(car.getName() + " : " + "-".repeat(car.getPosition()));
        }
        System.out.println();
    }

    private static List<String> determineWinners(List<Car> cars) {
        int maxPosition = cars.stream().mapToInt(Car::getPosition).max().orElse(0);
        List<String> winners = new ArrayList<>();
        for (Car car : cars) {
            if (car.getPosition() == maxPosition) {
                winners.add(car.getName());
            }
        }
        return winners;
    }

    private static void printWinners(List<String> winners) {
        System.out.print("최종 우승자 : ");
        for (int i = 0; i < winners.size(); i++) {
            if (i != 0) {
                System.out.print(", ");
            }
            System.out.print(winners.get(i));
        }
    }
}