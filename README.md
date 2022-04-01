# Riemman Sum Calculator

## A mathematical tool designed to compute left, midpoint, right riemman sums.

The following is addressed in this README:
- Limitations
- What will this application do?
- Who will use it?
- Why is this project of interest to you?
- Phase 4: Task 2
- Phase 4: Task 3
- Moving Forward

### 0. Limitations

Currently, the Riemman Sum Calculator can only compute functions of the type
- Fundamental trigonometric operations (sin, cos, tan)
- log and ln(x)
- linear functions 

These functions are also limited to having just their vertical
scaling factor (a in ax, acos(x), alog(x), etc.) and no other function constants. The vertical scaling factor must also be an integer.

**Side note:** for very large values of "n" (100+), precision errors can arise due to floating point imprecision



### 1. What will this application do?

- The Riemman Sum calculator will serve as a desktop application designed to estimate integrals by using Riemman Sums of the left, midpoint, or right variety. 
- Given a relatively simple function (basic polynomial, trigonometric, exponential functions and more) and a partition width, this calculator will be able approximate integrals and provide a visualization for your Riemman sum.
- Additional feature(s) include:
    - History of your previous Riemman Sum computations

### 2. Who will use it?

- At the moment, I'm doing an Integral Calculus course, so this tool will be very beneficial for me to both practice Riemman Sums, and also checking my work using them. It will also serve as a visual aid as it will shorten the time to see all three Left, Midpoint, and Right Riemman Sums at once, and then compare the differences between them.
- Other Integral Calculus students who are just starting to learn about integrals can also leverage this tool to understand how Riemman Sums work, which ones are more accurate, and ultimately what an integral really is.

### 3. Why is this project of interest to you?

- I enjoy math, so I wanted to make a project that pertains to math and this seemed to be a good option. While an integral or derivative calculator would be cool to make, it would be on orders of magnitude more
  complex than this, so I decided to settle with a Riemman Sum Calculator that will still have some challenges such as parsing functions in infix notation (and eventually reverse polish notation) and visualizing the Riemman sums. 
- In addition, this will serve to be a handy tool to check my work when doing Riemman sums, especially when the "n" value is large (no one wants to manually compute 36 rectangles)

### 4. User Stories

- As a user, I want to be able to compute left, right, midpoint sums.
- As a user, I want to add a Riemman Sum computation to a history of previous computations and view them.
- As a user, I want to remove the latest Riemman Sum computation from a history of previous computations.
- As a user, I want to be able to input basic fundamental trigonometric, logarithmic, or linear function to compute my Riemman sum.
- As a user, I want to be able to adjust the n value used in the most recent Riemman Sum calculation without having to input the function in again and then compare the new and old results.
- As a user, I want to save my current Riemman Sum computation and computation history to file.
- As a user, I want to load my current Riemman Sum computation and computation history from file.

### 5. Phase 4: Task 2
A representative example of how events related to the adding and removing of Xs (Computations) to Y (RiemmanSum) are logged
in the application.
```
Thu Mar 31 20:09:32 PDT 2022
Added Computation #1 of name: 5sin(x) and type: left sum to Riemman Sum Computation history.

Thu Mar 31 20:09:39 PDT 2022
Added Computation #2 of name: 2x and type: midpoint sum to Riemman Sum Computation history.

Thu Mar 31 20:09:45 PDT 2022
Added Computation #3 of name: -3log(x) and type: midpoint sum to Riemman Sum Computation history.

Thu Mar 31 20:09:55 PDT 2022
Added Computation #4 of name: -90ln(x) and type: right sum to Riemman Sum Computation history.

Thu Mar 31 20:09:57 PDT 2022
Removed Computation #4 of name: -90ln(x) and type: right sum to Riemman Sum Computation history.

Thu Mar 31 20:09:57 PDT 2022
Removed Computation #3 of name: -3log(x) and type: midpoint sum to Riemman Sum Computation history.

Thu Mar 31 20:09:58 PDT 2022
Removed Computation #2 of name: 2x and type: midpoint sum to Riemman Sum Computation history.

Thu Mar 31 20:10:09 PDT 2022
Added Computation #2 of name: 82tan(x) and type: left sum to Riemman Sum Computation history.

Thu Mar 31 20:10:18 PDT 2022
Added Computation #3 of name: 3cos(x) and type: midpoint sum to Riemman Sum Computation history.

Thu Mar 31 20:10:26 PDT 2022
Added Computation #4 of name: 7x and type: right sum to Riemman Sum Computation history.

Thu Mar 31 20:10:28 PDT 2022
Removed Computation #4 of name: 7x and type: right sum to Riemman Sum Computation history.

Thu Mar 31 20:10:28 PDT 2022
Removed Computation #3 of name: 3cos(x) and type: midpoint sum to Riemman Sum Computation history.

Thu Mar 31 20:10:28 PDT 2022
Removed Computation #2 of name: 82tan(x) and type: left sum to Riemman Sum Computation history.

Thu Mar 31 20:10:29 PDT 2022
Removed Computation #1 of name: 5sin(x) and type: left sum to Riemman Sum Computation history.
```

### 6. Phase 4: Task 3


**Design Reflection:** Overall, I'm quite satisfied with the design of my application, however, it still can be improved substantially.
I think my program's design could be revamped to use design patterns, abstract classes, and
a more robust approach to building classes with utilities such as exceptions. These are some things I would refactor in
my current application to be better designed:

- Apply the Observer pattern to the DataTab and RiemmanSum classes. 
  - Turn RiemmanSum into an Observable and DataTab into an Observer. Then, once a new computation is added or removed, RiemmanSum will notify and update DataTab of these changes.
  - This will allow for a cleaner implementation as there's currently a non-favourable amount of coupling between the ComputingTab and DataTab in order for DataTab to always be aware of when the current RiemmanSum object is updated. 
  - It will also make room for scalability in feature complexity within the GUI (like a graphical visualizer) as those features may require information on the current RiemmanSum's status.
  
  <br />
- Refactor the MathFunction class' methods to be more robust. 
  - Utilize exceptions like ArithmeticExceptions for when the MathFunction encounters values in its domain that produce undefined values.
  - Utilize something along the lines of a WrongFunctionType exception to handle and avoid the cases where it tries to parse the wrong type of function and crashes (i.e., parsing a "linear function" when in reality it is a trigonometric function.)
  - Making this class more robust will enable the user to be notified of their mistakes for fixing, instead of the program having to crash.
  
  <br />
- Refactor MathFunction into an abstract class, and then introduce new subclasses such as LinearFunctions, LogarithmicFunctions, TrigonometricFunctions, etc.
    - Make MathFunction an abstract class that houses similar behavior among all the different types of functions, avoiding code duplication by making utilities such as the vertical coefficient parser, computing function at x, function type and name getters, etc. contained in the superclass.
    - Split MathFunction into several subclasses like LinearFunction that extend MathFunction so their contents only contains core parsing functionality that is focused to that specific function type.
    - These changes fix the issue of bloating one class with parsing functionality for every supported function type, and makes for more readable classes.

### 7. Moving Forward
There's still some things I would like to add to this application, but were too complex for me to do so initially.

One is making a proper function parser that stores the important symbols of
a function in a stack data structure when a function is inputted in RPN (reverse polish notation). This will enable for higher levels of support on the ranges of functions possible
including additional terms, horizontal scaling coefficients, and more.

Another thing I really want to implement is a graphical visualizer that plots the
function over the selected domain, and then draws left, right, or midpoint rectangles over the function's graph. A visualizer is key to make a Riemman Sum calculator
illustrative of how accurate your approximated integral really is.


