//
//  RecurringType.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 2/1/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

enum RecurringType {
    case NotRecurring
    case Daily
    case EveryXDays
    case CertainDaysOfWeek
    case Weekly
    case Monthly
    
    static func getJsonCode(type:RecurringType)->Int {
        if JSONProtocolNames.recurringTypeMap[type] != nil {
            return JSONProtocolNames.recurringTypeMap[type]!
        } else {
            return JSONProtocolNames.notRecurring
        }
    }
    
    static func getRecurringItemType(code:Int)->RecurringType {
        for (type, jsonCode) in JSONProtocolNames.recurringTypeMap {
            if code == jsonCode {
                return type
            }
        }
        return .NotRecurring
    }
    
    static let RecurringTypeStringMap:Dictionary<RecurringType, String> =
        [.NotRecurring: "None",
         .Daily: "Daily",
         .EveryXDays: "Every X Days",
         .CertainDaysOfWeek: "Certain Days of Week",
         .Weekly: "Weekly",
         .Monthly: "Monthly"]
    
    static let RecurringTypeOrderedStringMap:Array<String> = ["None", "Daily", "Every X Days", "Certain Days of Week", "Weekly", "Monthly"]
    
    static func getSelectedRecurringItemType(item:String)->RecurringType {
        for (type, itemString) in RecurringTypeStringMap {
            if item == itemString {
                return type
            }
        }
        return .NotRecurring
    }
    
    static let DaysOfWeek:Array<String> = ["Sunday", "Monday","Tuesday","Wednesday","Thursday", "Friday", "Saturday"]
    
    static let EveryXDaysOptions:Array<String> = ["1","2","3","4", "5", "6", "7", "8", "9", "10"]
    
    static let MonthlyOptions:Array<String> = ["1","2","3","4", "5", "6", "7", "8", "9", "10","11","12","13","14", "15", "16", "17", "18", "19", "20",
    "21","22","23","24", "25", "26", "27", "28", "29", "30", "31"]
    
    
}
