package com.cokerj.collegecompanion.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes", foreignKeys = {@ForeignKey(entity = Course.class,
        parentColumns = "courseId",
        childColumns = "courseId",
        onDelete = ForeignKey.CASCADE)})
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int noteId;
    private String title;
    private String content;
    private int courseId;

    public Note(String title, String content, int courseId) {
        this.title = title;
        this.content = content;
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return title;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
