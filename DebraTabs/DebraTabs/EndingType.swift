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
    
    static func getJSONCode(type:EndingType)->Int {
        if JSONProtocolNames.endingTypeMap[type] != nil {
            return JSONProtocolNames.endingTypeMap[type]!
        } else {
            return JSONProtocolNames.never
        }
    }
    
    static func getEndingItemType(code:Int)->EndingType {
        for (type, jsonCode) in JSONProtocolNames.endingTypeMap {
            if code == jsonCode {
                return type
            }
        }
        return .Never
    }
}
