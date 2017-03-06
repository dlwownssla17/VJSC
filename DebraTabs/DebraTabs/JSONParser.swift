//
//  JSONParser.swift
//  DebraTabs
//
//  Created by Spiro Metaxas on 3/1/17.
//  Copyright © 2017 Spiro Metaxas. All rights reserved.
//

import Foundation

class JSONParser {
    
    static func getJSONObjectFromString(jsonString: String)->[String: Any] {
        let data = jsonString.data(using: .utf8)!
        let json = try? JSONSerialization.jsonObject(with: data) as? [String: Any]
        return json!!
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
        let json = getJSONObjectFromString(jsonString: jsonString)
        print(json["Language"]!)
    }
}
