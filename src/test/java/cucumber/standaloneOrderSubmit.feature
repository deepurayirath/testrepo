
@tag
Feature: Purchase order from Eccomerce
  I want to use this template for my feature file

Background:
Given I landed on ecommerce Page

  @tag2
  Scenario Outline: Postivecase for submit order
    Given I login to ecom app with <Username> and <Password>
    When I add <Product> to the cart
    And I verify the <Product> on cart and submit the order
    Then "Thankyou for the order." message is displayed on confirmation page

    Examples: 
      | Username 						| Password | Product     |
      | deepu9656@gmail.com |  Pa$$w0rd| ZARA COAT 3 |
      
