package GUI.Util;


import BE.School;

import java.util.concurrent.Callable;

public class Loader implements Runnable , Callable<School> {
    @Override
    public void run() {

    }

    @Override
    public School call() throws Exception {
        return null;
    }
}
//  Instant start = Instant.now();
//
//        // Invokes the divisor counter
//        ExecutorService es = Executors.newFixedThreadPool(5);
//        DivisorCounter task = new DivisorCounter(1, 20000);
//        DivisorCounter task1 = new DivisorCounter(20000,40000);
//        DivisorCounter task2 = new DivisorCounter(40000,60000);
//        DivisorCounter task3 = new DivisorCounter(60000,80000);
//        DivisorCounter task4 = new DivisorCounter(80000,100000);
//        System.out.println("Looking for the best result...");
//        List<Future<Result>> futureList = es.invokeAll(Arrays.asList(task,task1,task2,task3,task4));
//
//        // Fetches the end time of the method.
//        Instant end = Instant.now();
//
//        // Find the highest result
//        Result result = DivisorCounter.getBestResult();
//        System.out.println(result.getNumber() + " maxResult " + result.getDivisorCounter() + " divisors!");
//
//        System.out.println("Duration: " + Duration.between(start, end).toMillis() + " ms");
//        System.out.println("Duaraion: 123 ms");
//        es.shutdown();

//public class DivisorCounter implements Runnable, Callable<Result> {
//
//    private final static Vector<Result> results = new Vector<>();
//    private final int minimum;
//    private final int maximum;
//
//    public DivisorCounter(int minimum, int maximum)
//    {
//        this.minimum = minimum;
//        this.maximum = maximum;
//    }
//
//    @Override
//    public void run() {
//        call();
//    }
//
//    public static Result getBestResult() {
//        Result result = new Result(0, 0);
//        for(Result r : results) {
//            if (r.getDivisorCounter() > result.getDivisorCounter()) {
//                result = r;
//            }
//        }
//        return result;
//    }
//
//    @Override
//    public Result call() {
//        Result result = new Result(0, 0);
//        for(int i = minimum; i <= maximum; i++) {
//            int counter = 0;
//            for(int j = 1; j<=i/2; j++) {
//                if(i % j == 0) {
//                    counter++;
//                }
//            }
//            if(counter > result.getDivisorCounter()) {
//                result = new Result(i, counter);
//            }
//        }
//
//        results.add(result);
//        return result;
//    }
//}

//package dk.easv;
//
//public class Result {
//    private int number;
//    private int divisorCounter;
//
//    public Result(int number, int divisorCounter) {
//        this.number = number;
//        this.divisorCounter = divisorCounter;
//    }
//
//    public int getDivisorCounter() {
//        return this.divisorCounter;
//    }
//
//    public int getNumber() {
//        return this.number;
//    }
//}
//package sample.ui;
//
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.StringProperty;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import sample.logic.Result;
//
//public class ResultChangeListener implements ChangeListener<Result> {
//
//    private Result bestResultSoFar;
//    private StringProperty textProperty;
//
//    public ResultChangeListener() {
//        textProperty = new SimpleStringProperty("");
//        bestResultSoFar = new Result(-1, -1);
//    }
//
//    @Override
//    public void changed(ObservableValue<? extends Result> observable, Result oldValue, Result newValue) {
//        if (newValue.getDivisorCounter() > bestResultSoFar.getDivisorCounter()) {
//            bestResultSoFar = newValue;
//            String txtUpdate = "The number " + bestResultSoFar.getNumber() + " has " + bestResultSoFar.getDivisorCounter() + " divisors!";
//            textProperty.setValue(txtUpdate);
//        }
//    }
//
//    public ObservableValue<String> getTextProperty() {
//        return textProperty;
//    }
//
//}
//package sample.ui;
//
//import javafx.beans.property.SimpleDoubleProperty;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//
//public class ProgressChangeListner implements ChangeListener<Number> {
//
//    private SimpleDoubleProperty progressProperty;
//
//    private double totalProgress;
//    private double maxProgress;
//
//    public ProgressChangeListner() {
//        progressProperty = new SimpleDoubleProperty(0.0);
//    }
//
//    public ObservableValue<? extends Number> getProgressProperty() {
//        return progressProperty;
//    }
//
//    @Override
//    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//        totalProgress += (newValue.doubleValue() - oldValue.doubleValue());
//        progressProperty.set((totalProgress / maxProgress) / maxProgress);
//    }
//
//    public void setNumberOfTasks(int number) {
//        maxProgress = number;
//    }
//}