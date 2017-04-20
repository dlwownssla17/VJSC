//
//  DataViewController.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 2/14/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation
import UIKit
import Alamofire

class DataViewController: UIViewController , UITableViewDelegate, UITableViewDataSource {
    
    public var blueColor: UIColor = UIColor(red: CGFloat(0/255.0), green: CGFloat(122/255.0), blue: CGFloat(255/255.0), alpha: CGFloat(1.0))
    
    var createNewTeamButton: UIButton = UIButton()
    var teamNameLabel:UILabel = UILabel()
    let teamStatusLabel:UILabel = UILabel()
    let teamMembersLabel:UILabel = UILabel()
    var doesTeamExist:Bool = false
    var currentTeam:Team = Team()
    private var myTableView: UITableView!
    private var teamMemberTableView: UITableView!
    
    public var ObjectsArray = [TeamInvitation]()
    public var TeamMemberArray = [TeamMember]()
    
    let leaderNoCompetitionLabel:String = "View Competition"
    let memberNoCompetitionLabel:String = "No Competition"
    let pendingCompetitionLabel:String = "View Pending Competition"
    let currentCompetitionLabel:String = "View Current Competition"
    let viewCompetitionHistoryLabel:String = "View Competition History"
    let addMembersLabel:String = "Add Members"
    let noTeamLabel:String = "No Team"
    let isMemberLabel:String = "Status: Member"
    let isLeaderLabel:String = "Status: Leader"
    let memberListLabel:String = "Team Members"
    let leaveTeamLabel:String = "Leave Team"
    let removeTeamLabel:String = "Remove Team"
    
