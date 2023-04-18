package API.Table;

/**
 * @author Max Day
 * Created At: 2023/04/18
 */

public class Task {

    private int taskNumber;
    private String taskName;
    private String taskDescription;
    private String devDetails;
    private int taskDuration; // TODO might make this a float or a time thing just because its asking for the correct way to store it.
    private String taskID;
    private String taskStatusStr;

    private enum taskStatus { // I love this
        TODO("To Do"),
        DOING("Doing"),
        DONE("Done");
        private String label;

        taskStatus(String label) {
            this.label = label;
        }

        public String toString() {
            return label;
        }
    }

    public Task(String tName, int tNumber, String tDescription, String dDetails, int tHours, taskStatus status) {

        taskName = tName;
        taskNumber = tNumber;
        taskDescription = tDescription;
        devDetails = dDetails;
        taskDuration = tHours;
        taskStatusStr = status.toString();//TODO test this because idk if it will work but it should
    }

    public int getTaskDuration() {
        return taskDuration;
    }

    public int getTaskNumber() {
        return taskNumber;
    }

    public String getDevDetails() {
        return devDetails;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getTaskID() {
        return taskName.substring(0, 2).toUpperCase() + ":" + taskNumber + ":" + devDetails.substring(devDetails.length() - 3).toUpperCase(); //TODO maybe use getters for this idk
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskStatusStr() {
        return taskStatusStr;
    }



}
