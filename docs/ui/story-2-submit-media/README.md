### User story - Submit media metadata as publisher(FR9) 

### User story
As a publisher, I want to submit my media's metadata (title, author, synopsis) to the database, 
so that the community has accurate information when they discuss and review it.

Acceptance criterion
Given a registered publisher is on the submission portal, when they fill out all required metadata fields and click "Submit", 
then the system validates that the title is not a duplicate. 

Given a registered publisher is on the submission portal, when they fill out all required metadata fields and click "Submit", 
then the system routes the details to a queue. 

Given a registered publisher is on the submission portal, when they fill out all required metadata fields and click "Submit", 
then the system displays a message stating: "Your request has been submitted for review. 

### Main flow
1. Publisher navigates to the submission portal.
2. System verifies publisher status.
3. Publisher enters the media's metadata.
4. System verifies that the entries in each field are in the correct format.
5. System checks the database to see if media with the same title and info already exists.
6. System confirms that the title is not a duplicate.
7. System displays the message "Request has been submitted for review."

### Alternative flow 1 – Required metadata is missing or invalid
1. At step 4, the system displays an error message for invalid metadata.
2. Publisher corrects the error and resubmits.
3. System goes back to the main flow.

### Alternative flow 2 – Duplicate title is detected
1. At step 5, the system detects a duplicate title and displays an error message.
2. Publisher goes back, changes the title, and resubmits.
3. System goes back to the main flow.

### Alternative flow 3 – Publisher is not authenticated
1. At step 2, publisher status verification fails.
2. System redirects the publisher to the Login page.
3. Login is successful.
4. System resumes the main flow.

## Screens



