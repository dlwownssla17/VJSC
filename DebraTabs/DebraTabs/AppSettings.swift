//
//  AppSettings.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/10/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

class Settings {
    static let serverURL:String = "http://130.91.134.209:8000/"
    
    static let dayViewRootURL:String = "test"
   
    static var usernameString: String = ""
  
    static let testUserID:Int = 123;
    
    static func getDayViewURL(userID:Int, day:String)->String {
        let relativeURL:String = String(userID) + "/day/view/" + day
        return serverURL + relativeURL
    }
    
    static func getMonthViewURL(userID:Int, month:Int, year:Int)->String {
        let relativeURL:String = String(userID) + "/month/view/" + String(year) + "/" + String(month)
        return serverURL + relativeURL
    }
    
    static func getCheckinViewURL(userID:Int)->String {
        let relativeURL:String = String(userID) + "/checkin/view"
        return serverURL + relativeURL
    }
    
    static func getAddScheduleItemURL(userID:Int, date:String)->String {
        let relativeURL:String = String(userID) + "/day/add/" + date
        return serverURL + relativeURL
    }
    
    static func getEditScheduleItemURL(userID:Int, date:String)->String {
        let relativeURL:String = String(userID) + "/day/edit/" + date
        return serverURL + relativeURL
    }
    
    static func getRemoveScheduleItemURL(userID:Int, date:String)->String {
        let relativeURL:String = String(userID) + "/day/remove/" + date
        return serverURL + relativeURL
    }
    
    static func getCheckinURL(userID:Int, itemID:Int)->String {
        let relativeURL:String = String(userID) + "/checkin/" + String(itemID)
        return serverURL + relativeURL
    }
}