//
//  TeamMember.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/31/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

class TeamMember {
    
    var username:String = ""
    var userScore:Int = -1
    var todayScoreSoFar:Int = -1
    var competitionScore:Int = -1
    var inTeamSince:Date = Date()
    var isLeader:Bool = false
    
    init(json:JSON) {
        self.username = json[JSONProtocolNames.usernameHeaderName].stringValue
        self.userScore = json[JSONProtocolNames.userScoreHeaderName].intValue
        self.todayScoreSoFar = json[JSONProtocolNames.todayScoreSoFarHeaderName].intValue
        self.competitionScore = json[JSONProtocolNames.userCompetitionScoreHeaderName].intValue
        let dateString:String = json[JSONProtocolNames.inTeamSinceHeaderName].stringValue
        let dateFormatter:DateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
        let timeZone = NSTimeZone(name: "GMT")
        dateFormatter.timeZone=timeZone as TimeZone!
        if (dateFormatter.date(from: dateString) != nil) {
            self.inTeamSince = dateFormatter.date(from: dateString)!
        }
        self.isLeader = json[JSONProtocolNames.isLeaderHeaderName].boolValue
        
    }
    
}
