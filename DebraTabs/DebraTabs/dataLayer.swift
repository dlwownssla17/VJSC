//
//  DataLayer.swift
//  DebraTabs
//
//  Created by Chad Nachiappan on 1/16/17.
//  Copyright © 2017 Chad Nachiappan. All rights reserved.
//

import Foundation
import UIKit
import CoreData

class dataLayer {
    
    class func getContext () -> NSManagedObjectContext {
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        return appDelegate.persistentContainer.viewContext
    }
    
    class func storeTranscription(username: String) {
        let context = getContext()
        
        //retrieve the entity that we just created
        let entity =  NSEntityDescription.entity(forEntityName: "GeneralObject", in: context)
        
        let transc = NSManagedObject(entity: entity!, insertInto: context)
        
        //set the entity values
        transc.setValue(username, forKey: "username")
        
        //save the object
        do {
            try context.save()
            print("saved!")
            Settings.usernameString = username
        } catch let error as NSError  {
            print("Could not save \(error), \(error.userInfo)")
        } catch {
            
        }
    }
    
    class func storeDateTranscription(datecheck: String) {
        let context = getContext()
        
        //retrieve the entity that we just created
        let entity =  NSEntityDescription.entity(forEntityName: "GeneralObject", in: context)
        
        let transc = NSManagedObject(entity: entity!, insertInto: context)
        
        //set the entity values
        transc.setValue(datecheck, forKey: "datecheck")
        
        //save the object
        do {
            try context.save()
            print("saved date!")
            Settings.datecheckString = datecheck
        } catch let error as NSError  {
            print("Could not save \(error), \(error.userInfo)")
        } catch {
            
        }
    }
    
    class func getTranscriptions () -> [Any] {
        //create a fetch request, telling it about the entity
        let fetchRequest = NSFetchRequest<NSFetchRequestResult>(entityName: "GeneralObject")
        //let fetchRequest: NSFetchRequest<GeneralObject> = GeneralObject.fetchRequest()
        //        let fetchRequest =  NSFetchRequest<NSFetchRequestResult>(entityName: "GeneralObject")
        
        do {
            //go get the results
            let searchResults = try getContext().fetch(fetchRequest)
//            
//            //I like to check the size of the returned results!
//            print ("num of results = \(searchResults.count)")
//            
//            
            for trans in searchResults as! [NSManagedObject] {
                Settings.usernameString = trans.value(forKey: "username") as! String
              
                if ((trans.value(forKey: "datecheck")) != nil) {
                    Settings.datecheckString = trans.value(forKey: "datecheck") as! String
                }
                //get the Key Value pairs (although there may be a better way to do that...
                print("in datalyer: \(trans.value(forKey: "username"))")
                 print("in datalyer: \(trans.value(forKey: "datecheck"))")
            }
            
            return searchResults
        } catch {
            print("Error with request: \(error)")
        }
        return [""]
    }
}
