//
//  RecurringType.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/1/17.
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
}
