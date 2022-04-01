# Riemman Sum Calculator

## A mathematical tool designed to compute left, midpoint, right riemman sums.

The following is addresed in this README:
- Limitations
- What will this application do?
- Who will use it?
- Why is this project of interest to you?

### 0. Limitations

Currently the Riemman Sum Calculator can only compute functions of the type
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

- At the moment, I'm doing an Integral Calculus course, so this tool will be very benefical for me to both practice Riemman Sums, and also checking my work using them. It will also serve as a visual aid as it will shorten the time to see all three Left, Midpoint, and Right Riemman Sums at once, and then compare the differences between them.
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

### 5. "Phase 4: Task 2


### 6. "Phase 4: Task 3

