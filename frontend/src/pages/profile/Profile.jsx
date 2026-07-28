import { useEffect, useState } from "react";
import { getInstructorDashboard } from "../../service/instructorService";
import { getMyCourses, deleteCourse } from "../../service/courseService";
import CourseCard from "../../components/course/CourseCard";
import EditCourseModal from "../../components/common/modals/EditCourseModal";
import DeleteConfirmModal from "../../components/common/modals/DeleteConfirmModal";
import AddCourseModal from "../../components/common/modals/AddCourseModal";
import "./Profile.css";

function Profile() {
    const [dashboard, setDashboard] = useState(null);
    const [courses, setCourses] = useState([]);
    const [editCourseId, setEditCourseId] = useState(null);
    const [deleteCourseId, setDeleteCourseId] = useState(null);
    const [addCourse, setAddCourse] = useState(false);

    const user = {
        name: localStorage.getItem("name"),
        email: localStorage.getItem("email"),
        role: localStorage.getItem("role"),
        imageUrl: localStorage.getItem("imageUrl")
    };

    useEffect(() => {
        loadData();
    }, []);

    const loadData = async () => {
        const dashboardResponse = await getInstructorDashboard();
        const courseResponse = await getMyCourses();
        setDashboard(dashboardResponse.data);
        setCourses(courseResponse.data);
    };

    const handleDelete = async () => {
        try {
            await deleteCourse(deleteCourseId);
            setDeleteCourseId(null);
            loadData();
        } catch (error) {
            console.log(error);
            alert("Delete failed");
        }
    };

    if (!dashboard) {
        return <h3 className="text-center mt-5">Loading...</h3>;
    }

    return (
        <div className="container py-5">
            <div className="profile-card shadow">
                <div className="profile-left">
                    <img
                        src={
                            dashboard.profileImageUrl
                                ? `http://localhost:8080/${dashboard.profileImageUrl}`
                                : "/default-user.png"
                        }
                        className="profile-image"
                        alt="profile"
                    />
                </div>
                <div className="profile-right">
                    <h2>{user.name}</h2>
                    <span className="badge bg-primary">{user.role}</span>
                    <p className="mt-3">📧 {user.email}</p>
                    <div className="profile-stats">
                        <div className="stat-box">
                            <h4>{dashboard.totalCourses}</h4>
                            <span>Courses</span>
                        </div>
                        <div className="stat-box">
                            <h4>{dashboard.totalLessons}</h4>
                            <span>Lessons</span>
                        </div>
                        <div className="stat-box">
                            <h4>⭐ {dashboard.averageRating}</h4>
                            <span>Rating</span>
                        </div>
                        {/* Add this before closing profile-card */}
                        <button
                            className="add-course-btn"
                            onClick={() => setAddCourse(true)}
                        >
                            + Add Course
                        </button>
                    </div>
                </div>
            </div>
            <div className="my-course-card mt-5">
                <div className="course-header">
                    <h3>
                        My Courses
                    </h3>
                    <div className="course-line"></div>
                </div>
                <div className="row g-4">
                    {
                        courses.map(course => (
                            <div className="col-md-4" key={course.id}>
                                <CourseCard
                                    course={course}
                                    instructorView={true}
                                    onEdit={(id) => setEditCourseId(id)}
                                    onDelete={(id) => setDeleteCourseId(id)}
                                />
                            </div>
                        ))
                    }
                </div>
            </div>
            {
                editCourseId && (
                    <EditCourseModal
                        courseId={editCourseId}
                        onClose={() => setEditCourseId(null)}
                        onUpdated={() => {
                            loadData();
                            setEditCourseId(null);
                        }}
                    />
                )
            }
            {
                deleteCourseId && (
                    <DeleteConfirmModal
                        onClose={() => setDeleteCourseId(null)}
                        onConfirm={handleDelete}
                    />
                )
            }
            {
                addCourse && (

                    <AddCourseModal
                        onClose={() => setAddCourse(false)}
                        onAdded={loadData}
                    />

                )
            }
        </div>
    );
}
export default Profile;