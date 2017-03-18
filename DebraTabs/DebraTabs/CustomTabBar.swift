//
//  CustomTabBarViewController.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 2/14/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import UIKit
import CoreData

class CustomTabBar: UITabBarController, CustomTabBarDataSource, CustomTabBarDelegate {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        print("HELLO")
        print(Settings.usernameString)
        
        // Do any additional setup after loading the view.
        self.tabBar.isHidden = true
        
//        let customTabBar = CustomTabs(frame: self.tabBar.frame)
//        self.view.addSubview(customTabBar)
//        customTabBar.datasource = self
//        customTabBar.setup()
//        customTabBar.delegate = self
        
        //let tabBarFrame = CGRect(x: 0 , y: 0, width: self.tabBar.frame.width * 0.8, height: self.tabBar.frame.height)
        let customTabBar = CustomTabs(frame: self.tabBar.frame)
        //let customTabBar = CustomTabs(frame: tabBarFrame)
        print(self.tabBar.frame.maxX)
        self.view.addSubview(customTabBar)
        customTabBar.datasource = self
        customTabBar.setup()
        customTabBar.delegate = self
        
        // START
        
        // END
        
        configureExpandingMenuButton()

    }
    
    fileprivate func configureExpandingMenuButton() {
        let menuButtonSize: CGSize = CGSize(width: 64.0, height: 64.0)
        let menuButton = ExpandingMenuButton(frame: CGRect(origin: CGPoint.zero, size: menuButtonSize), centerImage: UIImage(named: "chooser-button-tab")!, centerHighlightedImage: UIImage(named: "chooser-button-tab-highlighted")!)
        menuButton.center = CGPoint(x: self.view.bounds.width - 32.0, y: self.view.bounds.height - 32.0)
        self.view.addSubview(menuButton)
        
        func showAlert(_ title: String) {
            let alert = UIAlertController(title: title, message: nil, preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "OK", style: .cancel, handler: nil))
            self.present(alert, animated: true, completion: nil)
            
            if (title == "Log Out") {
                Settings.usernameString = ""
                Settings.datecheckString = "2000-01-01"
                
                // Initialize Fetch Request
                let fetchRequest = NSFetchRequest<NSFetchRequestResult>(entityName: "GeneralObject")
                
                // Configure Fetch Request
                fetchRequest.includesPropertyValues = false
                
                do {
                    let items = try dataLayer.getContext().fetch(fetchRequest) as! [NSManagedObject]
                    
                    for item in items {
                        dataLayer.getContext().delete(item)
                    }
                    
                    // Save Changes
                    try dataLayer.getContext().save()
                    
                } catch {
                    // Error Handling
                    // ...
                }
                
//                let moc = dataLayer.getContext()
//                let fetchRequest = NSFetchRequest<NSFetchRequestResult>(entityName: "GeneralObject")
//                
//                let result = try? moc.fetch(fetchRequest)
//                let resultData = result as! [NSManagedObject]
//                
//                for object in resultData {
//                    moc.delete(object)
//                }
                let appDelegate = UIApplication.shared.delegate as! AppDelegate
                appDelegate.resetAppToFirstController()
//                exit(0)
                
//                do {
//                    try moc.save()
//                    print("saved!")
//                } catch let error as NSError  {
//                    print("Could not save \(error), \(error.userInfo)")
//                } catch {
//                    
//                }

                
                
//                let fetchRequest = NSFetchRequest<NSFetchRequestResult>(entityName: "GeneralObject")
//                do {
//                    let searchResults = try dataLayer.getContext().fetch(fetchRequest)
//                    for object in searchResults {
//                        dataLayer.getContext().delete(object as! NSManagedObject)
//                        print("deleting here")
//                    }
//                    exit(0)
//                } catch {
//                    print("Error with request: \(error)")
//                }
                
            }
        }
        
        let item1 = ExpandingMenuItem(size: menuButtonSize, title: "Music", image: UIImage(named: "chooser-moment-icon-music")!, highlightedImage: UIImage(named: "chooser-moment-icon-place-highlighted")!, backgroundImage: UIImage(named: "chooser-moment-button"), backgroundHighlightedImage: UIImage(named: "chooser-moment-button-highlighted")) { () -> Void in
            showAlert("Music")
        }
        
        let item2 = ExpandingMenuItem(size: menuButtonSize, title: "Place", image: UIImage(named: "chooser-moment-icon-place")!, highlightedImage: UIImage(named: "chooser-moment-icon-place-highlighted")!, backgroundImage: UIImage(named: "chooser-moment-button"), backgroundHighlightedImage: UIImage(named: "chooser-moment-button-highlighted")) { () -> Void in
            showAlert("Place")
        }
        
        let item3 = ExpandingMenuItem(size: menuButtonSize, title: "Camera", image: UIImage(named: "chooser-moment-icon-camera")!, highlightedImage: UIImage(named: "chooser-moment-icon-camera-highlighted")!, backgroundImage: UIImage(named: "chooser-moment-button"), backgroundHighlightedImage: UIImage(named: "chooser-moment-button-highlighted")) { () -> Void in
            showAlert("Camera")
        }
        
        let item4 = ExpandingMenuItem(size: menuButtonSize, title: "Thought", image: UIImage(named: "chooser-moment-icon-thought")!, highlightedImage: UIImage(named: "chooser-moment-icon-thought-highlighted")!, backgroundImage: UIImage(named: "chooser-moment-button"), backgroundHighlightedImage: UIImage(named: "chooser-moment-button-highlighted")) { () -> Void in
            showAlert("Thought")
        }
        
        let item5 = ExpandingMenuItem(size: menuButtonSize, title: "Log Out", image: UIImage(named: "chooser-moment-icon-sleep")!, highlightedImage: UIImage(named: "chooser-moment-icon-sleep-highlighted")!, backgroundImage: UIImage(named: "chooser-moment-button"), backgroundHighlightedImage: UIImage(named: "chooser-moment-button-highlighted")) { () -> Void in
            showAlert("Log Out")
        }
        
        menuButton.addMenuItems([item1, item2, item3, item4, item5])
        
        menuButton.willPresentMenuItems = { (menu) -> Void in
            print("MenuItems will present.")
        }
        
        menuButton.didDismissMenuItems = { (menu) -> Void in
            print("MenuItems dismissed.")
        }
    }
    
    func tabBarItemsInCustomTabBar(tabBarView: CustomTabs) -> [UITabBarItem] {
        return tabBar.items!
    }
    
    func didSelectViewController(tabBarView: CustomTabs, atIndex index: Int) {
        self.selectedIndex = index
    }
    
}
