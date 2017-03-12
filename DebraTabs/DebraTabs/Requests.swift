//
//  Requests.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/8/17.
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
        dateFormatter.dateFormat = "hh:mm:ss"
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
        dateFormatter.dateFormat = "hh:mm:ss"
        let startInformation = dateFormatter.string(from: item.scheduleItemStart)
        
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
                JSONProtocolNames.scheduleItemDurationHeaderName: item.scheduleItemDuration
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
                    let scheduleItemObject = ScheduleItem(json: jsonObject)
                    scheduleItems.append(scheduleItemObject)
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
