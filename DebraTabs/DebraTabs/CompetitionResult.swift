//
//  CompetitionResult.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/1/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

enum CompetitionResult {
    case Won
    case Tied
    case Lost
    
    static func getJSONCode(type:CompetitionResult)->String {
        if JSONProtocolNames.competitionResult[type] != nil {
            return JSONProtocolNames.competitionResult[type]!
        } else {
            return JSONProtocolNames.red
        }
    }
    
    static func getCompetitionResultStatus(code:String)->CompetitionResult {
        for (type, jsonCode) in JSONProtocolNames.competitionResult {
            if code == jsonCode {
                return type
            }
        }
        return .Won
    }
    
}
