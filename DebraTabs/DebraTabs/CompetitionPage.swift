//
//  CompetitionPage.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/5/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation
import UIKit
import Alamofire

class CompetitionPage:UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    public var blueColor: UIColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
    
    public var isLeader:Bool = false
    public var teamID:Int = -1
    
    private var redTeamTableView: UITableView!
    private var blueTeamTableView: UITableView!
    
    let statusLabel:String = "Status: "
    let redTeamLabel:String = "Red Team"
    let blueTeamLabel:String = "Blue Team"
    let scoreLabel:String = "Score:"
    let leaveCompetitionLabel:String = "Exit Competition"
    let cancelCompetitionLabel:String = "Cancel Competition"
    let closeLabel:String = "Close"
    let startLabel:String = "Start: "
    let endLabel:String = "End: "
    
    var statusText:UILabel = UILabel()
    var redTeamScoreLabel:UILabel = UILabel()
    var blueTeamScoreLabel:UILabel = UILabel()
    var redTeamScoreValueLabel:UILabel = UILabel()
    var blueTeamScoreValueLabel:UILabel = UILabel()
    var competitionNameLabel:UILabel = UILabel()
    var redTeamColorLabel:UILabel = UILabel()
    var blueTeamColorLabel:UILabel = UILabel()
    var redTeamName:UILabel = UILabel()
    var blueTeamName:UILabel = UILabel()
    var startCompetitionLabel:UILabel = UILabel()
    var endCompetitionLabel:UILabel = UILabel()
    
    var leaveButton:UIButton = UIButton()
    var cancelButton:UIButton = UIButton()
    var closeButton:UIButton = UIButton()
    
    var competitionName:String = ""
    var competitionStatus:Bool = false
    let activeLabel:String = "Active"
    let pendingLabel:String = "Pending"
    var redTeamNameValue:String = ""
    var blueTeamNameValue:String = ""
    var currentCompetition:Competition = Competition()
    
    var redTeamMembers:Array<TeamMember> = []
    var blueTeamMembers:Array<TeamMember> = []
    public var loadPage:Bool = true
    public var json:JSON = []


    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        let displayWidth: CGFloat = self.view.frame.width
        let displayHeight: CGFloat = self.view.frame.height
        let borderAlpha : CGFloat = 0.7
        let cornerRadius : CGFloat = 5.0
        
        let barHeight: CGFloat = UIApplication.shared.statusBarFrame.size.height
        
        leaveButton = UIButton(frame: CGRect(x: displayWidth/2, y: self.view.frame.height - 120, width: 300, height: 44))
        leaveButton.center.x = self.view.center.x
        leaveButton.setTitle(self.leaveCompetitionLabel, for: UIControlState.normal)
        leaveButton.setTitleColor(blueColor, for: UIControlState.normal)
        leaveButton.backgroundColor = UIColor.clear
        leaveButton.layer.borderWidth = 1.0
        leaveButton.layer.borderColor = blueColor.cgColor
        leaveButton.layer.cornerRadius = cornerRadius
        leaveButton.addTarget(self, action: #selector(leaveButtonPressed(_:)), for: .touchDown)
        self.view.addSubview(leaveButton)
        
        cancelButton = UIButton(frame: CGRect(x: displayWidth/2, y: self.view.frame.height - 120, width: 300, height: 44))
        cancelButton.center.x = self.view.center.x
        cancelButton.setTitle(self.cancelCompetitionLabel, for: UIControlState.normal)
        cancelButton.setTitleColor(blueColor, for: UIControlState.normal)
        cancelButton.backgroundColor = UIColor.clear
        cancelButton.layer.borderWidth = 1.0
        cancelButton.layer.borderColor = blueColor.cgColor
        cancelButton.layer.cornerRadius = cornerRadius
        cancelButton.addTarget(self, action: #selector(cancelButtonPressed(_:)), for: .touchDown)
        self.view.addSubview(cancelButton)
        
        closeButton = UIButton(frame: CGRect(x: displayWidth/2, y: self.view.frame.height - 60, width: 300, height: 44))
        closeButton.center.x = self.view.center.x
        closeButton.setTitle(self.closeLabel, for: UIControlState.normal)
        closeButton.setTitleColor(blueColor, for: UIControlState.normal)
        closeButton.backgroundColor = UIColor.clear
        closeButton.layer.borderWidth = 1.0
        closeButton.layer.borderColor = blueColor.cgColor
        closeButton.layer.cornerRadius = cornerRadius
        closeButton.addTarget(self, action: #selector(closeButtonPressed(_:)), for: .touchDown)
        self.view.addSubview(closeButton)
        
        competitionNameLabel.frame = CGRect(x: self.view.center.x / 2, y: 10, width: displayWidth, height: 100)
        competitionNameLabel.center.x = self.view.center.x
        competitionNameLabel.textAlignment = .center
        competitionNameLabel.text = self.competitionName
        competitionNameLabel.numberOfLines = 5
        competitionNameLabel.textColor = blueColor
        competitionNameLabel.font=UIFont.systemFont(ofSize: 30)
        competitionNameLabel.backgroundColor=UIColor.clear
        self.view.addSubview(competitionNameLabel)
        
        statusText.frame = CGRect(x: self.view.center.x / 2, y: 50, width: displayWidth, height: 100)
        statusText.center.x = self.view.center.x
        statusText.textAlignment = .center
        var statusData:String = self.statusLabel
        if self.competitionStatus {
            statusData = statusData + self.activeLabel
        } else {
            statusData = statusData + self.pendingLabel
        }
        statusText.text = statusData
        statusText.numberOfLines = 5
        statusText.textColor = blueColor
        statusText.font=UIFont.systemFont(ofSize: 20)
        statusText.backgroundColor=UIColor.clear
        self.view.addSubview(statusText)
        
        redTeamColorLabel.frame = CGRect(x: self.view.center.x / 4, y: 200, width: displayWidth / 2, height: 100)
        redTeamColorLabel.center.x = self.view.center.x / 2
        redTeamColorLabel.textAlignment = .center
        redTeamColorLabel.text = self.redTeamLabel
        redTeamColorLabel.numberOfLines = 5
        redTeamColorLabel.textColor = UIColor.red
        redTeamColorLabel.font=UIFont.systemFont(ofSize: 15)
        redTeamColorLabel.backgroundColor=UIColor.clear
        self.view.addSubview(redTeamColorLabel)
        
        blueTeamColorLabel.frame = CGRect(x: 3 * self.view.center.x / 4, y: 200, width: displayWidth / 2, height: 100)
        blueTeamColorLabel.center.x = 3 * self.view.center.x / 2
        blueTeamColorLabel.textAlignment = .center
        blueTeamColorLabel.text = self.blueTeamLabel
        blueTeamColorLabel.numberOfLines = 5
        blueTeamColorLabel.textColor = blueColor
        blueTeamColorLabel.font=UIFont.systemFont(ofSize: 15)
        blueTeamColorLabel.backgroundColor=UIColor.clear
        self.view.addSubview(blueTeamColorLabel)
        
        redTeamName.frame = CGRect(x: self.view.center.x / 4, y: 240, width: displayWidth / 2, height: 100)
        redTeamName.center.x = self.view.center.x / 2
        redTeamName.textAlignment = .center
        redTeamName.text = self.redTeamNameValue
        redTeamName.numberOfLines = 5
        redTeamName.textColor = UIColor.red
        redTeamName.font=UIFont.systemFont(ofSize: 30)
        redTeamName.backgroundColor=UIColor.clear
        self.view.addSubview(redTeamName)
        
        blueTeamName.frame = CGRect(x: 3 * self.view.center.x / 4, y: 240, width: displayWidth / 2, height: 100)
        blueTeamName.center.x = 3 * self.view.center.x / 2
        blueTeamName.textAlignment = .center
        blueTeamName.text = self.blueTeamNameValue
        blueTeamName.numberOfLines = 5
        blueTeamName.textColor = UIColor.blue
        blueTeamName.font=UIFont.systemFont(ofSize: 30)
        blueTeamName.backgroundColor=UIColor.clear
        self.view.addSubview(blueTeamName)
        
        startCompetitionLabel.frame = CGRect(x: self.view.center.x / 2, y: 120, width: displayWidth, height: 100)
        startCompetitionLabel.center.x = self.view.center.x
        startCompetitionLabel.textAlignment = .center
        startCompetitionLabel.text = self.startLabel
        startCompetitionLabel.numberOfLines = 5
        startCompetitionLabel.textColor = blueColor
        startCompetitionLabel.font=UIFont.systemFont(ofSize: 20)
        startCompetitionLabel.backgroundColor=UIColor.clear
        self.view.addSubview(startCompetitionLabel)
        
        endCompetitionLabel.frame = CGRect(x: self.view.center.x / 2, y: 160, width: displayWidth, height: 100)
        endCompetitionLabel.center.x = self.view.center.x
        endCompetitionLabel.textAlignment = .center
        endCompetitionLabel.text = self.endLabel
        endCompetitionLabel.numberOfLines = 5
        endCompetitionLabel.textColor = blueColor
        endCompetitionLabel.font=UIFont.systemFont(ofSize: 20)
        endCompetitionLabel.backgroundColor=UIColor.clear
        self.view.addSubview(endCompetitionLabel)
        
        redTeamTableView = UITableView(frame: CGRect(x: 0, y: 310, width: displayWidth / 2, height: 1 * self.view.frame.height / 4))
        redTeamTableView.register(UITableViewCell.self, forCellReuseIdentifier: "MyCell")
        redTeamTableView.dataSource = self
        redTeamTableView.delegate = self
        
        redTeamTableView.layer.masksToBounds = true
        redTeamTableView.layer.borderColor = blueColor.cgColor
        redTeamTableView.layer.borderWidth = 2.0
        
        self.view.addSubview(redTeamTableView)
        
        
        blueTeamTableView = UITableView(frame: CGRect(x: displayWidth / 2, y: 310, width: displayWidth / 2, height: 1 * self.view.frame.height / 4))
        blueTeamTableView.register(UITableViewCell.self, forCellReuseIdentifier: "MyCell")
        blueTeamTableView.dataSource = self
        blueTeamTableView.delegate = self
        
        blueTeamTableView.layer.masksToBounds = true
        blueTeamTableView.layer.borderColor = blueColor.cgColor
        blueTeamTableView.layer.borderWidth = 2.0
        
        self.view.addSubview(blueTeamTableView)
        
        redTeamScoreLabel.frame = CGRect(x: self.view.center.x / 2, y: 480, width: displayWidth / 2, height: 50)
        redTeamScoreLabel.center.x = self.view.center.x / 2
        redTeamScoreLabel.textAlignment = .center
        redTeamScoreLabel.text = self.scoreLabel
        redTeamScoreLabel.numberOfLines = 5
        redTeamScoreLabel.textColor = UIColor.red
        redTeamScoreLabel.font=UIFont.systemFont(ofSize: 15)
        redTeamScoreLabel.backgroundColor=UIColor.clear
        self.view.addSubview(redTeamScoreLabel)
        
        blueTeamScoreLabel.frame = CGRect(x: 3 * self.view.center.x / 2, y: 480, width: displayWidth / 2, height: 50)
        blueTeamScoreLabel.center.x = 3 * self.view.center.x / 2
        blueTeamScoreLabel.textAlignment = .center
        blueTeamScoreLabel.text = self.scoreLabel
        blueTeamScoreLabel.numberOfLines = 5
        blueTeamScoreLabel.textColor = UIColor.blue
        blueTeamScoreLabel.font=UIFont.systemFont(ofSize: 15)
        blueTeamScoreLabel.backgroundColor=UIColor.clear
        self.view.addSubview(blueTeamScoreLabel)
        
        redTeamScoreValueLabel.frame = CGRect(x: self.view.center.x / 2, y: 510, width: displayWidth / 2, height: 50)
        redTeamScoreValueLabel.center.x = self.view.center.x / 2
        redTeamScoreValueLabel.textAlignment = .center
        redTeamScoreValueLabel.text = "0"
        redTeamScoreValueLabel.numberOfLines = 5
        redTeamScoreValueLabel.textColor = UIColor.red
        redTeamScoreValueLabel.font=UIFont.systemFont(ofSize: 30)
        redTeamScoreValueLabel.backgroundColor=UIColor.clear
        self.view.addSubview(redTeamScoreValueLabel)
        
        blueTeamScoreValueLabel.frame = CGRect(x: 3 * self.view.center.x / 2, y: 510, width: displayWidth / 2, height: 50)
        blueTeamScoreValueLabel.center.x = 3 * self.view.center.x / 2
        blueTeamScoreValueLabel.textAlignment = .center
        blueTeamScoreValueLabel.text = "0"
        blueTeamScoreValueLabel.numberOfLines = 5
        blueTeamScoreValueLabel.textColor = UIColor.blue
        blueTeamScoreValueLabel.font=UIFont.systemFont(ofSize: 30)
        blueTeamScoreValueLabel.backgroundColor=UIColor.clear
        self.view.addSubview(blueTeamScoreValueLabel)
        
    }
    
    func leaveButtonPressed(_ button: UIButton) {
        let message:String = "Exiting the competition will result in automatic loss for your team."
        let joinAction = UIAlertController(title: "Exit Competition?", message: message, preferredStyle: UIAlertControllerStyle.alert)
        joinAction.addAction(UIAlertAction(title: "Yes", style: .default, handler: { (action: UIAlertAction!) in
            let parameters = Requests.leaveCompetition(id: self.teamID)
            let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString]
            Alamofire.request(Settings.getCompetitionLeaveURL(), method: .post, parameters: parameters, encoding: JSONEncoding.default, headers:headers).validate()
                .responseString { response in
                    switch response.result {
                    case .success(let _):
                        print("Left Competition")
                        self.viewDidAppear(true)
                        
                    case .failure(let error):
                        print("Request failed with error: \(error)")
                    }
            }
        }))
        joinAction.addAction(UIAlertAction(title: "No", style: .default, handler: { (action: UIAlertAction!) in
            return
        }))
        self.present(joinAction, animated: true, completion: nil)
    }
    
    func cancelButtonPressed(_ button: UIButton) {
        let message:String = "Do you want to cancel this competition?"
        let joinAction = UIAlertController(title: "Cancel Competition?", message: message, preferredStyle: UIAlertControllerStyle.alert)
        joinAction.addAction(UIAlertAction(title: "Yes", style: .default, handler: { (action: UIAlertAction!) in
            let parameters = Requests.cancelCompetition(teamId: self.teamID, competitionId: self.currentCompetition.competitionID)
            let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString]
            Alamofire.request(Settings.getCompetitionCancelURL(), method: .post, parameters: parameters, encoding: JSONEncoding.default, headers:headers).validate()
                .responseString { response in
                    switch response.result {
                    case .success(let _):
                        print("Canceled Competition")
                        //self.viewDidAppear(true)
                        self.dismiss(animated: true, completion: nil)
                        
                    case .failure(let error):
                        if (response.response?.statusCode == 401) {
                            let message:String = "Competition is no longer pending."
                            let joinAction = UIAlertController(title: "Error", message: message, preferredStyle: UIAlertControllerStyle.alert)
                            joinAction.addAction(UIAlertAction(title: "Ok", style: .default, handler: { (action: UIAlertAction!) in
                                return
                            }))
                            self.present(joinAction, animated: true, completion: nil)
                        } else if (response.response?.statusCode == 455) {
                            let message:String = "Competition no longer exists."
                            let joinAction = UIAlertController(title: "Error", message: message, preferredStyle: UIAlertControllerStyle.alert)
                            joinAction.addAction(UIAlertAction(title: "Ok", style: .default, handler: { (action: UIAlertAction!) in
                                self.dismiss(animated: true, completion: nil)
                            }))
                            self.present(joinAction, animated: true, completion: nil)
                            print("INVALID STATE")
                        } else {
                            let message:String = "Could not cancel competition."
                            let joinAction = UIAlertController(title: "Error", message: message, preferredStyle: UIAlertControllerStyle.alert)
                            joinAction.addAction(UIAlertAction(title: "Ok", style: .default, handler: { (action: UIAlertAction!) in
                                return
                            }))
                            self.present(joinAction, animated: true, completion: nil)
                        }
                        print("Request failed with error: \(error)")
                    }
            }
        }))
        joinAction.addAction(UIAlertAction(title: "No", style: .default, handler: { (action: UIAlertAction!) in
            return
        }))
        self.present(joinAction, animated: true, completion: nil)
    }
    
    func closeButtonPressed(_ button: UIButton) {
        self.dismiss(animated: true, completion: nil)
        print("Close Button Pressed")
    }
    
    func processJSON() {
        print(self.json)
        self.currentCompetition = Competition(json: self.json)
        self.competitionNameLabel.text = self.currentCompetition.competitionName
        self.redTeamName.text = self.currentCompetition.teamRedName
        self.blueTeamName.text = self.currentCompetition.teamBlueName
        self.competitionStatus = self.currentCompetition.competitionStatus
        var statusData:String = self.statusLabel
        if self.competitionStatus {
            statusData = statusData + self.activeLabel
        } else {
            statusData = statusData + self.pendingLabel
        }
        self.statusText.text = statusData
        let dateFormatter:DateFormatter = DateFormatter()
        dateFormatter.dateFormat = "MMM dd, yyyy hh:mm a"
        let timeZone = NSTimeZone(name: "GMT")
        dateFormatter.timeZone=timeZone as TimeZone!
        let startTimeString:String = dateFormatter.string(from: self.currentCompetition.competitionStartDate)
        self.startCompetitionLabel.text = self.startLabel + startTimeString + " GMT"
        let endTimeString:String = dateFormatter.string(from: self.currentCompetition.competitionEndDate)
        self.endCompetitionLabel.text = self.endLabel + endTimeString + " GMT"
        self.redTeamMembers = []
        for member in self.currentCompetition.teamRedMembers {
            print("CATS")
            self.redTeamMembers.append(member)
        }
        self.blueTeamMembers = []
        for member in self.currentCompetition.teamBlueMembers {
            print("DOGS")
            self.blueTeamMembers.append(member)
        }
        self.redTeamScoreValueLabel.text = String(self.currentCompetition.teamRedScore)
        self.blueTeamScoreValueLabel.text = String(self.currentCompetition.teamBlueScore)
        if self.currentCompetition.competitionStatus {
            self.showActive()
        } else {
            self.hideActive()
        }
        if self.isLeader {
            self.showLeader()
        } else {
            self.hideLeader()
        }
        
        //                if self.currentCompetition.competitionStatus {
        //                    self.cancelButton.isHidden = true
        //                    self.leaveButton.isHidden = false
        //                } else {
        //                    self.cancelButton.isHidden = false
        //                    self.leaveButton.isHidden = true
        //                }
        self.redTeamTableView.reloadData()
        self.blueTeamTableView.reloadData()
    }
    
    func updateCompetitionPage() {
        if self.loadPage {
            let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString]
            Alamofire.request(Settings.getCompetitionViewURL(), method: .get, headers: headers).validate().responseJSON { response in
                switch response.result {
                case .success(let data):
                    self.json = JSON(data)
                    self.processJSON()
                    
                case .failure(let error):
                    print("Request failed with error: \(error)")
                }
            }
        } else {
            self.processJSON()
            self.loadPage = true
        }
    }
    
    func showActive() {
        self.redTeamTableView.isHidden = false
        self.blueTeamTableView.isHidden = false
        self.redTeamScoreLabel.isHidden = false
        self.blueTeamScoreLabel.isHidden = false
        self.redTeamScoreValueLabel.isHidden = false
        self.blueTeamScoreValueLabel.isHidden = false
    }
    
    func hideActive() {
        self.redTeamTableView.isHidden = true
        self.blueTeamTableView.isHidden = true
        self.redTeamScoreLabel.isHidden = true
        self.blueTeamScoreLabel.isHidden = true
        self.redTeamScoreValueLabel.isHidden = true
        self.blueTeamScoreValueLabel.isHidden = true
    }
    
    func showLeader() {
        if self.currentCompetition.competitionStatus {
            self.cancelButton.isHidden = true
            self.leaveButton.isHidden = false
        } else {
            self.cancelButton.isHidden = false
            self.leaveButton.isHidden = true
        }
        //self.leaveButton.isHidden = false
    }
    
    func hideLeader() {
        if self.currentCompetition.competitionStatus {
            self.cancelButton.isHidden = true
            self.leaveButton.isHidden = true
        } else {
            self.cancelButton.isHidden = true
            self.leaveButton.isHidden = true
        }
        //self.leaveButton.isHidden = true
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if tableView == self.redTeamTableView {
            self.redTeamTableView.deselectRow(at: indexPath, animated: true)
        } else if tableView == self.blueTeamTableView {
            self.blueTeamTableView.deselectRow(at: indexPath, animated: true)
        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        var value = 0
        if tableView == self.redTeamTableView {
            value = self.redTeamMembers.count
            if value > 0
            {
                self.redTeamTableView.separatorStyle = .singleLine
                self.redTeamTableView.backgroundView = nil
            }
            else
            {
                let noDataLabel: UILabel     = UILabel(frame: CGRect(x: 0, y: 0, width: tableView.bounds.size.width, height: tableView.bounds.size.height))
                noDataLabel.text          = "No Team Members"
                noDataLabel.textColor     = UIColor.black
                noDataLabel.textAlignment = .center
                self.redTeamTableView.backgroundView  = noDataLabel
                self.redTeamTableView.separatorStyle  = .none
            }
        } else if tableView == self.blueTeamTableView {
            value = self.blueTeamMembers.count
            if value > 0
            {
                self.blueTeamTableView.separatorStyle = .singleLine
                self.blueTeamTableView.backgroundView = nil
            }
            else
            {
                let noDataLabel: UILabel     = UILabel(frame: CGRect(x: 0, y: 0, width: tableView.bounds.size.width, height: tableView.bounds.size.height))
                noDataLabel.text          = "No Team Members"
                noDataLabel.textColor     = UIColor.black
                noDataLabel.textAlignment = .center
                self.blueTeamTableView.backgroundView  = noDataLabel
                self.blueTeamTableView.separatorStyle  = .none
            }
        }
        return value
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell: UITableViewCell = UITableViewCell(style: UITableViewCellStyle.subtitle, reuseIdentifier: "MyTestCell")
        if tableView == self.redTeamTableView {
            cell.textLabel!.text = self.redTeamMembers[indexPath.row].username
            if currentCompetition.showTeamMembers {
                cell.detailTextLabel?.text = "Score: " + String(self.redTeamMembers[indexPath.row].competitionScore)
            }
        } else if tableView == self.blueTeamTableView {
            cell.textLabel!.text = self.blueTeamMembers[indexPath.row].username
            if currentCompetition.showTeamMembers {
                cell.detailTextLabel?.text = "Score: " + String(self.blueTeamMembers[indexPath.row].competitionScore)
            }
        }
        
        return cell
    }
    
    override func viewDidAppear(_ animated: Bool) {
        
        print("AHHH TESTING THINGS")
        
        let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString]
        
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
                    self.updateCompetitionPage()
                    
                case .failure(let error):
                    print("Request failed with error: \(error)")
                }
            }
        } else {
            self.updateCompetitionPage()
            print("Doesnt Need to Update Score")
        }
    }
    
}
