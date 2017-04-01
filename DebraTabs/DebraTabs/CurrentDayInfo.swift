//
//  CurrentDayInfo.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/11/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

class CurrentDayInfo {
    
    var currentDayString:String = ""
    var currentMonth:Int = 0
    var currentYear:Int = 0
    
    init() {
        let today = Date()
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd"
        currentDayString = dateFormatter.string(from: today)
        let calendar = Calendar.current
        currentMonth = calendar.component(.month, from: today)
        currentYear = calendar.component(.year, from: today)
    }
    
    func getNextMonth()->Int {
        var nextMonth = currentMonth + 1
        if nextMonth > 12 {
            nextMonth = 1
        }
        return nextMonth
    }
    
    func getPreviousMonth()->Int {
        var previousMonth = currentMonth + 1
        if previousMonth < 1 {
            previousMonth = 12
        }
        return previousMonth
    }
    
    func getNextYear()->Int {
        var nextYear = currentYear
        if currentMonth == 12 {
            nextYear = nextYear + 1
        }
        return nextYear
    }
    
    func getPreviousYear()->Int {
        var previousYear = currentYear
        if currentMonth == 1 {
            previousYear = previousYear - 1
        }
        return previousYear
    }
    
    func setToPreviousMonth() {
        if currentMonth == 1 {
            currentMonth = 12
            currentYear = currentYear - 1
        } else {
            currentMonth = currentMonth - 1
        }
    }
    
    func setToNextMonth() {
        if currentMonth == 12 {
            currentMonth = 1
            currentYear = currentYear + 1
        } else {
            currentMonth = currentMonth + 1
        }
    }
    
    func setCurrentDate(value:String) {
        currentDayString = value
    }
    
    func setToTomorrow() {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd"
        let currentDay:Date = dateFormatter.date(from: self.currentDayString)!
        let tomorrow = Calendar.current.date(byAdding: .day, value: 1, to: currentDay)
        self.currentDayString = dateFormatter.string(from: tomorrow!)
    }
}
