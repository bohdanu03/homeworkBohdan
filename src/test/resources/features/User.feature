Feature: Users API


  Background: User same step
    Given client has  Users endpoint

  Scenario: List Users API
    When client sends GET request to List Users
    Then client validate status code 200
    And client validate users count,All users names, ids, avatar URLs:
      | id | email                      | first_name | last_name | avatar                                   |
      | 1  | george.bluth@reqres.in     | George     | Bluth     | https://reqres.in/img/faces/1-image.jpg  |
      | 2  | janet.weaver@reqres.in     | Janet      | Weaver    | https://reqres.in/img/faces/2-image.jpg  |
      | 3  | emma.wong@reqres.in        | Emma       | Wong      | https://reqres.in/img/faces/3-image.jpg  |
      | 4  | eve.holt@reqres.in         | Eve        | Holt      | https://reqres.in/img/faces/4-image.jpg  |
      | 5  | charles.morris@reqres.in   | Charles    | Morris    | https://reqres.in/img/faces/5-image.jpg  |
      | 6  | tracey.ramos@reqres.in     | Tracey     | Ramos     | https://reqres.in/img/faces/6-image.jpg  |
      | 7  | michael.lawson@reqres.in   | Michael    | Lawson    | https://reqres.in/img/faces/7-image.jpg  |
      | 8  | lindsay.ferguson@reqres.in | Lindsay    | Ferguson  | https://reqres.in/img/faces/8-image.jpg  |
      | 9  | tobias.funke@reqres.in     | Tobias     | Funke     | https://reqres.in/img/faces/9-image.jpg  |
      | 10 | byron.fields@reqres.in     | Byron      | Fields    | https://reqres.in/img/faces/10-image.jpg |
      | 11 | george.edwards@reqres.in   | George     | Edwards   | https://reqres.in/img/faces/11-image.jpg |
      | 12 | rachel.howell@reqres.in    | Rachel     | Howell    | https://reqres.in/img/faces/12-image.jpg |


  Scenario Outline: Single Users API
    When client sends GET request with <id> to obtain user info
    Then client validate status code 200
    And client validate supportURL 'https://reqres.in/#support-heading'
    And client validate user <id>,'<first_name>','<last_name>','<avatar>':
    Examples:
      | id | first_name | last_name | avatar                                   |
      | 1  | George     | Bluth     | https://reqres.in/img/faces/1-image.jpg  |
      | 2  | Janet      | Weaver    | https://reqres.in/img/faces/2-image.jpg  |
      | 3  | Emma       | Wong      | https://reqres.in/img/faces/3-image.jpg  |
      | 4  | Eve        | Holt      | https://reqres.in/img/faces/4-image.jpg  |
      | 5  | Charles    | Morris    | https://reqres.in/img/faces/5-image.jpg  |
      | 6  | Tracey     | Ramos     | https://reqres.in/img/faces/6-image.jpg  |
      | 7  | Michael    | Lawson    | https://reqres.in/img/faces/7-image.jpg  |
      | 8  | Lindsay    | Ferguson  | https://reqres.in/img/faces/8-image.jpg  |
      | 9  | Tobias     | Funke     | https://reqres.in/img/faces/9-image.jpg  |
      | 10 | Byron      | Fields    | https://reqres.in/img/faces/10-image.jpg |
      | 11 | George     | Edwards   | https://reqres.in/img/faces/11-image.jpg |
      | 12 | Rachel     | Howell    | https://reqres.in/img/faces/12-image.jpg |


  Scenario: Single User Not Found API
    When client sends GET request with no existing id 23
    Then client validate status code 404
    And client validate empty response body