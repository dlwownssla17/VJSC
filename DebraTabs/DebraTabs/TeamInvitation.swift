//
//  TeamInvitation.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/31/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

class TeamInvitation {
    
    var teamName:String = ""
    var teamID:Int = -1
    var teamLeader:String = ""
    var teamCreated:Date = Date()
    
    init(json:JSON) {
        self.teamName = json[JSONProtocolNames.teamNameHeaderName].stringValue
        self.teamID = json[JSONProtocolNames.teamIDHeaderName].intValue
        self.teamLeader = json[JSONProtocolNames.teamLeaderHeaderName].stringValue
        let dateString:String = json[JSONProtocolNames.teamCreatedHeaderName].stringValue
        let dateFormatter:DateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
        let timeZone = NSTimeZone(name: "GMT")
        dateFormatter.timeZone=timeZone as TimeZone!
        self.teamCreated = dateFormatter.date(from: dateString)!
    }
    
}
