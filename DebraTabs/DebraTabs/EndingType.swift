//
//  EndingType.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/1/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

enum EndingType {
    case NotRecurring
    case Never
    case AfterYOccurrences
    case OnT
    
    func getJSONCode(type:EndingType)->Int {
        switch type {
        case .Never:
            return JSONProtocolNames.never
        case .AfterYOccurrences:
            return JSONProtocolNames.afterYOccurrences
        case .OnT:
            return JSONProtocolNames.onT
        default:
            return JSONProtocolNames.notRecurring
        }
    }
    
    func getScheduleItemType(code:Int)->EndingType {
        if code == JSONProtocolNames.never {
            return .Never
        } else if code == JSONProtocolNames.afterYOccurrences {
            return .AfterYOccurrences
        } else if code == JSONProtocolNames.onT {
            return .OnT
        } else {
            return .NotRecurring
        }
    }
}
