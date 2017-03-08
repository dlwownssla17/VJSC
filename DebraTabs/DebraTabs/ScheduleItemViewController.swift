//
//  ScheduleItemViewController.swift
//  DebraTabs
//
//  Created by Chad Nachiappan on 3/7/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import UIKit

class ScheduleItemViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    
    public var ObjectsArray = [ScheduleItem]()
    private var myTableView: UITableView!
    public var button: UIButton = UIButton()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        view.backgroundColor = .white
        
        let jsonString1 = "{" +
            "\"Language\": {" +
            "\"Field\":[" +
            "{" +
            "\"Number\":\"976\"," +
            "\"Name\":\"Test\"" +
            "}," +
            "{" +
            "\"Number\":\"977\"," +
            "\"Name\":\"Test\"" +
            "}" +
            "]" +
            "}" +
        "}"
        
        var act1 = ScheduleItem(jsonString: jsonString1)
        var act2 = ScheduleItem(jsonString: jsonString1)
        var act3 = ScheduleItem(jsonString: jsonString1)
        
        
        // For Testing purposes
        act1.scheduleItemTitle = "Run"
        act1.scheduleItemStart = Date() as NSDate
        
        act2.scheduleItemTitle = "Eat"
        act2.scheduleItemStart = Date() as NSDate
        
        act3.scheduleItemTitle = "Code"
        act3.scheduleItemStart = Date() as NSDate
        
        ObjectsArray.append(act1)
        ObjectsArray.append(act2)
        ObjectsArray.append(act3)
        
        let barHeight: CGFloat = UIApplication.shared.statusBarFrame.size.height
        let displayWidth: CGFloat = self.view.frame.width
        let displayHeight: CGFloat = self.view.frame.height
        
        myTableView = UITableView(frame: CGRect(x: 0, y: barHeight + 50, width: displayWidth, height: displayHeight - 200))//barHeight*1.5))
        print("yo")
        print(displayHeight)
        print(barHeight)
        myTableView.register(UITableViewCell.self, forCellReuseIdentifier: "MyCell")
        myTableView.dataSource = self
        myTableView.delegate = self
        self.view.addSubview(myTableView)
        
        // Buttons
        button = UIButton(frame: CGRect(x: displayWidth/2, y: displayHeight - 110, width: 100, height: 44))
        button.setTitle("Add Item", for: UIControlState.normal)
        button.setTitleColor(UIColor.blue, for: UIControlState.normal)
        button.backgroundColor = UIColor.clear
        button.layer.borderWidth = 1.0
        let borderAlpha : CGFloat = 0.7
        let cornerRadius : CGFloat = 5.0
        button.layer.borderColor = UIColor.blue.cgColor
        button.layer.cornerRadius = cornerRadius
        button.addTarget(self, action: #selector(addButtonTapped(_:)), for: .touchDown)
        self.view.addSubview(button)
        
        print("HELLO")
        JSONParser.testJSON()
    }
    
    // MARK: Button Action
    func addButtonTapped(_ button: UIButton) {
        button.transform = CGAffineTransform(scaleX: 0.1, y: 0.1)
        UIView.animate(withDuration: 2.0,
                       delay: 0,
                       usingSpringWithDamping: 0.2,
                       initialSpringVelocity: 6.0,
                       options: .allowUserInteraction,
                       animations: { [weak self] in
                        self?.button.transform = .identity
            },
                       completion: nil)
        
        print("Add Schedule Item Button pressed")
        
        
        let secondViewController:AddNewItem = AddNewItem()
        self.present(secondViewController, animated: true, completion: nil)
        
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return ObjectsArray.count
    }
    
    func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        return true
    }
    
    func tableView(_ tableView: UITableView, editActionsForRowAt: IndexPath) -> [UITableViewRowAction]? {
        
        let edit = UITableViewRowAction(style: .normal, title: "Edit") { action, index in
            print("edit button tapped")
        }
        edit.backgroundColor = .orange
        
        let delete = UITableViewRowAction(style: .normal, title: "Delete") { action, index in
            print("delete button tapped")
            // handle delete (by removing the data from your array and updating the tableview)
            //            // SEND DELETE COMMAND TO BACKEND
            //
            ////            myTableView.beginUpdates()
            ////            ObjectsArray.remove(at: indexPath.row)
            ////            myTableView.deleteRows(at: [indexPath], with: UITableViewRowAnimation.automatic)
            ////            myTableView.endUpdates()

        }
        delete.backgroundColor = .red
    
        
        return [delete, edit]
    }
    
//    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
//        if (editingStyle == UITableViewCellEditingStyle.delete) {
//            // handle delete (by removing the data from your array and updating the tableview)
//            // SEND DELETE COMMAND TO BACKEND
//            
////            myTableView.beginUpdates()
////            ObjectsArray.remove(at: indexPath.row)
////            myTableView.deleteRows(at: [indexPath], with: UITableViewRowAnimation.automatic)
////            myTableView.endUpdates()
//        }
//    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        //        let cell = tableView.dequeueReusableCell(withIdentifier: "MyCell", for: indexPath as IndexPath)
        //        cell.textLabel!.text = "\(Array[indexPath.row])"
        //        return cell
        
        
        let cell: UITableViewCell = UITableViewCell(style: UITableViewCellStyle.subtitle, reuseIdentifier: "MyTestCell")

        cell.textLabel!.text = "\(ObjectsArray[indexPath.row].scheduleItemTitle)"
        cell.detailTextLabel?.text = "\(ObjectsArray[indexPath.row].scheduleItemStart)"
        
        return cell
    }
    
}
