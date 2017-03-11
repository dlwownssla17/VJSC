//
//  CalendarViewController.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/10/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation
import UIKit
import Alamofire

class CalendarViewController: UIViewController {
    
    var buttonWidth: CGFloat = 0.0
    var buttonHeight: CGFloat = 0.0
    var currentDayInfo:CurrentDayInfo = CurrentDayInfo()
    
    var calendarHeightStart:CGFloat = 0.0
    
    let numDays:Int = 7
    
    var ObjectsArray:Array<DayItem> = []
    var FullObjectArray:Array<DayItem> = []
    var buttons:Array<UIButton> = []
    var buttonToDay:Dictionary<UIButton, DayItem> = [:]
    
    var leftRightBurronHeight:CGFloat = 0.0
    
    var calendarTitle:UILabel = UILabel()
    
    override func viewDidLoad() {
        
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        
        buttonWidth = self.view.frame.width / CGFloat(numDays)
        buttonHeight = buttonWidth
        
        calendarHeightStart = buttonWidth * 3.0
        
        leftRightBurronHeight = buttonWidth
        
        ObjectsArray.append(DayItem())
        ObjectsArray.append(DayItem())
        ObjectsArray.append(DayItem())
        
        let leftButton = UIButton(frame: CGRect(x: 0, y: leftRightBurronHeight, width: buttonWidth * 2, height: buttonHeight))
        leftButton.setTitle("< Prev", for: UIControlState.normal)
        leftButton.setTitleColor(UIColor.blue, for: UIControlState.normal)
        leftButton.backgroundColor = UIColor.clear
        leftButton.layer.borderWidth = 1.0
        let borderAlpha : CGFloat = 0.7
        let cornerRadius : CGFloat = 5.0
        leftButton.layer.borderColor = UIColor.blue.cgColor
        leftButton.addTarget(self, action: #selector(leftButtonTapped(_:)), for: .touchDown)
        self.view.addSubview(leftButton)
        
        let rightButton = UIButton(frame: CGRect(x: buttonWidth * 5, y: leftRightBurronHeight, width: buttonWidth * 2, height: buttonHeight))
        rightButton.setTitle("Next >", for: UIControlState.normal)
        rightButton.setTitleColor(UIColor.blue, for: UIControlState.normal)
        rightButton.backgroundColor = UIColor.clear
        rightButton.layer.borderWidth = 1.0
        rightButton.layer.borderColor = UIColor.blue.cgColor
        rightButton.addTarget(self, action: #selector(rightButtonTapped(_:)), for: .touchDown)
        self.view.addSubview(rightButton)
        
        let calendarTitle = UILabel(frame: CGRect(x: 0.0, y: buttonWidth * 2, width: buttonWidth * 7, height: buttonHeight))
        calendarTitle.text = ""
        calendarTitle.textColor = UIColor.black
        self.view.addSubview(calendarTitle)
        
    }
    
    func getMonthTitle(month:Int)->String {
        let dateFormatter = DateFormatter()
        let months = dateFormatter.shortMonthSymbols
        let monthSymbol = (months?[month - 1])! as String
        return monthSymbol
    }
    
    func clearPreviousButtons() {
        for button in buttons {
            button.isEnabled = false
            button.isHidden = true
            button.removeFromSuperview()
            print("REMOVED")
        }
        buttons = []
        self.view.setNeedsDisplay()
    }
    
    func getMonthString(month:Int)->String {
        if month < 10 {
            return "0" + String(month)
        } else {
            return String(month)
        }
    }
    
    func getDayString(day:Int)->String {
        if day < 10 {
            return "0" + String(day)
        } else {
            return String(day)
        }
    }
    
    func getWeekday()->Int {
        let formatter  = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd"
        let dayString = String(currentDayInfo.currentYear) + "-" + getMonthString(month: currentDayInfo.currentMonth) + "-01"
        let todayDate = formatter.date(from: dayString)!
        let myCalendar = NSCalendar(calendarIdentifier: NSCalendar.Identifier.gregorian)!
        let myComponents = myCalendar.components(.weekday, from: todayDate)
        let weekDay = myComponents.weekday
        return weekDay! - 1
    }
    
    func getNumberOfDaysInMonth()->Int {
        let dateComponents = DateComponents(year: currentDayInfo.currentYear, month: currentDayInfo.currentMonth)
        let calendar = Calendar.current
        let date = calendar.date(from: dateComponents)!
        
        let range = calendar.range(of: .day, in: .month, for: date)!
        let numDays = range.count
        return numDays
    }
    
    func getAllDayItems() {
        let numDays = getNumberOfDaysInMonth()
        for i in 1...numDays {
            let dayItem = DayItem()
            dayItem.color = [150, 150, 150]
            dayItem.dayNumber = i
            dayItem.score = 0
            let dayString = String(currentDayInfo.currentYear) + "-" + getMonthString(month: currentDayInfo.currentMonth) + "-" + getDayString(day: i)
            dayItem.day = dayString
            let formatter  = DateFormatter()
            formatter.dateFormat = "yyyy-MM-dd"
            dayItem.dayValue = formatter.date(from: dayString)!
            dayItem.setInactive()
            FullObjectArray.append(dayItem)
        }
    }
    
    func mergeDayItemArrays() {
        for day in ObjectsArray {
            for existingDay in FullObjectArray {
                if day.dayNumber == existingDay.dayNumber {
                    existingDay.color = day.color
                    existingDay.score = day.score
                    existingDay.day = day.day
                    existingDay.dayValue = day.dayValue
                    existingDay.setActive()
                }
            }
        }
    }
    
    func insertButtons() {
        var xPos = getWeekday()
        if xPos == numDays {
            xPos = 0
        }
        var yPos = 0
        
        buttonToDay = [:]
        clearPreviousButtons()
        
        FullObjectArray = []
        getAllDayItems()
        mergeDayItemArrays()
        for day in FullObjectArray {
            let button = UIButton(frame: CGRect(x: CGFloat(xPos) * buttonWidth, y: calendarHeightStart + CGFloat(yPos) * buttonHeight, width: buttonWidth, height: buttonHeight))
            let subtitle = UILabel(frame: CGRect(x: 0.0, y: 0.0, width: buttonWidth / 2, height: buttonHeight / 2))
            xPos = xPos + 1
            if xPos == numDays {
                xPos = 0
                yPos = yPos + 1
            }
            if day.active {
                button.setTitle(String(day.score), for: UIControlState.normal)
            } else {
                button.setTitle("", for: UIControlState.normal)
            }
            button.setTitleColor(UIColor.blue, for: UIControlState.normal)
            button.backgroundColor = UIColor(red: CGFloat(Double(day.color[0]) / 255.0), green: CGFloat(Double(day.color[1]) / 255.0), blue: CGFloat(Double(day.color[2]) / 255.0), alpha: CGFloat(1.0))
            button.layer.borderWidth = 1.0
            button.layer.borderColor = UIColor.blue.cgColor
            subtitle.backgroundColor = UIColor.clear //UIColor.green
            subtitle.text = String(day.dayNumber)
            subtitle.textColor = UIColor.black
            button.addSubview(subtitle)
            button.addTarget(self, action: #selector(calendarViewButtonTapped(_:)), for: .touchDown)
            buttonToDay[button] = day
            buttons.append(button)
            self.view.addSubview(button)
        }
    }
    
    func leftButtonTapped(_ button: UIButton) {
        currentDayInfo.setToPreviousMonth()
        self.viewDidAppear(true)
    }
    
    func rightButtonTapped(_ button: UIButton) {
        currentDayInfo.setToNextMonth()
        
        self.viewDidAppear(true)
    }
    
    func calendarViewButtonTapped(_ button: UIButton) {
        print("Add Schedule Item Button pressed")
        let dayItemPicked:DayItem = buttonToDay[button]!
        currentDayInfo.currentDayString = dayItemPicked.day
        print("Picked " + dayItemPicked.day)
        self.dismiss(animated: true, completion: nil)
        
    }
    
    override func viewDidAppear(_ animated: Bool) {
        //"http://130.91.134.209:8000/test"
        let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString, JSONProtocolNames.yearHeaderName: self.currentDayInfo.currentYear, JSONProtocolNames.monthHeaderName: self.currentDayInfo.currentMonth]
        Alamofire.request(Settings.getMonthViewURL(), method: .get, headers: headers).validate().responseJSON { response in
            switch response.result {
            case .success(let data):
                let json = JSON(data)
                let dayArray = json[JSONProtocolNames.monthListHeaderName].arrayValue
                //print(dayArray)
                self.ObjectsArray = []
                for dayData in dayArray {
                    let dayItemData = DayItem(json: dayData)
                    self.ObjectsArray.append(dayItemData)
                }
                print("TEST: \(dayArray.count)")
                self.insertButtons()
                self.calendarTitle.removeFromSuperview()
                self.calendarTitle.isHidden = true
                self.calendarTitle = UILabel(frame: CGRect(x: 0.0, y: self.buttonWidth * 2, width: self.buttonWidth * 7, height: self.buttonHeight))
                self.calendarTitle.text = self.getMonthTitle(month: self.currentDayInfo.currentMonth) + " " + String(self.currentDayInfo.currentYear)
                self.calendarTitle.textColor = UIColor.black
                self.view.addSubview(self.calendarTitle)
            //return scheduleItems
            case .failure(let error):
                print("Request failed with error: \(error)")
            }
        }
//        let days:String = "{\"Day-Info\": [{\"Date\": \"2017-03-01\", \"Daily-Score\": 5, \"Daily-Color\": [255,0,0]}, {\"Date\": \"2017-03-02\", \"Daily-Score\": 5, \"Daily-Color\": [255,0,0]}, {\"Date\": \"2017-03-03\", \"Daily-Score\": 5, \"Daily-Color\": [255,0,0]}, {\"Date\": \"2017-03-04\", \"Daily-Score\": 5, \"Daily-Color\": [255,0,0]}, {\"Date\": \"2017-03-05\", \"Daily-Score\": 5, \"Daily-Color\": [255,0,0]}, {\"Date\": \"2017-03-06\", \"Daily-Score\": 5, \"Daily-Color\": [255,0,0]}, {\"Date\": \"2017-03-07\", \"Daily-Score\": 5, \"Daily-Color\": [255,0,0]}, {\"Date\": \"2017-03-08\", \"Daily-Score\": 5, \"Daily-Color\": [255,0,0]}, {\"Date\": \"2017-03-09\", \"Daily-Score\": 5, \"Daily-Color\": [255,0,0]}, {\"Date\": \"2017-03-10\", \"Daily-Score\": 5, \"Daily-Color\": [255,0,0]}]}"
//        let dayJSON = JSON(days.data(using: .utf8)!)
//        print(dayJSON)
//        let dayArray = dayJSON[JSONProtocolNames.monthListHeaderName].arrayValue
//        print(dayArray)
//        for dayData in dayArray {
//            let dayItemData = DayItem(json: dayData)
//            ObjectsArray.append(dayItemData)
//        }
//        print("TEST: \(dayArray.count)")
//        insertButtons()
    }

}
