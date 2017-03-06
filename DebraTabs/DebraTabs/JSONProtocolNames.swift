//
//  JSONProtocolNames.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/1/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

class JSONProtocolNames {
    // Schedule Item Types
    static let scheduleItemTypeHeaderName:String = "Schedule-Item-Type"
    static let medication:String = "Medication"
    static let exercise:String = "Exercise"
    static let glucoseLevel:String = "GlucoseLevel"
    static let eating:String = "Eating"
    static let none:String = ""
    
    // Recurring Item Types
    static let recurringTypeHeaderName:String = "Recurring-Type"
    static let notRecurring:Int = -1
    static let daily:Int = 0
    static let everyXDays:Int = 1
    static let certainDaysOfWeek:Int = 2
    static let weekly:Int = 3
    static let monthly:Int = 4
    
    // Ending Type
    static let endingType:String = "Ending-Type"
    static let never:Int = 0
    static let afterYOccurrences:Int = 1
    static let onT:Int = 2
}
