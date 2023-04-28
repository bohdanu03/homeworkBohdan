Feature: Resource API


  Background: Resource same step
    Given user has List Resources endpoint

  Scenario: List Resources API
    When user sends GET request to List Resources
    Then user validate status code 200
    And user validate Sum of ids equals to 78
    And user validate Avg of years equals to 2005.5


  Scenario Outline: Single Resource API
    When user sends GET request with <id> to obtain resource info
    Then user validate status code 200
    And validate response body <id>, <year>, '<color>', '<support>'
    Examples:
      | id | year | color   | support                                                                  |
      | 1  | 2000 | #98B2D1 | To keep ReqRes free, contributions towards server costs are appreciated! |
      | 2  | 2001 | #C74375 | To keep ReqRes free, contributions towards server costs are appreciated! |
      | 3  | 2002 | #BF1932 | To keep ReqRes free, contributions towards server costs are appreciated! |
      | 4  | 2003 | #7BC4C4 | To keep ReqRes free, contributions towards server costs are appreciated! |
      | 5  | 2004 | #E2583E | To keep ReqRes free, contributions towards server costs are appreciated! |
      | 6  | 2005 | #53B0AE | To keep ReqRes free, contributions towards server costs are appreciated! |
      | 7  | 2006 | #DECDBE | To keep ReqRes free, contributions towards server costs are appreciated! |
      | 8  | 2007 | #9B1B30 | To keep ReqRes free, contributions towards server costs are appreciated! |
      | 9  | 2008 | #5A5B9F | To keep ReqRes free, contributions towards server costs are appreciated! |
      | 10 | 2009 | #F0C05A | To keep ReqRes free, contributions towards server costs are appreciated! |
      | 11 | 2010 | #45B5AA | To keep ReqRes free, contributions towards server costs are appreciated! |
      | 12 | 2011 | #D94F70 | To keep ReqRes free, contributions towards server costs are appreciated! |

  Scenario: Single Resources Not Found API
    When user sends GET request with no existing id 23
    Then user validate status code 404
    And user validate empty response body