# scheduler
automatic scheduler

Need a tool to make a schedule for me


## What are the inputs it needs 
### Events: ( should events have priority) no cz u either attend them or you don't. 
when getting the event, ask if it is a recurring event, if yes, then ask till when, and add multiple rows with proper date.
# Name | when | Time | Duration | Travel via ? Bus, Uber | 
CV | tuesday | 9.45 | 1 | 
CV | thursday | 9.45 | 1 | 
TIS | tuesday | 1400 | 1.30|
TIS | thursday | 1400 | 1.30|
SS tuesday | 1530 | 1.30|
SS | thursday | 1530 | 1.30|


## Projects:
# Name | Required hours | Deadline | completed_hours |  
CC Quiz | 8 | 
Everyday activities :
Name | from |  to |  continuous_hours | 
sleep | 22.30 | 5.30 | 7 |
lunch | 1300 | 1330
dinner | 2000 | 2030|
call home | --> there would be activities which are to be done in a day, but have no assigned time
TV |
relax | 
SSS |


### Daily schedule 
how : 
find the wake up time
1.  get {from ,to } for every daily activity and events (merge if overlap ?? ) for simplicity might change this later on
2. sort the intervals wrt to ascending order of start time 
3. generate intervals with 15 mins gap (this is because if you can not find 15 mins between two intervals then, no point in putting a new interval there.
4. Fill the intervals with (projects, daily activities which do not have an assigned time)
    1. get all the projects with ascending order in deadline and least completed hours.
    2. and assign them to generated intervals 
    3. the remaining intervals can be used for everyday activity with no time constraints and would also give time to breadth
 
Cumulative schedule ?
What about very long projects 
With multiple milestone ? Schedule maker not project management 
Should keep a track of how much work is remaining in every project.  Yep added completed time
Us pomodoro (done, 2 hours for project work)
How should the output be ?
How to integrate it with Google calendar ?






Weekly schedule 
