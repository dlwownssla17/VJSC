//
//  Requests.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 2/8/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation
import Alamofire

class Requests {
    
    static func addScheduleItemJSON(item:ScheduleItem)->[String: Any] {
        
        let recurringType = RecurringType.getJsonCode(type: item.recurringType)
        let endingType = EndingType.getJSONCode(type: item.endingType)
        let scheduleItemType = ScheduleItemType.getJsonCode(type: item.scheduleItemType)
        let dateFormatter = DateFormatter()
        let timeZone = NSTimeZone(name: "GMT")
        dateFormatter.timeZone=timeZone as TimeZone!
        dateFormatter.dateFormat = "HH:mm:ss"
        let startInformation = dateFormatter.string(from: item.scheduleItemStart)
        let progressType = ProgressType.getJSONCode(type: item.scheduleItemProgressType)
        
        
//        let itemInformation: [String: Any] = [
//            JSONProtocolNames.scheduleItemsAddScheduleItemName: [
//                JSONProtocolNames.recurringTypeHeaderName: recurringType,
//                JSONProtocolNames.recurringValueHeaderName: item.recurringValue,
//                JSONProtocolNames.endingTypeHeaderName: endingType,
//                JSONProtocolNames.endingValueHeaderName: item.endingValue,
//                JSONProtocolNames.scheduleItemTitleHeaderName: item.scheduleItemTitle,
//                JSONProtocolNames.scheduleItemDescriptionHeaderName: item.scheduleItemDescription,
//                JSONProtocolNames.scheduleItemTypeHeaderName: scheduleItemType,
//                JSONProtocolNames.scheduleItemStartHeaderName: startInformation,
//                JSONProtocolNames.scheduleItemProgressTypeHeaderName: progressType,
//                JSONProtocolNames.scheduleItemDurationHeaderName: item.scheduleItemDuration
//            ]
//        ]
        
        let itemInformation: [String: Any] = [
                JSONProtocolNames.recurringTypeHeaderName: recurringType,
                JSONProtocolNames.recurringValueHeaderName: item.recurringValue,
                JSONProtocolNames.endingTypeHeaderName: endingType,
                JSONProtocolNames.endingValueHeaderName: item.endingValue,
                JSONProtocolNames.scheduleItemTitleHeaderName: item.scheduleItemTitle,
                JSONProtocolNames.scheduleItemDescriptionHeaderName: item.scheduleItemDescription,
                JSONProtocolNames.scheduleItemTypeHeaderName: scheduleItemType,
                JSONProtocolNames.scheduleItemStartHeaderName: startInformation,
                JSONProtocolNames.scheduleItemProgressTypeHeaderName: progressType,
                JSONProtocolNames.scheduleItemDurationHeaderName: item.scheduleItemDuration
        ]
        
        //print(itemInformation.rawString()!)
        
        return itemInformation
    }
    
    static func editScheduleItem(item:ScheduleItem)->[String: Any] {
        let scheduleItemType = ScheduleItemType.getJsonCode(type: item.scheduleItemType)
        let dateFormatter = DateFormatter()
        let timeZone = NSTimeZone(name: "GMT")
        dateFormatter.timeZone=timeZone as TimeZone!
        dateFormatter.dateFormat = "HH:mm:ss"
        print(item.scheduleItemStart)
        print("CANDY")
        let startInformation = dateFormatter.string(from: item.scheduleItemStart)
        print(startInformation)
        
//        let itemInformation: [String: Any] = [
//            JSONProtocolNames.scheduleItemsEditScheduleItemName: [
//                JSONProtocolNames.scheduleItemIDHeaderName: item.itemID,
//                JSONProtocolNames.scheduleItemTypeHeaderName: scheduleItemType,
//                JSONProtocolNames.scheduleItemTitleHeaderName: item.scheduleItemTitle,
//                JSONProtocolNames.scheduleItemDescriptionHeaderName: item.scheduleItemDescription,
//                JSONProtocolNames.scheduleItemStartHeaderName: startInformation,
//                JSONProtocolNames.scheduleItemDurationHeaderName: item.scheduleItemDuration
//            ]
//        ]
        
        let itemInformation: [String: Any] = [
                JSONProtocolNames.scheduleItemIDHeaderName: item.itemID,
                //JSONProtocolNames.scheduleItemTypeHeaderName: scheduleItemType,
                JSONProtocolNames.scheduleItemTitleHeaderName: item.scheduleItemTitle,
                JSONProtocolNames.scheduleItemDescriptionHeaderName: item.scheduleItemDescription,
                JSONProtocolNames.scheduleItemStartHeaderName: startInformation,
                JSONProtocolNames.scheduleItemDurationHeaderName: item.scheduleItemDuration,
                JSONProtocolNames.recurringIDHeaderName: item.recurringID
        ]
        
        return itemInformation
    }
    
    static func checkInSubmitItem(id:Int, progress:Double)->[String: Any] {
        let itemInformation: [String: Any] = [
            JSONProtocolNames.scheduleItemIDHeaderName: id,
            JSONProtocolNames.progressHeaderName: progress
        ]
        return itemInformation
    }
    
    static func removeScheduleItem(item:ScheduleItem, removeAllRecurring:Bool)->[String: Any] {
        var recurring:Int = -1
        if removeAllRecurring {
            recurring = item.recurringID
        }
//        let itemInformation: [String: Any] = [
//            JSONProtocolNames.scheduleItemsRemoveScheduleItemName: [
//                JSONProtocolNames.scheduleItemIDHeaderName: item.itemID,
//                JSONProtocolNames.recurringIDHeaderName: recurring
//            ]
//        ]
        
        let itemInformation: [String: Any] = [
            JSONProtocolNames.scheduleItemIDHeaderName: item.itemID,
            JSONProtocolNames.recurringIDHeaderName: recurring
        ]
        
        return itemInformation
    }
    
