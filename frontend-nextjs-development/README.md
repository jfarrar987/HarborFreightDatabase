## Development Setup
First, clone the repository and open the directory in GitHub Desktop. Enter the terminal and run the following command: ```npm install next```

## Running the Development Environment
Assuming the next install completed,
and all the necessary postgres configuration is setup, run the ```HFDB-API``` instance, then in the ```frontendext-nextjs``` terminal, type ```npm run dev``` to start the development environment. This will start the front end on port 3000. Do note that the API can only tag CORS patterns originating from IP addresses as acceptable. In order for the front end to function correctly when making API calls, you must navigate to it using the IP address provided to the API. For example: ```http://127.0.0.1:3000``` works, but ```http://localhost:3000``` does not. Once that is done, you should end up on the landing page.

## Making edits in React
Once the page is running properly, you can edit on your site instance, and it will automatically rerender the page when you save any file relating to it. If it doesn't rerender you may need to save *all* the related files.

## Helpful extensions / VSCode shortcuts
You'll definitely want to install the React Developer Tools. This will help you see the React components which comprise a page. I recommend you use a Chromium Browser (Edge/Firefox), and in the DevTools you should see a tab for the React Developer Tools.

Also, you should set up Emmet in VSCode: Go to File > Preferences > Settings and search for "emmet". Under Emmet: Include Languages, click on "Add Item" and enter the following values into their respective fields: ```Item:javascript``` and ```Value:javascriptreact```. This shortcut allows you to quickly create divs. For example, if you (in Visual Studio Code) type ```.example``` and press Enter, it will replace the text with ```<div className = "example"></div>```. This is a very fast way to create divs which are easily indentified for CSS.
