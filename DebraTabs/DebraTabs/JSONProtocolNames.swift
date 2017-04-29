//
//  JSONProtocolNames.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 2/1/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

class JSONProtocolNames {
    // Schedule Item Types
    static let scheduleItemTypeHeaderName:String = "Schedule-Item-Type"
    static let medication:String = "medication"
    static let exercise:String = "exercise"
    static let glucoseLevel:String = "blood_glucose_measurement"
    static let eating:String = "eating"
    static let none:String = ""
    
    static let scheduleItemTypeMap:Dictionary<ScheduleItemType, String> =
        [.Medication: "medication",
         .Exercise: "exercise",
         .GlucoseLevel: "blood_glucose_measurement",
         .Eating: "eating",
         .None: "none"]
    
    // Recurring Item Types
    static let recurringTypeHeaderName:String = "Recurring-Type"
    static let notRecurring:Int = -1
    static let daily:Int = 0
    static let everyXDays:Int = 1
    static let certainDaysOfWeek:Int = 2
    static let weekly:Int = 3
    static let monthly:Int = 4
    
    static let recurringTypeMap:Dictionary<RecurringType, Int> =
        [.NotRecurring: -1,
         .Daily: 0,
         .EveryXDays: 1,
         .CertainDaysOfWeek: 2,
         .Weekly: 3,
         .Monthly: 4]
    
    // Ending Type
    static let endingTypeHeaderName:String = "Ending-Type"
    static let never:Int = 0
    static let afterYOccurrences:Int = 1
    static let onT:Int = 2
    static let endingTypeNotNeeded:String = "-1"
    
    static let endingTypeMap:Dictionary<EndingType, Int> =
        [.NotRecurring: -1,
         .Never: 0,
         .AfterYOccurrences: 1,
         .OnT: 2]
    
    // Progress Type
    static let scheduleItemProgressTypeHeaderName:String = "Schedule-Item-Progress-Type"
    static let boolean:String = "boolean"
    static let percentage:String = "percentage"
    
    static let durationProgressTypeNotNeeded:Int = -1
    
    static let progressTypeMap:Dictionary<ProgressType, String> =
        [.Boolean: "boolean",
         .Percentage: "percentage"]
    
    static let scheduleItemIDHeaderName:String = "Schedule-Item-ID"
    static let recurringIDHeaderName:String = "Recurring-ID"
    static let recurringValueHeaderName:String = "Recurring-Value"
    static let endingValueHeaderName:String = "Ending-Value"
    static let scheduleItemTitleHeaderName:String = "Schedule-Item-Title"
    static let scheduleItemDescriptionHeaderName:String = "Schedule-Item-Description"
    static let scheduleItemStartHeaderName:String = "Schedule-Item-Start"
    static let scheduleItemDurationHeaderName:String = "Schedule-Item-Duration"
    static let scheduleItemProgressHeaderName:String = "Schedule-Item-Progress"
    static let scheduleItemScoreHeaderName:String = "Schedule-Item-Score"
    static let scheduleItemActiveHeaderName:String = "Schedule-Item-Active"
    static let scheduleItemColorHeaderName:String = "Schedule-Item-Color"
    static let scheduleItemModifiableHeaderName:String = "Schedule-Item-Modifiable"
    static let scheduleItemCheckedInAtStartHeaderName:String = "Schedule-Item-Checked-In-At-Start"
    
    static let scheduleItemsListResponseName:String = "Daily-Items"  // Daily-Items
    
    static let scheduleItemsAddScheduleItemName:String = "Add-Schedule-Item"
    
    static let scheduleItemsEditScheduleItemName:String = "Edit-Schedule-Item"
    
    static let scheduleItemsRemoveScheduleItemName:String = "Remove-Schedule-Item"
    
    static let monthDayHeaderName:String = "Date"
    static let monthDayScoreHeaderName:String = "Daily-Score"
    static let monthDayColorHeaderName:String = "Daily-Color"
    
    static let monthListHeaderName:String = "Day-Info"
    
