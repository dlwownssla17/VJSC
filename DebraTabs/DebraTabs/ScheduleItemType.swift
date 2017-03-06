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
    
    func getJsonCode(type:ScheduleItemType)->String {
        switch type {
        case .Medication:
            return JSONProtocolNames.medication
        case .Exercise:
            return JSONProtocolNames.exercise
        case .GlucoseLevel:
            return JSONProtocolNames.glucoseLevel
        case .Eating:
            return JSONProtocolNames.eating
        default:
            return JSONProtocolNames.none
        }
    }
    
    func getScheduleItemType(code:String)->ScheduleItemType {
        if code == JSONProtocolNames.medication {
            return .Medication
        } else if code == JSONProtocolNames.exercise {
            return .Exercise
        } else if code == JSONProtocolNames.glucoseLevel {
            return .GlucoseLevel
        } else if code == JSONProtocolNames.eating {
            return .Eating
        } else {
            return .None
        }
    }
}
