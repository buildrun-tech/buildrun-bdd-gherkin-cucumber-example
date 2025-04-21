Feature: Rooms

  Narrative:
    As a Customer,
    I want to know all the available rooms
    so that i can book then

  Scenario Outline: List rooms
    When i list all the rooms
    Then we detect that the room <name> exists

    Examples:
    | name  |
    | "Sala América do Sul"     |
    | "Sala Europa"     |
    | "Sala América do Norte"     |
    | "Sala África"     |