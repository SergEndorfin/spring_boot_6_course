package com.itkon.spring6restmvc.service;

import com.itkon.spring6restmvc.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface BeerCsvService {
    List<BeerCSVRecord> convertToCSV(File csvFile);
}
