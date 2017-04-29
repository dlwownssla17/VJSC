//
//  EndingType.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 2/1/17.
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
            return JSONProtocolNames.notRecurring
        }
    }
    
    static func getEndingItemType(code:Int)->EndingType {
        for (type, jsonCode) in JSONProtocolNames.endingTypeMap {
            if code == jsonCode {
                return type
            }
        }
        return .NotRecurring
    }
    
    static func getSelectedEndingItemType(item:String)->EndingType {
        for (type, itemString) in EndingTypeStringMap {
            if item == itemString {
                return type
            }
        }
        return .NotRecurring
    }
    
    static let EndingTypeStringMap:Dictionary<EndingType, String> =
        [.Never: "Ending Never",
         .AfterYOccurrences: "Ending After X Times",
         .OnT: "Ending On a Certain Date"]
    
    static let EndingTypeOrderedStringMap:Array<String> = ["Ending After X Times", "Ending On a Certain Date"]
    
    static let EndingTypeOrderedStringMapAll:Array<String> = ["Ending Never", "Ending After X Times", "Ending On a Certain Date"]
    
    static let AfterYOrruccencesOptions:Array<String> = ["1","2","3","4", "5", "6", "7", "8", "9", "10"]
}
