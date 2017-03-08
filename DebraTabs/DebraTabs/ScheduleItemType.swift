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
    case GlucoseLevel
    case Exercise
    case Eating
    
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
}
