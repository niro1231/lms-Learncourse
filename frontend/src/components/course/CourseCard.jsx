import { useNavigate } from "react-router-dom";
import "../course/CourseCard.css";

function CourseCard({course,instructorView=false,onEdit,onDelete}){
    const navigate=useNavigate();

    return(
        <div
            className="card course-card h-100 shadow-sm"
            onClick={()=>navigate(`/courses/${course.id}`)}
            style={{cursor:"pointer"}}
        >
            <img
                src={
                    course.thumbnailUrl
                    ?
                    `http://localhost:8080/${course.thumbnailUrl}`
                    :
                    "https://picsum.photos/400/250"
                }
                className="card-img-top"
                alt={course.title}
                style={{
                    height:"140px",
                    objectFit:"cover"
                }}
            />

            <div className="card-body p-3">
                <h6 className="card-title fw-bold">
                    {course.title}
                </h6>

                <p className="text-muted small mb-2">
                    {course.description}
                </p>

                <div className="d-flex align-items-center justify-content-between">
                    <div className="d-flex gap-1">
                        <span className="badge bg-light text-dark border">
                            👤 {course.instructorName}
                        </span>

                        <span className="badge bg-light text-dark border">
                            ⭐ {course.averageRating}
                        </span>
                    </div>

                    {
                        instructorView && (
                            <div className="d-flex gap-1">

                                <button
                                    className="btn btn-sm btn-primary"
                                    onClick={(e)=>{
                                        e.stopPropagation();
                                        onEdit(course.id);
                                    }}
                                >
                                    Edit
                                </button>

                                <button
                                    className="btn btn-sm btn-danger"
                                    onClick={(e)=>{
                                        e.stopPropagation();
                                        onDelete(course.id);
                                    }}
                                >
                                    Delete
                                </button>

                            </div>
                        )
                    }
                </div>
            </div>
        </div>
    );
}

export default CourseCard;