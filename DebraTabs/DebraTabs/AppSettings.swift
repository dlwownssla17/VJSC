//
//  AppSettings.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/10/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

class Settings {

    //static let serverURL:String = "http://130.91.135.160:8000/"
    static let serverURL:String = "http://165.123.207.125:8000/"
    //static let serverURL:String = "http://130.91.134.209:8000/"
    
    static let dayViewRootURL:String = "test"
   
    static var usernameString: String = ""
    static var datecheckString: String = ""
  
    static let testUserID2:String = "123";
    
    static var displayAMPM:Bool = true
    
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
}
