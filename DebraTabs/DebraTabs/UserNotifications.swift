//
//  UserNotifications.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 2/30/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation
import Alamofire
import UserNotifications
import UserNotificationsUI //framework to customize the notification

class UserNotifications {
    
    var allItems:Array<NotificationItem> = []
    
    func getNotificationTime(time:Date)->Date {
        let calendar = Calendar.current
        let offset:Int = 30
        let notificationTime:Date = calendar.date(byAdding: .minute, value: -1 * offset, to: time)!
        return notificationTime
    }
    
    func addNotification(item:NotificationItem, view:UNUserNotificationCenterDelegate) {
        let content = UNMutableNotificationContent()
        content.title = item.name
        content.body = item.description
        content.subtitle = "In 30 minutes"
        content.userInfo["time"] = item.time
        content.sound = UNNotificationSound.default()
        
        let notificationTime:Date = self.getNotificationTime(time: item.time)
        let triggerDate = Calendar.current.dateComponents([.year,.month,.day,.hour,.minute,.second,], from: notificationTime)
        let trigger = UNCalendarNotificationTrigger(dateMatching: triggerDate, repeats: false)
        //let trigger = UNTimeIntervalNotificationTrigger.init(timeInterval: 5.0, repeats: false)
        let request = UNNotificationRequest(identifier:item.id, content: content, trigger: trigger)
        
        UNUserNotificationCenter.current().delegate = view
        UNUserNotificationCenter.current().add(request){(error) in
            
            if (error != nil){
                
                print(error?.localizedDescription)
            } else {
                //print("ADDED NOTIFICATION FOR: \(item.time)")
            }
        }
        print("ADDED NOTIFICATION FOR: \(item.time)")
    }
    
    func removeNotification(item:NotificationItem) {
        let center = UNUserNotificationCenter.current()
        center.removePendingNotificationRequests(withIdentifiers: [item.id])
        print("REMOVED NOTIFICATION FOR: \(item.time)")
    }
    
    func updateNotifications(view:UNUserNotificationCenterDelegate) {
        print("UPDATING NOTIFICATIONS")
        self.updateNotificationsStep(step: Settings.notifyNumDays, view:view)
    }
    
    func checkWithExistingItem(currentNotifications:Array<NotificationItem>, view:UNUserNotificationCenterDelegate) {
        print("CURRENT ITEMS: \(currentNotifications.count)")
        
        var notificationsToRemove:Array<NotificationItem> = []
        for existingItem in currentNotifications {
            var itemExists:Bool = false
            for currentItem in self.allItems {
                if currentItem.isMatch(item: existingItem) {
                    itemExists = true
                }
            }
            if !itemExists {
                notificationsToRemove.append(existingItem)
            }
        }
        
        var notificationsToAdd:Array<NotificationItem> = []
        for currentItem in self.allItems {
            var itemExists:Bool = false
            for existingItem in currentNotifications {
                if currentItem.isMatch(item: existingItem) {
                    itemExists = true
                }
            }
            if !itemExists {
                notificationsToAdd.append(currentItem)
            }
        }
        
        print("ITEMS TO ADD: \(notificationsToAdd.count)")
        
        for item in notificationsToAdd {
            self.addNotification(item: item, view:view)
        }
        
        for item in notificationsToRemove {
            self.removeNotification(item: item)
        }
    }
    
    func processItems(view:UNUserNotificationCenterDelegate) {
        var currentNotifications:Array<NotificationItem> = []
        let center = UNUserNotificationCenter.current()
        center.getPendingNotificationRequests(completionHandler: { requests in
            for request in requests {
                let item:NotificationItem = NotificationItem()
                item.id = request.identifier
                item.time = request.content.userInfo["time"] as! Date
                currentNotifications.append(item)
                print("EXISTING NOTIFICATION: \(item.id)")
            }
            self.checkWithExistingItem(currentNotifications: currentNotifications, view: view)
        })
    }
    
    func updateNotificationsStep(step:Int, view:UNUserNotificationCenterDelegate) {
        //var currentItems:Array<NotificationItem> = []
        let currentDayInfo:CurrentDayInfo = CurrentDayInfo()
        for j in 1..<step {
            currentDayInfo.setToTomorrow()
        }
        let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString, JSONProtocolNames.dateHeaderName: currentDayInfo.currentDayString]
        Alamofire.request(Settings.getDayViewURL(), method: .get, headers: headers).validate().responseJSON { response in
            switch response.result {
            case .success(let data):
                let json = JSON(data)
                let jsonObjectList = json[JSONProtocolNames.scheduleItemsListResponseName].arrayValue
                //print(jsonObjectList)
                for jsonObject in jsonObjectList {
                    let scheduleItemObject = ScheduleItem(json: jsonObject, itemDay: currentDayInfo.currentDayString)
                    let notification:NotificationItem = NotificationItem(item: scheduleItemObject)
                    self.allItems.append(notification)
                }
                print("GOT: \(currentDayInfo.currentDayString)")
                if step == 1 {
                    self.processItems(view: view)
                } else {
                    self.updateNotificationsStep(step:step - 1, view:view)
                }
            //return scheduleItems
            case .failure(let error):
                print("NOTIFICATION Request failed with error: \(error)")
            }
            
        }
        
    }
}
