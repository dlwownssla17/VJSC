//
//  ProgressType.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/7/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

enum ProgressType {

    case Boolean
    case Percentage
    
    static func getJSONCode(type:ProgressType)->String {
        if JSONProtocolNames.progressTypeMap[type] != nil {
            return JSONProtocolNames.progressTypeMap[type]!
        } else {
            return JSONProtocolNames.boolean
        }
    }
    
    static func getProgressType(code:String)->ProgressType {
        for (type, jsonCode) in JSONProtocolNames.progressTypeMap {
            if code == jsonCode {
                return type
            }
        }
        return .Boolean
    }
}
