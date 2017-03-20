//
//  FirstViewController.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 1/16/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import UIKit
import PathMenu
import Alamofire

class FirstViewController: UIViewController {
    
    let items = ["Ray Wenderlich", "NSHipster", "iOS Developer Tips", "Jameson Quave", "Natasha The Robot", "Coding Explorer", "That Thing In Swift", "Andrew Bancroft", "iAchieved.it", "Airspeed Velocity", "Ray Wenderlich", "NSHipster", "iOS Developer Tips", "Jameson Quave", "Natasha The Robot", "Coding Explorer", "That Thing In Swift", "Andrew Bancroft", "iAchieved.it", "Airspeed Velocity"]
    
    var currentDayInfo:CurrentDayInfo = CurrentDayInfo()
    var scoreLabel = UILabel()
    var scoreTextLabel = UILabel()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        scoreLabel = UILabel(frame: CGRect(x: 0, y: 0, width: 200, height: 21))
        scoreLabel.center = CGPoint(x: 160, y: 285)
        scoreLabel.textAlignment = .center
        scoreLabel.text = "Retrieving Score"
        self.view.addSubview(scoreLabel)
        
        scoreTextLabel = UILabel(frame: CGRect(x: 0, y: 0, width: 250, height: 21))
        scoreTextLabel.center = CGPoint(x: self.view.frame.width / 2, y: self.view.frame.height * 0.7)
        scoreTextLabel.text = "Your Current Score For Today"
        self.view.addSubview(scoreTextLabel)
        
        // Do any additional setup after loading the view, typically from a nib.
        

    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func updateHomePage() {
        let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString]
        
        Alamofire.request(Settings.getHomeScreenURL(), method: .get, headers: headers).validate().responseJSON { response in
            switch response.result {
            case .success(let data):
                let json = JSON(data)
                let score:Int = json[JSONProtocolNames.userScoreHeaderName].intValue
                print("SCORE: \(score)")
                print("UPDATING HOME SCREEN")
                self.scoreLabel.removeFromSuperview()
                //self.scoreLabel.isHidden = true
                self.scoreLabel = UILabel(frame: CGRect(x: 0, y: 0, width: 150, height: 150))
                self.scoreLabel.text = String(score)
                self.scoreLabel.center = self.view.center
                self.scoreLabel.font = UIFont(name: self.scoreLabel.font.fontName, size: 80)
                self.scoreLabel.textAlignment = .center
                self.view.addSubview(self.scoreLabel)
                print("FINSHED DISPLAYING SCORE")
                
            case .failure(let error):
                print("Request failed with error: \(error)")
            }
        }
        
    }
    
    override func viewDidAppear(_ animated: Bool) {
        let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString, JSONProtocolNames.dateHeaderName: currentDayInfo.currentDayString]
        
        //Update Scores
        let x = dataLayer.getTranscriptions()
        
        let parameters:[String: Any] = [JSONProtocolNames.lastDayCHeckedHeaderName: Settings.datecheckString]
        
        // HERE - Send Settings.datecheckString to send to server
        let currentDate:Date = Date()
        let currentFormatter:DateFormatter = DateFormatter()
        currentFormatter.dateFormat = "yyyy-MM-dd"
        let currentDateString: String = currentFormatter.string(from: currentDate)
        print("BEFORE IF STATEMENT FIRST: \(Settings.datecheckString)")
        print("BEFORE IF STATEMENT SECOND: \(currentDateString)")
        if Settings.datecheckString != currentDateString {
            //if false {
            print("AFTER UPDATE FIRST: \(Settings.datecheckString)")
            print("AFTER UPDATE SECOND: \(currentDateString)")
            Alamofire.request(Settings.getUpdateScoresURL(), method: .post, parameters: parameters, encoding: JSONEncoding.default, headers: headers).validate().responseString { response in
                switch response.result {
                case .success(let data):
                    //let json = JSON(data)
                    dataLayer.storeDateTranscription(datecheck: currentDateString)
                    dataLayer.storeTranscription(username: Settings.usernameString)
                    print("Updated Scores")
                    self.updateHomePage()
                    
                case .failure(let error):
                    print("Request failed with error: \(error)")
                }
            }
        } else {
            self.updateHomePage()
            print("Doesnt Need to Update Score")
        }
    }
    
    
}

extension FirstViewController: PathMenuDelegate {
    func didSelect(on menu: PathMenu, index: Int) {
        print("Select the index : \(index)")
    }
    
    func willStartAnimationOpen(on menu: PathMenu) {
        print("Menu will open!")
    }
    
    func willStartAnimationClose(on menu: PathMenu) {
        print("Menu will close!")
    }
    
    func didFinishAnimationOpen(on menu: PathMenu) {
        print("Menu was open!")
    }
    
    func didFinishAnimationClose(on menu: PathMenu) {
        print("Menu was closed!")
    }
    
    
}

/*extension FirstViewController: UITableViewDelegate {
}

extension FirstViewController: UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return items.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath)
        cell.textLabel?.text = items[(indexPath as NSIndexPath).row]
        return cell
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
}*/



