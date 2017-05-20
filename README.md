[![Build Status](https://travis-ci.org/aztecrex/learn-inventory.svg?branch=master)](https://travis-ci.org/aztecrex/learn-inventory)

This is an example implementation of the CJ Learn Codeschool project,
https://github.com/cjlearn/learn-inventory.  If you are a CJ Learn
student and were assigned the inventory project, use this project
for inspiration if you are really stuck but use it sparingly because;

0. Part of learning is struggling to understand. That way you internalize
it in your own way. If you look at someone else's code and say "I get it"
then just use that code, it is likely that your understanding is
superficial
0. When you go to pair with a CJ engineer, your understanding of your own
code will be expected. The questions you get about it may be subtle and
you will want to know exactly why you made each line of your solution.

If, instead, the inventory problem is being worked out in-class and your
assignment is a different problem, use this project as practice for reading
other people's tests and source code. For each test, make sure you can
describe what it proves. If you can't, bring it up in your next session as
a discussion item.

# Inventory

[_CJ Powered_](https://engineering.cj.com)

Sample application for learning object-oriented programming and test-driven
design.

## The Problem

We are Ace Dry Goods, an Internet store selling packaged foods that store
well and taste great. Unfortunately, we keep running out of the stuff we
sell.

We need to build an inventory management system right away. Some company
managers got together and fleshed out the inventory rules. Our ordering
system developers came up with a Java interface, `InventoryManager` that
will give them all they need to place orders each day as long as we
implement it according to the rules.  They also gave us an interface to
the database they've been using to manually track items and levels,
`InventoryDatabase`.

Some of the rules involve information from the marketing department,
including information about items on sale and seasons. Their developers
put together a Java interface `MarketingInfo` we will use to integrate
with them.

Before you got here, the ordering department and marketing department
developers put together a skeleton of the application, a classs called
`AceInventoryManager`. Good thing you're here, though; they need to get
back to their own development projects as quickly as possible.

Use the other departments' offerings as much as you need. If you can show
where any of those interfaces are insufficient, don't hesitate to ask for
interface changes. They will review any requests and enhance the interfaces
if necessary.


## How to Proceed

0. Clone this repo and get your development environment working. You will
know it's all working when you can run the existing unit test suite.
    - you'll need a Java 8 (aka JDK 1.8) SDK
    - you'll probably want an IDE, http://eclipse.org has a good one, just the plain Java IDE is all you need
    - you should have Maven, too: http://maven.apache.org .
0. _Bonus: have travis-ci.org automatically build and test your clone_
    - sign up to travis-ci.org if you haven't already
    - activate your repository on the Travis-CI website
    - replace the badge in this readme
0. Start working on the rules as if they are new features, one-by-one.
0. You may be advised of new rules or changes to the rules as you progress.

## Tips for Success

- Focus on one feature at a time. Don't worry about any feature except for
the one you are working on
- Practice red-green-refactor. Write a failing test, get it to pass, commit
the code, refactor if you like, commit the code. Never refactor when you are
not passing
- Don't forget to test your edge cases. Each feature may require several unit
tests
- Only write code that is necessary to pass tests. That way you ensure your
tests cover a high proportion of your code
- Don't let requirements changes throw you. Keep focused on the current
feature, no matter how the requirements change. Keep working on features in
priority order even if that priority changes
- If requirements are incomplete or in conflict, your teacher will act as
the product owner and help clarify

## Technical Notes and Contracts

- you deliver software through progressive enhancements to the `AceInventoryManager`
class's `#getOrders(LocalDate)` method.
- Note that `AceInventoryManager` implements
the `InventoryManager` interface. You can't change that interface without agreement with the
ordering department.
- you are free to implement the Item interface as needed for your enhancements. The
ordering department will adjust their own database implementation to ensure your
Item instance data is returned when needed.
- Item equality is outside your scope. The ordering department defines item equality
via the standard Java `#equals(Object)` method. You must assume that two Item instances
are equal if and only if they are the exact same instance. In other words, two items,
created by two different construction calls are, by definition, not equal even if they
contain the same instance data
 

## The Business Rules

Here is the company's best shot at the inventory rules
you should implement. As mentioned, they are sure to discover other rules or
even change their minds as you give them more and more working code.

These are in priority order. Unless it is blocked by something else, you
should always work on the highest priority item first.

0. For each item we stock, order enough to bring the quantity on hand to its
specified inventory level
0. When an item goes on sale, keep an additional 20 units on hand
0. For seasonal items, keep double the normal inventory on hand during the
high-demand season. Each seasonal item has one high-demand season.
0. When the required inventory level is above normal due to a sale, season,
or other reason, use the highest calculated level. In other words, modifiers
do not stack
0. Some items can only be ordered on the first day of the month, do not issue
any orders for those items except on the first of the month
0. Some items can only be ordered in packages containing multiple units. we
can stock more than the normal inventory level for those items if necessary
