//
//  JSONParser.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/1/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

class JSONParser {
    
    static func getJSONObjectFromString2(jsonString: String)->[String: Any] {
        let data = jsonString.data(using: .utf8)!
        let json = try? JSONSerialization.jsonObject(with: data) as? [String: Any]
        return json!!
    }
    
    static func getJSONObjectFromString(jsonString: String)->JSON {
        let json = JSON(data: jsonString.data(using: .utf8)!)
        //print(type(of: json))
        return json
    }
    
    
    
    static func testJSON() {
        let jsonString = "{" +
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
        let testGetScheduleItemResponse = "{\"Schedule-Items\": [{\"Schedule-Item-ID\": 1, \"Recurring-ID\": 2, \"Recurring-Type\": 3, \"Recurring-Value\":[1,3,4], \"Ending-Type\": 1, \"Ending-Value\": 5, \"Schedule-Item-Title\": \"TEST\", \"Schedule-Item-Description\": \"DESCRIPTION\", \"Schedule-Item-Type\": \"medication\", \"Schedule-Item-Start\": \"12:00:00\", \"Schedule-Item-Progress-Type\": \"boolean\", \"Schedule-Item-Duration\": -1, \"Schedule-Item-Progress\": 0.0, \"Schedule-Item-Score\": 2.0, \"Schedule-Item-Active\": true, \"Schedule-Item-Color\": [150, 150, 150, 255], \"Schedule-Item-Modifiable\": true}]}"
        let json = getJSONObjectFromString(jsonString: testGetScheduleItemResponse)
        print(json["Schedule-Items"].count)
        let scheduleItem = (json["Schedule-Items"].arrayValue)[0]
        print("HELLO")
        let scheduleItemObject = ScheduleItem(json: scheduleItem)
        print(scheduleItemObject.scheduleItemDescription)
        print(scheduleItemObject.recurringID)
        
        Requests.addScheduleItemJSON(item: scheduleItemObject)
    }
}
