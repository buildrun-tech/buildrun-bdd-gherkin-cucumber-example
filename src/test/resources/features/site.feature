Feature: Hello Site

  Scenario: Hi Google
    Given I want to access "https://google.com"
    When I access this website
    Then the website is correctly loaded