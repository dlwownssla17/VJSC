//
//  AppSettings.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/10/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

class Settings {
    static let serverURL:String = "http://165.123.207.125:8000/"
    // 165.123.207.125
    // 130.91.135.160
    
    static let dayViewRootURL:String = "test"
   
    static var usernameString: String = ""
    static var datecheckString: String = ""
  
    static let testUserID2:String = "123";
    
    static func getDayViewURL(userID:String, day:String)->String {
        let relativeURL:String = String(userID) + "/day/view/" + day
        return serverURL + relativeURL
    }
    
    static func getMonthViewURL(userID:String, month:Int, year:Int)->String {
        let relativeURL:String = String(userID) + "/month/view/" + String(year) + "/" + String(month)
        return serverURL + relativeURL
    }
    
    static func getCheckinViewURL(userID:String)->String {
        let relativeURL:String = String(userID) + "/checkin/view"
        return serverURL + relativeURL
    }
    
    static func getAddScheduleItemURL(userID:String, date:String)->String {
        let relativeURL:String = String(userID) + "/day/add/" + date
        return serverURL + relativeURL
    }
    
    static func getEditScheduleItemURL(userID:String, date:String)->String {
        let relativeURL:String = String(userID) + "/day/edit/" + date
        return serverURL + relativeURL
    }
    
    static func getRemoveScheduleItemURL(userID:String, date:String)->String {
        let relativeURL:String = String(userID) + "/day/remove/" + date
        return serverURL + relativeURL
    }
    
    static func getCheckinURL(userID:String, itemID:Int)->String {
        let relativeURL:String = String(userID) + "/checkin/" + String(itemID)
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
