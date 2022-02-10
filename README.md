# Paws
Is an application for animal rescue managment system. It allow animal rescue group to keep track of all rescued animal and allow volunteers to easliy donate to the group to cover vet and care cost.

## **Introduction**

The application is developed to meet the requirements for the final project in the Kotlin Bootcamp - Tuwaiq 1000
* Multiple requirements include android components (notification, shared preference, implicit intent, etc..)
* Database connection through an API (implementing CRUD)
* Follow an architectural patter design MVVM

### Install

#### Command Line

Open terminal app and navigate to project folders

`$ cd /Users/user/project_folders`

Clone project repository

`$ git clone https://github.com/joud-almahdi/Project-Tuwaiq.git`

## **Prototype**

An example design to follow for the application 

[Figma Prototype](https://www.figma.com/file/3DMJ4eoKpGq6DUHjZwk7cp/Paws-Prototype?node-id=0%3A1)

## Flowchart

The following section covers the flow of the application

### Splash/Launcher

When the application is first launched, it checks if there is a network connection and then check if the user is logged in. If there is no network connection, then a dialog is displayed informing the user that they need it to access the app. And if the user had not logged in, then they are redirected to select to login or SignUp to the application.

<img src="https://github.com/SamPaddock/Project_Tuwaiq/blob/main/Screenshots/splash.jpg" width="200" />  <img src="https://github.com/SamPaddock/Project_Tuwaiq/blob/main/Screenshots/splash_redirect_to_account.jpg" width="200" />

### Account 

When the user selects either option from the previous interface, they are redirect to their select to either login or signup, where the email and password are validated while typing.

<img src="https://github.com/SamPaddock/Project_Tuwaiq/blob/main/Screenshots/account_login.jpg" width="200" />   <img src="https://github.com/SamPaddock/Project_Tuwaiq/blob/main/Screenshots/account_register1.jpg" width="200" />   <img src="https://github.com/SamPaddock/Project_Tuwaiq/blob/main/Screenshots/account_register2.jpg" width="200" />

### Main Page

When the user first logs in, a list of animals registered is displayed, they can filter through the list by the animal type or the status. At the bottom is a navigation bar that allows the user to view vet clinics and pet shops and the distances between them to the places. The user can also navigate to other pages through the slider menu accessible by clicking on the hamburger menu on the top left.

<img src="https://github.com/SamPaddock/Project_Tuwaiq/blob/main/Screenshots/main_animalresuce.jpg" width="200" /> <img src="https://github.com/SamPaddock/Project_Tuwaiq/blob/main/Screenshots/main_vets.jpg" width="200" /> <img src="https://github.com/SamPaddock/Project_Tuwaiq/blob/main/Screenshots/main_shops.jpg" width="200" />

#### Viewing an vendors information

When the user selects a vendor, their information is displayed with methods of contacting the vendor and viewing their social media and website.

<img src="https://github.com/SamPaddock/Project_Tuwaiq/blob/main/Screenshots/main_viewvendor.jpg" width="200" />

#### Adding/Editing an animal

A user can add an animal or edit their information to be able to track the condition and status of the animal and add notes regarding their grooming and medical needs.

<img src="https://github.com/SamPaddock/Project_Tuwaiq/blob/main/Screenshots/animal_add1.jpg" width="200" />   <img src="https://github.com/SamPaddock/Project_Tuwaiq/blob/main/Screenshots/animal_add2.jpg" width="200" />   <img src="https://github.com/SamPaddock/Project_Tuwaiq/blob/main/Screenshots/animal_add3.jpg" width="200" />

#### Viewing an animals information

When the user selects an animal, their information is displayed, including their grooming and medical needs, which volunteer added them, and under which charity is it with.

<img src="https://github.com/SamPaddock/Project_Tuwaiq/blob/main/Screenshots/animal_view.jpg" width="200" />

### Profile

A user can view their profile information and edit them to update it

<img src="https://github.com/SamPaddock/Project_Tuwaiq/blob/main/Screenshots/profile_main.jpg" width="200" />   <img src="https://github.com/SamPaddock/Project_Tuwaiq/blob/main/Screenshots/profile_edit.jpg" width="200" />

#### Adding/Editing a charity (Admin user)

An Admin user can add a charity from their profile or edit the charity information as it should not be accessiable for all users.

<img src="https://github.com/SamPaddock/Project_Tuwaiq/blob/main/Screenshots/charity_edit1.jpg" width="200" />   <img src="https://github.com/SamPaddock/Project_Tuwaiq/blob/main/Screenshots/charity_edit2.jpg" width="200" />

### Charity

A user can view a list of charities that are working together and can view their contact information.

<img src="https://github.com/SamPaddock/Project_Tuwaiq/blob/main/Screenshots/charity_main.jpg" width="200" />

#### Viewing a charitys information

When the user selects a charity, they can view their information. They can also view the STC pay, social media, and contact information.

<img src="https://github.com/SamPaddock/Project_Tuwaiq/blob/main/Screenshots/charity_view.jpg" width="200" />

### Fun Facts

Users can read fun facts about cats and they will get a notification when new facts are available.

<img src="https://github.com/SamPaddock/Project_Tuwaiq/blob/main/Screenshots/funfacts.jpg" width="200" />

## Sources and Technology
[MaterialDrawer](https://github.com/mikepenz/MaterialDrawer)
[splash animation](https://github.com/EyalBira/loading-dots)
[StateProgressBar](https://github.com/kofigyan/StateProgressBar)
[Picasso](https://github.com/square/picasso)
[Retrofit](https://github.com/square/retrofit)
[Firebase](https://console.firebase.google.com/)
[Cat Facts API](https://catfact.ninja)


## Licence
The content of this repository is licensed under a [Creative Commons Attribution License](https://creativecommons.org/licenses/by/3.0/us/) by Udacity

