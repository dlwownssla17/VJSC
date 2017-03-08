//
//  ScheduleItemType.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/1/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

enum ScheduleItemType {
    case None
    case Medication
    case Exercise
    case Eating
    case GlucoseLevel
    
    static func getJsonCode(type:ScheduleItemType)->String {
        if JSONProtocolNames.scheduleItemTypeMap[type] != nil {
            return JSONProtocolNames.scheduleItemTypeMap[type]!
        } else {
            return JSONProtocolNames.none
        }
    }
    
    static func getScheduleItemType(code:String)->ScheduleItemType {
        for (type, jsonCode) in JSONProtocolNames.scheduleItemTypeMap {
            if code == jsonCode {
                return type
            }
        }
        return .None
    }
    
    static func getSelectedScheduleItemType(item:String)->ScheduleItemType {
        for (type, itemType) in ScheduleItemTypeStringMap {
            if item == itemType {
                return type
            }
        }
        return .None
    }
    
    static let ScheduleItemTypeStringMap:Dictionary<ScheduleItemType, String> =
        [.Medication: "Meds",
         .Exercise: "Exercise",
         .Eating: "Food",
         .GlucoseLevel: "Blood Glucose"]
    
    static let ScheduleItemTypeOrderedStringMap:Array<String> = ["Meds", "Exercise", "Food", "Blood Glucose"]
}
