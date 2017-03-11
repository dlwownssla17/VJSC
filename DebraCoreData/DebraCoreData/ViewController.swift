//
//  ViewController.swift
//  DebraCoreData
//
//  Created by Chad Nachiappan on 1/16/17.
//  Copyright © 2017 Chad Nachiappan. All rights reserved.
//

import UIKit
import CoreData

class ViewController: UIViewController {
    
    var textFiled: UITextField!
    var textFiled2: UITextField!

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        
        textFiled = UITextField(frame: CGRect(x: 50, y: 100, width: 200, height: 100))
        textFiled.backgroundColor = UIColor.red
        textFiled.borderStyle = UITextBorderStyle.line
        self.view.addSubview(textFiled)
        
        
        textFiled2 = UITextField(frame: CGRect(x: 50, y: 200, width: 200, height: 100))
        textFiled2.borderStyle = UITextBorderStyle.line
        self.view.addSubview(textFiled2)
        
        let button = UIButton(type: .system) // let preferred over var here
        button.frame = CGRect(x: 50, y: 300, width: 200, height: 100)
        button.backgroundColor = UIColor.green
        button.setTitle("Save", for: UIControlState.normal)
        button.addTarget(self, action: #selector(buttonAction(button:)), for: .touchUpInside)
        self.view.addSubview(button)
        
        let button2 = UIButton(type: .system) // let preferred over var here
        button2.frame = CGRect(x: 50, y: 400, width: 200, height: 100)
        button2.backgroundColor = UIColor.green
        button2.setTitle("Print vals", for: UIControlState.normal)
        button2.addTarget(self, action: #selector(buttonAction2(button:)), for: .touchUpInside)
        self.view.addSubview(button2)
    }
    
    func textFieldShouldReturn(textField: UITextField) -> Bool // called when 'return' key pressed. return false to ignore.
    {
        textField.resignFirstResponder()
        return true
    }
    
    func buttonAction(button: UIButton) {
        DataLayer.storeTranscription(info: textFiled.text!, type: textFiled2.text!)
/*        print("1:", textFiled.text!)
        print("2:", textFiled2.text!)*/
    }
    
    func buttonAction2(button: UIButton) {
        let x = DataLayer.getTranscriptions()
        for trans in x as! [NSManagedObject] {
            //                //get the Key Value pairs (although there may be a better way to do that...
                            print("\(trans.value(forKey: "info"))")
            //                print("\(trans.value(forKey: "type"))")
                        }


    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}
