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
    
    func getJSONCode(type:RecurringType)->Int {
        switch type {
        case .Daily:
            return JSONProtocolNames.daily
        case .EveryXDays:
            return JSONProtocolNames.everyXDays
        case .CertainDaysOfWeek:
            return JSONProtocolNames.certainDaysOfWeek
        case .Weekly:
            return JSONProtocolNames.weekly
        case .Monthly:
            return JSONProtocolNames.monthly
        default:
            return JSONProtocolNames.notRecurring
        }
    }
    
    func getScheduleItemType(code:Int)->RecurringType {
        if code == JSONProtocolNames.daily {
            return .Daily
        } else if code == JSONProtocolNames.everyXDays {
            return .EveryXDays
        } else if code == JSONProtocolNames.certainDaysOfWeek {
            return .CertainDaysOfWeek
        } else if code == JSONProtocolNames.weekly {
            return .Weekly
        } else if code == JSONProtocolNames.monthly {
            return .Monthly
        } else {
            return .NotRecurring
        }
    }
}