    var viewCompetitionButton: UIButton = UIButton()
    var viewCompetitionHistoryButton: UIButton = UIButton()
    var addMembersButton: UIButton = UIButton()
    var leaveTeamButton: UIButton = UIButton()
    var removeTeamButton: UIButton = UIButton()

    
    override func viewDidLoad() {
        super.viewDidLoad()
        let displayWidth: CGFloat = self.view.frame.width
        let displayHeight: CGFloat = self.view.frame.height
        
        let codedLabel:UILabel = UILabel()
        codedLabel.frame = CGRect(x: 100, y: 100, width: displayWidth, height: 200)
        codedLabel.textAlignment = .center
        codedLabel.text = "Under Construction! Check back later please."
        codedLabel.numberOfLines = 5
        codedLabel.textColor = blueColor
        codedLabel.font=UIFont.systemFont(ofSize: 40)
        codedLabel.backgroundColor=UIColor.clear
        
//        self.view.addSubview(codedLabel)
//        codedLabel.translatesAutoresizingMaskIntoConstraints = false
//        codedLabel.heightAnchor.constraint(equalToConstant: 200).isActive = true
//        codedLabel.widthAnchor.constraint(equalToConstant: displayWidth).isActive = true
//        codedLabel.centerXAnchor.constraint(equalTo: codedLabel.superview!.centerXAnchor).isActive = true
//        codedLabel.centerYAnchor.constraint(equalTo: codedLabel.superview!.centerYAnchor).isActive = true
        
        let barHeight: CGFloat = UIApplication.shared.statusBarFrame.size.height
        
        createNewTeamButton = UIButton(frame: CGRect(x: displayWidth/2, y: barHeight * 6, width: 200, height: 44))
        createNewTeamButton.center.x = self.view.center.x
        createNewTeamButton.setTitle("Create New Team", for: UIControlState.normal)
        createNewTeamButton.setTitleColor(blueColor, for: UIControlState.normal)
        createNewTeamButton.backgroundColor = UIColor.clear
        createNewTeamButton.layer.borderWidth = 1.0
        let borderAlpha : CGFloat = 0.7
        let cornerRadius : CGFloat = 5.0
        createNewTeamButton.layer.borderColor = blueColor.cgColor
        createNewTeamButton.layer.cornerRadius = cornerRadius
        createNewTeamButton.addTarget(self, action: #selector(createNewTeamButtonPressed(_:)), for: .touchDown)
        self.view.addSubview(createNewTeamButton)
        
        teamNameLabel.frame = CGRect(x: 100, y: 20, width: displayWidth, height: 100)
        teamNameLabel.textAlignment = .center
        teamNameLabel.center.x = self.view.center.x
        teamNameLabel.text = self.noTeamLabel
        teamNameLabel.textColor = blueColor
        teamNameLabel.font=UIFont.systemFont(ofSize: 40)
        teamNameLabel.backgroundColor=UIColor.clear
        self.view.addSubview(teamNameLabel)
        
        teamStatusLabel.frame = CGRect(x: 100, y: 60, width: displayWidth, height: 100)
        teamStatusLabel.textAlignment = .center
        teamStatusLabel.center.x = self.view.center.x
        if currentTeam.isLeader {
            teamStatusLabel.text = self.isLeaderLabel
        } else {
            teamStatusLabel.text = self.isMemberLabel
        }
        teamStatusLabel.textColor = blueColor
        teamStatusLabel.font=UIFont.systemFont(ofSize: 20)
        teamStatusLabel.backgroundColor=UIColor.clear
        self.view.addSubview(teamStatusLabel)
        
        teamMembersLabel.frame = CGRect(x: 100, y: self.view.frame.height / 2 - 70, width: displayWidth, height: 100)
        teamMembersLabel.textAlignment = .center
        teamMembersLabel.center.x = self.view.center.x
        teamMembersLabel.text = self.memberListLabel
        teamMembersLabel.textColor = blueColor
        teamMembersLabel.font=UIFont.systemFont(ofSize: 20)
        teamMembersLabel.backgroundColor=UIColor.clear
        self.view.addSubview(teamMembersLabel)
        
        myTableView = UITableView(frame: CGRect(x: 0, y: barHeight * 10, width: displayWidth, height: displayHeight - barHeight * 10))
        myTableView.register(UITableViewCell.self, forCellReuseIdentifier: "MyCell")
        myTableView.dataSource = self
        myTableView.delegate = self
        
        myTableView.layer.masksToBounds = true
        myTableView.layer.borderColor = blueColor.cgColor
        myTableView.layer.borderWidth = 2.0
        
        self.view.addSubview(myTableView)
        
        teamMemberTableView = UITableView(frame: CGRect(x: 0, y: self.view.frame.height / 2, width: displayWidth, height: self.view.frame.height / 2 - 110))
        teamMemberTableView.register(UITableViewCell.self, forCellReuseIdentifier: "MyCell")
        teamMemberTableView.dataSource = self
        teamMemberTableView.delegate = self
        
        teamMemberTableView.layer.masksToBounds = true
        teamMemberTableView.layer.borderColor = blueColor.cgColor
        teamMemberTableView.layer.borderWidth = 2.0
        
        self.view.addSubview(teamMemberTableView)
        
        viewCompetitionButton = UIButton(frame: CGRect(x: displayWidth/2, y: barHeight * 8, width: 300, height: 44))
        viewCompetitionButton.center.x = self.view.center.x
        viewCompetitionButton.setTitle(self.memberNoCompetitionLabel, for: UIControlState.normal)
        viewCompetitionButton.setTitleColor(blueColor, for: UIControlState.normal)
        viewCompetitionButton.backgroundColor = UIColor.clear
        viewCompetitionButton.layer.borderWidth = 1.0
        viewCompetitionButton.layer.borderColor = blueColor.cgColor
        viewCompetitionButton.layer.cornerRadius = cornerRadius
        viewCompetitionButton.addTarget(self, action: #selector(viewCompetitionButtonPressed(_:)), for: .touchDown)
        self.view.addSubview(viewCompetitionButton)
        
        viewCompetitionHistoryButton = UIButton(frame: CGRect(x: displayWidth/2, y: barHeight * 11, width: 300, height: 44))
        viewCompetitionHistoryButton.center.x = self.view.center.x
        viewCompetitionHistoryButton.setTitle(self.viewCompetitionHistoryLabel, for: UIControlState.normal)
        viewCompetitionHistoryButton.setTitleColor(blueColor, for: UIControlState.normal)
        viewCompetitionHistoryButton.backgroundColor = UIColor.clear
        viewCompetitionHistoryButton.layer.borderWidth = 1.0
        viewCompetitionHistoryButton.layer.borderColor = blueColor.cgColor
        viewCompetitionHistoryButton.layer.cornerRadius = cornerRadius
        viewCompetitionHistoryButton.addTarget(self, action: #selector(viewCompetitionHistoryButtonPressed(_:)), for: .touchDown)
        self.view.addSubview(viewCompetitionHistoryButton)
        
        addMembersButton = UIButton(frame: CGRect(x: displayWidth/2, y: barHeight * 14, width: 300, height: 44))
        addMembersButton.center.x = self.view.center.x
        addMembersButton.setTitle(self.addMembersLabel, for: UIControlState.normal)
        addMembersButton.setTitleColor(blueColor, for: UIControlState.normal)
        addMembersButton.backgroundColor = UIColor.clear
        addMembersButton.layer.borderWidth = 1.0
        addMembersButton.layer.borderColor = blueColor.cgColor
        addMembersButton.layer.cornerRadius = cornerRadius
        addMembersButton.addTarget(self, action: #selector(addMembersButtonPressed(_:)), for: .touchDown)
        self.view.addSubview(addMembersButton)
        
        leaveTeamButton = UIButton(frame: CGRect(x: displayWidth/2, y: self.view.frame.height - 100, width: 300, height: 44))
        leaveTeamButton.center.x = self.view.center.x
        leaveTeamButton.setTitle(self.leaveTeamLabel, for: UIControlState.normal)
        leaveTeamButton.setTitleColor(blueColor, for: UIControlState.normal)
        leaveTeamButton.backgroundColor = UIColor.clear
        leaveTeamButton.layer.borderWidth = 1.0
        leaveTeamButton.layer.borderColor = blueColor.cgColor
        leaveTeamButton.layer.cornerRadius = cornerRadius
        leaveTeamButton.addTarget(self, action: #selector(leaveTeamButtonPressed(_:)), for: .touchDown)
        self.view.addSubview(leaveTeamButton)
        
        removeTeamButton = UIButton(frame: CGRect(x: displayWidth/2, y: self.view.frame.height - 100, width: 300, height: 44))
        removeTeamButton.center.x = self.view.center.x
        removeTeamButton.setTitle(self.removeTeamLabel, for: UIControlState.normal)
        removeTeamButton.setTitleColor(blueColor, for: UIControlState.normal)
        removeTeamButton.backgroundColor = UIColor.clear
        removeTeamButton.layer.borderWidth = 1.0
        removeTeamButton.layer.borderColor = blueColor.cgColor
        removeTeamButton.layer.cornerRadius = cornerRadius
        removeTeamButton.addTarget(self, action: #selector(removeTeamButtonPressed(_:)), for: .touchDown)
        self.view.addSubview(removeTeamButton)
        
        self.hideTeamItems()
        self.displayNoTeamItems()
        
        
    }
    
    func createNewTeamButtonPressed(_ button: UIButton) {
        let secondViewController:CreateNewTeam = CreateNewTeam()
        self.present(secondViewController, animated: true, completion: nil)
    }
    
    func viewCompetitionButtonPressed(_ button: UIButton) {
        if self.currentTeam.competitonStatus == .None {
            let secondViewController:SetCompetitionPage = SetCompetitionPage()
            secondViewController.currentTeam = self.currentTeam
            self.present(secondViewController, animated: true, completion: nil)
        } else {
            let secondViewController:CompetitionPage = CompetitionPage()
            secondViewController.isLeader = self.currentTeam.isLeader
            secondViewController.teamID = self.currentTeam.teamID
            //secondViewController.viewDidAppear(false)
            let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString]
            Alamofire.request(Settings.getCompetitionViewURL(), method: .get, headers: headers).validate().responseJSON { response in
                switch response.result {
                case .success(let data):
                    let json = JSON(data)
                    secondViewController.json = json
                    secondViewController.loadPage = false
                    self.present(secondViewController, animated: true, completion: nil)
                case .failure(let error):
                    if (response.response?.statusCode == 455) {
                        print("INVALID STATE")
                        self.viewDidAppear(false)
                    } else {
                        print("INVALID STATE")
                        self.viewDidAppear(false)
                    }
                }
            }
        }
        print("HANDLED VIEW COMPETITION PRESSED")
    }
    
    func setCompetitionButtonPressed(_ button: UIButton) {
        
        let secondViewController:CompetitionPage = CompetitionPage()
        secondViewController.isLeader = self.currentTeam.isLeader
        self.present(secondViewController, animated: true, completion: nil)
    }
    
    func viewCompetitionHistoryButtonPressed(_ button: UIButton) {
        let secondViewController:TeamHistoryPage = TeamHistoryPage()
        secondViewController.teamHistory = self.currentTeam.teamHistory
        //secondViewController.viewDidAppear(false)
        self.present(secondViewController, animated: true, completion: nil)
        print("PRESSED TEAM HISTORY")
    }
    
    func addMembersButtonPressed(_ button: UIButton) {
        let secondViewController:AddMemberToTeam = AddMemberToTeam()
        self.present(secondViewController, animated: true, completion: nil)
    }
    
    func leaveTeamButtonPressed(_ button: UIButton) {
        if self.currentTeam.competitonStatus == .Active {
            let message:String = "Cannot Leave Team During Active Competition."
            let leaveTeamAction = UIAlertController(title: "Cannot Leave", message: message, preferredStyle: UIAlertControllerStyle.alert)
            leaveTeamAction.addAction(UIAlertAction(title: "Ok", style: .default, handler: { (action: UIAlertAction!) in
                return
            }))
            self.present(leaveTeamAction, animated: true, completion: nil)
            return
        }
        
        let message:String = "Are you sure you want to leave the team?"
        let leaveTeamAction = UIAlertController(title: "Leave Team?", message: message, preferredStyle: UIAlertControllerStyle.alert)
        
        
        leaveTeamAction.addAction(UIAlertAction(title: "Yes", style: .default, handler: { (action: UIAlertAction!) in
            let parameters = Requests.leaveTeam(id: self.currentTeam.teamID)
            let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString]
            Alamofire.request(Settings.getLeaveTeamURL(), method: .post, parameters: parameters, encoding: JSONEncoding.default, headers:headers)
                .responseString { response in
                    switch response.result {
                    case .success(let _):
                        print("Left Team")
                        self.viewDidAppear(true)
                        
                    case .failure(let error):
                        if (response.response?.statusCode == 455) {
                            self.viewDidAppear(false)
                        } else {
                            self.viewDidAppear(false)
                        }
                        print("Request failed with error: \(error)")
                    }
            }
        }))
        
        leaveTeamAction.addAction(UIAlertAction(title: "Cancel", style: .default, handler: { (action: UIAlertAction!) in
            return
        }))
        self.present(leaveTeamAction, animated: true, completion: nil)
    }
    
    func removeTeamButtonPressed(_ button: UIButton) {
        let message:String = "Are you sure you want to permanently remove the team?"
        let removeTeamAction = UIAlertController(title: "Remove Team?", message: message, preferredStyle: UIAlertControllerStyle.alert)
        
        
        removeTeamAction.addAction(UIAlertAction(title: "Yes", style: .default, handler: { (action: UIAlertAction!) in
            let parameters = Requests.removeTeam(id: self.currentTeam.teamID)
            let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString]
            Alamofire.request(Settings.getRemoveTeamURL(), method: .post, parameters: parameters, encoding: JSONEncoding.default, headers: headers).validate()
                .responseString { response in
                    switch response.result {
                    case .success(let _):
                        print("Removed Team")
                        self.viewDidAppear(true)
                        
                    case .failure(let error):
                        if (response.response?.statusCode == 455) {
                            self.viewDidAppear(false)
                        } else {
                            self.viewDidAppear(false)
                        }
                        print("Request failed with error: \(error)")
                    }
            }
        }))
        
        removeTeamAction.addAction(UIAlertAction(title: "Cancel", style: .default, handler: { (action: UIAlertAction!) in
            return
        }))
        self.present(removeTeamAction, animated: true, completion: nil)
    }
    
    
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        if tableView == self.myTableView {
            return true
        } else if tableView == self.teamMemberTableView {
            return self.currentTeam.isLeader && (self.TeamMemberArray[indexPath.row].username != Settings.usernameString)
        }
        return false
    }
    
    func tableView(_ tableView: UITableView, editActionsForRowAt indexPath: IndexPath) -> [UITableViewRowAction]? {
        if tableView == self.myTableView {
            let delete = UITableViewRowAction(style: .normal, title: "Delete") { action, index in
                print("Delete Request")
                let parameters = Requests.declineTeamInvite(id: self.ObjectsArray[indexPath.row].teamID)
                let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString]
                Alamofire.request(Settings.getDeclineTeamInviteURL(), method: .post, parameters: parameters, encoding: JSONEncoding.default, headers: headers)
                    .responseString { response in
                        print("Response String: \(response.response?.statusCode == 401)")
                        switch response.result {
                        case .success(let _):
                            print("Deleted Invitation From Team")
                            self.viewDidAppear(true)
                            
                        case .failure(let error):
                            if (response.response?.statusCode == 455) {
                                self.viewDidAppear(false)
                            } else {
                                self.viewDidAppear(false)
                            }
                            print("Request failed with error: \(error)")
                        }
                }

            }
            delete.backgroundColor = .red
            return [delete]
        } else if tableView == self.teamMemberTableView {
            let kickOut = UITableViewRowAction(style: .normal, title: "Remove") { action, index in
                let message:String = "Are you sure you want to remove " + self.TeamMemberArray[indexPath.row].username + " from the team?"
                let kickOutAction = UIAlertController(title: "Remove Member?", message: message, preferredStyle: UIAlertControllerStyle.alert)
                
                
                kickOutAction.addAction(UIAlertAction(title: "Yes", style: .default, handler: { (action: UIAlertAction!) in
                    let parameters = Requests.kickOutUser(username: self.TeamMemberArray[indexPath.row].username, id: self.currentTeam.teamID)
                    let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString]
                    Alamofire.request(Settings.getKickoutMemberURL(), method: .post, parameters: parameters, encoding: JSONEncoding.default, headers:headers)
                        .responseString { response in
                            switch response.result {
                            case .success(let _):
                                print("Kicked Out Member From Team")
                                self.viewDidAppear(true)
                                
                            case .failure(let error):
                                if (response.response?.statusCode == 455) {
                                    self.viewDidAppear(false)
                                } else {
                                    self.viewDidAppear(false)
                                }
                                
                                print("Request failed with error: \(error)")
                            }
                    }
                }))
                
                kickOutAction.addAction(UIAlertAction(title: "Cancel", style: .default, handler: { (action: UIAlertAction!) in
                    return
                }))
                self.present(kickOutAction, animated: true, completion: nil)
                
            }
            kickOut.backgroundColor = .red
            return [kickOut]
        }

        return []
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        self.myTableView.deselectRow(at: indexPath, animated: true)
        if tableView == self.myTableView {
            let message:String = "Name: " + self.ObjectsArray[indexPath.row].teamName + "\nLeader: " + self.ObjectsArray[indexPath.row].teamLeader
            let joinTeamAction = UIAlertController(title: "Join Team?", message: message, preferredStyle: UIAlertControllerStyle.alert)
        
            joinTeamAction.addAction(UIAlertAction(title: "Join", style: .default, handler: { (action: UIAlertAction!) in
                let parameters = Requests.joinTeam(id: self.ObjectsArray[indexPath.row].teamID)
                let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString]
                Alamofire.request(Settings.getJoinTeamURL(), method: .post, parameters: parameters, encoding: JSONEncoding.default, headers: headers)
                    .validate().responseString { response in
                        //print("Response String: \(response.response?.statusCode == 401)")
                        switch response.result {
                        case .success(let data):
                            print("Joined Team")
                            self.viewDidAppear(true)
                            
                        case .failure(let error):
                            print("Request failed with error: \(error)")
                            if (response.response?.statusCode == 401) {
                                let message:String = "Team reached max size."
                                let joinAction = UIAlertController(title: "Cannot Join", message: message, preferredStyle: UIAlertControllerStyle.alert)
                                joinAction.addAction(UIAlertAction(title: "Cancel", style: .default, handler: { (action: UIAlertAction!) in
                                    return
                                }))
                                self.present(joinAction, animated: true, completion: nil)
                                self.viewDidAppear(true)
                            } else {
                                if (response.response?.statusCode == 455) {
                                    let message:String = "Error"
                                    let joinAction = UIAlertController(title: "Cannot Join", message: message, preferredStyle: UIAlertControllerStyle.alert)
                                    joinAction.addAction(UIAlertAction(title: "Cancel", style: .default, handler: { (action: UIAlertAction!) in
                                        self.viewDidAppear(true)
                                    }))
                                    self.present(joinAction, animated: true, completion: nil)
                                } else {
                                    let message:String = "Error"
                                    let joinAction = UIAlertController(title: "Cannot Join", message: message, preferredStyle: UIAlertControllerStyle.alert)
                                    joinAction.addAction(UIAlertAction(title: "Cancel", style: .default, handler: { (action: UIAlertAction!) in
                                        self.viewDidAppear(true)
                                    }))
                                    self.present(joinAction, animated: true, completion: nil)
                                }
                            }
                        }
                }
            }))
        
            joinTeamAction.addAction(UIAlertAction(title: "Close", style: .default, handler: { (action: UIAlertAction!) in
                return
            }))
            self.present(joinTeamAction, animated: true, completion: nil)
        } else if tableView == self.teamMemberTableView {
            let dateFormatter:DateFormatter = DateFormatter()
            dateFormatter.dateFormat = "MMM dd, yyyy"
            let dateString:String = dateFormatter.string(from: self.TeamMemberArray[indexPath.row].inTeamSince)
            let message:String = "Name: " + self.TeamMemberArray[indexPath.row].username + "\nScore Today: " + String(self.TeamMemberArray[indexPath.row].todayScoreSoFar) + "\nTotal Score: " + String(self.TeamMemberArray[indexPath.row].userScore) + "\nMember Since: " + dateString
            var joinTeamAction = UIAlertController(title: "Member", message: message, preferredStyle: UIAlertControllerStyle.alert)
            if TeamMemberArray[indexPath.row].isLeader {
                joinTeamAction = UIAlertController(title: "Leader", message: message, preferredStyle: UIAlertControllerStyle.alert)
            }
            
            joinTeamAction.addAction(UIAlertAction(title: "Ok", style: .default, handler: { (action: UIAlertAction!) in
                return
            }))
            self.present(joinTeamAction, animated: true, completion: nil)
        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if tableView == self.myTableView {
            var numOfSections: Int = 0
            if ObjectsArray.count > 0
            {
                myTableView.separatorStyle = .singleLine
                numOfSections            = ObjectsArray.count
                myTableView.backgroundView = nil
            }
            else
            {
                let noDataLabel: UILabel     = UILabel(frame: CGRect(x: 0, y: 0, width: tableView.bounds.size.width, height: tableView.bounds.size.height))
                noDataLabel.text          = "No Team Requests"
                noDataLabel.textColor     = UIColor.black
                noDataLabel.textAlignment = .center
                myTableView.backgroundView  = noDataLabel
                myTableView.separatorStyle  = .none
            }
            return ObjectsArray.count
        } else if tableView == self.teamMemberTableView {
            var numOfSections: Int = 0
            if TeamMemberArray.count > 0
            {
                self.teamMemberTableView.separatorStyle = .singleLine
                numOfSections            = TeamMemberArray.count
                self.teamMemberTableView.backgroundView = nil
            }
            else
            {
                let noDataLabel: UILabel     = UILabel(frame: CGRect(x: 0, y: 0, width: tableView.bounds.size.width, height: tableView.bounds.size.height))
                noDataLabel.text          = "No Members"
                noDataLabel.textColor     = UIColor.black
                noDataLabel.textAlignment = .center
                self.teamMemberTableView.backgroundView  = noDataLabel
                self.teamMemberTableView.separatorStyle  = .none
            }
            return TeamMemberArray.count
        }
        return 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell: UITableViewCell = UITableViewCell(style: UITableViewCellStyle.subtitle, reuseIdentifier: "MyTestCell")
        if tableView == self.myTableView {
        
            cell.textLabel!.text = self.ObjectsArray[indexPath.row].teamName
            cell.detailTextLabel?.text = "Leader: \(self.ObjectsArray[indexPath.row].teamLeader)"
            cell.accessoryType = .disclosureIndicator
        } else if tableView == self.teamMemberTableView {
            
            if self.TeamMemberArray[indexPath.row].isLeader {
                cell.textLabel!.text = self.TeamMemberArray[indexPath.row].username + " - Leader"
            } else {
                cell.textLabel!.text = self.TeamMemberArray[indexPath.row].username
            }
            if self.TeamMemberArray[indexPath.row].username == Settings.usernameString {
                cell.textLabel!.text = cell.textLabel!.text! + " - (Me)"
            }
            if self.currentTeam.isLeader && (self.TeamMemberArray[indexPath.row].username != Settings.usernameString) {
                cell.accessoryType = .disclosureIndicator
            }
            cell.detailTextLabel?.text = "Today: \(self.TeamMemberArray[indexPath.row].todayScoreSoFar) - Total: \(self.TeamMemberArray[indexPath.row].userScore)"
        }
        return cell
    }
    
