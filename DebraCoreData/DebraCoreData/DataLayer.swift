//
//  DataLayer.swift
//  DebraCoreData
//
//  Created by Chad Nachiappan on 1/16/17.
//  Copyright © 2017 Chad Nachiappan. All rights reserved.
//

import Foundation
import UIKit
import CoreData

class DataLayer {
    
    class func getContext () -> NSManagedObjectContext {
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        return appDelegate.persistentContainer.viewContext
    }
    
    class func storeTranscription(info: String, type: String) {
        let context = getContext()
        
        //retrieve the entity that we just created
        let entity =  NSEntityDescription.entity(forEntityName: "GeneralObject", in: context)
        
        let transc = NSManagedObject(entity: entity!, insertInto: context)
        
        //set the entity values
        transc.setValue(info, forKey: "info")
        transc.setValue(type, forKey: "type")
        
        //save the object
        do {
            try context.save()
            print("saved!")
        } catch let error as NSError  {
            print("Could not save \(error), \(error.userInfo)")
        } catch {
            
        }
    }
    
    class func getTranscriptions () {
        //create a fetch request, telling it about the entity
         let fetchRequest = NSFetchRequest<NSFetchRequestResult>(entityName: "GeneralObject")
        //let fetchRequest: NSFetchRequest<GeneralObject> = GeneralObject.fetchRequest()
//        let fetchRequest =  NSFetchRequest<NSFetchRequestResult>(entityName: "GeneralObject")
        
        do {
            //go get the results
            let searchResults = try getContext().fetch(fetchRequest)
            
            //I like to check the size of the returned results!
            print ("num of results = \(searchResults.count)")
            

            for trans in searchResults as! [NSManagedObject] {
                //get the Key Value pairs (although there may be a better way to do that...
                print("\(trans.value(forKey: "info"))")
                print("\(trans.value(forKey: "type"))")
            }
        } catch {
            print("Error with request: \(error)")
        }
    }
}
