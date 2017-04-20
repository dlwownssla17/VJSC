//
//  TeamColor.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 4/1/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

enum TeamColor {
    case Red
    case Blue
    
    static func getJSONCode(type:TeamColor)->String {
        if JSONProtocolNames.teamColorMap[type] != nil {
            return JSONProtocolNames.teamColorMap[type]!
        } else {
            return JSONProtocolNames.red
        }
    }
    
    static func getCompetitionStatus(code:String)->TeamColor {
        for (type, jsonCode) in JSONProtocolNames.teamColorMap {
            if code == jsonCode {
                return type
            }
        }
        return .Red
    }
    
    static func getColorString(type: TeamColor)->String {
        switch type {
        case .Red:
            return "Red"
        case .Blue:
            return "Blue"
        }
    }
}
