# OOD-MVC-TaskManagement
Task management application using MVC framework allowing users to create and organize to-do lists.

Apply Model-View-Controller Pattern, and we will illustrate how our classes meet the separate module’s concerns. 

- Model:
    - The Model part consists of the ToDoArgData class, the Todo class, the SortingEnum class, and the PriorityEnum class.
    
    - Todo class
        - The most basic unit to store data. A Todo object represents one task the user added and stored in this App. It contains properties like id, text description, due date, completion status, priority and category of a todo object.
    - TodoArgData class
        - Stores data that parsed from the command line. 
        - The constructer TodoArgData(String invalidArg) will be called when the argument parser finds the command line data invalid during parsing. In this circumstance, the TodoArgData object will store the error message for the view part to display it. 
        - If no error found in the parsing process, the TodoArgData(String csvPath, ...) constructer is used to store all information got from the command line. The dependency of commands and their value will also be stored and checked in this class. 
        - The isValid() method lets the controller know if the user input is valid and helps the controller determine the working flow. The displayDetailError() method will display the error message in detail when called by the controller part. 
    - PriorityEnum class
        - An Enum class that includes the priorities of todos in this app. It is designed for preventing the user enter a priority that not allowed in this app. It also helps the sorting function in the view part when the user wants the todos to be listed according to their priorities.
    - SortingEnum class
        - An Enum class that stores possible properties when the user wants to sort the todo list by. It could be retrieved and used by the view part when display the list. Also, it will help the argument parser to check the validation of user inputs.

- View:
    - TodoManager class
        - The purpose of View is representing the visualization of the data, and we believe the TodoManager class reaches this aim. One of the major functionalities of TodoManager is displaying the to-do tasks based on the sorting or/and filter requirements, and we design the displayTodoByRequirement method in TodoManager class to make the to-do list visualized. In order to achieve the goal, we take in sortType(Enum) and other filter requirements as parameters in displayTodoByRequirement methods.
        - Before we go deeper with the code flow in this method, we check whether clients have any filter requirement or not. If they don’t, we call the displayTodoBySorting (a helper function in TodoManager class) directly and let it sort all to-do lists by required sorting type. And the displayTodoBySorting applies the “Factory pattern” to generate the specific comparator and pass it into the Collection.sort().
        - Otherwise, if the user has a specific filter requirement, we create an empty List first and add the to-do task which meets client’s filter requirement into this new List. Once we fill the List with to-do tasks, we continue to  sort the List in a specific sorting type that the user requires. 
        - These methods I explained above do perform the role of visualizing the todo list, so it is safe to conclude that the major functionality of TodoManager class reaches the purpose of View. For future improvement, it will be better to keep the single responsibility for one class and encapsulate all related functionality into one class, so that the module division could be more clear and each class can be more independent. 

- Controller:
    - The controller part consists of the Main class and the TodoArgParser class.
    - Main
        - A compact class that is only used to controls the working flow of this app. It supports communication between the view part and the model part and decide what to do.
    - TodoArgParser
        - Acts like a command-line parser. The TodoArgData parse(String[] args) method is a static method that will be triggered by the Main class. It parses possible operation information of this to-do App and produce a TodoArgData object storing the information.
        - The parseTodoInformation(String[] args...) method will be called when the parse() method finds the user is going to create a new todo object. The parseTodoInformation() method will parse and check the attributes the user wanted the new todo item has and store them in a list. This method is able to be extended if we want this todo App supports creating several new todos in the future.
        - The isCommand(String arg) and the isTodoProperties(String arg) methods are helper functions to check whether the user input is a valid command.
        - The displayInstruction() will be called by the Main class to display the instruction to use this app.
