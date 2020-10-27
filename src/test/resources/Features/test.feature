Feature: Test simple Get service

  @Scenario1

  Scenario Outline:
    Given get the "<url>" for testing
    Then hit the url and validate the response
    Then write the response in the output folder
    Then validate the "<firstName>" and "<lastName>"
    Examples:
      | url                                | firstName | lastName |
      | https://reqres.in/api/users?page=2 | Michael   | Lawson   |

  @Scenario2
  Scenario Outline: Post Test
    Given get the post "<Posturl>" for testing
    Then post the url and validate the response
    Examples:
      | Posturl                                       |
      | http://dummy.restapiexample.com/api/v1/create |

  @Scenario3

  Scenario Outline: Open browser and navigate to url
    Given Launch the chrome browser and navigate to "<Amazon>"
    Then search for the samsung phone "<Phone>"

    Examples:
      | Amazon                  | Phone             |
      | https://www.amazon.com/ | samsung s20 phone |