    static let passwordHeaderName:String = "Password"
    static let usernameHeaderName:String = "Username"
    static let dateHeaderName:String = "Date"
    static let yearHeaderName:String = "Year"
    static let monthHeaderName:String = "Month"
    static let progressHeaderName:String = "Progress"
    static let lastDayCHeckedHeaderName:String = "Last-Day-Checked"
    static let userScoreHeaderName:String = "User-Score"
    static let todayScoreSoFarHeaderName:String = "Today-Score-So-Far"
    
    static let scheduleItemTitleMaxLength:Int = 128
    static let scheduleItemDescriptionLength:Int = 1024
    
    static let inTeamHeaderName:String = "In-Team"
    static let invitationsHeaderName:String = "Invitations"
    static let teamHeaderName:String = "Team"
    static let teamNameHeaderName:String = "Team-Name"
    static let teamIDHeaderName:String = "Team-ID"
    static let teamLeaderHeaderName:String = "Team-Leader"
    static let teamCreatedHeaderName:String = "Team-Created"
    static let isLeaderHeaderName:String = "Is-Leader"
    static let maxTeamSizeHeaderName:String = "Max-Team-Size"
    static let teanSizeHeaderName:String = "Team-Size"
    static let teamMembersHeaderName:String = "Team-Members"
    static let inTeamSinceHeaderName:String = "In-Team-Since"
    static let teamHistoryHeaderName:String = "Team-History"
    
    static let competitionNameHeaderName:String = "Competition-Name"
    static let competitionResultHeaderName:String = "Competition-Result"
    static let statsHeaderName:String = "Stats"
    static let teamRedNameHeaderName:String = "Team-Red-Name"
    static let teamRedScoreHeaderName:String = "Team-Red-Score"
    static let teamRedLeftHeaderName:String = "Team-Red-Left"
    static let teamBlueNameHeaderName:String = "Team-Blue-Name"
    static let teamBlueScoreHeaderName:String = "Team-Blue-Score"
    static let teamBlueLeftHeaderName:String = "Team-Blue-Left"
    static let competitionStartDateHeaderName:String = "Competition-Start-Date"
    static let competitionEndDateHeaderName:String = "Competition-End-Date"
    static let competitionStatusHeaderName:String = "Competition-Status"
    static let competitionIDHeaderName:String = "Competition-ID"
    static let competitionInvitationsHeaderName:String = "Competition-Invitations"
    static let otherTeamNameHeaderName:String = "Other-Team-Name"
    static let otherTeamSizeHeaderName:String = "Other-Team-Size"
    static let otherTeamLeaderHeaderName:String = "Other-Team-Leader"
    static let otherTeamColorHeaderName:String = "Other-Team-Color"
    static let usersInvitedHeaderName:String = "Users-Invited"
    static let memberUsernameHeaderName:String = "Member-Username"
    static let teamRedIDHeaderName:String = "Team-Red-ID"
    static let teamRedMemberScoresHeaderName:String = "Team-Red-Member-Scores"
    static let teamBlueIDHeaderName:String = "Team-Blue-Score"
    static let teamBlueMemberScoresHeaderName:String = "Team-Blue-Member-Scores"
    static let userCompetitionScoreHeaderName:String = "User-Competition-Score"
    static let showTeamMembersHeaderName:String = "Show-Team-Members"
    static let teamColorHeaderName:String = "Team-Color"
    static let stepsHeaderName:String = "Steps"
    
    static let noneCompetition:String = "none"
    static let pendingCompetition:String = "pending"
    static let activeCompetition:String = "active"
    
    static let competitionStatusMap:Dictionary<CompetitionStatus, String> =
        [.None: noneCompetition,
         .Pending: pendingCompetition,
         .Active: activeCompetition]
    
    static let red:String = "red"
    static let blue:String = "blue"
    
    static let teamColorMap:Dictionary<TeamColor, String> =
        [.Red: red,
         .Blue: blue]
    
    static let won:String = "won"
    static let tied:String = "tied"
    static let lost:String = "lost"
    
    static let competitionResult:Dictionary<CompetitionResult, String> =
        [.Won: won,
         .Tied: tied,
         .Lost: lost]
    
    
}
