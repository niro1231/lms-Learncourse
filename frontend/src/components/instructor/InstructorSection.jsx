import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getAllInstructors } from "../../service/instructorService";
import "./InstructorSection.css";

function InstructorSection() {

    const navigate = useNavigate();

    const [instructors, setInstructors] = useState([]);

    useEffect(() => {
        loadInstructors();
    }, []);

    const loadInstructors = async () => {
        try {
            const response = await getAllInstructors();
            setInstructors(response.data);
        } catch (error) {
            console.log(error);
        }
    };

    return (
        <section className="instructor-banner mt-5">

            <div className="banner-left">

                <h1>
                    Learn from the best instructors
                </h1>

                <p>
                    Improve your skills with industry experts,
                    practical projects and high quality courses.
                </p>

                <button onClick={() => navigate("/instructors")}>
                    Explore instructors →
                </button>

            </div>

            <div className="banner-right">

                {instructors
                    .filter((ins) => ins.averageRating >= 3)
                    .sort((a, b) => b.averageRating - a.averageRating)
                    .slice(0, 3)
                    .map((ins) => (

                        <div
                            className="teacher-card"
                            key={ins.id}
                            onClick={() => navigate(`/instructors/${ins.id}`)}
                        >

                            <img
                                src={
                                    ins.profileImageUrl
                                        ? `http://localhost:8080/${ins.profileImageUrl}`
                                        : "https://picsum.photos/300/180"
                                }
                                alt={ins.name}
                            />

                            <div className="teacher-content">

                                <h4>
                                    {ins.name}
                                </h4>

                                <span className="badge bg-light text-dark border">
                                    📚 {ins.courseCount} Courses
                                </span>

                                <span className="badge bg-light text-dark border mt-2">
                                    ⭐ {ins.averageRating.toFixed(1)}
                                </span>

                            </div>

                        </div>

                    ))}

            </div>

        </section>
    );
}

export default InstructorSection;