    func displayNoTeamItems() {
        createNewTeamButton.isHidden = false
        myTableView.isHidden = false
    }
    
    func hideNoTeamItems() {
        createNewTeamButton.isHidden = true
        myTableView.isHidden = true
    }
    
    func displayTeamMemberItems() {
        viewCompetitionButton.isHidden = false
        viewCompetitionHistoryButton.isHidden = false
        teamMemberTableView.isHidden = false
        teamMembersLabel.isHidden = false
        teamStatusLabel.isHidden = false
        addMembersButton.isHidden = true
        leaveTeamButton.isHidden = false
        removeTeamButton.isHidden = true
    }
    
    func displayTeamLeaderItems() {
        addMembersButton.isHidden = false
        leaveTeamButton.isHidden = true
        removeTeamButton.isHidden = false
    }
    
    func hideTeamItems() {
        viewCompetitionButton.isHidden = true
        viewCompetitionHistoryButton.isHidden = true
        teamMemberTableView.isHidden = true
        teamMembersLabel.isHidden = true
        teamStatusLabel.isHidden = true
        addMembersButton.isHidden = true
        leaveTeamButton.isHidden = true
        removeTeamButton.isHidden = true
    }
    
    func updateCommunityPage() {
        let headers = [JSONProtocolNames.usernameHeaderName: Settings.usernameString]
        
        Alamofire.request(Settings.getCommunityHomeURL(), method: .get, headers: headers).validate().responseJSON { response in
            switch response.result {
            case .success(let data):
                print("UPDATING HOME SCREEN")
                self.ObjectsArray = []
                let json = JSON(data)
                print(json)
                let inTeam:Bool = json[JSONProtocolNames.inTeamHeaderName].boolValue
                if inTeam {
                    let teamJSON:JSON = json[JSONProtocolNames.teamHeaderName]
                    let team = Team(json: teamJSON)
                    self.currentTeam = team
                    self.TeamMemberArray = []
                    for member in team.teamMembers {
                        print("FOUND MEMBER")
                        self.TeamMemberArray.append(member)
                    }
                    
                    self.teamNameLabel.text = team.teamName
                    if team.isLeader {
                        self.teamStatusLabel.text = self.isLeaderLabel
                    } else {
                        self.teamStatusLabel.text = self.isMemberLabel
                    }
                    print("STATUS: \(team.competitonStatus)")
                    if team.maxTeamSize > 0 {
                        self.teamMembersLabel.text = self.memberListLabel + " (Max: \(team.maxTeamSize)):"
                    } else {
                        self.teamMembersLabel.text = self.memberListLabel + " (Unlimited):"
                    }
                    switch team.competitonStatus {
                    case .None:
                        if team.isLeader {
                            self.viewCompetitionButton.setTitle(self.leaderNoCompetitionLabel, for: UIControlState.normal)
                            self.viewCompetitionButton.isEnabled = true
                        } else {
                            self.viewCompetitionButton.setTitle(self.memberNoCompetitionLabel, for: UIControlState.normal)
                            self.viewCompetitionButton.isEnabled = false
                        }
                    case .Pending:
                        self.viewCompetitionButton.setTitle(self.pendingCompetitionLabel, for: UIControlState.normal)
                        self.viewCompetitionButton.isEnabled = true
                    case .Active:
                        self.viewCompetitionButton.setTitle(self.currentCompetitionLabel, for: UIControlState.normal)
                        self.viewCompetitionButton.isEnabled = true
                    }
                    print("TEAM NAME: \(team.teamName)")
                    print("TEAM ID: \(team.teamID)")
                    print("TEAM COMPETITION ID: \(team.competitionID)")
                    self.hideNoTeamItems()
                    self.displayTeamMemberItems()
                    if team.isLeader {
                        self.displayTeamLeaderItems()
                    }
                    self.teamMemberTableView.reloadData()
                } else {
                    let invitationsJSON:Array<JSON> = json[JSONProtocolNames.invitationsHeaderName].arrayValue
                    for invitationJSON in invitationsJSON {
                        let item = TeamInvitation(json: invitationJSON)
                        self.ObjectsArray.append(item)
                        print("GOT INVITATION: \(item.teamName)")
                    }
                    self.teamNameLabel.text = self.noTeamLabel
                    self.hideTeamItems()
                    self.displayNoTeamItems()
                    self.myTableView.reloadData()
                }
                
                
                
            case .failure(let error):
                print("Request failed with error: \(error)")
            }
        }
        
    }
    
    override func viewDidAppear(_ animated: Bool) {
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
                    self.updateCommunityPage()
                    
                case .failure(let error):
                    print("Request failed with error: \(error)")
                }
            }
        } else {
            self.updateCommunityPage()
            print("Doesnt Need to Update Score")
        }
    }
    
}
