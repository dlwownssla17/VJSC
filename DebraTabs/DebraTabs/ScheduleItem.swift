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
    var endingType:Int = -1
    var endingValue:Int = -1
    var scheduleItemTitle:String = ""
    var scheduleItemDescription:String = ""
    var scheduleItemType:ScheduleItemType = .None
    var scheduleItemStart:NSDate = NSDate()
    var scheduleItemProgressType:Int = 0  // FIX
    var scheduleItemProgress:Int = 0  // Fix
    var scheduleItemScore:Int = 0
    var scheduleItemActive:Bool = false
    var scheduleItemColor:UIColor = UIColor.black
    
    init(jsonString: String) {
        let json = JSONParser.getJSONObjectFromString(jsonString: jsonString)
    }
    
}
