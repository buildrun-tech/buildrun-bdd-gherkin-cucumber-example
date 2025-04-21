Feature: Unbook a room

  As as customer,
  I want to unbook a room
  so that i can reschedule my agenda when something occurs

  Scenario: Unbook a booked room
    Given the room "Sala América do Norte" exists
    And the room has no bookings for today
    And I book the room for one hour from now
    And I get the booking number
    When I try to unbook the room
    Then the room should be successfully unbooked

  Scenario: Unbook a booked room after the start time
    Given the room "Sala América do Norte" exists
    And the room has no bookings for today
    And I book the room for right now
    And I get the booking number
    And the booking time has already passed
    When I try to unbook the room
    Then the room cannot be unbooked