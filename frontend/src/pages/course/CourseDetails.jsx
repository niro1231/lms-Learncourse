import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getCourseById } from "../../service/courseService";
import { getLessonsByCourse, deleteLesson as deleteLessonApi } from "../../service/lessonService";
import EditLessonModal from "../../components/common/modals/EditLessonModal";
import DeleteLessonModal from "../../components/common/modals/DeleteLessonModal";
import AddLessonModal from "../../components/common/modals/AddLessonModal";
import GiveFeedbackModal from "../../components/common/modals/GiveFeedbackModal";

import "./CourseDetails.css";

function CourseDetails() {
    const { id } = useParams();
    const [course, setCourse] = useState(null);
    const [lessons, setLessons] = useState([]);
    const [editLesson, setEditLesson] = useState(null);
    const [deleteLesson, setDeleteLesson] = useState(null);
    const [addLesson, setAddLesson] = useState(false);
    const [showFeedback, setShowFeedback] = useState(false);

    const loggedInEmail = localStorage.getItem("email");

    useEffect(() => {
        loadCourse();
        loadLessons();
    }, []);

    const loadCourse = async () => {
        const response = await getCourseById(id);
        setCourse(response.data);
    };

    const loadLessons = async () => {
        const response = await getLessonsByCourse(id);
        setLessons(response.data);
    };

    const handleDeleteLesson = async () => {
        await deleteLessonApi(id, deleteLesson.id);
        loadLessons();
        setDeleteLesson(null);
    };

    if (!course) {
        return <h3 className="text-center mt-5">Loading...</h3>;
    }

    const isOwner = course.instructorEmail === loggedInEmail;

    return (
        <div className="container py-4">

            <div className="course-details-header">
                <div className="row align-items-center">

                    <div className="col-lg-4">
                        <div className="course-image-box">
                            <img
                                src={`http://localhost:8080/${course.thumbnailUrl}`}
                                alt={course.title}
                                className="course-image"
                            />
                        </div>
                    </div>

                    <div className="col-lg-8">
                        <h2 className="course-title">{course.title}</h2>

                        <p className="course-description">
                            {course.description}
                        </p>

                        <div className="course-meta">
                            <span className="badge bg-light text-dark border">
                                👤 {course.instructorName}
                            </span>

                            <span className="badge bg-light text-dark border">
                                ⭐ {course.averageRating}
                            </span>

                            <span className="badge bg-primary">
                                {course.category}
                            </span>
                        </div>
                    </div>

                </div>
                <div className="header-buttons">

                    <button
                        className="feedback-btn"
                        onClick={() => setShowFeedback(true)}
                    >
                        ⭐ Give Feedback
                    </button>

                    {isOwner && (
                        <button
                            className="add-lesson-btn"
                            onClick={() => setAddLesson(true)}
                        >
                            + Add Lesson
                        </button>
                    )}

                </div>
            </div>

            <div className="lesson-section">

                <h3 className="lesson-heading">
                    Course Lessons
                </h3>

                <div className="lesson-container">

                    {lessons.map((lesson, index) => (

                        <div className="lesson-card" key={lesson.id}>

                            <div className="lesson-number">
                                {String(index + 1).padStart(2, "0")}
                            </div>

                            <div className="lesson-info">
                                <h5>{lesson.title}</h5>
                            </div>

                            <div className="d-flex gap-2">

                                <a
                                    href={`http://localhost:8080/${lesson.pdfUrl}`}
                                    target="_blank"
                                    rel="noreferrer"
                                    className="lesson-button"
                                >
                                    View
                                </a>

                                {isOwner && (
                                    <>
                                        <button
                                            className="lesson-button"
                                            onClick={() => setEditLesson(lesson)}
                                        >
                                            Edit
                                        </button>

                                        <button
                                            className="lesson-button"
                                            onClick={() => setDeleteLesson(lesson)}
                                        >
                                            Delete
                                        </button>
                                    </>
                                )}

                            </div>

                        </div>

                    ))}

                </div>

            </div>

            {editLesson && (
                <EditLessonModal
                    courseId={id}
                    lessonId={editLesson.id}
                    onClose={() => setEditLesson(null)}
                    onUpdated={loadLessons}
                />
            )}

            {deleteLesson && (
                <DeleteLessonModal
                    onClose={() => setDeleteLesson(null)}
                    onConfirm={handleDeleteLesson}
                />
            )}
            {
                addLesson && (

                    <AddLessonModal
                        courseId={id}
                        onClose={() => setAddLesson(false)}
                        onAdded={loadLessons}
                    />

                )
            }
            {
                showFeedback && (
                    <GiveFeedbackModal
                        courseId={id}
                        onClose={() => setShowFeedback(false)}
                    />
                )
            }

        </div>
    );
}

export default CourseDetails;