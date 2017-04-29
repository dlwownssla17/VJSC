//
//  CompetitionInvitation.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/1/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

class CompetitionInvitation {
    
    var competitionName:String = ""
    var competitionId:Int = -1
    var competitionStartDate:Date = Date()
    var competitionEndDate:Date = Date()
    var otherTeamName:String = ""
    var otherTeamLeader:String = ""
    var otherTeamColor:TeamColor = .Red
    
    init(json:JSON) {
        self.competitionName = json[JSONProtocolNames.competitionNameHeaderName].stringValue
        self.competitionId = json[JSONProtocolNames.competitionIDHeaderName].intValue
        let dateFormatter:DateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd"
        let competitonStartDateString:String = json[JSONProtocolNames.competitionStartDateHeaderName].stringValue
        self.competitionStartDate = dateFormatter.date(from: competitonStartDateString)!
        let competitionEndDateString:String = json[JSONProtocolNames.competitionEndDateHeaderName].stringValue
        self.competitionEndDate = dateFormatter.date(from: competitionEndDateString)!
        self.otherTeamName = json[JSONProtocolNames.otherTeamNameHeaderName].stringValue
        self.otherTeamLeader = json[JSONProtocolNames.otherTeamLeaderHeaderName].stringValue
        let teamColorCode:String = json[JSONProtocolNames.otherTeamColorHeaderName].stringValue
        self.otherTeamColor = TeamColor.getCompetitionStatus(code: teamColorCode)
    }
    
}
