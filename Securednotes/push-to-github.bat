@echo off
echo ========================================
echo Push OJT Tracker to GitHub
echo ========================================
echo.

echo Step 1: Initializing Git repository...
git init
echo.

echo Step 2: Adding all files...
git add .
echo.

echo Step 3: Creating initial commit...
git commit -m "Initial commit: Complete OJT Tracker Android App with GitHub Actions"
echo.

echo Step 4: Setting up remote repository...
echo.
echo IMPORTANT: Before running this step, you need to:
echo 1. Go to https://github.com
echo 2. Create a new repository named "ojt-tracker"
echo 3. Copy the repository URL
echo 4. Replace YOUR_USERNAME below with your GitHub username
echo.
echo Then run these commands manually:
echo.
echo git remote add origin https://github.com/YOUR_USERNAME/ojt-tracker.git
echo git branch -M main
echo git push -u origin main
echo.
echo After pushing, go to:
echo https://github.com/YOUR_USERNAME/ojt-tracker/actions
echo.
echo Wait for the build to complete, then download your APK!
echo.
pause
