//
//  Requests.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/8/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

class Requests {
    
    static func addScheduleItemJSON(item:ScheduleItem)->JSON {
        
        let recurringType = RecurringType.getJsonCode(type: item.recurringType)
        let endingType = EndingType.getJSONCode(type: item.endingType)
        let scheduleItemType = ScheduleItemType.getJsonCode(type: item.scheduleItemType)
        let dateFormatter = DateFormatter()
        let timeZone = NSTimeZone(name: "GMT")
        dateFormatter.timeZone=timeZone as TimeZone!
        dateFormatter.dateFormat = "hh:mm:ss"
        let startInformation = dateFormatter.string(from: item.scheduleItemStart)
        let progressType = ProgressType.getJSONCode(type: item.scheduleItemProgressType)
        
        
        let itemInformation: JSON = [
            JSONProtocolNames.scheduleItemsAddScheduleItemName: [
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
        ]
        
        print(itemInformation.rawString()!)
        
        return itemInformation
    }
    
    static func editScheduleItem(item:ScheduleItem)->JSON {
        let scheduleItemType = ScheduleItemType.getJsonCode(type: item.scheduleItemType)
        let dateFormatter = DateFormatter()
        let timeZone = NSTimeZone(name: "GMT")
        dateFormatter.timeZone=timeZone as TimeZone!
        dateFormatter.dateFormat = "hh:mm:ss"
        let startInformation = dateFormatter.string(from: item.scheduleItemStart)
        
        let itemInformation: JSON = [
            JSONProtocolNames.scheduleItemsEditScheduleItemName: [
                JSONProtocolNames.scheduleItemIDHeaderName: item.itemID,
                JSONProtocolNames.scheduleItemTypeHeaderName: scheduleItemType,
                JSONProtocolNames.scheduleItemTitleHeaderName: item.scheduleItemTitle,
                JSONProtocolNames.scheduleItemDescriptionHeaderName: item.scheduleItemDescription,
                JSONProtocolNames.scheduleItemStartHeaderName: startInformation,
                JSONProtocolNames.scheduleItemDurationHeaderName: item.scheduleItemDuration
            ]
        ]
        
        return itemInformation
    }
    
}
