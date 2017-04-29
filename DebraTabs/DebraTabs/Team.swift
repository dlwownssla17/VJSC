//
//  Team.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 2/31/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

class Team {
    
    var isLeader:Bool = false
    var teamName:String = ""
    var teamID:Int = -1
    var maxTeamSize:Int = -1
    var teamSize:Int = -1
    var teamMembers:Array<TeamMember> = []
    var teamHistory:Array<TeamHistoryInfo> = []
    var competitonStatus:CompetitionStatus = .None
    var competitionID:Int = -1
    //var competitionInvitations:Array<CompetitionInvitation> = []
    var invitedUsers:Array<String> = []
    
    init() {
        
    }
    
    init(json:JSON) {
        self.isLeader = json[JSONProtocolNames.isLeaderHeaderName].boolValue
        self.teamName = json[JSONProtocolNames.teamNameHeaderName].stringValue
        self.teamID = json[JSONProtocolNames.teamIDHeaderName].intValue
        self.maxTeamSize = json[JSONProtocolNames.maxTeamSizeHeaderName].intValue
        self.teamSize = json[JSONProtocolNames.teanSizeHeaderName].intValue
        let teamMemberArray:Array<JSON> = json[JSONProtocolNames.teamMembersHeaderName].arrayValue
        for teamMember in teamMemberArray {
            let item = TeamMember(json: teamMember)
            self.teamMembers.append(item)
        }
        let teamHistoryArray:Array<JSON> = json[JSONProtocolNames.teamHistoryHeaderName].arrayValue
        for teamHistory in teamHistoryArray {
            let item = TeamHistoryInfo(json: teamHistory)
            self.teamHistory.append(item)
        }
        let competitionStatusCode:String = json[JSONProtocolNames.competitionStatusHeaderName].stringValue
        self.competitonStatus = CompetitionStatus.getCompetitionStatus(code: competitionStatusCode)
        self.competitionID = json[JSONProtocolNames.competitionIDHeaderName].intValue
//        let competitionInvitationArray:Array<JSON> = json[JSONProtocolNames.competitionInvitationsHeaderName].arrayValue
//        for competitionInvitation in competitionInvitationArray {
//            let item = CompetitionInvitation(json: competitionInvitation)
//            self.competitionInvitations.append(item)
//        }
        let invitedUsersArray:Array<JSON> = json[JSONProtocolNames.usersInvitedHeaderName].arrayValue
        for invitedUser in invitedUsersArray {
            let item:String = invitedUser[JSONProtocolNames.usernameHeaderName].stringValue
            self.invitedUsers.append(item)
        }
        
        
    }
}
