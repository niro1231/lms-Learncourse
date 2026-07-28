import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {
    getInstructorById,
    getCoursesByInstructor
} from "../../service/instructorService";
import CourseCard from "../../components/course/CourseCard";
import "../profile/Profile.css";
function InstructorCourses() {
    const { id } = useParams();
    const [dashboard, setDashboard] = useState(null);
    const [courses, setCourses] = useState([]);
    useEffect(() => {
        loadData();
    }, [id]);
    const loadData = async () => {
        try {
            const profileResponse = await getInstructorById(id);
            const coursesResponse = await getCoursesByInstructor(id);
            setDashboard(profileResponse.data);
            setCourses(coursesResponse.data);
        } catch(error) {
            console.log(error);
        }
    };
    if(!dashboard){
        return (
            <h3 className="text-center mt-5">
                Loading...
            </h3>
        );
    }

    return (
        <div className="container py-5">
            {/* Instructor Profile */}
            <div className="profile-card shadow">
                <div className="profile-left">
                    <img
                        src={
                            dashboard.profileImageUrl
                            ?
                            `http://localhost:8080/${dashboard.profileImageUrl}`
                            :
                            "/default-user.png"
                        }

                        className="profile-image"
                        alt="profile"
                    />
                </div>
                <div className="profile-right">
                    <h2>
                        {dashboard.instructorName}
                    </h2>
                    <span className="badge bg-primary">
                        INSTRUCTOR
                    </span>
                    <div className="profile-stats mt-4">
                        <div className="stat-box">
                            <h4>
                                {dashboard.totalCourses}
                            </h4>
                            <span>
                                Courses
                            </span>
                        </div>
                        <div className="stat-box">
                            <h4>
                                {dashboard.totalLessons}
                            </h4>
                            <span>
                                Lessons
                            </span>
                        </div>
                        <div className="stat-box">
                            <h4>
                                ⭐ {dashboard.averageRating}
                            </h4>
                            <span>
                                Rating
                            </span>
                        </div>
                    </div>
                </div>
            </div>
            {/* Courses Section */}
            <div className="my-course-card mt-5">
                <div className="course-header">
                    <h3>
                        Courses by {dashboard.instructorName}
                    </h3>
                    <div className="course-line"></div>
                </div>
                <div className="row g-4">
                    {
                        courses.map(course => (
                            <div
                                className="col-md-4"
                                key={course.id}
                            >
                                <CourseCard
                                    course={course}
                                />
                            </div>
                        ))
                    }
                </div>
            </div>
        </div>
    );
}
export default InstructorCourses;