@VerifyUserOperations
Feature: HerokuApp API Validation

  @VerifyUserActivity
  Scenario: Perform operations using token
    Given I have a valid token
    Then I should be able to <action> user <username> with value <score>
		|POST|shri10|10| 
    And I should be able to <action> user <username> with value <score>
		|PUT|shri10|12|
    And I should be able to <action> user <username>
    |DELETE|shri10|
