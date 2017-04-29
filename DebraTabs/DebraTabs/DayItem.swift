//
//  DayItem.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 2/10/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

class DayItem {
    var day:String = ""
    var dayValue:Date = Date()
    var dayNumber:Int = 0
    var score:Int = 0
    var color:Array<Int> = []
    var active:Bool = true
    
    init(json:JSON) {
        day = json[JSONProtocolNames.monthDayHeaderName].stringValue
        score = json[JSONProtocolNames.monthDayScoreHeaderName].intValue
        let colorValues = json[JSONProtocolNames.monthDayColorHeaderName].arrayValue
        var colorArray:Array<Int> = []
        for val in colorValues {
            colorArray.append(val.intValue)
        }
        color = colorArray
        
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd"
        dayValue = dateFormatter.date(from: day)!
        let calendar = Calendar.current
        dayNumber = calendar.component(.day, from: dayValue)
    }
    
    init() {
        
    }
    
    func setInactive() {
        active = false
    }
    
    func setActive() {
        active = true
    }
}
