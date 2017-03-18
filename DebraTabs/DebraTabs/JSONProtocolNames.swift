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
    static let medication:String = "medication"
    static let exercise:String = "exercise"
    static let glucoseLevel:String = "blood_glucose_measurement"
    static let eating:String = "eating"
    static let none:String = ""
    
    static let scheduleItemTypeMap:Dictionary<ScheduleItemType, String> =
        [.Medication: "medication",
         .Exercise: "exercise",
         .GlucoseLevel: "blood_glucose_measurement",
         .Eating: "eating",
         .None: "none"]
    
    // Recurring Item Types
    static let recurringTypeHeaderName:String = "Recurring-Type"
    static let notRecurring:Int = -1
    static let daily:Int = 0
    static let everyXDays:Int = 1
    static let certainDaysOfWeek:Int = 2
    static let weekly:Int = 3
    static let monthly:Int = 4
    
    static let recurringTypeMap:Dictionary<RecurringType, Int> =
        [.NotRecurring: -1,
         .Daily: 0,
         .EveryXDays: 1,
         .CertainDaysOfWeek: 2,
         .Weekly: 3,
         .Monthly: 4]
    
    // Ending Type
    static let endingTypeHeaderName:String = "Ending-Type"
    static let never:Int = 0
    static let afterYOccurrences:Int = 1
    static let onT:Int = 2
    static let endingTypeNotNeeded:String = "-1"
    
    static let endingTypeMap:Dictionary<EndingType, Int> =
        [.NotRecurring: -1,
         .Never: 0,
         .AfterYOccurrences: 1,
         .OnT: 2]
    
    // Progress Type
    static let scheduleItemProgressTypeHeaderName:String = "Schedule-Item-Progress-Type"
    static let boolean:String = "boolean"
    static let percentage:String = "percentage"
    
    static let durationProgressTypeNotNeeded:Int = -1
    
    static let progressTypeMap:Dictionary<ProgressType, String> =
        [.Boolean: "boolean",
         .Percentage: "percentage"]
    
    static let scheduleItemIDHeaderName:String = "Schedule-Item-ID"
    static let recurringIDHeaderName:String = "Recurring-ID"
    static let recurringValueHeaderName:String = "Recurring-Value"
    static let endingValueHeaderName:String = "Ending-Value"
    static let scheduleItemTitleHeaderName:String = "Schedule-Item-Title"
    static let scheduleItemDescriptionHeaderName:String = "Schedule-Item-Description"
    static let scheduleItemStartHeaderName:String = "Schedule-Item-Start"
    static let scheduleItemDurationHeaderName:String = "Schedule-Item-Duration"
    static let scheduleItemProgressHeaderName:String = "Schedule-Item-Progress"
    static let scheduleItemScoreHeaderName:String = "Schedule-Item-Score"
    static let scheduleItemActiveHeaderName:String = "Schedule-Item-Active"
    static let scheduleItemColorHeaderName:String = "Schedule-Item-Color"
    static let scheduleItemModifiableHeaderName:String = "Schedule-Item-Modifiable"
    static let scheduleItemCheckedInAtStartHeaderName:String = "Schedule-Item-Checked-In-At-Start"
    
    static let scheduleItemsListResponseName:String = "Daily-Items"  // Daily-Items
    
    static let scheduleItemsAddScheduleItemName:String = "Add-Schedule-Item"
    
    static let scheduleItemsEditScheduleItemName:String = "Edit-Schedule-Item"
    
    static let scheduleItemsRemoveScheduleItemName:String = "Remove-Schedule-Item"
    
    static let monthDayHeaderName:String = "Date"
    static let monthDayScoreHeaderName:String = "Daily-Score"
    static let monthDayColorHeaderName:String = "Daily-Color"
    
    static let monthListHeaderName:String = "Day-Info"
    
    static let passwordHeaderName:String = "Password"
    static let usernameHeaderName:String = "Username"
    static let dateHeaderName:String = "Date"
    static let yearHeaderName:String = "Year"
    static let monthHeaderName:String = "Month"
    static let progressHeaderName:String = "Progress"
    
}
