## ...............Short points.................
-Network call cannot be made of main thread,it will give error,in other words we can't do 
 HttpConnection without Async or loaders.

-make sure you got internet permission in manifest,sometimes its the biggest error we are searching for. 
-AsyncTask is almost obselete ...loaders is the new thing.
-even when if AsynTask was used. it was good for task that completes in less then 100ms after that app crashes.
-AsyncTask is an abstract class.
-Generic's with Async are just place holders and are used by some functions like doInBackground and onPostExecute as parameter type or return type.
-we call execute function on the Async obj in OnCreate().
-execute may have no parameter at all
-execute functions parameters are actually doInBackground functions parameters.

-results returned from doInBackground function are actually input parameters of the onPostExecute function,therefore
 the return type of doInBackground and input type of onPostExecute should be the same,by the way they already are same by design.

-Using String url we make Url obj ,then using that url object we make HttpConnection and get the stream of 0's and 1's
 this stream is then converted to character lines using bufferReader. all lines are combined and final json response is formed.
 this json response is then parsed and list of objects or desire data is extracted.
 
 ## ................OverView.....................................
 So this project demonstrates the process of getting some data from the internet to your app, by the way the project is about earthquakes.
 we have included recyclerView to display the list of earthquakes and their data.The networking part of this app is really just getting data for the adapter of the recyclerView.
 getting data from internet means making a network call and this is not allowed on the mainThread/UI Thread by android. so we have to find a way to do this on background thread. for this purpose at first we used the AsyncTask but that has problems which are identified by android itself for example one problem is ,it can't handle orientation change very well.
 so android have provide an alternate called loaders .Loaders are the new "Async's". they addresses the problems in AsyncTasks.so we have used loaders in this project.but the project is also implemented with Async,which is still available in some previous commit.
##  ..................Summary............................
 so this app code can be broken into two parts apart from "recyclerView".
-part one is making Network calls and getting data.
-part two is making a mechanism to manage the process of making network calls, in short implementing loaders.
          
##           ....Making Network calls and getting data............
for this purpose we defined a class called Utils .this calls holds methods to make network calls .its constructor is private because we
don't want to make its object.
### - fetchDataFromUrl()
                  takes in string url.
                  makes url object out of it.
                  make call to another function called makeHttpRequest() and pass in the url object.
                  
### - makeHttpRequest()
                  uses url.openConnection method to get HttpConnection object.
                  makes connect call on that httpConnection object.
                  checks if response is 200(0k),if yes than get the input stream.
                  inputStream is data in form of 1's and 0's ,this is the form in which data is sent by Api websites.
                  this inputStream is further sent as a parameter to another function called readFromTheStream()
### -readFromtheStream()
                  uses classes like InputStreamReader and Buffer reader to convert stream of 1's and 0's to readable character lines.
                  another class String Builder is used to append the lines. StringBuilder is specialize form of String to append lines.
### -parsingJsonToMakeObjects()
                  this function is called by fetchDataFromUrl() and passed in "JsonResponse string" created by readFromStream().
                  this makes Json object from that response and then traverse through the data to get the desired data to make obj.
                  finally list of earthquake objs is made and then returned.
                  
##        ..... making a mechanism to manage the process of making network calls............
this simply is about implementing loaders tech. so this involve three steps.
-step one is creating a loader class and defining doInBackground method in it.
-step two is provinding body for callback methods of Loader manager. in short defining loader manager.
-Step three is initating a loader with specific id from MainActivity.
 
##  ...............creating a loader class and defining doInBackground method............
 so in this step we need to define a loader class. this is done by extending that class from AsyncLoader<D> class. this AsyncLoader<D> class is abstract and we need to provide body for its function named "<D> loadInBackground() .this loadInBackground will return <D> on
 completion. so this loader is initiated when ever its obj is made and loadInBackground() function is called automatically when obj is made. well technically onStartLoading is called first.
 at first we defined this loader class as an inner class inside MainActivity which contains the methods of LoaderManager which deals with this loader class ,but then this was creating problems and giving errors like non static inner class obj make memory leaks etc.
 so we transfered it and made it an external class.
 also please methods from support library for loaders.
##  ...............provinding body for only callback methods of Loader manager..........................
 so in this part we need to define only some functions of Loadermanager to manage loaders. this is done by implementing an interface called LoaderManager.LoaderCallbacks<D> . this interface has three methods that we will provide implementation for .
###  -<D> onCreateLoader()
                here we simple make obj of the loader class we define ,as are result loadInBackground() method is invoked.
                onCreateLoader() return <D> type result. this result is simply sent by android itself as input for next function                         onLoadFinished.
 ###  -onLoadFinished() 
                this will get result from onCreateLoader() and transfer it to where needed.in our case its adapter.
 ###  -onLoaderReset
                here we can clear data recieved from this loader.
                
 ##  .....................initating a loader from MainActivity.......................
  this is final step ,we have already defined a loader and also Loader manager callBack function for it. now its time we invoke the  loader. this is done by getting loaderManager First through getSupportLoaderManager() call in onCreate().
 -initLoader(id,input bundle,callBacks obj);
         now call initLoader(EARTHQUAKE_LOADER_ID, null, this) function of this loadermanager obj.
        this initLoader has three parameters ,id which is unique for every loader ,this init function checks if loader with this id              exists? if yes lauch it otherwise launch callBack methods in obj which provides in third parameter.
        second parameter is bundle of inputs ,usually set to null.
        third parameter is the object that implemented interface LoaderManager.CallBacks ,in our case its MainActivity.
  
                
 
 
 
 
 
 
 
 
 
 
