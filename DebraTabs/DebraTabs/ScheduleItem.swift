//
//  ScheduleItem.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/1/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation
import UIKit

class ScheduleItem {
    
    var itemID:Int = -1
    var recurringID:Int = -1
    var recurringType:RecurringType = .NotRecurring
    var recurringValue:Array<Int> = []
    var endingType:EndingType = .Never
    var endingValue:String = ""
    var scheduleItemTitle:String = ""
    var scheduleItemDescription:String = ""
    var scheduleItemType:ScheduleItemType = .None
    var scheduleItemStart:Date = Date()
    var scheduleItemProgressType:ProgressType = .Boolean
    var scheduleItemDuration:Int = 0
    var scheduleItemProgress:Double = 0
    var scheduleItemScore:Int = 0
    var scheduleItemActive:Bool = false
    var scheduleItemColor:UIColor = UIColor.black
    var scheduleItemModifiable:Bool = false
    
    init(jsonString: String) {
        //let json = JSONParser.getJSONObjectFromString(jsonString: jsonString)
    }
    
    init() {
        
    }
    
    init(json: JSON) {
        itemID = json[JSONProtocolNames.scheduleItemIDHeaderName].intValue
        recurringID = json[JSONProtocolNames.recurringIDHeaderName].intValue
        let recurringTypeCode = json[JSONProtocolNames.recurringTypeHeaderName].intValue
        recurringType = RecurringType.getRecurringItemType(code: recurringTypeCode)
        let tempArray = json[JSONProtocolNames.recurringValueHeaderName].arrayValue
        var recurringValueArray:Array<Int> = []
        for val in tempArray {
            recurringValueArray.append(val.intValue)
        }
        recurringValue = recurringValueArray
        let endingTypeCode = json[JSONProtocolNames.endingTypeHeaderName].intValue
        endingType = EndingType.getEndingItemType(code: endingTypeCode)
        endingValue = json[JSONProtocolNames.endingValueHeaderName].stringValue
        scheduleItemTitle = json[JSONProtocolNames.scheduleItemTitleHeaderName].stringValue
        scheduleItemDescription = json[JSONProtocolNames.scheduleItemDescriptionHeaderName].stringValue
        let scheduleItemTypeCode = json[JSONProtocolNames.scheduleItemTypeHeaderName].stringValue
        scheduleItemType = ScheduleItemType.getScheduleItemType(code: scheduleItemTypeCode)
        let scheduleItemStartCode = json[JSONProtocolNames.scheduleItemStartHeaderName].stringValue
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "k:mm:ss"
        let timeZone = NSTimeZone(name: "GMT")
        dateFormatter.timeZone=timeZone as TimeZone!
        scheduleItemStart = dateFormatter.date(from: scheduleItemStartCode)!
        let progressTypeCode = json[JSONProtocolNames.scheduleItemProgressTypeHeaderName].stringValue
        scheduleItemProgressType = ProgressType.getProgressType(code: progressTypeCode)
        scheduleItemDuration = json[JSONProtocolNames.scheduleItemDurationHeaderName].intValue
        scheduleItemProgress = json[JSONProtocolNames.scheduleItemProgressHeaderName].doubleValue
        scheduleItemScore = json[JSONProtocolNames.scheduleItemScoreHeaderName].intValue
        scheduleItemActive = json[JSONProtocolNames.scheduleItemActiveHeaderName].boolValue
        var scheduleItemColorArray:Array<Int> = []
        let scheduleItemColorValues = json[JSONProtocolNames.scheduleItemColorHeaderName].arrayValue
        for val in scheduleItemColorValues {
            scheduleItemColorArray.append(val.intValue)
        }
        scheduleItemColor = UIColor(red: CGFloat(scheduleItemColorArray[0]) / 255.0, green: CGFloat(scheduleItemColorArray[1]) / 255.0, blue: CGFloat(scheduleItemColorArray[2]) / 255.0, alpha: CGFloat(scheduleItemColorArray[3]) / 255.0)
        scheduleItemModifiable = json[JSONProtocolNames.scheduleItemModifiableHeaderName].boolValue
    }
    
}
