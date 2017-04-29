//
//  TeamHistory.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/5/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

class TeamHistoryInfo {
    
    var competitionName:String = ""
    var competitionResult:CompetitionResult = .Won
    var teamRedName:String = ""
    var teamRedScore:Int = -1
    var teamRedLeft:Bool = false
    var teamBlueName:String = ""
    var teamBlueScore:Int = -1
    var teamBlueLeft:Bool = false
    var competitionStartDate:Date = Date()
    var competitionEndDate:Date = Date()
    
    init(json:JSON) {
        self.competitionName = json[JSONProtocolNames.competitionNameHeaderName].stringValue
        let competitionResultCode = json[JSONProtocolNames.competitionResultHeaderName].stringValue
        self.competitionResult = CompetitionResult.getCompetitionResultStatus(code: competitionResultCode)
        
        let statObject:JSON = json[JSONProtocolNames.statsHeaderName]
        self.teamRedName = statObject[JSONProtocolNames.teamRedNameHeaderName].stringValue
        self.teamRedScore = statObject[JSONProtocolNames.teamRedScoreHeaderName].intValue
        self.teamRedLeft = statObject[JSONProtocolNames.teamRedLeftHeaderName].boolValue
        self.teamBlueName = statObject[JSONProtocolNames.teamBlueNameHeaderName].stringValue
        self.teamBlueScore = statObject[JSONProtocolNames.teamBlueScoreHeaderName].intValue
        self.teamBlueLeft = statObject[JSONProtocolNames.teamBlueLeftHeaderName].boolValue
        
        let dateFormatter:DateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd"
        let timeZone = NSTimeZone(name: "GMT")
        dateFormatter.timeZone=timeZone as TimeZone!
        let dateStartString:String = json[JSONProtocolNames.competitionStartDateHeaderName].stringValue
        self.competitionStartDate = dateFormatter.date(from: dateStartString)!
        let dateEndString:String = json[JSONProtocolNames.competitionEndDateHeaderName].stringValue
        self.competitionEndDate = dateFormatter.date(from: dateEndString)!
        
    }
    
}
