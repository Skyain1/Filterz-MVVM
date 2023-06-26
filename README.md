# Filterz [MVVM, Koin, Glide]
This repository is made according to the course, to improve the skills of applying the pattern in practice

## Screenshots
Screenshot of each window

<img src="app/src/main/res/raw/screen_main.jpg"  height="500"></img>
<img src="app/src/main/res/raw/screen_make_filter.jpg" height="500" ></img>

<img src="app/src/main/res/raw/screen_save.jpg" height="500" ></img>
<img src="app/src/main/res/raw/screen_saved_images.jpg" height="500" ></img>

## Structure

#### Model
The model is presented in the form of two repositories that provide suspend functions for receiving data
- `EditImageRepositoryImpl` - provides filters for photos
- `SavedImageRepositoryImpl` - provides already saved photos after processing

#### View
View are classic activities
- `Activity` 
- `Adapter`
  
#### ViewModel
ViewModels contain the logic of their screens
 - `EditImageViewModel`
 - `SavedImageViewModel`