    static func loginItem(password:String)->[String: Any] {
        return [JSONProtocolNames.passwordHeaderName: password]
    }
    
    static func kickOutUser(username:String, id:Int)->[String:Any] {
        let itemInformation: [String: Any] = [
            JSONProtocolNames.memberUsernameHeaderName: username,
            JSONProtocolNames.teamIDHeaderName: id
        ]
        return itemInformation
    }
    
    static func removeTeam(id:Int)->[String:Any] {
        let itemInformation: [String: Any] = [
            JSONProtocolNames.teamIDHeaderName: id
        ]
        return itemInformation
    }
    
    static func joinTeam(id:Int)->[String:Any] {
        let itemInformation: [String: Any] = [
            JSONProtocolNames.teamIDHeaderName: id
        ]
        return itemInformation
    }
    
    static func declineTeamInvite(id:Int)->[String:Any] {
        let itemInformation: [String: Any] = [
            JSONProtocolNames.teamIDHeaderName: id
        ]
        return itemInformation
    }
    
    static func inviteUserToTeam(username:String, id:Int)->[String:Any] {
        let itemInformation: [String: Any] = [
            JSONProtocolNames.memberUsernameHeaderName: username,
            JSONProtocolNames.teamIDHeaderName: id
        ]
        return itemInformation
    }
    
    static func createNewTeam(name:String, size:Int)->[String:Any] {
        let itemInformation: [String: Any] = [
            JSONProtocolNames.teamNameHeaderName: name,
            JSONProtocolNames.maxTeamSizeHeaderName: size
        ]
        return itemInformation
    }
    
    static func leaveTeam(id:Int)->[String:Any] {
        let itemInformation: [String: Any] = [
            JSONProtocolNames.teamIDHeaderName: id
        ]
        return itemInformation
    }
    
    static func leaveCompetition(id:Int)->[String:Any] {
        let itemInformation: [String: Any] = [
            JSONProtocolNames.teamIDHeaderName: id
        ]
        return itemInformation
    }
    
    static func cancelCompetition(teamId:Int, competitionId:Int)->[String:Any] {
        let itemInformation: [String: Any] = [
            JSONProtocolNames.teamIDHeaderName: teamId,
            JSONProtocolNames.competitionIDHeaderName: competitionId
        ]
        return itemInformation
    }
    
    static func declineCompetition(teamId:Int, competitionId:Int)->[String:Any] {
        let itemInformation: [String: Any] = [
            JSONProtocolNames.teamIDHeaderName: teamId,
            JSONProtocolNames.competitionIDHeaderName: competitionId
        ]
        return itemInformation
    }
    
    static func joinCompetition(teamId:Int, competitionId:Int)->[String:Any] {
        let itemInformation: [String: Any] = [
            JSONProtocolNames.teamIDHeaderName: teamId,
            JSONProtocolNames.competitionIDHeaderName: competitionId
        ]
        return itemInformation
    }
    
    static func createCompetition(teamId:Int, competitionName:String, teamName:String, start:String, end:String, color:String, show:Bool)->[String:Any] {
        let itemInformation: [String: Any] = [
            JSONProtocolNames.teamIDHeaderName: teamId,
            JSONProtocolNames.competitionNameHeaderName: competitionName,
            JSONProtocolNames.competitionStartDateHeaderName: start,
            JSONProtocolNames.competitionEndDateHeaderName: end,
            JSONProtocolNames.teamColorHeaderName: color,
            JSONProtocolNames.showTeamMembersHeaderName: show,
            JSONProtocolNames.otherTeamNameHeaderName: teamName
        ]
        return itemInformation
    }
    
    static func competitionInvitation(teamId:Int)->[String:Any] {
        let itemInformation: [String: Any] = [
            JSONProtocolNames.teamIDHeaderName: teamId
        ]
        return itemInformation
    }
    
    static func getDayViewRoot()->Array<ScheduleItem> {
//        Alamofire.request("http://130.91.134.209:8000/test").responseJSON { response in
//            print("START")
//            print(response.request)  // original URL request
//            print(response.response) // HTTP URL response
//            print(response.data)     // server data
//            print(response.result)   // result of response serialization
//            print("END")
//            
//            
//            //print(type(of: (response.result.value!)))
//            let test = (((response.result.value!) as AnyObject).allValues)[0]
//            print(type(of: test))
//            //let parsedJSON = JSONParser.getJSONObjectFromString(jsonString: JSON!)
//            print("TEST CAT")
//            //print(parsedJSON)
//        }
        
        Alamofire.request("http://130.91.134.209:8000/test", method: .get).validate().responseJSON { response in
            switch response.result {
            case .success(let data):
                let json = JSON(data)
                print(json.count)
                print("TestCAT")
                let jsonObjectList = json[JSONProtocolNames.scheduleItemsListResponseName].arrayValue
                var scheduleItems:Array<ScheduleItem> = []
                for jsonObject in jsonObjectList {
                    //let scheduleItemObject = ScheduleItem(json: jsonObject)
                    //scheduleItems.append(scheduleItemObject)
                }
                //return scheduleItems
            case .failure(let error):
                print("Request failed with error: \(error)")
                //return []
            }
        }
        return []
    }
}
