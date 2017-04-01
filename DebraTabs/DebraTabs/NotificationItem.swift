//
//  NotificationItem.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/30/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

class NotificationItem {
    
    var id:String = ""
    var time:Date = Date()
    var name:String = ""
    var description:String = ""
    
    init() {
        
    }
    
    init(item:ScheduleItem) {
        self.id = String(item.itemID)
        self.time = item.scheduleItemStart
        self.name = item.scheduleItemTitle
        self.description = item.scheduleItemDescription
    }
    
    func isMatch(item:NotificationItem)->Bool {
        return self.id == item.id && self.time == item.time
    }
    
    func isUpcoming()->Bool {
        let now:Date = Date()
        return self.time > now
    }
}
