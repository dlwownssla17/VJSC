//
//  CompetitionStatus.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/31/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

enum CompetitionStatus {
    case None
    case Pending
    case Active
    
    static func getJSONCode(type:CompetitionStatus)->String {
        if JSONProtocolNames.competitionStatusMap[type] != nil {
            return JSONProtocolNames.competitionStatusMap[type]!
        } else {
            return JSONProtocolNames.noneCompetition
        }
    }
    
    static func getCompetitionStatus(code:String)->CompetitionStatus {
        for (type, jsonCode) in JSONProtocolNames.competitionStatusMap {
            if code == jsonCode {
                return type
            }
        }
        return .None
    }
    
}
