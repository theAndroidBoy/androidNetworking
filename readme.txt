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

