# Pills and Capsules Analyser
This program's purpose is to identify pills in a given image. <br>
It has many features and options the user can configure to accurately find the types of pills/capsules the user is searching for. <br>
It shows the original image on the left of the program with the modified image that highlights detected pills on the right side of the screen.

# Options 
<b>Check Fields to see Metrics used to Calculate some Options' Outputs</b>
- Load Image - loads an image from a file. <br>
- Revert to Original - Reverts changes made to modified image <br>
-------------------------------------------------------------------------------------------------------------------------------------------------------------------  
- Black and White - Shows detected pills in black and white, calculated using the colour picker and HSB thresholds. <br> 
- Black and White + Noise Reduction - Same as previous, but minimum/maximum size and boundary are used to get a more accurate result. <br> 
- Black and White (Two-Tone) - Find two-tone pills (pills with two different coloured parts) using colour picker/HSB thresholds and distance. <br> 
- Black and White (Two-Tone) + Noise Reduction - Same as previous, but minimum/maximum size and boundary are used to get a more accurate result. <br> 
- Draw Rectangles (for Current Selection) - Draws a rectangle around all detected pills in the current selection (Anything found in the previous options). <br>
- Sequential Ordering (for Current Selection) - Same as previous, but numbers each rectangle, ordered from the top left of the image to the bottom right. <br>
------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
- Reset total pill collection - Deletes all pill types in the total pill collection - note that the current selection will NOT be deleted. <br>
- Add current selection to pill selection - Adds the current selection to the total pill collection with the name entered in the name/type field. <br>
------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
- View Black and White (Pill Collection) - Highlights the entire pill collection (excluding current selection) in black and white. <br>
- Draw Rectangles - Draws a rectangle around all detected pills in the total pill collection. <br>
- Sequential Ordering - Same as previous, but numbers each rectangle, ordered from the top left of the image to the bottom right. <br>
- Sample Colour Image - Highlights the entire pill collection (excluding current selection) in the each pills' respective colours. <br>
- Random Colour Image - Highlights the entire pill collection (excluding current selection) with random colours for each pill. <br>

# Fields
- Hue Threshold - The amount the hue of a pill can differ from the colour selected in the colour picker. <br>
- Saturation Threshold - The amount the saturation of a pill can differ from the colour selected in the colour picker. <br>
- Brightness Threshold - The amount the brightness of a pill can differ from the colour selected in the colour picker. <br>
------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
- Colour Picker - Left click picks the main colour for the pills being searched for, and right click chooses the secondary colour (only relevant with two-tone pills). <br>
------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
- Minimum Size - The minimum size a cluster of pixels needs to be to be considered a pill (measured in pixels). <br>
- Maximum Size - The maximum size a cluster of pixels needs to be to be considered a pill (measured in pixels). <br>
- Minimum Boundary - The minimum width and height a cluster of pixels needs to be to be considered a pill (measured in pixels). <br>
- Maximum Boundary - The maximum width and height a cluster of pixels needs to be to be considered a pill (measured in pixels). <br>
------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
- Distance - The maximum distance between two clusters of pixels to be joined together and counted as one complete pill (measured in pixels + only for two-tone pills) <br>
- Name/Type - The name of the type of pills identified in the current selection. <br>

# Other Information
- Pills Detected - Shows number of pills detected. <br>
- Summary of Pills - Shows a small image of each pill found in the total pill collection, sorted by name/type and given a number (ordered from the top left of the image to the bottom right).
