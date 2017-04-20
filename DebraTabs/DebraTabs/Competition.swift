//
//  Competition.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 4/8/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

class Competition {
    
    var competitionName:String = ""
    var competitionID:Int = 0
    var teamRedName:String = ""
    var teamRedID:Int = -1
    var teamRedScore:Int = -1
    var teamRedMembers:Array<TeamMember> = []
    var teamBlueName:String = ""
    var teamBlueID:Int = -1
    var teamBlueScore:Int = -1
    var teamBlueMembers:Array<TeamMember> = []
    var competitionStartDate:Date = Date()
    var competitionEndDate:Date = Date()
    var teamColor:TeamColor = .Red
    var showTeamMembers:Bool = false
    var competitionStatus:Bool = false
    
    init() {
        
    }
    
    init(json:JSON) {
        self.competitionName = json[JSONProtocolNames.competitionNameHeaderName].stringValue
        self.competitionID = json[JSONProtocolNames.competitionIDHeaderName].intValue
        let stats:JSON = json[JSONProtocolNames.statsHeaderName]
        self.teamRedName = stats[JSONProtocolNames.teamRedNameHeaderName].stringValue
        self.teamRedID = stats[JSONProtocolNames.teamRedIDHeaderName].intValue
        self.teamRedScore = stats[JSONProtocolNames.teamRedScoreHeaderName].intValue
        let membersRedList:Array<JSON> = stats[JSONProtocolNames.teamRedMemberScoresHeaderName].arrayValue
        for member in membersRedList {
            let item = TeamMember(json: member)
            self.teamRedMembers.append(item)
        }
        self.teamBlueName = stats[JSONProtocolNames.teamBlueNameHeaderName].stringValue
        self.teamBlueID = stats[JSONProtocolNames.teamBlueIDHeaderName].intValue
        self.teamBlueScore = stats[JSONProtocolNames.teamBlueScoreHeaderName].intValue
        let memberBlueList:Array<JSON> = stats[JSONProtocolNames.teamBlueMemberScoresHeaderName].arrayValue
        for member in memberBlueList {
            let item = TeamMember(json: member)
            self.teamBlueMembers.append(item)
        }
        let competitionStartDateString:String = json[JSONProtocolNames.competitionStartDateHeaderName].stringValue
        print("DATE STRING: \(competitionStartDateString)")
        let dateFormatter:DateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd"
        let timeZone = NSTimeZone(name: "GMT")
        dateFormatter.timeZone=timeZone as TimeZone!
        self.competitionStartDate = dateFormatter.date(from: competitionStartDateString)!
        let competitionEndDateString:String = json[JSONProtocolNames.competitionEndDateHeaderName].stringValue
        self.competitionEndDate = dateFormatter.date(from: competitionEndDateString)!
        let teamColorString = json[JSONProtocolNames.teamColorHeaderName].stringValue
        self.teamColor = TeamColor.getCompetitionStatus(code: teamColorString)
        self.showTeamMembers = json[JSONProtocolNames.showTeamMembersHeaderName].boolValue
        self.competitionStatus = json[JSONProtocolNames.competitionStatusHeaderName].boolValue
    }
    
}
