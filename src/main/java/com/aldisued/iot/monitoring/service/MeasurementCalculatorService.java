package com.aldisued.iot.monitoring.service;


import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MeasurementCalculatorService {

  public List<Double> filterByAverageDeviation(List<Double> values, Double deviation) {
    if (deviation == null || !Double.isFinite(deviation) || deviation < 0.0
        || deviation > 1.0) {
      throw new IllegalArgumentException("Deviation must be between 0.0 and 1.0");
    }
    if (values.isEmpty()) {
      return List.of();
    }

    double average = values.stream().mapToDouble(Double::doubleValue).average().orElseThrow();
    double allowedDifference = Math.abs(average) * deviation;

    return values.stream()
        .filter(value -> Math.abs(value - average) <= allowedDifference)
        .toList();
  }

  public List<Double> getMovingAverage(List<Double> data, int windowSize) {
    if (windowSize <= 0 || windowSize > data.size()) {
      throw new IllegalArgumentException(
          "Window size must be positive and no greater than the number of values");
    }

    double sum = 0.0;
    for (int i = 0; i < windowSize; i++) {
      sum += data.get(i);
    }

    var averages = new ArrayList<Double>(data.size() - windowSize + 1);
    averages.add(sum / windowSize);
    for (int i = windowSize; i < data.size(); i++) {
      sum += data.get(i) - data.get(i - windowSize);
      averages.add(sum / windowSize);
    }
    return averages;
  }

}
