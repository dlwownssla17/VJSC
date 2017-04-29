//
//  AppSettings.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 2/10/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

class Settings {

    //static let serverURL:String = "http://130.91.135.160:8000/"
    //static let serverURL:String = "http://165.123.207.125:8000/"
    
    //static let serverURL:String = "http://158.130.193.230:8000/"
    //static let serverURL:String = "http://165.123.193.254:8000/"
    //static let serverURL:String = "http://165.123.207.125:8000/"
    //static let serverURL:String = "http://165.123.192.175:8000/";
    //static let serverURL:String = "http://10.215.170.83:8000/";
    //static let serverURL:String = "http://192.168.1.8:8000/"
    //static let serverURL:String = "http://158.130.196.51:8000/"
    //static let serverURL:String = "http://158.130.198.88:8000/"
    //static let serverURL:String = "http://158.130.193.228:8000/"
    //static let serverURL:String = "http://165.123.196.229:8000/"
    
    //static let serverURL:String = "http://165.123.200.168:8000/"
    
    //static let serverURL:String = "http://165.123.203.89:8000/"
    static let serverURL:String = "http://ec2-34-205-71-82.compute-1.amazonaws.com:8000/"
    //static let serverURL:String = "http://192.168.1.8:8000/"
    
    
    //static let serverURL:String = "http://130.91.134.209:8000/"
    
    static let dayViewRootURL:String = "test"
   
    static var usernameString: String = ""
    static var datecheckString: String = "2000-01-01"
  
    static let testUserID2:String = "123";
    
    static var displayAMPM:Bool = true
    static var askForGlucoseLevel:Bool = true
    static var notifyUser:Bool = true
    static var notifyNumDays:Int = 5
    
    static func getDayViewURL()->String {
        let relativeURL:String = "day/view/"
        return serverURL + relativeURL
    }
    
    static func getMonthViewURL()->String {
        let relativeURL:String = "month/view/"
        return serverURL + relativeURL
    }
    
    static func getCheckinViewURL()->String {
        let relativeURL:String = "checkin/view"
        return serverURL + relativeURL
    }
    
    static func getAddScheduleItemURL()->String {
        let relativeURL:String = "day/add/"
        return serverURL + relativeURL
    }
    
    static func getEditScheduleItemURL()->String {
        let relativeURL:String = "day/edit/"
        return serverURL + relativeURL
    }
    
    static func getRemoveScheduleItemURL()->String {
        let relativeURL:String = "day/remove/"
        return serverURL + relativeURL
    }
    
    static func getCheckinURL()->String {
        let relativeURL:String = "checkin/submit/"
        return serverURL + relativeURL
    }
    
    static func getRegistrationURL()->String {
        let relativeURL:String = "register/"
        return serverURL + relativeURL
    }
    
    static func getLoginURL()->String {
        let relativeURL:String = "login/"
        return serverURL + relativeURL
    }
    
    static func getUpdateScoresURL()->String {
        let relativeURL:String = "update-daily-scores/"
        return serverURL + relativeURL
    }
    
    static func getHomeScreenURL()->String {
        return serverURL
    }
    
    static func getCommunityHomeURL()->String {
        let relativeURL:String = "team/view/"
        return serverURL + relativeURL
    }
    
    static func getCreateTeamURL()->String {
        let relativeURL:String = "team/leader/create/"
        return serverURL + relativeURL
    }
    
    static func getRemoveTeamURL()->String {
        let relativeURL:String = "team/leader/remove/"
        return serverURL + relativeURL
    }
    
    static func getInviteMemberURL()->String {
        let relativeURL:String = "team/leader/invite/"
        return serverURL + relativeURL
    }
    
    static func getJoinTeamURL()->String {
        let relativeURL:String = "team/member/join/"
        return serverURL + relativeURL
    }
    
    static func getDeclineTeamInviteURL()->String {
        let relativeURL:String = "team/member/decline/"
        return serverURL + relativeURL
    }
    
    static func getKickoutMemberURL()->String {
        let relativeURL:String = "team/leader/dismiss/"
        return serverURL + relativeURL
    }
    
    static func getLeaveTeamURL()->String {
        let relativeURL:String = "team/member/leave/"
        return serverURL + relativeURL
    }
    
    static func getCompetitionViewURL()->String {
        let relativeURL:String = "competition/view"
        return serverURL + relativeURL
    }
    
    static func getCompetitionLeaveURL()->String {
        let relativeURL:String = "competition/leader/leave"
        return serverURL + relativeURL
    }
    
    static func getCompetitionCancelURL()->String {
        let relativeURL:String = "competition/leader/cancel"
        return serverURL + relativeURL
    }
    
    static func getCreateCompetitionURL()->String {
        let relativeURL:String = "competition/leader/create"
        return serverURL + relativeURL
    }
    
    static func getDeclineCompetitionURL()->String {
        let relativeURL:String = "competition/leader/decline"
        return serverURL + relativeURL
    }
    
    static func getJoinCompetitionURL()->String {
        let relativeURL:String = "competition/leader/join"
        return serverURL + relativeURL
    }
    
    static func getCompetitionInvitationsURL()->String {
        let relativeURL:String = "team/leader/competition-invitations-view"
        return serverURL + relativeURL
    }
    
    static func getFitBitURL(username:String)->String {
        let relativeURL:String = "http://ec2-34-205-71-82.compute-1.amazonaws.com:8080/debra/login?username=" + username
        return relativeURL
    }
    
    static func getFitBitStepsURL(username:String)->String {
        let relativeURL:String = "fitbit/get-steps"
        return serverURL + relativeURL
    }
}
