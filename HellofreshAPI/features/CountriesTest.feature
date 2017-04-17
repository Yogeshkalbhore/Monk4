Feature: Test the Countries Rest APIs

  Scenario: Validate the all Countries API
    When We make http get call request for all Countries.
    Then Response code for this should be "200" OK for all Countries.
    And In returned Response these Countries code US, DE and GB should be present.

  Scenario Outline: Validate the each country (US, DE and GB) individually from Countries API
    When We make http get call request with individually Countries "<CountriesCode>".
    Then Response code for this should be "200" OK for individually Countries.
    And In returned Response these Countries elements should be present.

    Examples: 
      | CountriesCode |
      | US            |
      | DE            |
      | GB            |

  Scenario Outline: Validate the in-existent country individually from Countries API
    When We make http get call request for in-existent individually Countries "<CountriesCode>".
    Then Response code for this should be "200" OK for in-existent individually Countries.
    Then In returned Response these Countries elements should not be present.

    Examples: 
      | CountriesCode |
      | AA            |
      | AC            |
      | AB            |
      | CB            |

  Scenario: Validate the Country POST API
    When We make http POST call request for Specific Countries with Request.
    Then Response code for this should be "404" OK for Any POST Request for Countries.
    Then In returned Response these Countries should give 404 Not Found.
