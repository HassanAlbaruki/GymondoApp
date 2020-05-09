# GymondoApp
Gomondo App

User Story #1: List of Exercises:
According to the first task: ExersicesList I used the API: https://wger.de/api/v2/exercise/
But the response didn’t contain imageUrl or image Id
So, I used to call the API: https://wger.de/api/v2/exerciseimage which contain a list of images with exerciseID at the beginning in the first Activity before load the exercisesList
This API contain 204 elements so we need to call it 7 times if we want to access all images 
So we need to wait 3 or 4 seconds untel all images loaded 

Now we have a list of images and every item have exerciseID
We need to call the API:  https://wger.de/api/v2/muscle/ to store muscles list
And the API:  https://wger.de/api/v2/exercisecategory/ to store GategoriesList
And the API: https://wger.de/api/v2/equipment  to store EquipmenstList
Why should we call all of these APIs?
Because according to the first task: Every list item has:
a. The name of the category the exercise belongs to(but exerciseAPI contain categoryID)
b. The name of the exercise (provided with exerciseAPI)
c. An image of the exercise or a placeholder if no image available.(but exerciseAPI didn’t contain a reference of image )
d. The equipment needed for the exercise (comma separated), if provided.(but exersciseAPI contain array of equipmentsID)
e. The muscles trained by this exercise (comma separated), if provided. .(but exersciseAPI contain array of musclesID)

after getting all the required data I need to send them to my adapter to bind every exercise item
using for loop for images and muscles and equipment and categories
Suggestion: 
the best way to not calling all these APIs and use for loops to get exercise full item infos is backend level
exercise list API should contain exerciseId Integer, exerciseName String , exerciseCategoryNane String, 
equipments Array(String), muscles Array(String),imageurl String
this succession possibly fast up the response
now we have our exercises list ready and listed in the HomeActivity
we called nextpage function when we reach the end of our list and if response.next form the API contain a value (stop condition when response.next=null)

User Story #2: Exercise Detail:
In this task we should cal the API: exerciseinfo https://wger.de/api/v2/exerciseinfo 
But this API contain a list of exercises and didn’t contain exerciseID
Usually the best way for details or info API is to provide the exersiceId as a parameter and the response is an Exersice Object with all required data 
For Example: https://wger.de/api/v2/exerciseinfo?exersiceid=10
In our case the exercise info already grouped in the adapter so I used Bundle to send all exercise’s info to the ExerciseDetailsActivity
In the ExerciseDetailsActivity I read the data from Bundle and fetched them in textview or Imageview slider

User Story #3: Search :
I used the normal way in this task which is implementing Searchview interface 
And when the user search for an item the adapter will update the item according to the result 
Succession:
If we want to return all match cases we need to use seachAPI because in this scenario 
We can only search in the listed items,
For example, it the list contains 20 items the search can be only on these 20 items
If the list contains 100 items the search can be done only on these 100 items
What should we do if we want to search in all the items in the database?
We should have searchAPI , the parameter will be our string query and the response will be all items that contain our query with paging




User Story #4 – Filter: 
In our case we have 7 exercises body part 
I called the api : ExersicesList https://wger.de/api/v2/exercise/ and I added a category parameter according to user's select 
Example
If user select “Arms” ,arms id : 8 , so we call: https://wger.de/api/v2/exercise/?page=1&category=8 
The API will return all the exercises with category id: 8
Ok now what if the user wants clear the filter or get exercises form all categories again?
We should have a defult id of "All" because the only way to get exercises with all categories is to set categoryid=null
And this is not good to assign null value to an integer variable (Succession we can use ID for All categories: -1)
So, I assigned null value when the user selects “All” 
You may ask why I didn’t use this way for search task by using name parameter?
Actually, I tried to add name parameter to the API but I got nothing 😊


User Story #5 - UI Feedback 
I’m using retrofit library for Calling API and we have tow main functions for the response:
onSuccess: in this case the API called successfully so we can handle the response
onFailer: in this case the API called failed so I hide the activity default layout and show “no_internet” layout which contain an image and a text telling the user that something went worng and a button to retry 
retry button will retry to call the API again and if success the default layout will be shown and “no_internet” layout will be hide 
to test this scenario, you can turn off your internet connection 

added features:
swipe to refresh: we can wipe in the Home activity to refresh data
animation: the MainActivity elements will animate when we lunch the app 
Used Libraries:
'com.squareup.picasso:picasso:2.71828': Picasso Library for load images
'com.squareup.retrofit2:retrofit:2.1.0' Retrofit to for calling API
'com.squareup.retrofit2:converter-gson:2.1.0' Retrofit-gson to work with gson responce
'com.github.smarteist:autoimageslider:1.3.6' autoimageslider library to use image Slider

